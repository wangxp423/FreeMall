package com.aero.droid.dutyfree.talent.fragment;


import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragment;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.util.ActivityJumpUtil;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.widgets.autoviewpager.AutoScrollViewPager;
import com.aero.droid.dutyfree.talent.widgets.autoviewpager.RecyclingPagerAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangxp on 2015/11/25.
 */
public class ViewPagerFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @Bind(R.id.viewpager_img)
    AutoScrollViewPager mViewPager;
    @Bind(R.id.viewpager_dot_layout)
    LinearLayout dotLayout;
    private List<HandpickBanner> mHandpickBannerList;
    private MyPagerAdapter adapter;


    /**************************/
    private int curPage = 0;
    private List<ImageView> mDotImgs = new ArrayList<>();


    @Override
    protected void onFirstUserVisible() {
        LogUtil.t("onFirstUserVisible");
    }

    @Override
    protected void onUserVisible() {
        LogUtil.t("onUserVisible");
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    public void refreshData(List<HandpickBanner> handpickBanners) {
        if (mHandpickBannerList.size() > 0) {
            mHandpickBannerList.clear();
            mHandpickBannerList.addAll(handpickBanners);
            adapter.notifyDataSetChanged();
            mViewPager.startAutoScroll();
        }
    }

    @Override
    protected void initViewsAndEvents() {
        mHandpickBannerList = getArguments().getParcelableArrayList("handpickList");
        if (null == adapter)
            adapter = new MyPagerAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setInterval(2000);
        mViewPager.startAutoScroll();
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mHandpickBannerList.size());
        for (int i = 0; i < mHandpickBannerList.size(); i++) {
            addDotImg(i);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fm_viewpager_img;
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewPager.startAutoScroll();
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewPager.stopAutoScroll();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switchDotImg(position % mHandpickBannerList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyPagerAdapter extends RecyclingPagerAdapter {
        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            ImageView imageView = new ImageView(getActivity());
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mScreenWidth,mScreenWidth/2);
//            imageView.setLayoutParams(params);
            Glide.with(getActivity()).load(mHandpickBannerList.get(position % mHandpickBannerList.size()).getImg()).centerCrop().into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityJumpUtil.activityJump(getActivity(),mHandpickBannerList.get(position % mHandpickBannerList.size()));
                }
            });

            return imageView;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

    }

    /**
     * @param i 给VeiwPager添加圆点
     */
    private void addDotImg(int i) {
        ImageView iv = new ImageView(getActivity());
        if (i == curPage) {
            iv.setImageResource(R.drawable.circle_solid_purple);
        } else {
            iv.setImageResource(R.drawable.circle_solid_gray);
        }
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10, 10);
        lp.setMargins(5, 4, 5, 4);
        iv.setLayoutParams(lp);
        dotLayout.addView(iv);
        mDotImgs.add(iv);
    }

    /**
     * 切换圆点
     *
     * @param cur
     */
    private void switchDotImg(int cur) {
        for (int i = 0; i < mDotImgs.size(); i++) {
            ImageView iv = mDotImgs.get(i);
            if (cur == i) {
                iv.setImageResource(R.drawable.circle_solid_purple);
            } else {
                iv.setImageResource(R.drawable.circle_solid_gray);
            }
        }
    }
}
