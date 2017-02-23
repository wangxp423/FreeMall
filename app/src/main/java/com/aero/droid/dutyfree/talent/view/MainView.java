package com.aero.droid.dutyfree.talent.view;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : 项目首页Activity
 */
public interface MainView {
    /**
     * 获取展示所有页面
     *
     * @param fragments
     */
    void showAllFragment(List<Fragment> fragments);

    /**
     * 显示精选页
     */
    void showHandpickPage();

    /**
     * 显示分类页
     */
    void showCategoryPage();

    /**
     * 显示购物袋页
     */
    void showShopBagsPage();

    /**
     * 显示我的页
     */
    void showMePage();
}
