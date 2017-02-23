package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;
import android.view.animation.Animation;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.interactor.AppStartInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.AppStartInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.listener.BaseSingleLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.AppStartPresenter;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.SharePreUtil;
import com.aero.droid.dutyfree.talent.util.StringUtils;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.network.RespListener;
import com.aero.droid.dutyfree.talent.view.AppStartView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/27
 * Desc :
 */
public class AppStartPresenterImpl implements AppStartPresenter,BaseMultiLoadedListener<Object>{
    private AppStartInteractor mInteractor;
    private AppStartView mStartView;
    private Activity mActivity;

    public AppStartPresenterImpl(Activity activity, AppStartView startView) {
        this.mStartView = startView;
        this.mActivity = activity;
        mInteractor = new AppStartInteractorImpl(this);
    }

    @Override
    public void initialized() {
        Animation animation = mInteractor.getImgAnimation(mActivity);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mStartView.animationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mStartView.imageAnimation(animation);
    }

    @Override
    public boolean isFirstIn() {
        if (SharePreUtil.getStringData(mActivity, "isFirstStart", "true").equals("true")){
            SharePreUtil.saveStringData(mActivity, "isFirstStart", "false");
            mStartView.go2ViewPagerActivity();
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void firstIn() {
        mInteractor.go2ViewPagerActivity(mActivity);
        mActivity.finish();
    }

    @Override
    public void noFirstIn() {
        mInteractor.go2MainActivity(mActivity);
        mActivity.finish();
    }

    @Override
    public void getRequest(String log_tag) {
        HashMap<String,String> params = new HashMap<>();
        mInteractor.getSomeFunctionUrl(log_tag,mActivity, params);
        String userId = UserUtil.getUserId(mActivity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        mInteractor.getShopBagData(log_tag, Constants.EVENT_GET_DATA,mActivity, params);
    }


    @Override
    public void onSuccess(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA){
            mStartView.getShopBagsNum();
        }else{
            mStartView.getSomeFunctionUrl();
        }
    }

    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag,String msg) {

    }

    @Override
    public void onException(String msg) {

    }
}
