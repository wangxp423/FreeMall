package com.aero.droid.dutyfree.talent.interactor;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/15
 * Desc : 订单详情 交互器
 */
public interface OrderDetailInteractor {
    /**
     * 获取订单商品列表
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void getGoodsList(String log_tag, int event_tag, HashMap<String, String> params);
}
