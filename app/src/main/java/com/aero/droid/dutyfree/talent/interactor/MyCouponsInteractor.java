package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的优惠券列表 交互器
 */
public interface MyCouponsInteractor {
    /**
     * 获取我的优惠券列表
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void getCouponsList(String log_tag, int event_tag, Context context, HashMap<String, String> params);
    /**
     * 删除 单个优惠券
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void deleteCoupons(String log_tag, int event_tag, Context context, HashMap<String, String> params);
    /**
     * 添加/兑换 优惠券
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void addCoupons(String log_tag, int event_tag, Context context, HashMap<String, String> params);
}
