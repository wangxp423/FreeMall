package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.AirportCompany;
import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.OrderDetail;
import com.aero.droid.dutyfree.talent.interactor.OrderConfrimInteractor;
import com.aero.droid.dutyfree.talent.interactor.OrderDetailInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.OrderConfrimInteractorImpl;
import com.aero.droid.dutyfree.talent.interactor.impl.OrderDetailInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.OrderConfrimPresenter;
import com.aero.droid.dutyfree.talent.presenter.OrderDetailPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.OrderConfrimView;
import com.aero.droid.dutyfree.talent.view.OrderDetailView;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/15
 * Desc : 订单详情 逻辑控制 实现类
 */
public class OrderDetailPresenterImpl implements OrderDetailPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private OrderDetailView detailView;
    private OrderDetailInteractor interactor;

    public OrderDetailPresenterImpl(Activity activity, OrderDetailView detailView) {
        this.activity = activity;
        this.detailView = detailView;
        interactor = new OrderDetailInteractorImpl(activity, this);
    }
    @Override
    public void getOrderDetail(String log_tag,String orderId) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("orderId",orderId);
        interactor.getGoodsList(log_tag, Constants.EVENT_GET_DATA, params);
    }


    @Override
    public void onSuccess(int event_tag, Object object) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            detailView.showGoodsListData((OrderDetail) object);
        }
    }


    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag,String msg) {
        detailView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }

}
