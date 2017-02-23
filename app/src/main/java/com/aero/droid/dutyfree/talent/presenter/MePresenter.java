package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/01/06
 * Desc : 首页 我的 逻辑控制器
 */
public interface MePresenter {
    /**
     * 登陆
     *
     * @param log_tag
     * @param memberId 用户Id
     */
    void getUserData(String log_tag, String memberId);

}
