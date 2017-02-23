package com.aero.droid.dutyfree.talent.view;

/**
 * Author : wangxp
 * Date : 2016/1/8
 * Desc : 用户资料 View
 */
public interface UserInfoView {
    /**
     * 修改昵称成功
     */
    void changeNameSuccess();

    /**
     * 绑定手机号成功
     */
    void bindPhoneSuccess();

    /**
     * 绑定微信成功
     */
    void bindWeixinSuccess();

    /**
     * 上传头像成功
     *
     * @param msg
     */
    void uploadPhotoS(String msg);

    /**
     * 请求失败
     *
     * @param msg
     */
    void requestFail(String msg);
}
