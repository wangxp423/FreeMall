package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.interactor.PhoneVerifyInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.PhoneVerifyInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.listener.TimeCountListener;
import com.aero.droid.dutyfree.talent.presenter.LoginPresenter;
import com.aero.droid.dutyfree.talent.presenter.PhoneverifyPresenter;
import com.aero.droid.dutyfree.talent.util.MD5Kit;
import com.aero.droid.dutyfree.talent.util.PatternUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.view.PhoneVerifyView;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/3
 * Desc :
 */
public class PhoneVerifyPresenterImpl implements PhoneverifyPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private PhoneVerifyView verifyView;
    private PhoneVerifyInteractorImpl interactor;

    public PhoneVerifyPresenterImpl(Activity activity, PhoneVerifyView verifyView) {
        this.activity = activity;
        this.verifyView = verifyView;
        interactor = new PhoneVerifyInteractorImpl(this);
    }


    @Override
    public void commitVerify(String log_tag, String phoneNumber, String jobNum, String name, String secCode, String inviteCode) {
        HashMap<String, String> params = new HashMap<>();
        params.put("jobNum",jobNum);
        params.put("uname",name);
        params.put("telNo",phoneNumber);
        params.put("secCode",secCode);
        params.put("inviteCode",inviteCode);
        interactor.verify(log_tag, Constants.EVENT_GET_DATA, activity, params);
    }

    @Override
    public void clickRegister(String log_tag, String phoneNumber, String password, String secCode, String inviteCode) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone",phoneNumber);
        params.put("password", MD5Kit.md5(password));
        params.put("code",secCode);
        params.put("inviteCode",inviteCode);
        params.put("deviceType", "-3");
        interactor.register(log_tag, Constants.EVENT_REFRESH_DATA, activity, params);
    }

    @Override
    public void clickGetAutoCode(String log_tag, String phoneNumber,String jobNum,String name) {
        HashMap<String, String> params = new HashMap<>();
        params.put("jobNum",jobNum);
        params.put("uname",name);
        params.put("telNo",phoneNumber);
        interactor.getAuthCode(log_tag, Constants.EVENT_ADD_DATA, activity, params);
    }


    @Override
    public void startTime() {
        interactor.startTime(60000, 1000, new TimeCountListener() {
            @Override
            public void onFinish() {
                verifyView.clickAbleGetAuthBtn();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                verifyView.unclickAbleGetAuthBtn(millisUntilFinished);
            }
        });
    }

    @Override
    public boolean checkTelNo(String telNo) {
        if (TextUtils.isEmpty(telNo)){
            ToastUtil.showShortToast(activity,activity.getResources().getString(R.string.phone_verify_hint));
            return false;
        }else {
            if (PatternUtil.isMobileNO(telNo)){
                return true;
            }else {
                ToastUtil.showShortToast(activity,activity.getResources().getString(R.string.phone_phone_error));
                return false;
            }
        }
    }

    @Override
    public void onSuccess(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            verifyView.getVerifySuccess();
        } else if (event_tag == Constants.EVENT_ADD_DATA) {
            verifyView.getAuthCodeSuccess();
        }else if (event_tag == Constants.EVENT_REFRESH_DATA) {
            verifyView.registerSuccess();
        }
    }

    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag,String msg) {
        verifyView.requestErroe(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
