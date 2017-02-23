package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.content.Intent;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.MainActivity;
import com.aero.droid.dutyfree.talent.interactor.PagerImgInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.PagerImgInteractorImpl;
import com.aero.droid.dutyfree.talent.presenter.PagerImgPresenter;
import com.aero.droid.dutyfree.talent.view.PagerImgView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangxp on 2015/11/26.
 */
public class PagerImgPresenterImpl implements PagerImgPresenter {
    private Activity mActivity;
    private PagerImgView mPagerImgView;
    private PagerImgInteractor mInteractor;

    public PagerImgPresenterImpl(Activity activity, PagerImgView pagerImgView) {
        this.mActivity = activity;
        this.mPagerImgView = pagerImgView;
        mInteractor = new PagerImgInteractorImpl();
    }



    @Override
    public void loadPagerImgData(HashMap<String, String> params) {
//        mInteractor.getPagerImgData(mActivity,params);  这里应该调用实现里面的接口数据
        //这里暂时用本地的
        List<Integer> views = new ArrayList<>();
        views.add(R.mipmap.viewpage1);
        views.add(R.mipmap.viewpage2);
        views.add(R.mipmap.viewpage3);
        mPagerImgView.showPager(views);
    }

    @Override
    public void go2MainActivity() {
        mPagerImgView.clickLastPager();
        Intent intent = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
}
