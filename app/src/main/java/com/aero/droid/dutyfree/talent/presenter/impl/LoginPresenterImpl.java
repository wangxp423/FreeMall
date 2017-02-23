package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.interactor.LoginInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.LoginInteracterImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.listener.TimeCountListener;
import com.aero.droid.dutyfree.talent.presenter.LoginPresenter;
import com.aero.droid.dutyfree.talent.util.MD5Kit;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.view.LoginView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author : wangxp
 * Date : 2015/12/2
 * Desc :
 */
public class LoginPresenterImpl implements LoginPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private LoginView loginView;
    private LoginInteractor interactor;
    private boolean isAccountNull = true;
    private boolean isAutoCodeNull = true;

    public LoginPresenterImpl(Activity activity, LoginView loginView) {
        this.activity = activity;
        this.loginView = loginView;
        interactor = new LoginInteracterImpl(this);
    }

    @Override
    public void clickLogin(String log_tag, String account, String password) {
        HashMap<String, String> params = new HashMap<>();
//        params.put("uname",account);
//        params.put("secCode",password);
        params.put("phone", account);
        params.put("password", MD5Kit.md5(password));
        params.put("deviceType", "-3");
        interactor.login(log_tag, Constants.EVENT_GET_DATA, activity, params);
    }

    @Override
    public void clickThirdLogin(String log_tag, String third, String usid, String unionid, String image, String nickName) {
        HashMap<String, String> params = new HashMap<>();
        params.put("third", third);
        params.put("usid", usid);
        params.put("unionid", unionid);
        params.put("image", image);
        params.put("nickName", nickName);
        params.put("deviceType", "-3");
        interactor.thirdlogin(log_tag, Constants.EVENT_REFRESH_DATA, activity, params);
    }

    @Override
    public void clickGetAutoCode(String log_tag, String phoneNumber) {
        HashMap<String, String> params = new HashMap<>();
        params.put("uname", phoneNumber);
        interactor.getAutoPassword(log_tag, Constants.EVENT_ADD_DATA, activity, params);
    }

    @Override
    public void inputingAccount(String text) {
        if (TextUtils.isEmpty(text)) {
            loginView.invisibleEditClear();
            isAccountNull = true;
            loginView.unclickAbleLoginBtn();
        } else {
            loginView.visibleEditClear();
            isAccountNull = false;
            if (!isAutoCodeNull) {
                loginView.clickAbleLoginBtn();
            } else {
                loginView.unclickAbleLoginBtn();
            }
        }
    }


    @Override
    public void inputingAutoCode(String text) {
        if (TextUtils.isEmpty(text)) {
            isAutoCodeNull = true;
            loginView.unclickAbleLoginBtn();
        } else {
            isAutoCodeNull = false;
            if (!isAccountNull) {
                loginView.clickAbleLoginBtn();
            } else {
                loginView.unclickAbleLoginBtn();
            }
        }
    }

    @Override
    public void startTime() {
        interactor.startTime(60000, 1000, new TimeCountListener() {
            @Override
            public void onFinish() {
                loginView.clickAbleGetAuthBtn();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                loginView.unclickAbleGetAuthBtn(millisUntilFinished);
            }
        });
    }

    @Override
    public boolean checkUserData(String uname) {
        if (TextUtils.isEmpty(uname)) {
            ToastUtil.showShortToast(activity, activity.getResources().getString(R.string.login_name_notify));
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onSuccess(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            loginView.loginSuccess();
        } else if (event_tag == Constants.EVENT_ADD_DATA) {
            loginView.getAutoPasswordSuccess();
        }else if (event_tag == Constants.EVENT_REFRESH_DATA) {
            loginView.thirdLoginSuccess();
        }

    }

    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag, String msg) {
        loginView.rquestDataError(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
