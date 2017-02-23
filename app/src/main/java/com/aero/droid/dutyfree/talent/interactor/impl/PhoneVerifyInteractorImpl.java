package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.User;
import com.aero.droid.dutyfree.talent.interactor.PhoneVerifyInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/3
 * Desc : 手机验证 交互器 实现类
 */
public class PhoneVerifyInteractorImpl extends LoginInteracterImpl implements PhoneVerifyInteractor {
    public PhoneVerifyInteractorImpl(BaseMultiLoadedListener loadedListener) {
        super(loadedListener);
    }
    @Override
    public void getAuthCode(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.AUTHSECURITY, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "获取手机验证验证码 = " + data.toString());
                multiLoadedListener.onSuccess(event_tag, msg);
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }

    @Override
    public void verify(String log_tag, final int event_tag, final Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpPost(context, Url.AUTHENTICATION, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "完成验证 = " + data.toString());
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
    public void register(String log_tag, final int event_tag, final Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpPost(context, Url.PHONEREGISTER, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "完成注册 = " + data.toString());
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
}
