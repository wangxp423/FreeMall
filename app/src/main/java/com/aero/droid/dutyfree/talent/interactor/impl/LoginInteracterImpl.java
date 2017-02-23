package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.User;
import com.aero.droid.dutyfree.talent.interactor.LoginInteractor;
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
 * Desc :
 */
public class LoginInteracterImpl implements LoginInteractor {
    protected BaseMultiLoadedListener<Object> multiLoadedListener;

    public LoginInteracterImpl(BaseMultiLoadedListener loadedListener) {
        multiLoadedListener = loadedListener;
    }

    /**
     * Url.LOGIN 是常规登陆
     * Url.PHONELOGIN 是手机号码登陆
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    @Override
    public void login(String log_tag, final int event_tag, final Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpPost(context, Url.PHONELOGIN, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "登陆成功 = " + data.toString());
                if (data.has("memberInfo")){
                    User user = JsonAnalysis.getLoginUser(data.optJSONObject("memberInfo"));
                    multiLoadedListener.onSuccess(event_tag, user);
                    UserUtil.setUserInfo(context, data.optJSONObject("memberInfo").toString());
                    UserUtil.setUserId(context,user.getMemberId());
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

    @Override
    public void thirdlogin(String log_tag, final int event_tag, final Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpPost(context, Url.THIRDLOGIN, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "登陆成功 = " + data.toString());
                if (data.has("memberInfo")){
                    User user = JsonAnalysis.getLoginUser(data.optJSONObject("memberInfo"));
                    multiLoadedListener.onSuccess(event_tag, user);
                    UserUtil.setUserInfo(context, data.optJSONObject("memberInfo").toString());
                    UserUtil.setUserId(context,user.getMemberId());
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

    @Override
    public void getAutoPassword(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.LOGINSECURITY, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "获取登陆验证码 = " + data.toString());
                if ("0".equals(code)) {
                    multiLoadedListener.onSuccess(event_tag, msg);
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

    @Override
    public void startTime(long millisInFuture, long countDownInterval, TimeCountListener listener) {
        TimeCountUtil timeCount = new TimeCountUtil(millisInFuture, countDownInterval, listener);
        timeCount.start();
    }
}
