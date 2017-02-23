package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.interactor.BrandGoodsInteractor;
import com.aero.droid.dutyfree.talent.interactor.SearchGoodsInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.BrandGoodsInteractorImpl;
import com.aero.droid.dutyfree.talent.interactor.impl.SearchGoodsInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.BrandGoodsPresenter;
import com.aero.droid.dutyfree.talent.presenter.SearchGoodsPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.BrandGoodsView;
import com.aero.droid.dutyfree.talent.view.SearchGoodsView;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 搜索商品 逻辑控制 实现类
 */
public class SearchGoodsPresenterImpl implements SearchGoodsPresenter, BaseMultiLoadedListener<List<GoodsInfo>> {
    private Activity activity;
    private SearchGoodsView searchGoodsView;
    private SearchGoodsInteractor interactor;

    public SearchGoodsPresenterImpl(Activity activity, SearchGoodsView searchGoodsView) {
        this.activity = activity;
        this.searchGoodsView = searchGoodsView;
        interactor = new SearchGoodsInteractorImpl(this);
    }

    @Override
    public void getGoodsList(String log_tag,String result,int curPage,int pageSize) {
        HashMap<String, String> params = new HashMap<>();
//        String userId = UserUtil.getUserId(activity);
//        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("paramType","3");
        params.put("param",result);
        params.put("curPage",String.valueOf(curPage));
        params.put("pageSize",String.valueOf(pageSize));
        interactor.getGoodsList(log_tag, Constants.EVENT_GET_DATA, activity, params);
    }


    @Override
    public void onSuccess(int event_tag, List<GoodsInfo> data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            searchGoodsView.showGoodsList(data);
        }
    }

    @Override
    public void onEmpty(int event_tag, List<GoodsInfo> data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            searchGoodsView.showEmptyData();
        }
    }

    @Override
    public void onError(int event_tag,String msg) {
        searchGoodsView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
