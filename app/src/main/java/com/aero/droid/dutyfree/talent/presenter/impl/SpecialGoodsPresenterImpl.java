package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.activity.WriteComentsActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.ActiveInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.interactor.ComentsInteractor;
import com.aero.droid.dutyfree.talent.interactor.SpecialGoodsInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.ComentsInteractorImpl;
import com.aero.droid.dutyfree.talent.interactor.impl.SpecialGoodsInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.ComentsPresenter;
import com.aero.droid.dutyfree.talent.presenter.SpecialGoodsPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.ComentsView;
import com.aero.droid.dutyfree.talent.view.SpecialGoodsView;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : (专场，专题，海报) 逻辑控制 实现类
 */
public class SpecialGoodsPresenterImpl implements SpecialGoodsPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private SpecialGoodsView specialGoodsView;
    private SpecialGoodsInteractor interactor;

    public SpecialGoodsPresenterImpl(Activity activity, SpecialGoodsView specialGoodsView) {
        this.activity = activity;
        this.specialGoodsView = specialGoodsView;
        interactor = new SpecialGoodsInteractorImpl(activity, this);
    }

    @Override
    public void getGoodsList(String log_tag,String activeId,String url) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("activeId",activeId);
        interactor.getGoodsList(log_tag, Constants.EVENT_GET_DATA,url, params);
    }

    @Override
    public void refreshGoodsList(String log_tag,String activeId,String url) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("activeId",activeId);
        interactor.getGoodsList(log_tag, Constants.EVENT_REFRESH_DATA,url, params);
    }

    @Override
    public void loadGoodsList(String log_tag,String activeId,String url) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("activeId",activeId);
        interactor.getGoodsList(log_tag, Constants.EVENT_LOAD_DATA,url, params);
    }


    @Override
    public void onSuccess(int event_tag, Object object) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            specialGoodsView.showGoodsListData((ActiveInfo) object);
        } else if (event_tag == Constants.EVENT_REFRESH_DATA) {
            specialGoodsView.refreshGoodsListData(((ActiveInfo) object).getGoodsInfoList());
        }else if (event_tag == Constants.EVENT_LOAD_DATA) {
            specialGoodsView.loadGoodsListData(((ActiveInfo) object).getGoodsInfoList());
        }
    }


    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag,String msg) {
        specialGoodsView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
