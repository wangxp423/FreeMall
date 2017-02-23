package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.adapter.MyFragmentPagerAdapter;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.fragment.CouponsFragment;
import com.aero.droid.dutyfree.talent.presenter.MyCouponsPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.MyCouponsPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.MyCouponsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/12/5
 * Desc : 我的优惠券页
 */
public class MyCouponsActivity extends BaseFragmentActivity implements MyCouponsView, View.OnClickListener, ViewPager.OnPageChangeListener {
    @Bind(R.id.title_back_layout)
    RelativeLayout titleBackLayout;
    @Bind(R.id.my_coupons_unused)
    TextView unusedTv;
    @Bind(R.id.my_coupons_used)
    TextView usedTv;
    @Bind(R.id.my_coupons_out_date)
    TextView outDateTv;
    @Bind(R.id.my_coupons_line)
    ImageView lineImg;
    @Bind(R.id.my_coupons_vp)
    ViewPager couponsVp;
    @Bind(R.id.my_coupons_container)
    FrameLayout containerLayout;

    /****************************/
    private MyFragmentPagerAdapter adapter;
    public MyCouponsPresenter presenter;
    private List<CouponsFragment> fragments = new ArrayList<>();
    private boolean isFirst = true;  //进入页面第一次 获取底部滑动条，获取分类按钮宽度
    private RelativeLayout.LayoutParams params;   //设置 滚动条布局参数
    private int viewPagerWidth, lineImgWidth;  //ViewPager宽度  滚动条宽度
    private int curFragment;
    /**
     * 进度条移动值
     **/
    private Integer moveI;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_my_coupons;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return couponsVp;
    }

    @Override
    protected void initViewsAndEvents() {
        couponsVp.setOnPageChangeListener(this);
        titleBackLayout.setOnClickListener(this);
        unusedTv.setOnClickListener(this);
        usedTv.setOnClickListener(this);
        outDateTv.setOnClickListener(this);
        params = (RelativeLayout.LayoutParams) lineImg.getLayoutParams();
//        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mContext, fragments);
//        couponsVp.setAdapter(adapter);


        presenter = new MyCouponsPresenterImpl(MyCouponsActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getCouponsList(TAG_LOG);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getCouponsList(TAG_LOG);
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
    public void showCouponsList(final List<CouponsInfo> infoList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false, null);
                presenter.setFragmentData(infoList, fragments);
                changeCouponType(0);
//                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void deleteCouponsSuccess(String msg) {
        fragments.get(curFragment).deleteCouponSuccess();
    }

    @Override
    public void addCouponsSuccess(String msg) {

    }

    @Override
    public void requestError(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false, null);
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
    }

    @Override
    public void showException(String msg) {

    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getCouponsList(TAG_LOG);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirst) {
            isFirst = false;
            viewPagerWidth = couponsVp.getWidth();
            int titleWidth = unusedTv.getWidth();
            lineImgWidth = titleWidth;
            params.width = titleWidth;
            lineImg.setLayoutParams(params);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_layout:
                finish();
                break;
            case R.id.my_coupons_unused:
//                couponsVp.setCurrentItem(0);
                changeCouponType(0);
                break;
            case R.id.my_coupons_used:
//                couponsVp.setCurrentItem(1);
                changeCouponType(1);
                break;
            case R.id.my_coupons_out_date:
//                couponsVp.setCurrentItem(2);
                changeCouponType(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        tabMove(position, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 进度条移动 :核心的移动算法
     *
     * @param position             当前页位置
     * @param positionOffsetPixels 移动像素值
     */
    private void tabMove(int position, int positionOffsetPixels) {
        LogUtil.t("lineWidth = " + lineImgWidth + "viewPagerWidth = " + viewPagerWidth + "positionOffsetPixels = "+ positionOffsetPixels);
        moveI = (int) ((int) (lineImgWidth * position) + (((double) positionOffsetPixels / mScreenWidth) * lineImgWidth));
        params.leftMargin = moveI + UiUtil.dip2px(mContext, 60);
        lineImg.setLayoutParams(params);
    }

    /**
     * 切换 顶部状态
     * @param position
     */
    private void changeCouponType(int position){
        curFragment = position;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.my_coupons_container,fragments.get(position));
        transaction.addToBackStack(null);
        transaction.commit();
        //顶部横线
        params.leftMargin = lineImgWidth*(position) + UiUtil.dip2px(mContext, 60);
        lineImg.setLayoutParams(params);
    }
}
