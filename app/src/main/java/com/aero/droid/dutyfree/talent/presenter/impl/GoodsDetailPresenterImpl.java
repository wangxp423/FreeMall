package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.DiscountInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.interactor.DiscountInteractor;
import com.aero.droid.dutyfree.talent.interactor.GoodsDetailInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.DiscountInteractorImpl;
import com.aero.droid.dutyfree.talent.interactor.impl.GoodsDetailInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.DiscountPresenter;
import com.aero.droid.dutyfree.talent.presenter.GoodsDetailPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.DiscountView;
import com.aero.droid.dutyfree.talent.view.GoodsDetailView;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 商品详情 逻辑控制 实现类
 */
public class GoodsDetailPresenterImpl implements GoodsDetailPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private GoodsDetailView detailView;
    private GoodsDetailInteractorImpl interactor;

    public GoodsDetailPresenterImpl(Activity activity, GoodsDetailView detailView) {
        this.activity = activity;
        this.detailView = detailView;
        interactor = new GoodsDetailInteractorImpl(activity, this);
    }

    @Override
    public void getGoodsDetail(String log_tag, String goodId, String srcType, String srcId) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("goodsId", goodId);
        params.put("srcType", srcType);
        params.put("srcId", srcId);
        interactor.getGoodsDetail(log_tag, Constants.EVENT_GET_DATA, params);
    }

    @Override
    public void getRecommendList(String log_tag, String paramType, String param, int curPage, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
//        String userId = UserUtil.getUserId(activity);
//        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("paramType",paramType);
        params.put("param", param);
        params.put("curPage",String.valueOf(curPage));
        params.put("pageSize",String.valueOf(pageSize));
        interactor.getRecommendList(log_tag, Constants.EVENT_REFRESH_DATA, params);
    }

    @Override
    public void getSameBrandList(String log_tag, String paramType, String param, int curPage, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
//        String userId = UserUtil.getUserId(activity);
//        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("paramType",paramType);
        params.put("param", param);
        params.put("curPage",String.valueOf(curPage));
        params.put("pageSize",String.valueOf(pageSize));
        interactor.getRecommendList(log_tag, Constants.EVENT_LOAD_DATA, params);
    }


    @Override
    public void addShopBag(String log_tag, String goodId) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("goodsId", goodId);
        interactor.addShopBag(log_tag, Constants.EVENT_ADD_DATA, params);
    }

    @Override
    public void addFavorite(String log_tag, String goodId) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("goodsId", goodId);
        interactor.addCollect(log_tag, Constants.EVENT_ADD_FAVO, params);
    }

    @Override
    public void removeFavorite(String log_tag, String goodId) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("goodsId", goodId);
        interactor.deleteCollect(log_tag, Constants.EVENT_REMOVE_DATA, activity, params);
    }

    @Override
    public void getImageView(String log_tag) {
        detailView.showSingleImg();
    }

    @Override
    public void getImgs(String log_tag, GoodsDetail detail) {
        detailView.showPagerImgs(interactor.getImgs(log_tag, detail));
    }

    @Override
    public void getGoodsDescView(String log_tag) {
        detailView.showIntroduceView();
    }

    @Override
    public void getComentsView(String log_tag) {
        detailView.showComentsView();
    }

    @Override
    public void getSameBrandView(String log_tag) {
        detailView.showSameBrandView();
    }

    @Override
    public void getRecommendGoodsView(String log_tag) {
        detailView.showRecommendView();
    }

    @Override
    public void onSuccess(int event_tag, Object object) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            detailView.showGoodsDetailData((GoodsDetail) object);
        } else if (event_tag == Constants.EVENT_REFRESH_DATA) {
            detailView.getRecommendGoodsData((List<GoodsInfo>) object);
        }else if (event_tag == Constants.EVENT_LOAD_DATA) {
            detailView.getSameBrandGoodsData((List<GoodsInfo>) object);
        } else if (event_tag == Constants.EVENT_ADD_DATA) {
            detailView.addShopBagSuccess((String) object);
        }else if (event_tag == Constants.EVENT_ADD_FAVO) {
            detailView.addFavoriteSuccess((String) object);
        }else if (event_tag == Constants.EVENT_REMOVE_DATA) {
            detailView.removeFavoriteSuccess((String) object);
        }
    }


    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag,String msg) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            detailView.requestError(msg);
        } else if (event_tag == Constants.EVENT_ADD_DATA) {
            detailView.requestError(msg);
        } else if (event_tag == Constants.EVENT_ADD_FAVO) {
            detailView.requestError(msg);
        }else if (event_tag == Constants.EVENT_REMOVE_DATA) {
            detailView.requestError(msg);
        }
    }

    @Override
    public void onException(String msg) {

    }
}
