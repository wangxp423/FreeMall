package com.aero.droid.dutyfree.talent.interactor.impl;

import android.app.Activity;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.TaskListInfo;
import com.aero.droid.dutyfree.talent.interactor.TaskInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : 特权任务 交互器实现类
 */
public class TaskInteractorImpl implements TaskInteractor {
    protected Activity activity;
    protected BaseMultiLoadedListener<Object> multiLoadedListener;
    private String taskDesc;

    public TaskInteractorImpl(Activity activity, BaseMultiLoadedListener loadedListener) {
        this.activity = activity;
        multiLoadedListener = loadedListener;
    }

    @Override
    public void getTaskList(String log_tag, final int event_tag, HashMap<String, String> params) {
//        String json = AssetsUtil.getFromAssets(activity,"discountArea.json");
//        try {
//            JSONObject result = new JSONObject(json.trim());
//            JSONObject data = result.optJSONObject("JSON");
//            List<DiscountInfo> infos = JsonAnalysis.getDiscountList(data.optJSONArray("discList"));
//            multiLoadedListener.onSuccess(event_tag, infos);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        OkHttpRequest.okHttpGet(activity, Url.PRIVILEGELIST, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "特权任务列表 = " + data.toString());
                if (data.has("spInfo")) {
                    JSONObject spInfo = data.optJSONObject("spInfo");
                    if (spInfo.has("desc"))
                        taskDesc = spInfo.optString("desc");
                    List<TaskListInfo> infos = JsonAnalysis.getTaskListInfo(spInfo.optJSONArray("spList"));
                    multiLoadedListener.onSuccess(event_tag, infos);
                } else {
                    multiLoadedListener.onError(event_tag,msg);
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    @Override
    public void getPrivilege(String log_tag, final int event_tag, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(activity, Url.OBTAINPRIVILEGE, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "领取特权 = " + data.toString());
                multiLoadedListener.onSuccess(event_tag, msg);
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }


}
