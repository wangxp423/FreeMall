package com.aero.droid.dutyfree.talent.presenter;

import android.app.Activity;

import com.aero.droid.dutyfree.talent.bean.GoodsCategory;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc :商品分类页任命器
 */
public interface GoodsCategoryPresenter {
    /**
     * 获取数据
     * @param log_tag
     * @param activity
     * @param pageSize
     * @param curPage
     * @return
     */
    void getData(String log_tag, Activity activity, int pageSize, int curPage);

    /**
     * 获取下拉刷新数据
     * @param log_tag
     * @param activity
     * @param pageSize
     * @param curPage
     * @return
     */
    void getRefreshData(String log_tag, Activity activity, int pageSize, int curPage);

    /**
     * 获取上拉加载数据
     * @param log_tag
     * @param activity
     * @param pageSize
     * @param curPage
     * @return
     */
    void getLoadData(String log_tag,Activity activity, int pageSize, int curPage);

    /**
     * 点击商品分类
     *
     * @param category
     * @return
     */
    void clickCategory(GoodsCategory category);
}
