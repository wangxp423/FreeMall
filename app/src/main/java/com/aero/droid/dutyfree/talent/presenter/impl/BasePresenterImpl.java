package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

/**
 * Author : wangxp
 * Date : 2015/11/30
 * Desc : 逻辑控制基类 主要处理 loading
 */
public class BasePresenterImpl {
    protected Activity activity;
    protected BaseView baseView;
    public BasePresenterImpl(Activity activity,BaseView baseView){
        this.activity = activity;
        this.baseView = baseView;
    }

    /**
     * 获取数据成功
     */
    protected void getDataSuccess(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                baseView.hideLoading();
            }
        });


    }

    /**
     * 没有数据
     */
    protected void getDataEmpty(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                baseView.hideLoading();
                baseView.showEmpty(activity.getResources().getString(R.string.request_error),0);
            }
        });


    }

    /**
     * 获取数据失败
     */
    protected void getDataError(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                baseView.hideLoading();
                baseView.showNetError();
            }
        });

    }
}
