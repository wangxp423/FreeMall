package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.PhoneverifyActivity;
import com.aero.droid.dutyfree.talent.activity.WebViewActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.AirportCompany;
import com.aero.droid.dutyfree.talent.interactor.RegisterInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.RegisterInteracterImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.listener.TimeCountListener;
import com.aero.droid.dutyfree.talent.presenter.RegisterPresenter;
import com.aero.droid.dutyfree.talent.util.PatternUtil;
import com.aero.droid.dutyfree.talent.util.TimeCountUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.view.RegisterView;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/3
 * Desc : 空乘认证逻辑控制 器实现类
 */
public class RegisterPresenterImpl implements RegisterPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private RegisterView registerView;
    private RegisterInteracterImpl interactor;
    private TimeCountUtil countUtil;

    public RegisterPresenterImpl(Activity activity, RegisterView registerView) {
        this.activity = activity;
        this.registerView = registerView;
        interactor = new RegisterInteracterImpl(this);
    }

    @Override
    public void clickVerify(String log_tag, String twoCode, String workNum, String name) {
        HashMap<String, String> params = new HashMap<>();
        params.put("jobNum", workNum);
        params.put("uname", name);
        interactor.register(log_tag, Constants.EVENT_GET_DATA, activity, params);
    }

    @Override
    public void clickAuthCodeButton(String log_tag, String phone) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("codeType", "register");
        interactor.getPhoneSecurityCode(log_tag, Constants.EVENT_REFRESH_DATA, activity, params);
    }

    @Override
    public void startTime() {
        countUtil = new TimeCountUtil(60000, 1000, new TimeCountListener() {
            @Override
            public void onFinish() {
                registerView.clickAbleGetAuthBtn();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                registerView.unclickAbleGetAuthBtn(millisUntilFinished);
            }
        });
        countUtil.start();
    }

    public void finishTime() {
        if (null != countUtil)
            countUtil.cancel();
    }

    @Override
    public void getAirportCompanyList(String log_tag) {
        HashMap<String, String> params = new HashMap<>();
        interactor.getAirportCompanyList(log_tag, Constants.EVENT_ADD_DATA, activity, params);
    }

    @Override
    public boolean checkTelNo(String telNo) {
        if (TextUtils.isEmpty(telNo)) {
            ToastUtil.showShortToast(activity, activity.getResources().getString(R.string.phone_verify_hint));
            return false;
        } else {
            if (PatternUtil.isMobileNO(telNo)) {
                return true;
            } else {
                ToastUtil.showShortToast(activity, activity.getResources().getString(R.string.phone_phone_error));
                return false;
            }
        }
    }

    @Override
    public void clickNextStep(String jobNum, String name, String inviteCode) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
//        bundle.putString("jobNum",jobNum);
//        bundle.putString("uname",name);
//        bundle.putString("inviteCode",inviteCode);
        bundle.putString("phone", jobNum);
        bundle.putString("authCode", name);
        intent.putExtras(bundle);
        intent.setClass(activity, PhoneverifyActivity.class);
        activity.startActivityForResult(intent, Constants.EVENT_START_ACTIVITY);
    }

    @Override
    public void clickInvatationCode() {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("invitationCode", "invitationCode");
        activity.startActivity(intent);
    }

    @Override
    public void clickAgreement() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("url", "http://www.aerotq.com/coogo/userpro.html");
        intent.setClass(activity, WebViewActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public boolean checkInputData(String workNum, String name) {
        if (TextUtils.isEmpty(workNum)) {
            ToastUtil.showLongToast(activity, activity.getResources().getString(R.string.phone_verify_hint));
            return false;
        } else {
            if (TextUtils.isEmpty(name)) {
                ToastUtil.showLongToast(activity, activity.getResources().getString(R.string.find_password_authcode_hint));
                return false;
            } else {
                return true;
            }

        }
    }

    @Override
    public void onSuccess(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            registerView.verifySuccess();
        } else if (event_tag == Constants.EVENT_ADD_DATA) {
            List<AirportCompany> companyList = (List<AirportCompany>) data;
            registerView.showAirportCompany(companyList);
        } else if (event_tag == Constants.EVENT_REFRESH_DATA) {
            registerView.getCodeSuccess();
        }
    }

    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag, String msg) {
        registerView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
