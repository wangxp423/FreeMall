package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/12/20
 * Desc : 手机验证 逻辑控制器
 */
public interface PhoneverifyPresenter {
    /**
     * 登陆
     *
     * @param log_tag
     * @param phoneNumber 手机号
     * @param jobNum 工号
     * @param name 名字
     * @param secCode 验证码
     * @param inviteCode 邀请码
     */
    void commitVerify(String log_tag, String phoneNumber,String jobNum,String name,String secCode,String inviteCode);
    /**
     * 完成 注册
     *
     * @param log_tag
     * @param phoneNumber 手机号
     * @param password 密码
     * @param secCode 验证码
     * @param inviteCode 邀请码
     */
    void clickRegister(String log_tag, String phoneNumber,String password,String secCode,String inviteCode);

    /**
     * 获取动态密码
     *
     * @param log_tag
     * @param phoneNumber 手机号
     * @param jobNum 工号
     * @param name 名字
     */
    void clickGetAutoCode(String log_tag, String phoneNumber,String jobNum,String name);

    /**
     * 开始倒计时
     */
    void startTime();

    /**
     * 检测手机号
     * @param telNo
     * @return
     */
    boolean checkTelNo(String telNo);
}
