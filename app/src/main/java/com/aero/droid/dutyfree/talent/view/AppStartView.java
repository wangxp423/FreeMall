package com.aero.droid.dutyfree.talent.view;

import android.view.animation.Animation;

/**
 reated by wangxp on 2015/11/27.
 * 启动页View
 */
public interface AppStartView {
    /**
     * 动画结束
     */
    void animationEnd();
    /**
     * 启动页动画
     * @param animation
     */
    void imageAnimation(Animation animation);

    /**
     * 跳转到app轮播图介绍页
     */
    void go2ViewPagerActivity();

    /**
     * 获取购物袋数量
     */
    void getShopBagsNum();

    /**
     * 获取功能性页面(关于我们，用户协议等)
     */
    void getSomeFunctionUrl();
}
