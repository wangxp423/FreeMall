package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.GoodsInfo;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/5
 * Desc : 搜索 商品页
 */
public interface SearchGoodsView {
    /**
     * 品牌下商品列表
     * @param infoList
     */
    void showGoodsList(List<GoodsInfo> infoList);

    /**
     * 搜索数据为空
     */
    void showEmptyData();


    /**
     * 请求失败
     */
    void requestError(String msg);



}
