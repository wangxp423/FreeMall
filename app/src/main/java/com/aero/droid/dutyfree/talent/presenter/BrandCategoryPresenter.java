package com.aero.droid.dutyfree.talent.presenter;

import android.app.Activity;

import com.aero.droid.dutyfree.talent.bean.GoodsBrand;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc :品牌分类 任命 接口类
 */
public interface BrandCategoryPresenter extends GoodsCategoryPresenter{

    /**
     * 获取品牌 A-G分类
     * @param brandList  所有分类
     * @param pointList  指定分类
     */
    void clickA2G(List<GoodsBrand> brandList,List<GoodsBrand> pointList);
    /**
     * 获取品牌 H-N分类
     * @param brandList  所有分类
     * @param pointList  指定分类
     */
    void clickH2N(List<GoodsBrand> brandList,List<GoodsBrand> pointList);
    /**
     * 获取品牌 O-T分类
     * @param brandList  所有分类
     * @param pointList  指定分类
     */
    void clickO2T(List<GoodsBrand> brandList,List<GoodsBrand> pointList);
    /**
     * 获取品牌 U-Z分类
     * @param brandList  所有分类
     * @param pointList  指定分类
     */
    void clickU2Z(List<GoodsBrand> brandList,List<GoodsBrand> pointList);

    /**
     * 点击商品 某一个品牌
     * @param brand
     */
    void clickGoodsBrand(GoodsBrand brand);
}
