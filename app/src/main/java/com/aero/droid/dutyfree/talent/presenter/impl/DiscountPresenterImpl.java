package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.DiscountInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.interactor.DiscountInteractor;
import com.aero.droid.dutyfree.talent.interactor.HandpickInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.DiscountInteractorImpl;
import com.aero.droid.dutyfree.talent.interactor.impl.HandpickInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.DiscountPresenter;
import com.aero.droid.dutyfree.talent.presenter.HandpickPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.DiscountView;
import com.aero.droid.dutyfree.talent.view.HandpickView;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 搜索商品 逻辑控制 实现类
 */
public class DiscountPresenterImpl  implements DiscountPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private DiscountView discountView;
    private DiscountInteractor interactor;

    public DiscountPresenterImpl(Activity activity, DiscountView discountView) {
        this.activity = activity;
        this.discountView = discountView;
        interactor = new DiscountInteractorImpl(this);
    }



    @Override
    public void getDiscountList(String log_tag) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getDiscountList(log_tag, Constants.EVENT_GET_DATA, activity, params);
    }

    @Override
    public void refreshDiscountList(String log_tag) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getDiscountList(log_tag, Constants.EVENT_REFRESH_DATA, activity, params);
    }

    @Override
    public void clickGoods(GoodsInfo info) {

    }

    @Override
    public void clickBuyBtn(GoodsInfo info) {

    }

    @Override
    public void onSuccess(int event_tag, Object object) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            discountView.showDiscountList((List<DiscountInfo>) object);
        }else if (event_tag == Constants.EVENT_REFRESH_DATA){
            discountView.refreshDiscountList((List<DiscountInfo>) object);
        }
    }


    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag,String msg) {
        discountView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
