package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import com.aero.droid.dutyfree.talent.listener.TimeCountListener;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/2
 * Desc : 登陆 交互器
 */
public interface LoginInteractor {
    /**
     * 登陆
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void login(String log_tag,int event_tag,Context context,HashMap<String,String> params);
    /**
     * 第三方登陆
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void thirdlogin(String log_tag,int event_tag,Context context,HashMap<String,String> params);
    /**
     * 获取 动态密码
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void getAutoPassword(String log_tag,int event_tag,Context context,HashMap<String,String> params);

    /**
     * 按钮倒计时
     * @param millisInFuture  总时长
     * @param countDownInterval  时间间隔
     * @param listener   回调
     */
    void startTime(long millisInFuture, long countDownInterval,TimeCountListener listener);
}
