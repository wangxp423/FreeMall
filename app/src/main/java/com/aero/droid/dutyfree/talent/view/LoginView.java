package com.aero.droid.dutyfree.talent.view;

/**
 * Author : wangxp
 * Date : 2015/12/2
 * Desc : 登陆 View
 */
public interface LoginView {
    /**
     * 显示清除账号按钮
     */
    void visibleEditClear();

    /**
     * 隐藏清除账号按钮
     */
    void invisibleEditClear();

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
     * 登陆按钮 可点击
     */
    void clickAbleLoginBtn();

    /**
     * 登陆按钮 可点击
     */
    void unclickAbleLoginBtn();

    /**
     * 登陆成功
     */
    void loginSuccess();
    /**
     * 第三方登陆成功
     */
    void thirdLoginSuccess();

    /**
     * 获取 动态密码成功
     */
    void getAutoPasswordSuccess();

    /**
     * 请求失败(这里统一处理)
     */
    void rquestDataError(String msg);
}
