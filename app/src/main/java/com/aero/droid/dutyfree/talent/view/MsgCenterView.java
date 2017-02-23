package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.MessageInfo;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2016/1/11
 * Desc : 消息中心 View
 */
public interface MsgCenterView extends BaseView{
    /**
     * 显示消息列表
     *
     * @param infos
     */
    void showMsgList(List<MessageInfo> infos);

    /**
     * 显示刷新的消息列表
     *
     * @param infos
     */
    void showRefreshMsgList(List<MessageInfo> infos);

    /**
     * 请求消息列表数据失败
     *
     * @param msg
     */
    void requestMsgError(String msg);

    /**
     * 刷新列表数据失败
     *
     * @param msg
     */
    void refreshMsgError(String msg);
}
