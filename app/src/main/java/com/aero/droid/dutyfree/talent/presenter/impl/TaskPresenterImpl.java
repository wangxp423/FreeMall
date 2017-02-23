package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.TaskListInfo;
import com.aero.droid.dutyfree.talent.interactor.TaskInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.TaskInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.TaskPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.TaskView;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : 特权任务 逻辑控制 实现类
 */
public class TaskPresenterImpl implements TaskPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private TaskView taskView;
    private TaskInteractorImpl interactor;

    public TaskPresenterImpl(Activity activity, TaskView taskView) {
        this.activity = activity;
        this.taskView = taskView;
        interactor = new TaskInteractorImpl(activity, this);
    }

    @Override
    public void getTaskList(String log_tag) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getTaskList(log_tag, Constants.EVENT_GET_DATA, params);
    }

    @Override
    public void getPrivilege(String log_tag, String spId) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("spId",spId);
        interactor.getPrivilege(log_tag, Constants.EVENT_ADD_DATA, params);
    }


    @Override
    public void onSuccess(int event_tag, Object object) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            taskView.showTaskListData((List<TaskListInfo>) object);
            taskView.showTaskDesc(interactor.getTaskDesc());
        }else if (event_tag == Constants.EVENT_ADD_DATA){
            taskView.getPrivilegeSuccess((String)object);
        }
    }

    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag,String msg) {
        taskView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
