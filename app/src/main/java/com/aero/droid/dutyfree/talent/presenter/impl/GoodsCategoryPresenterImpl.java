package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.aero.droid.dutyfree.talent.activity.BrandGoodsActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.GoodsBrand;
import com.aero.droid.dutyfree.talent.bean.GoodsCategory;
import com.aero.droid.dutyfree.talent.interactor.GoodsCategoryInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.GoodsCategoryInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.GoodsCategoryPresenter;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.network.RespListener;
import com.aero.droid.dutyfree.talent.view.GoodsCategoryView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc :
 */
public class GoodsCategoryPresenterImpl extends BasePresenterImpl implements GoodsCategoryPresenter,BaseMultiLoadedListener<List<GoodsCategory>> {
    private Activity mActivity;
    private GoodsCategoryView mCategoryView;
    private GoodsCategoryInteractor mInteractor;

    public GoodsCategoryPresenterImpl(Activity activity, GoodsCategoryView goodsCategoryView) {
        super(activity,goodsCategoryView);
        this.mActivity = activity;
        this.mCategoryView = goodsCategoryView;
        mInteractor = new GoodsCategoryInteractorImpl(this);
    }

    @Override
    public void getData(String log_tag,final Activity activity, int pageSize, int curPage) {
        mCategoryView.showLoading("");
        HashMap<String, String> params = new HashMap<>();
        mInteractor.getGoodsCategoryData(log_tag, Constants.EVENT_GET_DATA,activity, params);
    }

    @Override
    public void getRefreshData(String log_tag,Activity activity, int pageSize, int curPage) {
        mCategoryView.showLoading("");
        HashMap<String, String> params = new HashMap<>();
        mInteractor.getGoodsCategoryData(log_tag,Constants.EVENT_REFRESH_DATA,activity,  params);
    }

    @Override
    public void getLoadData(String log_tag,Activity activity, int pageSize, int curPage) {
        mCategoryView.showLoading("");
        HashMap<String, String> params = new HashMap<>();
        mInteractor.getGoodsCategoryData(log_tag,Constants.EVENT_LOAD_DATA,activity, params);
    }

    @Override
    public void clickCategory(GoodsCategory category) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("paramType","1");
        bundle.putString("categoryId", category.getCategoryId());
        bundle.putString("categoryLogo", category.getCategoryImg());
        bundle.putString("categoryName", category.getCategoryName());
        intent.setClass(mActivity, BrandGoodsActivity.class);
        intent.putExtras(bundle);
        mActivity.startActivity(intent);
    }

    @Override
    public void onSuccess(int event_tag, List<GoodsCategory> data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            mCategoryView.showGoodsCategory(data);
        } else if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mCategoryView.showRefreshGoodsCategory(data);
        } else if (event_tag == Constants.EVENT_LOAD_DATA) {
            mCategoryView.showloadGoodsCategory(data);
        }
        getDataSuccess();

    }

    @Override
    public void onEmpty(int event_tag, List<GoodsCategory> data) {
        getDataEmpty();
    }

    @Override
    public void onError(int event_tag,String msg) {
        getDataError();
    }

    @Override
    public void onException(String msg) {

    }
}
