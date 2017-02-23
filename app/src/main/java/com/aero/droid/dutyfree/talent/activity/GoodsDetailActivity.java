package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.fragment.ViewPagerFragment;
import com.aero.droid.dutyfree.talent.presenter.GoodsDetailPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.GoodsDetailPresenterImpl;
import com.aero.droid.dutyfree.talent.util.DownTimeUtil;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.PopWindowUtil;
import com.aero.droid.dutyfree.talent.util.SharePreUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.GoodsDetailView;
import com.aero.droid.dutyfree.talent.widgets.ComentsLayout;
import com.aero.droid.dutyfree.talent.widgets.DownTimerView;
import com.aero.droid.dutyfree.talent.widgets.GoodsIntroduceLayout;
import com.aero.droid.dutyfree.talent.widgets.RecommendGoodsLayout;
import com.bumptech.glide.Glide;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/12/10
 * Desc : 商品详情页
 */
public class GoodsDetailActivity extends BaseFragmentActivity implements GoodsDetailView, PullableScrollView.OnMyScrollListener, View.OnClickListener {
    @Bind(R.id.title_left_layout)
    RelativeLayout titleLeftBack;
    @Bind(R.id.title_right_layout)
    RelativeLayout titleRightShopBag;
    @Bind(R.id.gooddetail_shopbag_num)
    TextView shopBagCount;
    @Bind(R.id.gooddetail_imgs_layout)
    FrameLayout imgsLayout;
    @Bind(R.id.gooddetail_favo_num)
    TextView favoNumTv;
    @Bind(R.id.gooddetail_coments_num)
    TextView comentsNumTv;
    @Bind(R.id.gooddetail_scrollgoods_layout)
    FrameLayout scrollgoodsLayout;
    @Bind(R.id.gooddetail_brand_name)
    TextView brandNameTv;
    @Bind(R.id.gooddetail_good_name)
    TextView goodNameTv;
    @Bind(R.id.gooddetail_good_price1)
    TextView goodAppPriceTv;
    @Bind(R.id.gooddetail_good_price2)
    TextView goodRefPriceTv;
    @Bind(R.id.gooddetail_active_name)
    TextView goodActiveNameTv;
    @Bind(R.id.gooddetail_active_time)
    TextView goodActiveTimeTv;
    @Bind(R.id.gooddetail_active_downtime)
    DownTimerView goodDowntime;
    @Bind(R.id.gooddetail_active_downtime_layout)
    RelativeLayout goodDowntimeLayout;
    @Bind(R.id.gooddetail_detail_layout)
    LinearLayout goodDetailLayout;
    @Bind(R.id.gooddetail_pull_scrollview)
    PullableScrollView goodPullScrollview;
    @Bind(R.id.gooddetail_title_bg_layout)
    FrameLayout goodTitleBgLayout;
    @Bind(R.id.gooddetail_pull_layout)
    RGPullRefreshLayout goodPullLayout;
    @Bind(R.id.gooddetail_good_share)
    ImageView goodShareIv;
    @Bind(R.id.gooddetail_good_collect)
    ImageView goodCollectIv;
    @Bind(R.id.gooddetail_good_phone)
    ImageView goodPhoneIv;
    @Bind(R.id.gooddetail_add_shopbag_tv)
    TextView goodAddShopbagTv;
    @Bind(R.id.gooddetail_add_shopbag_layout)
    LinearLayout goodAddShopbagLayout;

    /********************************************/
    private GoodsDetailPresenter presenter;
    private float titleHeight;
    private GoodsDetail goodsDetail;
    private String goodId, srcType, srcId;
    private int curPage = 1;
    private int pageSize = 4;
    private List<GoodsInfo> recommendGoodsList;
    private List<GoodsInfo> sameBrandGoodsList;
    private boolean isFavo = false; //是否是收藏商品
    private int addShopbagCount = 0;//加入购物车商品数量

