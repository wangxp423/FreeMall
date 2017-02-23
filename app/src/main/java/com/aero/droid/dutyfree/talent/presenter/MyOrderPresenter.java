package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的订单 逻辑控制器
 */
public interface MyOrderPresenter {
    /**
     * 获取订单列表
     *
     * @param log_tag
     */
    void getOrderList(String log_tag);

    /**
     * 删除订单
     *
     * @param log_tag
     * @param orderId
     */
    void clickDeleteOrder(String log_tag, String orderId);
    /**
     * 取消订单
     *
     * @param log_tag
     * @param orderId
     */
    void clickCancelOrder(String log_tag, String orderId);
}
