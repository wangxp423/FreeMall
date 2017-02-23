package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.ComentsInfo;
import com.aero.droid.dutyfree.talent.interactor.WriteComentsInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.WriteComentsInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.WriteComentsPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.WriteComentsView;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/12
 * Desc : 商品评论 逻辑控制 实现类
 */
public class WriteComentsPresenterImpl implements WriteComentsPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private WriteComentsView writeView;
    private WriteComentsInteractor interactor;

    public WriteComentsPresenterImpl(Activity activity, WriteComentsView writeView) {
        this.activity = activity;
        this.writeView = writeView;
        interactor = new WriteComentsInteractorImpl(activity, this);
    }

    @Override
    public void commitComtens(String log_tag, String cmtInfo) {
        HashMap<String, String> params = new HashMap<>();
//        String userId = UserUtil.getUserId(activity);
//        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("cmtInfo", cmtInfo);
        interactor.writeComents(log_tag, Constants.EVENT_GET_DATA, params);
    }

    @Override
    public void clickIntroduceBtn() {

    }

    @Override
    public void onSuccess(int event_tag, Object object) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            writeView.writeComentsData((String) object);
        }
    }


    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag,String msg) {
        writeView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
