package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.interactor.PagerImgInteractor;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import java.util.HashMap;

/**
 * Created by wangxp on 2015/11/26.
 * App介绍轮播图 交互器
 */
public class PagerImgInteractorImpl implements PagerImgInteractor {
    @Override
    public void getPagerImgData(Context context, HashMap<String, String> params, RespListener listener) {
        //TODO 这里轮播图暂时用的本地的 随便定义一个URL
        OkHttpRequest.okHttpGet(context, Url.GLOBAL, params, listener);
    }
}
