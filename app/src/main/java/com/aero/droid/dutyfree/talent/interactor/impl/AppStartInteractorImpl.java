package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.aero.droid.dutyfree.talent.activity.MainActivity;
import com.aero.droid.dutyfree.talent.activity.ViewpageActivity;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.interactor.AppStartInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.SharePreUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/11/27
 * Desc :启动页实现类
 */
public class AppStartInteractorImpl extends ShopBagInteractorImpl implements AppStartInteractor {

    public AppStartInteractorImpl(BaseMultiLoadedListener<Object> listener) {
        super(listener);
    }

    @Override
    public void go2MainActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void go2ViewPagerActivity(Context context) {
        context.startActivity(new Intent(context, ViewpageActivity.class));
    }

    @Override
    public Animation getImgAnimation(Context context) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.6f, 1.0f);
        alphaAnimation.setDuration(2000);
        return alphaAnimation;
    }

    @Override
    public void getSomeFunctionUrl(String log_tag, final Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.GLOBAL, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "appStart.设置一些页面URL = " + data.toString());
                if (data.has("help")) {//使用帮助
                    if (!TextUtils.isEmpty(data.optString("help"))) {
                        SharePreUtil.saveStringData(context, "helpUrl", data.optString("help"));
                    }
                }
                if (data.has("aboutUs")) {//关于我们
                    if (!TextUtils.isEmpty(data.optString("aboutUs"))) {
                        SharePreUtil.saveStringData(context, "aboutUrl", data.optString("aboutUs"));
                    }
                }
                if (data.has("buy")) {//购买须知
                    if (!TextUtils.isEmpty(data.optString("buy"))) {
                        SharePreUtil.saveStringData(context, "buyUrl", data.optString("buy"));
                    }
                }
                if (data.has("agreement")) {//用户协议
                    if (!TextUtils.isEmpty(data.optString("agreement"))) {
                        SharePreUtil.saveStringData(context, "agreementUrl", data.optString("agreement"));
                    }
                }
                if (data.has("serviceTel")) {//服务电话
                    if (!TextUtils.isEmpty(data.optString("serviceTel"))) {
                        SharePreUtil.saveStringData(context, "serviceTel", data.optString("serviceTel"));
                    }
                }
                if (data.has("shareTip")) {//app分享文案
                    if (!TextUtils.isEmpty(data.optString("shareTip"))) {
                        SharePreUtil.saveStringData(context, "shareTip", data.optString("shareTip"));
                    }
                }
                if (data.has("inviteTip")) {//邀请有礼
                    if (!TextUtils.isEmpty(data.optString("inviteTip"))) {
                        SharePreUtil.saveStringData(context, "inviteTip", data.optString("inviteTip"));
                    }
                }
            }

            @Override
            public void onRespError(String code, String msg) {

            }
        });
    }


    @Override
    public void getShopBagData(String log_tag, int event_tag, Context context, HashMap<String, String> params) {
        super.getShopBagData(log_tag, event_tag, context, params);
    }
}
