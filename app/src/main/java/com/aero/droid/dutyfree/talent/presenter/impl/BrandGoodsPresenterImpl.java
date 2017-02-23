package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.interactor.BrandGoodsInteractor;
import com.aero.droid.dutyfree.talent.interactor.MyCollectInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.BrandGoodsInteractorImpl;
import com.aero.droid.dutyfree.talent.interactor.impl.MyCollectInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.BrandGoodsPresenter;
import com.aero.droid.dutyfree.talent.presenter.MyCollectPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.BrandGoodsView;
import com.aero.droid.dutyfree.talent.view.MyCollectView;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 品牌下商品 逻辑控制 实现类
 */
public class BrandGoodsPresenterImpl implements BrandGoodsPresenter, BaseMultiLoadedListener<List<GoodsInfo>> {
    private Activity activity;
    private BrandGoodsView brandGoodsView;
    private BrandGoodsInteractorImpl interactor;

    public BrandGoodsPresenterImpl(Activity activity, BrandGoodsView brandGoodsView) {
        this.activity = activity;
        this.brandGoodsView = brandGoodsView;
        interactor = new BrandGoodsInteractorImpl(this);
    }

    @Override
    public void getGoodsList(String log_tag, String paramType, String param, int curPage, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
//        String userId = UserUtil.getUserId(activity);
//        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("paramType", paramType);
        params.put("param", param);
        params.put("curPage", String.valueOf(curPage));
        params.put("pageSize", String.valueOf(pageSize));
        interactor.getGoodsList(log_tag, Constants.EVENT_GET_DATA, activity, params);
    }

    @Override
    public void refreshGoodsList(String log_tag, String paramType, String param, int curPage, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
//        String userId = UserUtil.getUserId(activity);
//        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("paramType", paramType);
        params.put("param", param);
        params.put("curPage", String.valueOf(curPage));
        params.put("pageSize", String.valueOf(pageSize));
        interactor.getGoodsList(log_tag, Constants.EVENT_REFRESH_DATA, activity, params);
    }

    @Override
    public void loadGoodsList(String log_tag, String paramType, String param, int curPage, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
//        String userId = UserUtil.getUserId(activity);
//        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("paramType", paramType);
        params.put("param", param);
        params.put("curPage", String.valueOf(curPage));
        params.put("pageSize", String.valueOf(pageSize));
        interactor.getGoodsList(log_tag, Constants.EVENT_LOAD_DATA, activity, params);
    }


    @Override
    public void onSuccess(int event_tag, List<GoodsInfo> data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            brandGoodsView.showGoodsList(data);
            brandGoodsView.showTitleData(interactor.getHeaderImg(), interactor.getCategoryDesc(), interactor.getTotalNum());
            if (interactor.isEnd())
                brandGoodsView.end();
        } else if (event_tag == Constants.EVENT_REFRESH_DATA) {
            brandGoodsView.refreshGoodsList(data);
            if (interactor.isEnd())
                brandGoodsView.end();
        } else if (event_tag == Constants.EVENT_LOAD_DATA) {
            brandGoodsView.loadGoodsList(data);
            if (interactor.isEnd())
                brandGoodsView.end();
        }
    }

    @Override
    public void onEmpty(int event_tag, List<GoodsInfo> data) {

    }

    @Override
    public void onError(int event_tag,String msg) {
        brandGoodsView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }

}
