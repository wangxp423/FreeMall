package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.MainActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.interactor.FeedbackInteractor;
import com.aero.droid.dutyfree.talent.interactor.UserInfoInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.FeedbackInteracterImpl;
import com.aero.droid.dutyfree.talent.interactor.impl.UserInfoInteracterImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.FeedbackPresenter;
import com.aero.droid.dutyfree.talent.presenter.UserInfoPresenter;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.FeedbackView;
import com.aero.droid.dutyfree.talent.view.UserInfoView;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2016/1/12
 * Desc : 反馈 逻辑控制实现类
 */
public class FeedbackPresenterImpl implements FeedbackPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private FeedbackView feedbackView;
    private FeedbackInteractor interactor;

    public FeedbackPresenterImpl(Activity activity, FeedbackView feedbackView) {
        this.activity = activity;
        this.feedbackView = feedbackView;
        interactor = new FeedbackInteracterImpl(activity, this);
    }

    @Override
    public void clickCommit(String log_tag, String startQty, String content, List<String> imgs) {
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> imgParams = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("feedback", content);
        params.put("starQty", startQty);
        if (imgs.contains("normal"))
            imgs.remove("normal");
        for (int i = 0; i < imgs.size(); i++) {
            if (0 == i) {
                imgParams.put("image1", imgs.get(i));
            } else if (1 == i) {
                imgParams.put("image2", imgs.get(i));
            } else if (2 == i) {
                imgParams.put("image3", imgs.get(i));
            }
        }
        interactor.commitFeedback(log_tag, Constants.EVENT_COMMIT_DATA, params, imgParams);
    }

    @Override
    public boolean checkData(String startQty, String content) {
        if (TextUtils.isEmpty(startQty)) {
            ToastUtil.showLongToast(activity, activity.getResources().getString(R.string.feedback_star_notify));
            return false;
        } else {
            if (TextUtils.isEmpty(content)) {
                ToastUtil.showLongToast(activity, activity.getResources().getString(R.string.feedback_content_notify));
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void onSuccess(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_COMMIT_DATA) {
            feedbackView.feedbackSuccess((String) data);
        }
    }

    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag, String msg) {
        feedbackView.feedbackFail(msg);
    }

    @Override
    public void onException(String msg) {

    }

}
