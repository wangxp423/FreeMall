package com.aero.droid.dutyfree.talent.presenter;

import com.aero.droid.dutyfree.talent.bean.GoodsInfo;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 折扣专区 逻辑控制器
 */
public interface DiscountPresenter {
    /**
     * 获取折扣商品列表
     *
     * @param log_tag
     */
    void getDiscountList(String log_tag);
    /**
     * 刷新折扣商品列表
     *
     * @param log_tag
     */
    void refreshDiscountList(String log_tag);

    /**
     * 点击购买按钮
     * @param info
     */
    void clickBuyBtn(GoodsInfo info);

    /**
     * 点击商品
     * @param info
     */
    void clickGoods(GoodsInfo info);

}
