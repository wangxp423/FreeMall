package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.activity.WriteComentsActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.ComentsInfo;
import com.aero.droid.dutyfree.talent.bean.DiscountInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.interactor.ComentsInteractor;
import com.aero.droid.dutyfree.talent.interactor.DiscountInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.ComentsInteractorImpl;
import com.aero.droid.dutyfree.talent.interactor.impl.DiscountInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.ComentsPresenter;
import com.aero.droid.dutyfree.talent.presenter.DiscountPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.ComentsView;
import com.aero.droid.dutyfree.talent.view.DiscountView;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/12
 * Desc : 商品评论 逻辑控制 实现类
 */
public class ComentsPresenterImpl implements ComentsPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private ComentsView comentsView;
    private ComentsInteractor interactor;

    public ComentsPresenterImpl(Activity activity, ComentsView comentsView) {
        this.activity = activity;
        this.comentsView = comentsView;
        interactor = new ComentsInteractorImpl(activity, this);
    }

    @Override
    public void getComentsList(String log_tag, String goodId, String cmtId, int curPage, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
//        String userId = UserUtil.getUserId(activity);
//        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("goodsId", goodId);
//        params.put("cmtId",cmtId);
        params.put("curPage", String.valueOf(curPage));
        params.put("pageSize", String.valueOf(pageSize));
        interactor.getComentsList(log_tag, Constants.EVENT_GET_DATA, params);
    }

    @Override
    public void loadComentsList(String log_tag, String goodId, String cmtId, int curPage, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
//        String userId = UserUtil.getUserId(activity);
//        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("goodsId", goodId);
//        params.put("cmtId",cmtId);
        params.put("curPage", String.valueOf(curPage));
        params.put("pageSize", String.valueOf(pageSize));
        interactor.getComentsList(log_tag, Constants.EVENT_ADD_DATA, params);
    }

    @Override
    public void clickWriteComtensBtn(String goodId,String goodName,String goodImg) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("goodId",goodId);
        bundle.putString("goodName",goodName);
        bundle.putString("goodImg",goodImg);
        intent.putExtras(bundle);
        intent.setClass(activity, WriteComentsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onSuccess(int event_tag, Object object) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            comentsView.showComentsListData((GoodComents) object);
        } else if (event_tag == Constants.EVENT_ADD_DATA) {
            comentsView.loadComtensListData(((GoodComents) object).getComentsList());
        }
    }


    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag,String msg) {
        comentsView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
