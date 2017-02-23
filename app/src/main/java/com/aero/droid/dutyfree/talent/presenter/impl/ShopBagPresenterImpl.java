package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.activity.OrderConfrimActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.interactor.ShopBagsInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.ShopBagInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.ShopBagPresenter;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.SharePreUtil;
import com.aero.droid.dutyfree.talent.util.StringUtils;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.network.RespListener;
import com.aero.droid.dutyfree.talent.view.ShopBagView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : wangxp
 * Date : 2015/11/29
 * Desc : 购物车 任命类实现类
 */
public class ShopBagPresenterImpl extends BasePresenterImpl implements ShopBagPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private ShopBagView shopBagView;
    private ShopBagsInteractor interactor;

    public ShopBagPresenterImpl(Activity activity, ShopBagView shopBagView) {
        super(activity, shopBagView);
        this.activity = activity;
        this.shopBagView = shopBagView;
        interactor = new ShopBagInteractorImpl(this);
    }

    @Override
    public void getShopBagData(String log_tag) {
        shopBagView.showLoading(null);
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getShopBagData(log_tag, Constants.EVENT_GET_DATA, activity, params);
    }

    @Override
    public void getTopGoods(String log_tag) {
//        shopBagView.showLoading(null);
        HashMap<String, String> params = new HashMap<>();
//        String userId = UserUtil.getUserId(activity);
//        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getTopGoods(log_tag, Constants.EVENT_REFRESH_DATA, activity, params);
    }

    @Override
    public void showDeleteBtn() {
        shopBagView.showDeleteBtn();
    }

    @Override
    public void hideDeleteBtn() {
        shopBagView.hideDeleteBtn();
    }

    @Override
    public void clickDeleteShopBag(String log_tag, List<GoodsInfo> infos) {
//        shopBagView.showLoading(null);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < infos.size(); i++) {
            if (i == infos.size() - 1) {
                builder.append(infos.get(i).getId());
            } else {
                builder.append(infos.get(i).getId()).append(",");
            }
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("goodsIds", builder.toString());
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getDeleteShopBagData(log_tag, Constants.EVENT_DELETE_DATA, activity, params);
    }

    @Override
    public void checkGoods(int position) {
        shopBagView.checkGoods(position);
    }

    @Override
    public void noCheckGoods(int position) {
        shopBagView.noCheckGoods(position);
    }

    @Override
    public void clickAddGood(String log_tag, String id, final int num) {
//        shopBagView.showLoading(null);
        String userId = UserUtil.getUserId(activity);
        HashMap<String, String> params = new HashMap<>();
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("goodsId", id);
        params.put("quantity", String.valueOf(num));
        interactor.changeShopbag(log_tag, Constants.EVENT_ADD_DATA, activity, params);
    }

    @Override
    public void clickRemoveGood(String log_tag, String id, final int num) {
//        shopBagView.showLoading(null);
        String userId = UserUtil.getUserId(activity);
        HashMap<String, String> params = new HashMap<>();
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("goodsId", id);
        params.put("quantity", String.valueOf(num));
        interactor.changeShopbag(log_tag, Constants.EVENT_REMOVE_DATA, activity, params);
    }

    @Override
    public void clickAddCollect(String log_tag, String id) {
        String userId = UserUtil.getUserId(activity);
        HashMap<String, String> params = new HashMap<>();
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        params.put("goodsId", id);
        interactor.addCollect(log_tag, Constants.EVENT_LOAD_DATA, activity, params);
    }

    @Override
    public void clickConfirmShopBag(List<GoodsInfo> shopbagGoods,int totalPrice) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("totalPrice",totalPrice);
        bundle.putParcelableArrayList("orderGoodsList", (ArrayList<? extends Parcelable>) shopbagGoods);
        intent.setClass(activity, OrderConfrimActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        shopBagView.confirmShopBag();
    }

    public void setCurShopBagNum(List<GoodsInfo> infos) {
        int num = 0;
        for (GoodsInfo info : infos) {
            num = num + StringUtils.parseInt(info.getQuantity());
        }
        SharePreUtil.saveStringData(activity, "shopCarNum", num + "");
    }

    @Override
    public void onSuccess(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            List<GoodsInfo> infos = (List<GoodsInfo>) data;
            shopBagView.showShopBagData(infos);
            getDataSuccess();
        }if (event_tag == Constants.EVENT_REFRESH_DATA) {
            List<GoodsInfo> infos = (List<GoodsInfo>) data;
            shopBagView.showTopGoods(infos);
        } else if (event_tag == Constants.EVENT_DELETE_DATA) {
            shopBagView.deleteShopBagSucc((String) data);
        } else if (event_tag == Constants.EVENT_ADD_DATA) {
            shopBagView.addGoodsSucc((String) data);
        } else if (event_tag == Constants.EVENT_REMOVE_DATA) {
            shopBagView.removeGoodsSucc((String) data);
        }else if (event_tag == Constants.EVENT_LOAD_DATA) {
            shopBagView.addCollectSucc((String) data);
        }

    }

    @Override
    public void onEmpty(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            getDataEmpty();
        }
    }

    @Override
    public void onError(int event_tag,String msg) {
        shopBagView.requestError(msg);
        getDataError();
    }

    @Override
    public void onException(String msg) {
        shopBagView.requestError(msg);
    }
}
