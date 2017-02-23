package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : 特权任务 逻辑控制器
 */
public interface TaskPresenter {
    /**
     * 获取特权任务列表
     *
     * @param log_tag
     */
    void getTaskList(String log_tag);

    /**
     * 获取特权
     *
     * @param log_tag
     * @param spId    特权ID
     */
    void getPrivilege(String log_tag, String spId);


}
