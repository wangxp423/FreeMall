package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.WriteComentsActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.AirportCompany;
import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.bean.FlightInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.interactor.ComentsInteractor;
import com.aero.droid.dutyfree.talent.interactor.OrderConfrimInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.ComentsInteractorImpl;
import com.aero.droid.dutyfree.talent.interactor.impl.OrderConfrimInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.ComentsPresenter;
import com.aero.droid.dutyfree.talent.presenter.OrderConfrimPresenter;
import com.aero.droid.dutyfree.talent.util.AppUtil;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.PatternUtil;
import com.aero.droid.dutyfree.talent.util.StringUtils;
import com.aero.droid.dutyfree.talent.util.TimeFormatUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.ComentsView;
import com.aero.droid.dutyfree.talent.view.OrderConfrimView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/15
 * Desc : 订单确认 逻辑控制 实现类
 */
public class OrderConfrimPresenterImpl implements OrderConfrimPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private OrderConfrimView confrimView;
    private OrderConfrimInteractor interactor;

    public OrderConfrimPresenterImpl(Activity activity, OrderConfrimView confrimView) {
        this.activity = activity;
        this.confrimView = confrimView;
        interactor = new OrderConfrimInteractorImpl(activity, this);
    }

    @Override
    public void getAirportList(String log_tag) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getAirportList(log_tag, Constants.EVENT_GET_DATA, params);
    }

    @Override
    public void getCouponsList(String log_tag, List<GoodsInfo> goodsInfoList) {
        HashMap<String, String> params = new HashMap<>();
        try {
            JSONArray goodsArray = new JSONArray();
            for (int i = 0; i < goodsInfoList.size(); i++) {
                GoodsInfo info = goodsInfoList.get(i);
                JSONObject good = new JSONObject();
                good.put("goodsId", info.getId());
                good.put("count", TextUtils.isEmpty(info.getQuantity()) ? "1" : info.getQuantity());
                goodsArray.put(good);
            }
            params.put("goodsInfo", goodsArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getCouponsList(log_tag, Constants.EVENT_ADD_DATA, params);
    }

    @Override
    public void checkCAAirport(String log_tag, String twoCode, String flightDate, String flightNum) {
        HashMap<String, String> params = new HashMap<>();
//        String userId = UserUtil.getUserId(activity);
//        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("flightDate", flightDate);
        params.put("twoCode", twoCode);
        params.put("flightNo", flightNum);
        interactor.checkAirport(log_tag, Constants.EVENT_REFRESH_DATA, params);
    }

    @Override
    public void checkHUAirport(String log_tag, String twoCode, String flightDate, String flightNum) {
        HashMap<String, String> params = new HashMap<>();
//        String userId = UserUtil.getUserId(activity);
//        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("flightDate", flightDate);
        params.put("twoCode", twoCode);
        params.put("flightNo", flightNum);
        interactor.checkAirport(log_tag, Constants.EVENT_REMOVE_DATA, params);
    }

    @Override
    public void commitOrder(String log_tag, String mobile, String takeOffDate, String fightNo, String payMoney, String memberName, String couponId, List<GoodsInfo> goodsInfoList) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        try {
            JSONObject orderInfo = new JSONObject();
            JSONArray goodsArray = new JSONArray();
            orderInfo.put("deviceNo", AppUtil.getDeviceId(activity));
            orderInfo.put("mobile", mobile);
            orderInfo.put("takeOffDate", takeOffDate);
            orderInfo.put("fightNo", fightNo);
            orderInfo.put("payMoney", payMoney);
            orderInfo.put("memberName", memberName);
            orderInfo.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
            orderInfo.put("status", "0");
            orderInfo.put("srcType", "5");
            orderInfo.put("couponId", TextUtils.isEmpty(couponId) ? "0" : couponId);
            orderInfo.put("payTime", TimeFormatUtil.translateDate(System.currentTimeMillis()));
            for (int i = 0; i < goodsInfoList.size(); i++) {
                GoodsInfo info = goodsInfoList.get(i);
                JSONObject good = new JSONObject();
                good.put("goodsId", info.getId());
                good.put("goodsName", info.getGoodsName());
                good.put("srcType", TextUtils.isEmpty(info.getSrcType()) ? "0" : info.getSrcType());
                good.put("srcId", TextUtils.isEmpty(info.getSrcId()) ? "0" : info.getSrcId());
                good.put("activeId", info.getActiveId());
                good.put("amount", info.getQuantity());
                good.put("price", info.getPrice_app_dollar());
                int money = StringUtils.parseInt(StringUtils.parseInt(info.getGoodsPrice())>0 ? info.getGoodsPrice() :info.getPrice_app_dollar());
                good.put("money", String.valueOf(money * StringUtils.parseInt(info.getQuantity())));
                goodsArray.put(good);
            }
            orderInfo.put("goods", goodsArray);
            params.put("orderInfo", orderInfo.toString());
            LogUtil.t("订单参数 = " + orderInfo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        interactor.commitOrder(log_tag, Constants.EVENT_LOAD_DATA, params);
    }

    public boolean checkAirportData(String twoCode, String flightDate, String flightNum) {
        if (!TextUtils.isEmpty(twoCode)) {
            if (!TextUtils.isEmpty(flightDate)) {
                if (!TextUtils.isEmpty(flightNum)) {
                    return true;
                } else {
                    ToastUtil.showLongToast(activity, activity.getResources().getString(R.string.order_confirm_airport_num_hint));
                    return false;
                }
            } else {
                ToastUtil.showLongToast(activity, activity.getResources().getString(R.string.order_confirm_date_hint));
                return false;
            }
        } else {
            ToastUtil.showLongToast(activity, activity.getResources().getString(R.string.order_confrim_choise_airport));
            return false;
        }
    }

    public boolean checkUserData(String name, String phone) {
        if (!TextUtils.isEmpty(name)) {
            if (!TextUtils.isEmpty(phone)) {
                if (!PatternUtil.isMobileNO(phone)) {
                    ToastUtil.showLongToast(activity, activity.getResources().getString(R.string.order_confirm_phone_error));
                    return false;
                } else {
                    return true;
                }
            } else {
                ToastUtil.showLongToast(activity, activity.getResources().getString(R.string.order_confirm_phone_hint));
                return false;
            }
        } else {
            ToastUtil.showLongToast(activity, activity.getResources().getString(R.string.order_confirm_name_hint));
            return false;
        }
    }


    @Override
    public void onSuccess(int event_tag, Object object) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            confrimView.showAirportListData((List<AirportCompany>) object);
        } else if (event_tag == Constants.EVENT_ADD_DATA) {
            confrimView.showCouponsListData((List<CouponsInfo>) object);
        } else if (event_tag == Constants.EVENT_LOAD_DATA) {
            confrimView.confrimOrderSuccess((String) object);
        } else if (event_tag == Constants.EVENT_REFRESH_DATA) {
            confrimView.checkCAAirportSuccess((FlightInfo) object);
        } else if (event_tag == Constants.EVENT_REMOVE_DATA) {
            confrimView.checkHUAirportSuccess((FlightInfo) object);
        }
    }


    @Override
    public void onEmpty(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_REFRESH_DATA || event_tag == Constants.EVENT_REMOVE_DATA) {
            confrimView.checkHUAirportFail((String) data);
        }

    }

    @Override
    public void onError(int event_tag,String msg) {
        confrimView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }

}
