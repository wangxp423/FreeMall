package com.aero.droid.dutyfree.talent.fragment;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.GoodsDetailActivity;
import com.aero.droid.dutyfree.talent.activity.LoginActivity;
import com.aero.droid.dutyfree.talent.activity.MoreGoodsActivity;
import com.aero.droid.dutyfree.talent.activity.OrderConfrimActivity;
import com.aero.droid.dutyfree.talent.activity.SecondCategoryActivity;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.base.BaseFragment;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.SecondDiscover;
import com.aero.droid.dutyfree.talent.bean.SubFind;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.SharePreUtil;
import com.aero.droid.dutyfree.talent.util.StringUtils;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.glidetrans.GlideCircleTransform;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;
import com.aero.droid.dutyfree.talent.widgets.CustomImageView;
import com.aero.droid.dutyfree.talent.widgets.InnerViewPager;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;

/**
 * 二级分类包含的商品展示页面
 * Created by wangxp on 2015/11/5.
 */
public class SecondCategoryDetailFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.second_category_title_img)
    ImageView mCategoryTitleImg;
    @Bind(R.id.second_category_total_page)
    TextView totalPagerTv;
    @Bind(R.id.second_category_curpage)
    TextView curPagerTv;
    @Bind(R.id.second_goods_logo)
    ImageView mGoodsLogo;
    @Bind(R.id.second_category_title_tv)
    TextView mCategoryTitleTv;
    @Bind(R.id.second_goods_desc)
    TextView mGoodsDesc;
    @Bind(R.id.second_goods_app_price)
    TextView mGoodsAppPrice;
    @Bind(R.id.second_goods_market_price)
    TextView mGoodsMarketPrice;
    @Bind(R.id.second_category_add_shopcar)
    TextView mAddShopCar;
    @Bind(R.id.second_category_more_good)
    TextView mMoreGoods;
    @Bind(R.id.second_goods_img_vp)
    InnerViewPager mViewPager;
    /**********************************/
    private SubFind subFind;
    private String sortParam = "f.sort"; //更多商品排序参数
    private String sortType = "ASC";//更多商品排序类型
    private List<GoodsInfo> mGoodsList;
    private Set<String> mShopCarSet;
    private int mCurGood;
    private SecondCategoryActivity mActivity;
    private SecondDiscover secondDiscover;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (SecondCategoryActivity) activity;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        secondDiscover = getArguments().getParcelable("secondDiscover");
        Glide.with(getActivity()).load(secondDiscover.getSubFind().getSubSquareImg()).placeholder(R.mipmap.square_normal_img).into(mCategoryTitleImg);
        mCategoryTitleTv.setText(secondDiscover.getSubFind().getSubFindName());
        mShopCarSet = mActivity.getShopCarSet();
        mAddShopCar.setOnClickListener(this);
        mMoreGoods.setOnClickListener(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mScreenWidth - UiUtil.dip2px(getActivity(), 62), mScreenWidth - UiUtil.dip2px(getActivity(), 60));
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mViewPager.setLayoutParams(params);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPageDateChange(position);
                isInShopCar(mShopCarSet.contains(mGoodsList.get(position).getId()));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mGoodsList = secondDiscover.getGoodsInfoList();
        setPagerDate(mGoodsList);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fm_second_category_detail;
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


    /**
     * 获取二级分类里面的商品
     */
    private void getData() {
        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("curPage", "1");
//        params.put("pageSize", "20");
        params.put("findId", subFind.getSubFindId());
//        params.put("sortParam", sortParam);
//        params.put("sortType", sortType);
        OkHttpRequest.okHttpGet(getActivity(), Url.SUBFINDS, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "发现详情列表 = " + data.toString());
                if ("0".equals(code)) {
                    if (data.has("goodsList")) {
                        mGoodsList = JsonAnalysis.getGoodsInfoList(data.optJSONArray("goodsList"));
                        if (mGoodsList.size() > 0) {
                            setPagerDate(mGoodsList);
                        }
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onRespError(String code, String msg) {
            }
        });
    }

    /**
     * 将数据放入ViewPager
     *
     * @param goodList
     */
    private void setPagerDate(List<GoodsInfo> goodList) {

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new MyPagerAdapter());
        totalPagerTv.setText(String.valueOf("/" + goodList.size()));
        setPageDateChange(0);
        isInShopCar(mShopCarSet.contains(goodList.get(0).getId()));
    }

    /**
     * ViewPager切换 显示数据改变
     *
     * @param position
     */
    private void setPageDateChange(int position) {
        mCurGood = position;
        curPagerTv.setText(String.valueOf(position + 1));
        GoodsInfo info = mGoodsList.get(position);
        Glide.with(getActivity()).load(info.getMarkLogo()).transform(new GlideCircleTransform(mContext)).into(mGoodsLogo);
        mGoodsDesc.setText(info.getGoodsName());
        mGoodsAppPrice.setText("＄" + info.getPrice_app_dollar());
        mGoodsMarketPrice.setText("＄" + info.getPrice_airport_dollar());
    }

    /**
     * 购物车按钮状态变化
     *
     * @param bool
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void isInShopCar(boolean bool) {
        if (bool) {
            Drawable drawable = getActivity().getResources().getDrawable(
                    R.mipmap.me_my_order_white);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            mAddShopCar.setCompoundDrawables(drawable, null, null, null);
            mAddShopCar.setText(getActivity().getResources()
                    .getString(R.string.order_now));
            mAddShopCar.setTextColor(getActivity().getResources().getColor(R.color.white));
            mAddShopCar.setBackground(getActivity().getResources().getDrawable(R.drawable.radius_stroke_gray_solid_purple));
        } else {
            Drawable drawable = getActivity().getResources().getDrawable(
                    R.mipmap.shopbag_purple);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            mAddShopCar.setCompoundDrawables(drawable, null, null, null);
            mAddShopCar.setText(getActivity().getResources()
                    .getString(R.string.add_shopcar));
            mAddShopCar.setTextColor(getActivity().getResources().getColor(R.color.purple));
            mAddShopCar.setBackground(getActivity().getResources().getDrawable(R.drawable.radius_stroke_gray_solid_white));
        }
    }

    /**
     * 加入购物车按钮动画
     */
    private void shopCarAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mAddShopCar, "rotationY",
                0.0f, 360.0f);
        anim.setDuration(800);
        anim.start();
    }

    /**
     * 加入购物车成功
     *
     * @param info
     */
    private void addShopCarSucc(final GoodsInfo info) {
        mShopCarSet.add(info.getId());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isInShopCar(mShopCarSet.contains(info.getId()));
                shopCarAnim();
            }
        });
        //购物袋数量缓存起来
        String shopCarNum = (StringUtils.parseInt(SharePreUtil.getStringData(getActivity(), "shopCarNum", "0")) + 1)
                + "";
        SharePreUtil.saveStringData(getActivity(),
                "shopCarNum", shopCarNum);
    }

    /**
     * 立即下单
     *
     * @param info
     */
    private void orderConfirm(GoodsInfo info) {
        /**
         * 分两步
         * 1，是否登陆
         * 2，请求购物车 判断购物车数量
         */
        if (TextUtils.isEmpty(UserUtil.getUserId(mContext))) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
        } else {
            //这一步是获取购物车中所有商品然后进入到订单确认页面，暂时不这样
//            getShopCarData();
            List<GoodsInfo> orderList = new ArrayList<>();
            orderList.add(info);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("orderGoodsList", (ArrayList<? extends Parcelable>) orderList);
            bundle.putInt("totalPrice", StringUtils.parseInt(info.getPrice_app_dollar()));
            readyGo(OrderConfrimActivity.class, bundle);
        }

    }

    private void go2OrderConirm(int totalPrice, List<GoodsInfo> goodsList) {
        Intent intent = new Intent(getActivity(), OrderConfrimActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("totalPrice", totalPrice);
        bundle.putParcelableArrayList("selectList", (ArrayList<? extends Parcelable>) goodsList);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 获取所有商品总价格
     *
     * @param list
     * @return
     */
    private int getTotalPrice(List<GoodsInfo> list) {
        int totalAppDollar = 0;
        for (GoodsInfo shopCarItem : list) {
            int num = StringUtils.parseInt(shopCarItem.getQuantity());
            totalAppDollar += StringUtils.parseInt(shopCarItem
                    .getPrice_app_dollar()) * num;
        }
        return totalAppDollar;
    }

    /**
     * 获取购物车中所有商品 一并下订单
     */
    private void getShopCarData() {
        HashMap<String, String> params = new HashMap<String, String>();
        String userId = UserUtil.getUserId(mContext);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        OkHttpRequest.okHttpGet(getActivity(), Url.SHOPBAGDETAIL, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "二级发现立即下单获取购物车 = " + data.toString());
                if ("1".equals(code)) {
                    ToastUtil.showShortToast(getActivity(), msg);
                } else {
                    if (data.has("goodsList")) {
                        List<GoodsInfo> list = JsonAnalysis.getGoodsInfoList(data.optJSONArray("goodsList"));
                        if (list.size() < 1) {
                            ToastUtil.showShortToast(getActivity(), getResources().getString(R.string.add_shopcar_first));
                        } else {
                            go2OrderConirm(getTotalPrice(list), list);
                        }
                    }
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                ToastUtil.showShortToast(getActivity(), msg);
            }
        });
    }


    /**
     * 加入购物车
     */
    private void addCar(final GoodsInfo info) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("goodsId", info.getId());
        String userId = UserUtil.getUserId(mContext);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        OkHttpRequest.okHttpGet(getActivity(), Url.ADDSHOPBAG, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "加入购物车 = " + data.toString());
                if ("0".equals(code)) {
                    //加入购物车
                    addShopCarSucc(info);
                } else {
                    ToastUtil.showShortToast(getActivity(), msg);
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                ToastUtil.showShortToast(getActivity(), msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.second_category_add_shopcar:
                GoodsInfo info = mGoodsList.get(mCurGood);
                if (mShopCarSet.contains(info.getId())) {
                    //跳转到订单确认页面
                    orderConfirm(info);
                } else {
                    //加入购物车
                    addCar(info);
                }
                break;
            case R.id.second_category_more_good:
                Bundle bundle = new Bundle();
                bundle.putString("title", secondDiscover.getSubFind().getSubFindName());
                bundle.putParcelableArrayList("goodsList", (ArrayList<? extends Parcelable>) secondDiscover.getGoodsInfoList());
                readyGo(MoreGoodsActivity.class, bundle);
                break;
        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mGoodsList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(getActivity());
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//            params.width = mWidth-UiUtil.dip2px(getActivity(),71);
//            params.height = mWidth-UiUtil.dip2px(getActivity(),71);
//            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(getActivity()).load(mGoodsList.get(position).getGoodsImg()).into(imageView);
            Glide.with(getActivity()).load(mGoodsList.get(position).getGoodsImg()).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsInfo item = mGoodsList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("goodId", item.getId());
                    bundle.putString("srcType", TextUtils.isEmpty(item.getSrcType()) ? "0" : item.getSrcType());
                    bundle.putString("srcId", TextUtils.isEmpty(item.getSrcId()) ? "0" : item.getSrcId());
                    readyGo(GoodsDetailActivity.class, bundle);
                }
            });
            ((ViewPager) container).addView(imageView, 0);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }
}
