package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.interactor.MyCollectInteractor;
import com.aero.droid.dutyfree.talent.interactor.MyCouponsInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;
import com.aero.droid.dutyfree.talent.view.MyCouponsView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的优惠券 交互器实现类
 */
public class MyCouponsInteractorImpl implements MyCouponsInteractor {
    protected BaseMultiLoadedListener<Object> multiLoadedListener;

    public MyCouponsInteractorImpl(BaseMultiLoadedListener loadedListener) {
        multiLoadedListener = loadedListener;
    }

    @Override
    public void getCouponsList(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.MYCOUPON, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "获取我的优惠券列表 = " + data.toString());
                if (data.has("couponList")) {
                    List<CouponsInfo> infos = JsonAnalysis.getMyCouponsList(data.optJSONArray("couponList"));
                        multiLoadedListener.onSuccess(event_tag, infos);
                } else {
                    multiLoadedListener.onError(event_tag, msg);
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag, msg);
            }
        });
    }

    @Override
    public void deleteCoupons(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.DELETECOUPON, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "删除单个优惠券成功 = " + data.toString());
                if ("0".equals(code)) {
                    multiLoadedListener.onSuccess(event_tag, msg);
                } else {
                    multiLoadedListener.onError(event_tag, msg);
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag, msg);
            }
        });
    }

    @Override
    public void addCoupons(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.ADDCOUPON, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "添加/兑换 优惠券成功 = " + data.toString());
                if ("0".equals(code)) {
                    multiLoadedListener.onSuccess(event_tag, msg);
                } else {
                    multiLoadedListener.onError(event_tag, msg);
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag, msg);
            }
        });
    }
}
