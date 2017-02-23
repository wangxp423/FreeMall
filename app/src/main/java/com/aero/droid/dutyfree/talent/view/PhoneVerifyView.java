package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.User;

/**
 * Author : wangxp
 * Date : 2015/12/3
 * Desc : 手机验证/改版一次注册
 */
public interface PhoneVerifyView {
    /**
     * 获取验证码成功
     */
    void getAuthCodeSuccess();

    /**
     * 验证成功
     */
    void getVerifySuccess();
    /**
     * 注册成功
     */
    void registerSuccess();

    /**
     * 请求失败
     */
    void requestErroe(String msg);
    /**
     * 获取动态密码 可点击
     */
    void clickAbleGetAuthBtn();

    /**
     * 获取动态密码 不可点击
     * @param millisUntilFinished  当前倒计时时间
     */
    void unclickAbleGetAuthBtn(long millisUntilFinished);
}
