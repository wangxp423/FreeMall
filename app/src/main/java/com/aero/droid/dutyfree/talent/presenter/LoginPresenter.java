package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/12/2
 * Desc : 登陆 逻辑控制器
 */
public interface LoginPresenter {
    /**
     * 登陆
     *
     * @param log_tag
     * @param account
     * @param password
     */
    void clickLogin(String log_tag, String account, String password);
    /**
     * 第三方登陆
     *
     * @param log_tag
     * @param third
     * @param usid
     * @param unionid
     * @param image
     * @param nickName
     */
    void clickThirdLogin(String log_tag, String third, String usid, String unionid, String image, String nickName);

    /**
     * 获取动态密码
     *
     * @param log_tag
     * @param phoneNumber
     */
    void clickGetAutoCode(String log_tag, String phoneNumber);

    /**
     * 正在输入账号
     *
     * @param text
     */
    void inputingAccount(String text);


    /**
     * 正在输入动态密码
     * @param text
     */
    void inputingAutoCode(String text);

    /**
     * 开始倒计时
     */
    void startTime();

    boolean checkUserData(String uname);

}
