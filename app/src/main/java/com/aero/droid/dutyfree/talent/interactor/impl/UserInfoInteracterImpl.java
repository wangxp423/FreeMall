package com.aero.droid.dutyfree.talent.interactor.impl;

import android.app.Activity;
import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.User;
import com.aero.droid.dutyfree.talent.interactor.LoginInteractor;
import com.aero.droid.dutyfree.talent.interactor.UserInfoInteractor;
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
 * Desc : 用户资料 交互器 实现类
 */
public class UserInfoInteracterImpl implements UserInfoInteractor {
    private Activity activity;
    protected BaseMultiLoadedListener<Object> multiLoadedListener;

    public UserInfoInteracterImpl(Activity activity,BaseMultiLoadedListener loadedListener) {
        this.activity = activity;
        multiLoadedListener = loadedListener;
    }

    @Override
    public void uploadPhoto(String log_tag, final int event_tag, HashMap<String, String> params,String path) {
        OkHttpRequest.okHttpPostUploadImg(activity, Url.UPLOADPHOTO, params,path, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "上传头像 = " + data.toString());
                    multiLoadedListener.onSuccess(event_tag, msg);
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }
}
