package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2016/1/8
 * Desc : 用户资料 逻辑控制器
 */
public interface UserInfoPresenter {

    /**
     * 点击修改昵称
     */
    void clickChangeName(String name);

    /**
     * 点击绑定手机号
     */
    void clickBindPhone();

    /**
     * 点击绑定微信
     */
    void clickBindWeixin();

    /**
     * 点击推出账号
     */
    void clickExit();

    /**
     * 上传头像
     *
     * @param tag_log
     * @param image
     */
    void uploadPhoto(String tag_log, String image);
}
