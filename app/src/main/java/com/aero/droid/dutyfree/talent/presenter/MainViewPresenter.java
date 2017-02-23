package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc :App主页Activity任命类
 */
public interface MainViewPresenter {
    /**
     * 初始化主页所有Fragment
     */
    void initAllFragments();

    /**
     * 点击精选页
     */
    void handpickChecked();
    /**
     * 点击分类页
     */
    void categoryChecked();
    /**
     * 点击购物袋页
     */
    void shopBagsChecked();
    /**
     * 点击我的页
     */
    void meChecked();
}
