package com.aero.droid.dutyfree.talent.interactor.impl;

import android.app.Activity;
import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.User;
import com.aero.droid.dutyfree.talent.interactor.LoginInteractor;
import com.aero.droid.dutyfree.talent.interactor.MeInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.listener.TimeCountListener;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.TimeCountUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/2
 * Desc : 首页 我的  交互器实现类
 */
public class MeInteracterImpl implements MeInteractor {
    protected Activity activity;
    protected BaseMultiLoadedListener<Object> multiLoadedListener;

    public MeInteracterImpl(Activity activity,BaseMultiLoadedListener loadedListener) {
        this.activity = activity;
        multiLoadedListener = loadedListener;
    }

    @Override
    public void getUserInfo(final String log_tag, final int event_tag, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(activity, Url.MEMBERCENTER, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "获取用户信息 = " + data.toString());
                if (data.has("memberInfo")){
                    User user = JsonAnalysis.getLoginUser(data.optJSONObject("memberInfo"));
                    multiLoadedListener.onSuccess(event_tag, user);
                }else {
                    multiLoadedListener.onError(event_tag,msg);
                }

            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }
}
