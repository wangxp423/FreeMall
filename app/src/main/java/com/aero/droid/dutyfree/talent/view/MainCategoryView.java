package com.aero.droid.dutyfree.talent.view;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : 首页 分类页
 */
public interface MainCategoryView {
    /**
     * 获取展示所有页面
     *
     * @param fragments
     */
    void showAllFragment(List<Fragment> fragments);

    /**
     * 显示商品分类
     */
    void showGoodsCategory();

    /**
     * 显示品牌分类
     */
    void showBrandCategory();
    /**
     * 显示购物袋
     */
    void showShopBags();
    /**
     * 显示搜索
     */
    void showSearch();
}