    @Override
    protected void getBundleExtras(Bundle extras) {
        goodId = extras.getString("goodId");
        srcType = extras.getString("srcType");
        srcId = extras.getString("srcId");
        addShopbagCount = Integer.parseInt(SharePreUtil.getStringData(mContext, "shopCarNum","0"));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_goodsdetail;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mScreenWidth, mScreenHeight - UiUtil.dip2px(mContext, 20));
//        goodPullLayout.setLayoutParams(params);
        return goodPullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        if (addShopbagCount == 0){
            shopBagCount.setVisibility(View.GONE);
        }else {
            shopBagCount.setText(addShopbagCount+"");
            shopBagCount.setVisibility(View.VISIBLE);
        }

        titleLeftBack.setOnClickListener(this);
        titleRightShopBag.setOnClickListener(this);
        goodShareIv.setOnClickListener(this);
        goodCollectIv.setOnClickListener(this);
        goodPhoneIv.setOnClickListener(this);
        goodAddShopbagLayout.setOnClickListener(this);
        titleHeight = UiUtil.dip2px(mContext, 200);
        goodTitleBgLayout.setAlpha(0);
        goodPullScrollview.setMyScrollListener(this);
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth);
        scrollgoodsLayout.setLayoutParams(scrollParams);
        presenter = new GoodsDetailPresenterImpl(GoodsDetailActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getGoodsDetail(TAG_LOG, goodId, srcType, srcId);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getGoodsDetail(TAG_LOG, goodId, srcType, srcId);
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
    public void showGoodsDetailData(final GoodsDetail detail) {
        this.goodsDetail = detail;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false, null);
            }
        });
        brandNameTv.setText(detail.getMarkName());
        goodNameTv.setText(detail.getGoodsName());
        goodAppPriceTv.setText(detail.getPrice_app_dollar());
        goodRefPriceTv.setText(detail.getPrice_ref_dollar());
        goodActiveNameTv.setText(detail.getActiveName());
        if ("0".equals(detail.getIsFavorite())) {
            isFavo = false;
            goodCollectIv.setImageResource(R.mipmap.good_detail_collect_normal);
        } else {
            isFavo = true;
            goodCollectIv.setImageResource(R.mipmap.good_detail_collect_pressed);
        }
        if (!"2".equals(srcType)) {
            goodDowntimeLayout.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(detail.getBeginTime()) && TextUtils.isEmpty(detail.getEndTime())) {
            goodDowntimeLayout.setVisibility(View.GONE);
        } else {
            goodDowntimeLayout.setVisibility(View.VISIBLE);
            long curTime = System.currentTimeMillis();
            if (Long.parseLong(detail.getBeginTime()) * 1000 > curTime) {
                goodActiveTimeTv.setText(getResources().getString(R.string.active_downtime_start));
                DownTimeUtil timeUtil = new DownTimeUtil(Long.parseLong(detail.getBeginTime()));
                goodDowntime.setTime(timeUtil.getDay(), timeUtil.getHour(), timeUtil.getMin(), timeUtil.getSec());
            } else if (Long.parseLong(detail.getBeginTime()) * 1000 < curTime && curTime < Long.parseLong(detail.getEndTime()) * 1000) {
                goodActiveTimeTv.setText(getResources().getString(R.string.active_downtime_text));
                DownTimeUtil timeUtil = new DownTimeUtil(Long.parseLong(detail.getEndTime()));
                goodDowntime.setTime(timeUtil.getDay(), timeUtil.getHour(), timeUtil.getMin(), timeUtil.getSec());
            } else {
                goodActiveTimeTv.setText(getResources().getString(R.string.active_downtime_end));
                goodDowntime.setTime(0, 0, 0, 1);
            }
            goodDowntime.start();
        }

        presenter.getImgs(TAG_LOG, detail);
        presenter.getComentsView(TAG_LOG);
        presenter.getGoodsDescView(TAG_LOG);
        presenter.getSameBrandList(TAG_LOG, "2", goodsDetail.getMarkId(), curPage, pageSize);
        presenter.getRecommendList(TAG_LOG, "1", goodsDetail.getCategoryId(), curPage, pageSize);
    }

    @Override
    public void getSameBrandGoodsData(List<GoodsInfo> goodsInfoList) {
        sameBrandGoodsList = goodsInfoList;
        presenter.getSameBrandView(TAG_LOG);
    }

    @Override
    public void getRecommendGoodsData(List<GoodsInfo> goodsInfoList) {
        recommendGoodsList = goodsInfoList;
        presenter.getRecommendGoodsView(TAG_LOG);
    }


    @Override
    public void showSingleImg() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView img = new ImageView(mContext);
                Glide.with(mContext).load(goodsDetail.getGoodsImg()).centerCrop().into(img);
                imgsLayout.addView(img);
            }
        });
    }

    @Override
    public void showPagerImgs(List<HandpickBanner> bannerList) {
        if (bannerList.size() > 1) {
            ViewPagerFragment pagerFragment = new ViewPagerFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("handpickList", (ArrayList<? extends Parcelable>) bannerList);
            pagerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.gooddetail_imgs_layout, pagerFragment).commit();
        } else {
            presenter.getImageView(TAG_LOG);
        }
    }

    @Override
    public void showComentsView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ComentsLayout layout = new ComentsLayout(mContext);
                layout.setConments(goodsDetail);
                goodDetailLayout.addView(layout);
            }
        });

    }

    @Override
    public void showIntroduceView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GoodsIntroduceLayout layout = new GoodsIntroduceLayout(mContext);
                layout.setGoodDetail(goodsDetail);
                goodDetailLayout.addView(layout);
            }
        });

    }

    @Override
    public void showSameBrandView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecommendGoodsLayout layout = new RecommendGoodsLayout(mContext);
                layout.setGoods(sameBrandGoodsList, 1, goodsDetail.getMarkId());
                goodDetailLayout.addView(layout);
            }
        });
    }

    @Override
    public void showRecommendView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecommendGoodsLayout layout = new RecommendGoodsLayout(mContext);
                layout.setGoods(recommendGoodsList, 2, goodsDetail.getCategoryId());
                goodDetailLayout.addView(layout);
            }
        });
    }

    @Override
    public void addShopBagSuccess(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addShopbagCount++;
                shopBagCount.setText(addShopbagCount + "");
                shopBagCount.setVisibility(View.VISIBLE);
                startCountAnim(shopBagCount);
                ToastUtil.showShortToast(mContext, msg);
            }
        });
    }

    private void startCountAnim(View view) {
        ScaleAnimation animation =new ScaleAnimation(1f, 1.5f, 1f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        view.startAnimation(animation);
    }

    @Override
    public void addFavoriteSuccess(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isFavo = true;
                goodCollectIv.setImageResource(R.mipmap.good_detail_collect_pressed);
                ToastUtil.showShortToast(mContext, msg);
            }
        });
    }

    @Override
    public void removeFavoriteSuccess(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isFavo = false;
                goodCollectIv.setImageResource(R.mipmap.good_detail_collect_normal);
                ToastUtil.showShortToast(mContext, msg);
            }
        });
    }

    @Override
    public void requestError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false, null);
                ToastUtil.showShortToast(mContext, msg);
            }
        });
    }


    @Override
    public void onMyScrollChange(int l, int t, int oldl, int oldt) {
        goodTitleBgLayout.setAlpha(t / titleHeight);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gooddetail_good_share:
                PopWindowUtil.showSharePop(GoodsDetailActivity.this, goodsDetail.getGoodsName(), goodsDetail.getShareImage(), goodsDetail.getShareHtml(), goodsDetail.getShareText(), mShareListener);
                break;
            case R.id.gooddetail_good_collect:
                if (isFavo) {
                    presenter.removeFavorite(TAG_LOG, goodId);
                } else {
                    presenter.addFavorite(TAG_LOG, goodId);
                }
                break;
            case R.id.gooddetail_good_phone:
                ToastUtil.showLongToast(mContext, "电话");
                break;
            case R.id.gooddetail_add_shopbag_layout:
                presenter.addShopBag(TAG_LOG, goodId);
                break;
            case R.id.title_right_layout:
                Bundle bundle = new Bundle();
                bundle.putInt("jumpType", 2);
                readyGo(MainActivity.class, bundle);
                break;
            case R.id.title_left_layout:
                finish();
                break;
        }
    }
}
