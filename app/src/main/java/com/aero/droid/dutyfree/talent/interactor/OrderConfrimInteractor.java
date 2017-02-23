package com.aero.droid.dutyfree.talent.interactor;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/15
 * Desc : 订单确认 交互器
 */
public interface OrderConfrimInteractor {
    /**
     * 航空公司列表
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void getAirportList(String log_tag, int event_tag, HashMap<String, String> params);
    /**
     * 优惠券列表
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void getCouponsList(String log_tag, int event_tag, HashMap<String, String> params);
    /**
     * 提交订单
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void commitOrder(String log_tag, int event_tag, HashMap<String, String> params);
    /**
     * 查询航班
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void checkAirport(String log_tag, int event_tag, HashMap<String, String> params);
}
