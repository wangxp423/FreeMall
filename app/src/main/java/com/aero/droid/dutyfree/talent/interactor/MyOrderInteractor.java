package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的订单 交互器
 */
public interface MyOrderInteractor {
    /**
     * 获取订单列表
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void getOrderList(String log_tag,int event_tag,Context context,HashMap<String,String> params);
    /**
     * 删除 订单
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void deleteOrder(String log_tag,int event_tag,Context context,HashMap<String,String> params);
    /**
     * 取消 订单
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void cancelOrder(String log_tag,int event_tag,Context context,HashMap<String,String> params);
}
