package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.activity.MainActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.interactor.UserInfoInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.UserInfoInteracterImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.UserInfoPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.UserInfoView;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2016/1/8
 * Desc :
 */
public class UserInfoPresenterImpl implements UserInfoPresenter,BaseMultiLoadedListener<Object> {
    private Activity activity;
    private UserInfoView userInfoView;
    private UserInfoInteractor interactor;
    public UserInfoPresenterImpl(Activity activity,UserInfoView userInfoView){
        this.activity = activity;
        this.userInfoView = userInfoView;
        interactor = new UserInfoInteracterImpl(activity,this);
    }
    @Override
    public void clickChangeName(String name) {

    }

    @Override
    public void clickBindPhone() {

    }

    @Override
    public void clickBindWeixin() {

    }

    @Override
    public void clickExit() {
        UserUtil.setUserInfo(activity, "");
        UserUtil.setUserId(activity, "");
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("jumpType",0);
        intent.putExtras(bundle);
        intent.setClass(activity,MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void uploadPhoto(String tag_log, String image) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.uploadPhoto(tag_log, Constants.EVENT_GET_DATA, params,image);
    }

    @Override
    public void onSuccess(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA){
            userInfoView.uploadPhotoS((String)data);
        }
    }

    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag, String msg) {

    }

    @Override
    public void onException(String msg) {

    }
}
