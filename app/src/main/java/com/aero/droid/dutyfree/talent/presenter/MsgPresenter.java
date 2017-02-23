package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2016/1/11
 * Desc : 消息中心 逻辑控制器
 */
public interface MsgPresenter {
    /**
     * 获取消息列表
     *
     * @param log_tag
     */
    void getMsgList(String log_tag);

    /**
     * 刷新消息列表
     *
     * @param log_tag
     */
    void refreshMsgList(String log_tag);

    /**
     * 点击消息分类
     *
     * @param type
     */
    void clickItem(String type);
}
