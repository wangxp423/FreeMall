package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/12/3
 * Desc : 注册页面 逻辑控制类
 */
public interface RegisterPresenter {
    /**
     * 注册
     *
     * @param log_tag
     * @param twoCode 二字码
     * @param workNum 工号
     * @param name 姓名
     */
    void clickVerify(String log_tag, String twoCode, String workNum,String name);
    /**
     * 点击获取验证码
     *
     * @param log_tag
     * @param phone 手机号码
     */
    void clickAuthCodeButton(String log_tag, String phone);

    /**
     * 获取航空公司列表
     *
     * @param log_tag
     */
    void getAirportCompanyList(String log_tag);

    /**
     * 检查手机号
     */
    boolean checkTelNo(String telNo);

    /**
     * 点击开始 验证码倒计时
     */
    void startTime();

    /**
     * 点击下一步
     * @param jobNum 工号
     * @param name 名字
     * @param inviteCode 邀请码
     */
    void clickNextStep(String jobNum, String name,String inviteCode);

    /**
     * 点击什么是邀请码
     */
    void clickInvatationCode();

    /**
     * 点击 用户协议
     */
    void clickAgreement();
}
