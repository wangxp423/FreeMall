package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.AirportCompany;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/3
 * Desc : 注册页面
 */
public interface RegisterView {
    /**
     * 显示航空公司列表
     * @param companyList
     */
    void showAirportCompany(List<AirportCompany> companyList);

    /**
     * 获取动态密码 可点击
     */
    void clickAbleGetAuthBtn();

    /**
     * 获取动态密码 不可点击
     * @param millisUntilFinished  当前倒计时时间
     */
    void unclickAbleGetAuthBtn(long millisUntilFinished);


    /**
     * 验证成功
     */
    void verifySuccess();

    /**
     * 获取验证码成功
     */
    void getCodeSuccess();

    /**
     * 请求失败
     * @param msg
     */
    void requestError(String msg);


}
