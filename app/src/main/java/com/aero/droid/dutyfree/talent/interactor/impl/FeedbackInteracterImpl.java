package com.aero.droid.dutyfree.talent.interactor.impl;

import android.app.Activity;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.interactor.FeedbackInteractor;
import com.aero.droid.dutyfree.talent.interactor.UserInfoInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2016/1/12
 * Desc : 反馈 交互器 实现类
 */
public class FeedbackInteracterImpl implements FeedbackInteractor {
    private Activity activity;
    protected BaseMultiLoadedListener<Object> multiLoadedListener;

    public FeedbackInteracterImpl(Activity activity, BaseMultiLoadedListener loadedListener) {
        this.activity = activity;
        multiLoadedListener = loadedListener;
    }

    @Override
    public void commitFeedback(String log_tag, final int event_tag, HashMap<String, String> params, HashMap<String, String> imgParams) {
        OkHttpRequest.okHttpPostUploadImgs(activity, Url.FEEDBACK, params, imgParams, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "提交反馈 = " + data.toString());
                multiLoadedListener.onSuccess(event_tag, msg);
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag, msg);
            }
        });
    }
}
