package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.GoodsBrand;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : 品牌分类V
 */
public interface BrandCategoryView extends BaseView{
    /**
     * 显示商品分类数据
     * @param brandList
     */
    void showBrandCategory(List<GoodsBrand> brandList);

    /**
     * 显示下拉刷新数据
     * @param brandList
     */
    void showRefreshBrandCategory(List<GoodsBrand> brandList);

    /**
     * 显示上拉加载数据
     * @param brandList
     */
    void showloadBrandCategory(List<GoodsBrand> brandList);
    /**
     * 显示首字母 A-G 品牌分类
     * @param brandList
     */
    void showagBrandCategory(List<GoodsBrand> brandList);
    /**
     * 显示首字母 H-N 品牌分类
     * @param brandList
     */
    void showhnBrandCategory(List<GoodsBrand> brandList);
    /**
     * 显示首字母 O-T 品牌分类
     * @param brandList
     */
    void showotBrandCategory(List<GoodsBrand> brandList);
    /**
     * 显示首字母 U-Z 品牌分类
     * @param brandList
     */
    void showuzBrandCategory(List<GoodsBrand> brandList);
}
