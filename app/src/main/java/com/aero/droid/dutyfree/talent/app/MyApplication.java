package com.aero.droid.dutyfree.talent.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.UmengRegistrar;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.Map;

/**
 * 全局应用程序类
 *
 * @author wangxp 2015/11/12
 */
public class MyApplication extends Application {

    private static MyApplication INSTANCE = null;

    public static synchronized MyApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        INSTANCE = this;
        super.onCreate();
        initShareWeixin();
        initUmengNotifityHandler();
    }


    /**
     * 初始化 微信分享
     */
    private void initShareWeixin() {

        // 支持微信朋友圈
        String appId = "wx775967fe97775153";
        String appSecret = "81f3a811e1755a33f44d9a78972be176";
        UMWXHandler wxHandler = new UMWXHandler(this, appId, appSecret);
        wxHandler.addToSocialSDK();
        wxHandler.setRefreshTokenAvailable(false);
        UMWXHandler wxCircleHandler = new UMWXHandler(this, appId,
                appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    private void initUmengNotifityHandler() {
        //增加友盟消息推送
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.enable();
        String device_token = UmengRegistrar.getRegistrationId(this);
        LogUtil.t("device_token = " + device_token);
        /**
         * 自定义消息打开动作
         * 该Handler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * public void launchApp(Context context, UMessage msg); sdk已处理
         * public void openUrl(Context context, UMessage msg); sdk已处理
         * public void openActivity(Context context, UMessage msg); sdk已处理
         * public void dealWithCustomAction(Context context, UMessage msg); 自定义处理
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void launchApp(Context context, UMessage uMessage) {
                super.launchApp(context, uMessage);
                LogUtil.t("launchApp消息 = " + uMessage.after_open);
            }

            @Override
            public void openUrl(Context context, UMessage uMessage) {
                super.openUrl(context, uMessage);
                LogUtil.t("openUrl消息 = " + uMessage.after_open);
            }

            @Override
            public void openActivity(Context context, UMessage uMessage) {
                super.openActivity(context, uMessage);
                LogUtil.t("openActivity消息 = " + uMessage.activity);
                LogUtil.t("openActivity消息 = " + uMessage.extra.toString());
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                Map<String,String> params = uMessage.extra;
                if (params != null && params.size() > 0) {
                    for (Map.Entry<String, String> item : params.entrySet()) {
                        bundle.putString(item.getKey(), item.getValue());
                    }
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClassName(getApplicationContext(), uMessage.activity);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                LogUtil.t("dealWithCustomAction消息 = " + msg.custom);
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        /**
         * 消息接收处理(打开or忽略)
         * public void dealWithNotificationMessage(Context context, UMessage msg); sdk默认处理
         * public void dealWithCustomMessage(Context context, UMessage msg); 自定义处理
         */
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler(getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                            LogUtil.t("uMeng消息 = " + msg.custom);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                            LogUtil.t("uMeng消息 = " + msg.custom);
                        }
//                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
    }
}
