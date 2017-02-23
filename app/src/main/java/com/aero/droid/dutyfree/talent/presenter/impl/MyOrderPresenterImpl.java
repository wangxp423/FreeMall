package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.OrderInfo;
import com.aero.droid.dutyfree.talent.bean.OrderListInfo;
import com.aero.droid.dutyfree.talent.interactor.LoginInteractor;
import com.aero.droid.dutyfree.talent.interactor.MyOrderInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.LoginInteracterImpl;
import com.aero.droid.dutyfree.talent.interactor.impl.MyOrderInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.MyOrderPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.LoginView;
import com.aero.droid.dutyfree.talent.view.MyOrderView;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc :
 */
public class MyOrderPresenterImpl implements MyOrderPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private MyOrderView orderView;
    private MyOrderInteractor interactor;

    public MyOrderPresenterImpl(Activity activity, MyOrderView orderView) {
        this.activity = activity;
        this.orderView = orderView;
        interactor = new MyOrderInteractorImpl(this);
    }

    @Override
    public void getOrderList(String log_tag) {
        orderView.showLoading(null);
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getOrderList(log_tag, Constants.EVENT_GET_DATA, activity, params);
    }

    @Override
    public void clickDeleteOrder(String log_tag, String orderId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId",orderId);
        interactor.deleteOrder(log_tag, Constants.EVENT_DELETE_DATA, activity, params);
    }

    @Override
    public void clickCancelOrder(String log_tag, String orderId) {
        HashMap<String, String> params = new HashMap<>();
        interactor.getOrderList(log_tag, Constants.EVENT_REMOVE_DATA, activity, params);
    }

    @Override
    public void onSuccess(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            List<OrderListInfo> infoList = (List<OrderListInfo>) data;
            orderView.showOrderList(infoList);
        } else if (event_tag == Constants.EVENT_DELETE_DATA) {
            orderView.deleteOrderSuccess((String) data);
        }else if (event_tag == Constants.EVENT_REMOVE_DATA) {
            orderView.cancelOrderSuccess((String) data);
        }
    }

    @Override
    public void onEmpty(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            orderView.showEmpty("",0);
        }
    }

    @Override
    public void onError(int event_tag,String msg) {
        orderView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
