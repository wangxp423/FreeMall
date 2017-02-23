package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import com.aero.droid.dutyfree.talent.listener.TimeCountListener;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/2
 * Desc : 注册页面 交互器
 */
public interface RegisterInteractor {
    /**
     * 注册
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void register(String log_tag, int event_tag, Context context, HashMap<String, String> params);
    /**
     * 注册 获取手机验证码
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void getPhoneSecurityCode(String log_tag, int event_tag, Context context, HashMap<String, String> params);
    /**
     * 获取 航空公司列表
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void getAirportCompanyList(String log_tag, int event_tag, Context context, HashMap<String, String> params);

}
