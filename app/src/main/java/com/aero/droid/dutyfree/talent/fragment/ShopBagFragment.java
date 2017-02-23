package com.aero.droid.dutyfree.talent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.GoodsDetailActivity;
import com.aero.droid.dutyfree.talent.activity.LoginActivity;
import com.aero.droid.dutyfree.talent.activity.MainActivity;
import com.aero.droid.dutyfree.talent.base.BaseFragment;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.presenter.impl.ShopBagPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.PopWindowUtil;
import com.aero.droid.dutyfree.talent.util.StringUtils;
import com.aero.droid.dutyfree.talent.util.TimeFormatUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.ShopBagView;
import com.aero.droid.dutyfree.talent.widgets.AhDiaLog;
import com.aero.droid.dutyfree.talent.widgets.RecommendGoodsLayout;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenu;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenuCreator;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenuItem;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenuListView;
import com.bumptech.glide.Glide;
import com.ybao.pullrefreshview.layout.PullRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/11/29
 * Desc : 主页 购物袋页面
 */
public class ShopBagFragment extends BaseFragment implements ShopBagView, View.OnClickListener ,AdapterView.OnItemClickListener{
    @Bind(R.id.title_left)
    ImageView titleLeft;
    @Bind(R.id.title_frame_left)
    FrameLayout titleFrameLeft;
    @Bind(R.id.title_content)
    TextView titleContent;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.shopbag_lv)
    SwipeMenuListView shopbagLv;
    @Bind(R.id.confrim_layout)
    RelativeLayout confrimLayout;
    @Bind(R.id.shopbag_pull_layout)
    PullRefreshLayout shopbagPullLayout;
    @Bind(R.id.shopbag_total_price)
    TextView shopbagTotalPrice;
    @Bind(R.id.shopbag_commit)
    TextView shopbagCommit;
    @Bind(R.id.shopbag_no_data)
    ImageView noData;
    @Bind(R.id.shopbag_recommend_layout)
    LinearLayout recommendLayout;

    /****************************/
    private Typeface typeFace;
    private List<GoodsInfo> shopBagList = new ArrayList<>();
    private List<GoodsInfo> selectList = new ArrayList<>();
    private JavaBeanBaseAdapter<GoodsInfo> adapter;
    private ShopBagPresenterImpl presenter;
    private int totalPrice;
    private int position;
    private MainActivity myActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (MainActivity) activity;
    }

    @Override
    protected void onFirstUserVisible() {
    }

    @Override
    protected void onUserVisible() {
        if (NetUtils.isNetworkConnected(getActivity())) {
//            if (TextUtils.isEmpty(UserUtil.getUserId(getActivity()))){
//                readyGo(LoginActivity.class);
//            }else {
                presenter.getShopBagData(TAG_LOG);
//            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getShopBagData(TAG_LOG);
                }
            });
        }
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected View getLoadingTargetView() {
        return shopbagPullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        shopbagLv.setOnItemClickListener(this);
        View listViewHeader = View.inflate(getActivity(), R.layout.view_brand_promise, null);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, UiUtil.dip2px(mContext, 55));
        listViewHeader.setLayoutParams(params);
        shopbagLv.addHeaderView(listViewHeader);
        typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/dengxianti.TTF");
        titleRight.setOnClickListener(this);
        noData.setOnClickListener(this);
        shopbagCommit.setOnClickListener(this);
        adapter = new JavaBeanBaseAdapter<GoodsInfo>(getActivity(), R.layout.item_shopbag, shopBagList) {
            @Override
            protected void bindView(final int position, ViewHolder holder, final GoodsInfo info) {
                ImageView itemGoodImg = holder.getView(R.id.iv_item_car_product);
                TextView itemGoodName = holder.getView(R.id.tv_shopcar_item_product);
                TextView itemGoodBrand = holder.getView(R.id.tv_shopcar_item_where);
                TextView itemGoodPrice = holder.getView(R.id.tv_shopcar_item_money);
                final ImageView itemAddGood = holder.getView(R.id.btn_shopcar_item_add);
                final ImageView itemRemoveGood = holder.getView(R.id.btn_shopcar_item_minus);
                TextView itemGoodNum = holder.getView(R.id.tv_shopcar_item_num);
                final int goodNum = Integer.parseInt(info.getQuantity());
                if (1 == goodNum) {
                    itemRemoveGood.setEnabled(false);
                    itemRemoveGood.setImageResource(R.mipmap.ic_buy_minus1);
                } else {
                    itemRemoveGood.setEnabled(true);
                    itemRemoveGood.setImageResource(R.drawable.shopbag_good_minus_selector);
                }
                itemGoodName.setText(info.getGoodsName());
                Glide.with(mContext).load(info.getGoodsImg()).into(itemGoodImg);
                itemGoodBrand.setTypeface(typeFace);
                itemGoodBrand.setText(mContext.getResources().getString(R.string.brand_from) + info.getMarkName());
                itemGoodPrice.setText("＄"
                        + TimeFormatUtil.formatDigist(info.getPrice_app_dollar()));
                itemGoodNum.setText(info.getQuantity());
                itemAddGood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopBagFragment.this.position = position;
                        getLoading();
                        presenter.clickAddGood(TAG_LOG, info.getId(), goodNum + 1);
                    }
                });
                itemRemoveGood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopBagFragment.this.position = position;
                        getLoading();
                        presenter.clickRemoveGood(TAG_LOG, info.getId(), goodNum - 1);
                    }
                });
            }
        };
        shopbagLv.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "收藏" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(104, 59, 164)));
                // set item width
                deleteItem.setWidth(UiUtil.dip2px(getActivity(), 90));
                // set a icon
                deleteItem.setIcon(R.mipmap.text_collect);
                // add to menu
                menu.addMenuItem(deleteItem);

                // create "删除" item
                SwipeMenuItem collectionItem = new SwipeMenuItem(getActivity());
                // set item background
                collectionItem.setBackground(new ColorDrawable(Color.rgb(255, 51, 52)));
                // set item width
                collectionItem.setWidth(UiUtil.dip2px(getActivity(), 90));
                // set a icon
                collectionItem.setIcon(R.mipmap.text_delete);
                // add to menu
                menu.addMenuItem(collectionItem);
            }
        };
        // set creator
        shopbagLv.setMenuCreator(creator);

        // step 2. listener item click event
        shopbagLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                GoodsInfo item = shopBagList.get(position);
                switch (index) {
                    case 1:
                        ShopBagFragment.this.position = position;
                        List<GoodsInfo> goodsInfos = new ArrayList<GoodsInfo>();
                        goodsInfos.add(shopBagList.get(position));
                        getLoading();
                        presenter.clickDeleteShopBag(TAG_LOG, goodsInfos);
                        break;
                    case 0:
                        getLoading();
                        GoodsInfo info = shopBagList.get(position);
                        presenter.clickAddCollect(TAG_LOG, info.getId());
                        break;
                }
                return false;
            }
        });


        presenter = new ShopBagPresenterImpl(getActivity(), this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fm_shopbag;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected void onEventBackgroundComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GoodsInfo info = shopBagList.get((int)id);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("goodId", info.getId());
        bundle.putString("srcType", "0");
        bundle.putString("srcId", "0");
        intent.setClass(mContext, GoodsDetailActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void showShopBagData(List<GoodsInfo> infos) {
        if (shopBagList.size() > 0)
            shopBagList.clear();
        if (selectList.size() > 0)
            selectList.clear();
        shopBagList.addAll(infos);
        selectList.addAll(infos);
        shopbagLv.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false, null);
                adapter.notifyDataSetChanged();

//        if (infos.size() > 0) {
//            presenter.showDeleteBtn();
//        } else {
//            presenter.hideDeleteBtn();
//        }
                setPrice();
                shopbagTotalPrice.setText("＄" + StringUtils.getSumMoney(totalPrice + ""));
                confrimLayout.setVisibility(View.VISIBLE);
                recommendLayout.removeAllViews();
            }
        });

    }

    @Override
    public void showTopGoods(final List<GoodsInfo> infos) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (recommendLayout.getChildCount() > 0)
                    recommendLayout.removeAllViews();
                RecommendGoodsLayout layout = new RecommendGoodsLayout(getActivity());
                layout.setTopGoods1(infos);
                recommendLayout.addView(layout);
            }
        });
    }

    @Override
    public void showDeleteBtn() {
        titleRight.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDeleteBtn() {
        titleRight.setVisibility(View.GONE);
    }

    @Override
    public void deleteShopBagSucc(String msg) {
        dismissLoading();
        shopBagList.remove(position);
        selectList.remove(position);
        setPrice();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                shopbagTotalPrice.setText("＄" + StringUtils.getSumMoney(totalPrice + ""));
            }
        });

        if (shopBagList.size() < 1) {
            showEmpty(null,0);
        }
    }


    @Override
    public void confirmShopBag() {

    }

    @Override
    public void addGoodsSucc(String msg) {
        dismissLoading();
        int num = Integer.parseInt(shopBagList.get(position).getQuantity()) + 1;
        shopBagList.get(position).setQuantity(String.valueOf(num));
        selectList.get(position).setQuantity(String.valueOf(num));
        presenter.setCurShopBagNum(shopBagList);
        setPrice();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                shopbagTotalPrice.setText("＄" + StringUtils.getSumMoney(totalPrice + ""));
            }
        });

    }


    @Override
    public void removeGoodsSucc(String msg) {
        dismissLoading();
        int num = Integer.parseInt(shopBagList.get(position).getQuantity()) - 1;
        shopBagList.get(position).setQuantity(String.valueOf(num));
        selectList.get(position).setQuantity(String.valueOf(num));
        presenter.setCurShopBagNum(shopBagList);
        setPrice();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                shopbagTotalPrice.setText("＄" + StringUtils.getSumMoney(totalPrice + ""));
            }
        });

    }

    @Override
    public void addCollectSucc(final String msg) {
        dismissLoading();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(getActivity(), msg);
            }
        });
    }


    @Override
    public void checkGoods(int position) {
        if (selectList.contains(shopBagList.get(position))) {
            selectList.remove(shopBagList.get(position));
        } else {
            selectList.add(shopBagList.get(position));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void noCheckGoods(int position) {
        //这个方法写重复了，只需要 checkGoods 就够了
    }

    @Override
    public void requestDataError(String msg) {
        toggleNetworkError(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getShopBagData(TAG_LOG);
            }
        });
    }

    @Override
    public void requestError(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissLoading();
                ToastUtil.showShortToast(getActivity(), msg);
            }
        });

    }


    @Override
    public void showNetError() {
        toggleNetworkError(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getShopBagData(TAG_LOG);
            }
        });
    }

    @Override
    public void showEmpty(String msg,int imgId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                confrimLayout.setVisibility(View.GONE);
                shopbagLv.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
                presenter.getTopGoods(TAG_LOG);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //页面隐藏
        } else {
            //页面显示
            if (NetUtils.isNetworkConnected(getActivity())) {
//                if (TextUtils.isEmpty(UserUtil.getUserId(getActivity()))){
//                    readyGo(LoginActivity.class);
//                }else {
                    presenter.getShopBagData(TAG_LOG);
//                }
            } else {
                toggleNetworkError(true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.getShopBagData(TAG_LOG);
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_right:
                PopWindowUtil.showLoadingPop(getActivity(), null);
//                showDialog();
                break;
            case R.id.shopbag_commit:
                presenter.clickConfirmShopBag(shopBagList,totalPrice);
                break;
            case R.id.shopbag_no_data:
                myActivity.switchRadioButton(0);
                break;
        }
    }

    /**
     * 设置价格条变动
     */
    private void setPrice() {
        int totalAppRmb = 0;
        int totalAppDolar = 0;
        int totalAirRmb = 0;
        int totalAirDolar = 0;
        int totalRefRmb = 0;
        int totalRefDolar = 0;
        if (shopBagList != null && shopBagList.size() > 0) {
            for (GoodsInfo shopCarItem : shopBagList) {
                int num = StringUtils.parseInt(shopCarItem.getQuantity());
                totalAirRmb += StringUtils.parseInt(shopCarItem
                        .getPrice_airport_rmb()) * num;
                totalAppRmb += StringUtils.parseInt(shopCarItem
                        .getPrice_app_rmb()) * num;
                totalAirDolar += StringUtils.parseInt(shopCarItem
                        .getPrice_airport_dollar()) * num;
                totalAppDolar += StringUtils.parseInt(shopCarItem
                        .getPrice_app_dollar()) * num;
                totalRefRmb += StringUtils.parseInt(shopCarItem
                        .getPrice_ref_rmb()) * num;
                totalRefDolar += StringUtils.parseInt(shopCarItem
                        .getPrice_ref_dollar()) * num;
            }
        }
//        shopbagPv.setPrice(totalAppDolar, totalAirDolar, totalRefDolar);
//        LogUtil.t(totalAppDolar + "    " + totalAirDolar + "   " + totalRefDolar);
//        shopbagPv.setBackgroundAnimation(totalRefDolar, totalAirDolar, totalAppDolar);
        totalPrice = totalAppDolar;
    }

    private AhDiaLog myDialog;

    private void showDialog() {
        myDialog = new AhDiaLog(getActivity(), getActivity().getResources().getString(R.string.dialog_title_delete_shopbag), getActivity().getResources().getString(R.string.sure_delete), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                presenter.clickDeleteShopBag(TAG_LOG, selectList);
                presenter.setCurShopBagNum(shopBagList);
            }
        });
        myDialog.show();
    }



}
