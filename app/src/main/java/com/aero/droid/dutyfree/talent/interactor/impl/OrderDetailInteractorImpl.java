package com.aero.droid.dutyfree.talent.interactor.impl;

import android.app.Activity;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.OrderDetail;
import com.aero.droid.dutyfree.talent.interactor.OrderDetailInteractor;
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
 * Date : 2015/12/15
 * Desc : 订单详情 交互器实现类
 */
public class OrderDetailInteractorImpl implements OrderDetailInteractor {
    protected BaseMultiLoadedListener<Object> multiLoadedListener;
    protected Activity activity;

    public OrderDetailInteractorImpl(Activity activity, BaseMultiLoadedListener loadedListener) {
        this.activity = activity;
        multiLoadedListener = loadedListener;
    }


    @Override
    public void getGoodsList(String log_tag, final int event_tag, HashMap<String, String> params) {
//        String json = AssetsUtil.getFromAssets(activity,"comentsList.json");
//        try {
//            JSONObject result = new JSONObject(json.trim());
//            JSONObject data = result.optJSONObject("JSON");
//            GoodComents coments = JsonAnalysis.getGoodComents(data.optJSONObject("coments"));
//            multiLoadedListener.onSuccess(event_tag, coments);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        OkHttpRequest.okHttpGet(activity, Url.ORDERDETAIL, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "订单详情 = " + data.toString());
                OrderDetail orderDetail = JsonAnalysis.getOrderDetail(data);
                    multiLoadedListener.onSuccess(event_tag, orderDetail);
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }
}
