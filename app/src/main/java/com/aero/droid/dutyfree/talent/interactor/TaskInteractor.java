package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : 特权任务页 交互器
 */
public interface TaskInteractor {
    /**
     * 特权任务列表
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void getTaskList(String log_tag, int event_tag, HashMap<String, String> params);
    /**
     * 领取特权
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void getPrivilege(String log_tag, int event_tag, HashMap<String, String> params);
}
