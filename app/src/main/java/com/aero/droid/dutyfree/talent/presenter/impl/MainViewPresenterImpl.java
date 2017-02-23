package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.aero.droid.dutyfree.talent.interactor.MainViewInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.MainViewInteractorImpl;
import com.aero.droid.dutyfree.talent.presenter.MainViewPresenter;
import com.aero.droid.dutyfree.talent.view.MainView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : App首页Activity任命实现类
 */
public class MainViewPresenterImpl implements MainViewPresenter {
    private Activity mActivity;
    private MainView mMainView;
    private MainViewInteractor mInteractor;

    public MainViewPresenterImpl(Activity activity, MainView mainView) {
        this.mActivity = activity;
        this.mMainView = mainView;
        mInteractor = new MainViewInteractorImpl();
    }

    @Override
    public void initAllFragments() {
        List<Fragment> fragments = mInteractor.getFragments();
        mMainView.showAllFragment(fragments);
    }

    @Override
    public void handpickChecked() {
        mMainView.showHandpickPage();
    }

    @Override
    public void categoryChecked() {
        mMainView.showCategoryPage();
    }

    @Override
    public void shopBagsChecked() {
        mMainView.showShopBagsPage();
    }

    @Override
    public void meChecked() {
        mMainView.showMePage();
    }
}
