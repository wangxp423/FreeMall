package com.aero.droid.dutyfree.talent.interactor.impl;

import android.support.v4.app.Fragment;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.fragment.BrandCategoryFragment;
import com.aero.droid.dutyfree.talent.fragment.GoodsCategoryFragment;
import com.aero.droid.dutyfree.talent.interactor.MainCategoryInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : 获取主页分类页Fragment
 */
public class MainCategoryInteractorImpl implements MainCategoryInteractor{
    @Override
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        GoodsCategoryFragment goodsCategoryFragment = new GoodsCategoryFragment();
        BrandCategoryFragment brandCategoryFragment = new BrandCategoryFragment();
        fragments.add(goodsCategoryFragment);
        fragments.add(brandCategoryFragment);
        return fragments;
    }
}
