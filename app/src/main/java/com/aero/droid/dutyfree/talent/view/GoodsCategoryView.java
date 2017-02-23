package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.GoodsCategory;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : 商品分类页View
 */
public interface GoodsCategoryView extends BaseView{
    /**
     * 显示商品分类数据
     * @param categoryList
     */
    void showGoodsCategory(List<GoodsCategory> categoryList);

    /**
     * 显示下拉刷新数据
     * @param categoryList
     */
    void showRefreshGoodsCategory(List<GoodsCategory> categoryList);

    /**
     * 显示上拉加载数据
     * @param categoryList
     */
    void showloadGoodsCategory(List<GoodsCategory> categoryList);
}
