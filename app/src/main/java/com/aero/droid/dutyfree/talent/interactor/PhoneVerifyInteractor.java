package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/3
 * Desc : 认证 手机验证交互器
 */
public interface PhoneVerifyInteractor {
    /**
     * 完成验证
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void verify(String log_tag,int event_tag,Context context,HashMap<String,String> params);
    /**
     * 完成注册
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void register(String log_tag,int event_tag,Context context,HashMap<String,String> params);
    /**
     * 获取验证码
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void getAuthCode(String log_tag,int event_tag,Context context,HashMap<String,String> params);
}
