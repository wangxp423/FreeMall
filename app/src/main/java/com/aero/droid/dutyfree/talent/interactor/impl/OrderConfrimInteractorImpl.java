package com.aero.droid.dutyfree.talent.interactor.impl;

import android.app.Activity;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.AirportCompany;
import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.bean.FlightInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.interactor.ComentsInteractor;
import com.aero.droid.dutyfree.talent.interactor.OrderConfrimInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.AssetsUtil;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/15
 * Desc : 订单确认 交互器实现类
 */
public class OrderConfrimInteractorImpl implements OrderConfrimInteractor {
    protected BaseMultiLoadedListener<Object> multiLoadedListener;
    protected Activity activity;

    public OrderConfrimInteractorImpl(Activity activity, BaseMultiLoadedListener loadedListener) {
        this.activity = activity;
        multiLoadedListener = loadedListener;
    }


    @Override
    public void getAirportList(String log_tag, final int event_tag, HashMap<String, String> params) {
//        String json = AssetsUtil.getFromAssets(activity,"comentsList.json");
//        try {
//            JSONObject result = new JSONObject(json.trim());
//            JSONObject data = result.optJSONObject("JSON");
//            GoodComents coments = JsonAnalysis.getGoodComents(data.optJSONObject("coments"));
//            multiLoadedListener.onSuccess(event_tag, coments);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        OkHttpRequest.okHttpGet(activity, Url.AIRLINELIST, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "航空公司列表 = " + data.toString());
                if (data.has("airlineList")) {
                    List<AirportCompany> airportList = JsonAnalysis.getAirlineList(data.optJSONArray("airlineList"));
                    multiLoadedListener.onSuccess(event_tag, airportList);
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
    public void getCouponsList(String log_tag, final int event_tag, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(activity, Url.CANUSECOUPON, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "可使用优惠券 = " + data.toString());
                if ("0".equals(code)) {
                    if (data.has("couponList")) {
                        List<CouponsInfo> couponList = JsonAnalysis.getMyCouponsList(data.optJSONArray("couponList"));
                        multiLoadedListener.onSuccess(event_tag, couponList);
                    } else {
                        multiLoadedListener.onError(event_tag,msg);
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
    public void commitOrder(String log_tag, final int event_tag, HashMap<String, String> params) {
        OkHttpRequest.okHttpPost(activity, Url.SAVEORDER, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "提交订单 = " + data.toString());
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
    public void checkAirport(String log_tag, final int event_tag, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(activity, Url.SEARCHFLIGHT, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "查询航班 = " + data.toString());
                if ("0".equals(code)) {
                    if (data.has("flightInfo")) {
                        FlightInfo info = JsonAnalysis.getFlightInfo(data.optJSONObject("flightInfo"));
                        multiLoadedListener.onSuccess(event_tag, info);
                    } else {
                        multiLoadedListener.onEmpty(event_tag, msg);
                    }

                } else {
                    multiLoadedListener.onEmpty(event_tag, msg);
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onEmpty(event_tag, msg);
            }
        });
    }
}
