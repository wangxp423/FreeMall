package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.OrderInfo;
import com.aero.droid.dutyfree.talent.bean.OrderListInfo;
import com.aero.droid.dutyfree.talent.interactor.MyOrderInteractor;
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
 * Date : 2015/12/4
 * Desc :  我的订单 交互器实现类
 */
public class MyOrderInteractorImpl implements MyOrderInteractor {
    protected BaseMultiLoadedListener<Object> multiLoadedListener;

    public MyOrderInteractorImpl(BaseMultiLoadedListener loadedListener) {
        multiLoadedListener = loadedListener;
    }

    @Override
    public void getOrderList(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.ORDERLIST, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "获取订单列表 = " + data.toString());
                if (data.has("orderList")) {
                    List<OrderListInfo> infos = JsonAnalysis.getOrderList(data.optJSONArray("orderList"));
                    if (infos.size() > 0) {
                        multiLoadedListener.onSuccess(event_tag, infos);
                    } else {
                        multiLoadedListener.onEmpty(event_tag, infos);
                    }
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
    public void deleteOrder(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(
                context, Url.DELORDER, params, new RespListener() {
                    @Override
                    public void onRespSucc(JSONObject data, String code, String msg) {
                        LogUtil.v("JSON", "订单删除成功 = " + data.toString());
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
    public void cancelOrder(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(
                context, Url.DELORDER, params, new RespListener() {
                    @Override
                    public void onRespSucc(JSONObject data, String code, String msg) {
                        LogUtil.v("JSON", "取消订单成功 = " + data.toString());
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
}
