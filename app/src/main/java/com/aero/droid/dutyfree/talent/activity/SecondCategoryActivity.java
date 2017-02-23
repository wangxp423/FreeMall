package com.aero.droid.dutyfree.talent.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.adapter.MyFragmentPagerAdapter;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.SecondDiscover;
import com.aero.droid.dutyfree.talent.bean.SubFind;
import com.aero.droid.dutyfree.talent.fragment.SecondCategoryDetailFragment;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.SharePreUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.ZoomOutPageTransformer;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;
import com.aero.droid.dutyfree.talent.widgets.ViewPagerScroller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 二级分类页面
 * Created by wangxp on 2015/11/5.
 */
public class SecondCategoryActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.second_category_view_pager)
    ViewPager mViewPager;
    @Bind(R.id.second_category_down)
    ImageView downFinish;
    /*********************************************/
    private MyFragmentPagerAdapter mAdapter;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private Set<String> mShopCarSet = new HashSet<String>();
    private List<SecondDiscover> secondDiscovers;
    private String discoverId;

    @Override
    protected void getBundleExtras(Bundle extras) {
        discoverId = extras.getString("discoverId");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_second_category;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return mViewPager;
    }

    @Override
    protected void initViewsAndEvents() {
        initViewPagerScroll();
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        downFinish.setOnClickListener(this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true,null);
            initData();
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleShowLoading(true,null);
                    initData();
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
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.BOTTOM;
    }


    private void initData() {
        getShopCarData();
    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator sInterpolator = new AccelerateDecelerateInterpolator();
            ViewPagerScroller scroller = new ViewPagerScroller(mViewPager.getContext(), sInterpolator);
            mScroller.set(mViewPager, scroller);
        } catch (Exception e) {
        }
    }

    private void setViewPagerData() {
        for (SecondDiscover secondDiscover : secondDiscovers) {
            SecondCategoryDetailFragment detailFragment = new SecondCategoryDetailFragment();
            Bundle bundle = new Bundle();
            if (secondDiscover.getGoodsInfoList().size()>0){
                bundle.putParcelable("secondDiscover", secondDiscover);
                detailFragment.setArguments(bundle);
                mFragmentList.add(detailFragment);
            }
        }
        if (null == mAdapter)
            mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mContext, mFragmentList
            );
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mViewPager.setAdapter(mAdapter);
                toggleShowLoading(false,null);
            }
        });

    }


    /**
     * 获取购物车商品
     */
    private void getShopCarData() {
        HashMap<String, String> params = new HashMap<String, String>();
        String userId = UserUtil.getUserId(mContext);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        //获取购物车商品数量
        OkHttpRequest.okHttpGet(this, Url.SHOPBAGDETAIL, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "二级发现.购物车信息 = " + data.toString());
                getSecondCategory();
                if ("0".equals(code)) {
                    if (data.has("goodsList"))
                        try {
                            List<GoodsInfo> goodsInfoList = JsonAnalysis.getGoodsInfoList(data.getJSONArray("goodsList"));
                            if (null != goodsInfoList && goodsInfoList.size() > 0) {
                                setShopCarSet(goodsInfoList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                getSecondCategory();
            }
        });
    }


    /**
     * 获取二级分类里面的商品
     */
    private void getSecondCategory() {
        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("curPage", "1");
//        params.put("pageSize", "20");
        params.put("findId", discoverId);
//        params.put("sortParam", sortParam);
//        params.put("sortType", sortType);
        OkHttpRequest.okHttpGet(mContext, Url.SUBFINDS, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "二级发现列表 = " + data.toString());
                if ("0".equals(code)) {
                    if (data.has("subFinds")) {
                        secondDiscovers = JsonAnalysis.getSecondDiscoverList(data.optJSONArray("subFinds"));
                        if (secondDiscovers.size() > 0) {
                            setViewPagerData();
                        }
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toggleShowLoading(false,null);
                    }
                });
            }
        });
    }

    /**
     * 初始化购物车数据
     */

    private void initShopCarGoods() {
        try {
            String goodsJson = SharePreUtil.getStringData(mContext, "shopCarGoods", "");
            if (!TextUtils.isEmpty(goodsJson)) {
                JSONArray goodsArray = new JSONArray(goodsJson);
                List<GoodsInfo> shopCarGoods = JsonAnalysis.getGoodsInfoList(goodsArray);
                for (GoodsInfo info : shopCarGoods) {
                    mShopCarSet.add(info.getId());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //TODO 把购物车数据放数据库中
    private void setShopCarSet(List<GoodsInfo> goodsInfoList) {
        for (GoodsInfo info : goodsInfoList) {
            mShopCarSet.add(info.getId());
        }
        LogUtil.t("购物车商品Id = " + mShopCarSet.toString());
    }

    public Set<String> getShopCarSet() {
        return mShopCarSet;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.second_category_down:
                finish();
                break;
        }
    }

    private float downX, downY;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            downX = event.getX();
//            downY = event.getY();
//        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//            if (0 <= downX && downX < UiUtil.dip2px(mContext, 35) && event.getX() - downX > 50) {
//                ToastUtil.showShortToast(mContext, "上一页");
//            } else if (mWidth - UiUtil.dip2px(mContext, 35) < downX && downX <= mWidth && event.getX() - downX < -50) {
//                ToastUtil.showShortToast(mContext, "下一页");
//            }
//        }
//        return super.onTouchEvent(event);

        return this.mViewPager.onTouchEvent(event);
    }

}
