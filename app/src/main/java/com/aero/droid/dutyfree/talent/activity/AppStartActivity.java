package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.presenter.AppStartPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.AppStartPresenterImpl;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.view.AppStartView;
import com.bumptech.glide.Glide;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

/**
 * 启动页
 */
public class AppStartActivity extends FragmentActivity implements AppStartView {
    private String log_tag = AppStartActivity.class.getSimpleName();
    private AppStartPresenter mPresenter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AppStartPresenterImpl(AppStartActivity.this, this);
        mPresenter.getRequest(log_tag);
        mPresenter.isFirstIn();
        view = View.inflate(this, R.layout.activity_app_start, null);
        ImageView app_start_iv = (ImageView) view.findViewById(R.id.app_start_iv);
        Glide.with(AppStartActivity.this).load(R.mipmap.app_start_background).into(app_start_iv);
        setContentView(view);
        mPresenter.initialized();
        //友盟统计
        PushAgent.getInstance(AppStartActivity.this).onAppStart();

    }

    @Override
    public void animationEnd() {
        mPresenter.noFirstIn();
    }

    @Override
    public void imageAnimation(Animation animation) {
        view.startAnimation(animation);
    }


    @Override
    public void go2ViewPagerActivity() {
        mPresenter.firstIn();
    }

    @Override
    public void getShopBagsNum() {
    }

    @Override
    public void getSomeFunctionUrl() {
    }
}
