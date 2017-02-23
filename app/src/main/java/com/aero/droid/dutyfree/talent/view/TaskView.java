package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.TaskListInfo;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/10
 * Desc : 特权任务页面
 */
public interface TaskView extends BaseView {

    /**
     * 显示特权任务列表
     *
     * @param infoList
     */
    void showTaskListData(List<TaskListInfo> infoList);

    /**
     * 领取特权成功
     *
     * @param msg
     */
    void getPrivilegeSuccess(String msg);

    /**
     * 显示特权任务描述
     *
     * @param desc
     */
    void showTaskDesc(String desc);


    /**
     * 请求失败
     *
     * @param msg
     */
    void requestError(String msg);
}
