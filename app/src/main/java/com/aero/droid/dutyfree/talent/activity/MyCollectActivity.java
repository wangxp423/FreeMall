package com.aero.droid.dutyfree.talent.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.FlightInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.OrderInfo;
import com.aero.droid.dutyfree.talent.presenter.MyCollectPresenter;
import com.aero.droid.dutyfree.talent.presenter.MyOrderPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.MyCollectPresenterImpl;
import com.aero.droid.dutyfree.talent.presenter.impl.MyOrderPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.StringUtils;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.MyCollectView;
import com.aero.droid.dutyfree.talent.view.MyOrderView;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenu;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenuCreator;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenuItem;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenuListView;
import com.bumptech.glide.Glide;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableGridView;

import org.bouncycastle.crypto.engines.CAST5Engine;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的收藏页面
 */
public class MyCollectActivity extends BaseFragmentActivity implements MyCollectView,View.OnClickListener {
    //    @Bind(R.id.my_collect_lv)
//    SwipeMenuListView myCollectLv;
    @Bind(R.id.my_collect_gv)
    PullableGridView myCollectGv;
    @Bind(R.id.my_collect_layout)
    RGPullRefreshLayout myCollectLayout;
    @Bind(R.id.my_collect_no_data)
    ImageView noData;

    /***********************************/
    private MyCollectPresenter presenter;
    private List<GoodsInfo> goodsInfoList = new ArrayList<>();
    private JavaBeanBaseAdapter<GoodsInfo> adapter;
    private int deletePosition;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_my_collect;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return myCollectLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        initTitle(getResources().getString(R.string.my_collect));
        noData.setOnClickListener(this);
        adapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_collection,goodsInfoList) {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void bindView(final int position, ViewHolder holder, final GoodsInfo info) {
                //商品图片
                ImageView goodImg = holder.getView(R.id.item_collect_img);
                int mWidth = mScreenWidth - UiUtil.dip2px(mContext, 50);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(mWidth / 3, mWidth / 3);
                goodImg.setLayoutParams(params1);
                Glide.with(mContext).load(info.getGoodsImg()).centerCrop().into(goodImg);
                goodImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        intent.setClass(mContext, GoodsDetailActivity.class);
                        bundle.putString("goodId", info.getId());
                        bundle.putString("srcType", TextUtils.isEmpty(info.getSrcType())?"0":info.getSrcType());
                        bundle.putString("srcId", TextUtils.isEmpty(info.getSrcId())?"0":info.getSrcId());
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
                //商品name
                TextView goodName = holder.getView(R.id.item_collect_good_name);
                goodName.setText(info.getGoodsName());
                //商品 app价格
                TextView appPrice = holder.getView(R.id.item_collect_good_price);
                appPrice.setText(info.getPrice_app_dollar());
                //商品 机上价格
                TextView refPrice = holder.getView(R.id.item_collect_good_price1);
                refPrice.setText(info.getPrice_ref_dollar());
                //商品 收藏 标签
                ImageView lable = holder.getView(R.id.item_collect_lable);
                lable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletePosition = position;
                        presenter.clickDeleteCollect(TAG_LOG,info.getId());
                    }
                });
                //立即购买
                TextView buy = holder.getView(R.id.item_collect_buy);
                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<GoodsInfo> orderGoods = new ArrayList<GoodsInfo>();
                        orderGoods.add(info);
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("orderGoodsList", (ArrayList<? extends Parcelable>) orderGoods);
                        bundle.putInt("totalPrice", StringUtils.parseInt(info.getPrice_app_dollar()));
                        intent.setClass(mContext, OrderConfrimActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
            }
        };
        myCollectGv.setAdapter(adapter);


        /**
         * 以下代码 是最开始用 listView展示别表 滑动删除代码
         */
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//
//            @Override
//            public void create(SwipeMenu menu) {
//                // create "delete" item
//                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
//                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(104, 59, 164)));
//                // set item width
//                deleteItem.setWidth(UiUtil.dip2px(mContext, 90));
//                // set a icon
//                deleteItem.setIcon(R.mipmap.edit_clear);
//                // add to menu
//                menu.addMenuItem(deleteItem);
//            }
//        };
//        // set creator
//        myCollectLv.setMenuCreator(creator);
//
//        // step 2. listener item click event
//        myCollectLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                switch (index) {
//                    case 0:
//                        ToastUtil.showShortToast(mContext, "delete");
////                        presenter.clickDeleteCollect(TAG_LOG,"id");
//                        break;
//                }
//                return false;
//            }
//        });

        presenter = new MyCollectPresenterImpl(MyCollectActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getCollectList(TAG_LOG);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getCollectList(TAG_LOG);
                }
            });
        }
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void showCollectList(final List<GoodsInfo> infoList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false, null);
                goodsInfoList.addAll(infoList);
                adapter.notifyDataSetChanged();
                if (null != goodsInfoList && goodsInfoList.size()<1)
                    myCollectGv.setEmptyView(noData);
            }
        });

    }

    @Override
    public void deleteCollectSuccess(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(mContext,msg);
                if (null != goodsInfoList && goodsInfoList.size()>0)
                    goodsInfoList.remove(deletePosition);
                adapter.notifyDataSetChanged();
                if (goodsInfoList.size()<1)
                    myCollectGv.setEmptyView(noData);
            }
        });
    }

    @Override
    public void requestError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(mContext, msg);
            }
        });

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showEmpty(String msg,int imgId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false,null);
                myCollectGv.setEmptyView(noData);
            }
        });
    }

    @Override
    public void showException(String msg) {

    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getCollectList(TAG_LOG);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_collect_no_data:
                Bundle bundle = new Bundle();
                bundle.putInt("jumpType",0);
                readyGo(MainActivity.class,bundle);
                break;
        }
    }
}
