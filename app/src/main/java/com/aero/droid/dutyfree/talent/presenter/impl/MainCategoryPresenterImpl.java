package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.aero.droid.dutyfree.talent.interactor.MainCategoryInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.MainCategoryInteractorImpl;
import com.aero.droid.dutyfree.talent.presenter.MainCategoryPresenter;
import com.aero.droid.dutyfree.talent.view.MainCategoryView;

import java.util.List;


/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : 主页 分类页 任命类
 */
public class MainCategoryPresenterImpl implements MainCategoryPresenter {
    private Activity mActivity;
    private MainCategoryInteractor mInteractor;
    private MainCategoryView mCategoryView;

    public MainCategoryPresenterImpl(Activity activity, MainCategoryView mainCategoryView) {
        this.mActivity = activity;
        this.mCategoryView = mainCategoryView;
        mInteractor = new MainCategoryInteractorImpl();
    }

    @Override
    public void initAllFragments() {
        List<Fragment> fragments = mInteractor.getFragments();
        mCategoryView.showAllFragment(fragments);
    }

    @Override
    public void clickBrand() {
        mCategoryView.showBrandCategory();
    }


    @Override
    public void clickCategory() {
        mCategoryView.showGoodsCategory();
    }

    @Override
    public void clickShopBag() {
        mCategoryView.showShopBags();
    }

    @Override
    public void clickSearch() {
        mCategoryView.showSearch();
    }
}
