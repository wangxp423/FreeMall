package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.interactor.HandpickInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.HandpickInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.HandpickPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.HandpickView;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 搜索商品 逻辑控制 实现类
 */
public class HandpickPresenterImpl extends BasePresenterImpl implements HandpickPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private HandpickView handpickView;
    private HandpickInteractorImpl interactor;

    public HandpickPresenterImpl(Activity activity, HandpickView handpickView) {
        super(activity,handpickView);
        this.activity = activity;
        this.handpickView = handpickView;
        interactor = new HandpickInteractorImpl(activity,this);
    }

    @Override
    public void getGoodsList(String log_tag) {
        handpickView.showLoading(null);
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getGoodsList(log_tag, Constants.EVENT_GET_DATA, activity, params);
    }

    @Override
    public void getOtherGoodsList(String log_tag) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getOtherGoodsList(log_tag, Constants.EVENT_ADD_DATA, activity, params);
    }

    @Override
    public void addFavorite(String log_tag, String goodId) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("goodsId", goodId);
        interactor.addCollect(log_tag, Constants.EVENT_ADD_FAVO, params);
    }

    /**
     * 刷新轮播图
     * @param log_tag
     */
    public void refreshGoodsList(String log_tag){
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getGoodsList(log_tag, Constants.EVENT_REFRESH_DATA, activity, params);
    }
    /**
     * 刷新其他分类数据
     * @param log_tag
     */
    public void refreshOtherGoodsList(String log_tag){
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getOtherGoodsList(log_tag, Constants.EVENT_LOAD_DATA, activity, params);
    }


    @Override
    public void onSuccess(int event_tag, Object object) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            handpickView.showGoodsList((List<HandpickBanner>) object);
        }else if (event_tag == Constants.EVENT_ADD_DATA){
            handpickView.showOhterGoodsList((List<HandpickType>) object);
        }else if (event_tag == Constants.EVENT_REFRESH_DATA){
            handpickView.refreshGoodsList((List<HandpickBanner>) object);
        }else if (event_tag == Constants.EVENT_LOAD_DATA){
            handpickView.refreshOhterGoodsList((List<HandpickType>) object);
        }else if (event_tag == Constants.EVENT_ADD_FAVO){
            handpickView.addFavoSuccess((String) object);
        }
        getDataSuccess();
    }

    @Override
    public void onEmpty(int event_tag, Object data) {

    }


    @Override
    public void onError(int event_tag,String msg) {
        getDataError();
        handpickView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
