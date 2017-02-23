package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 品牌or分类 商品页
 */
public interface BrandGoodsView {
    /**
     * 品牌下商品列表
     *
     * @param infoList
     */
    void showGoodsList(List<GoodsInfo> infoList);

    /**
     * 下拉刷新商品列表
     *
     * @param infoList
     */
    void refreshGoodsList(List<GoodsInfo> infoList);

    /**
     * 上拉加载商品列表
     *
     * @param infoList
     */
    void loadGoodsList(List<GoodsInfo> infoList);

    /**
     * 显示顶部说明栏数据
     *
     * @param titleImg 顶部背景图片
     * @param desc 品牌or分类描述
     * @param totalNum 商品总数
     */
    void showTitleData(String titleImg, String desc, String totalNum);

    /**
     * 所有数据加载完毕
     */
    void end();


    /**
     * 请求失败
     */
    void requestError(String msg);
}
