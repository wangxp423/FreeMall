package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.User;
import com.aero.droid.dutyfree.talent.interactor.MeInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.MeInteracterImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.MePresenter;
import com.aero.droid.dutyfree.talent.view.MyCenterView;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/2
 * Desc :
 */
public class MePresenterImpl implements MePresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private MyCenterView myCenterView;
    private MeInteractor interactor;

    public MePresenterImpl(Activity activity, MyCenterView myCenterView) {
        this.activity = activity;
        this.myCenterView = myCenterView;
        interactor = new MeInteracterImpl(activity,this);
    }
    @Override
    public void getUserData(String log_tag, String memberId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("memberId", memberId);
        interactor.getUserInfo(log_tag, Constants.EVENT_GET_DATA, params);
    }

    @Override
    public void onSuccess(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            myCenterView.showUserData((User)data);
        }
    }

    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag, String msg) {
        myCenterView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }


}
