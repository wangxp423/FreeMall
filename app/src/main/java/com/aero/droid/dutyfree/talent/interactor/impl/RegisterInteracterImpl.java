package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.AirportCompany;
import com.aero.droid.dutyfree.talent.interactor.LoginInteractor;
import com.aero.droid.dutyfree.talent.interactor.RegisterInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.listener.TimeCountListener;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.TimeCountUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/2
 * Desc : 注册页面 交互器 实现类
 */
public class RegisterInteracterImpl extends LoginInteracterImpl implements RegisterInteractor {
    protected BaseMultiLoadedListener<Object> multiLoadedListener;

    public RegisterInteracterImpl(BaseMultiLoadedListener loadedListener) {
        super(loadedListener);
        multiLoadedListener = loadedListener;
    }

    @Override
    public void register(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.AUTHUSER, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "空乘认证(校验工号姓名) = " + data.toString());
                multiLoadedListener.onSuccess(event_tag, msg);
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }

    @Override
    public void getPhoneSecurityCode(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.PHONESECURITYCODE, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "手机注册 发送验证码 = " + data.toString());
                multiLoadedListener.onSuccess(event_tag, msg);
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }

    @Override
    public void getAirportCompanyList(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.AIRLINELIST, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "获取航空公司列表 = " + data.toString());
                if ("0".equals(code)) {
                    if (data.has("airlineList")) {
                        JSONArray airportArray = data.optJSONArray("airlineList");
                        List<AirportCompany> airportList = JsonAnalysis.getAirlineList(airportArray);
                        if (airportList.size() < 1) {
                            AirportCompany company = new AirportCompany();
                            company.setName("国航");
                            company.setTwocode("CA");
                            AirportCompany company1 = new AirportCompany();
                            company1.setName("海航");
                            company1.setTwocode("HU");
                            airportList.add(company);
                            airportList.add(company1);
                        }
                        multiLoadedListener.onSuccess(event_tag, airportList);
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

}
