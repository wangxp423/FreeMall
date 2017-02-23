package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/11/27
 * Desc :
 */
public interface AppStartPresenter extends Presenter{
    /**
     * 是否是第一次进入
     * @return
     */
    boolean isFirstIn();

    /**
     * 第一次进入
     */
    void firstIn();

    /**
     * 不是第一次进入
     */
    void noFirstIn();

    /**
     * 首页启动要请求两个接口
     */
    void getRequest(String log_tag);
}
