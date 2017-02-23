package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2016/1/11
 * Desc : 消息中心 交互器
 */
public interface MsgInteractor {
    /**
     * 消息列表
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void getMsgList(String log_tag, int event_tag, HashMap<String, String> params);
}
