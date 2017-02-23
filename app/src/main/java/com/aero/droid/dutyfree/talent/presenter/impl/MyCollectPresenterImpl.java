package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.OrderInfo;
import com.aero.droid.dutyfree.talent.interactor.MyCollectInteractor;
import com.aero.droid.dutyfree.talent.interactor.MyOrderInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.MyCollectInteractorImpl;
import com.aero.droid.dutyfree.talent.interactor.impl.MyOrderInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.MyCollectPresenter;
import com.aero.droid.dutyfree.talent.presenter.MyOrderPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.MyCollectView;
import com.aero.droid.dutyfree.talent.view.MyOrderView;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的收藏 逻辑控制 实现类
 */
public class MyCollectPresenterImpl implements MyCollectPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private MyCollectView collectView;
    private MyCollectInteractor interactor;

    public MyCollectPresenterImpl(Activity activity, MyCollectView collectView) {
        this.activity = activity;
        this.collectView = collectView;
        interactor = new MyCollectInteractorImpl(this);
    }

    @Override
    public void getCollectList(String log_tag) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getCollectList(log_tag, Constants.EVENT_GET_DATA, activity, params);
    }

    @Override
    public void clickDeleteCollect(String log_tag, String goodsId) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("goodsId",goodsId);
        interactor.deleteCollect(log_tag, Constants.EVENT_DELETE_DATA, activity, params);
    }

    @Override
    public void onSuccess(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            List<GoodsInfo> infoList = (List<GoodsInfo>) data;
            collectView.showCollectList(infoList);
        } else if (event_tag == Constants.EVENT_DELETE_DATA) {
            collectView.deleteCollectSuccess((String) data);
        }
    }

    @Override
    public void onEmpty(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            collectView.showEmpty("",0);
        }
    }

    @Override
    public void onError(int event_tag,String msg) {
        collectView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
