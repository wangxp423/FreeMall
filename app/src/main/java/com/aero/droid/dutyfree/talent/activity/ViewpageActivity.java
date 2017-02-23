package com.aero.droid.dutyfree.talent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.presenter.PagerImgPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.PagerImgPresenterImpl;
import com.aero.droid.dutyfree.talent.view.PagerImgView;
import com.bumptech.glide.Glide;
import com.umeng.message.PushAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 轮播图页面
 */
public class ViewpageActivity extends FragmentActivity implements PagerImgView {
    private ViewPager mViewPager;
    private List<Integer> views;
    private PagerImgPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPresenter = new PagerImgPresenterImpl(this,this);
        mPresenter.loadPagerImgData(null);
        //友盟统计
        PushAgent.getInstance(ViewpageActivity.this).onAppStart();

    }

    @Override
    public void showPager(List<?> imgs) {
        views = (List<Integer>) imgs;
        mViewPager.setAdapter(new MyPagerAdapter());
    }

    @Override
    public void showPagerError(String code, String msg) {

    }

    @Override
    public void clickLastPager() {

    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(ViewpageActivity.this);
            Glide.with(ViewpageActivity.this).load(views.get(position)).centerCrop().into(imageView);
            if (position == views.size() - 1) {
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.go2MainActivity();
                    }
                });
            }
            ((ViewPager) container).addView(imageView, 0);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }


}