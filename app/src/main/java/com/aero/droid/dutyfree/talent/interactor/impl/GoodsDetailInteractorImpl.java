package com.aero.droid.dutyfree.talent.interactor.impl;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.interactor.GoodsDetailInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.AssetsUtil;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;
import com.aero.droid.dutyfree.talent.widgets.ComentsLayout;
import com.aero.droid.dutyfree.talent.widgets.GoodsIntroduceLayout;
import com.aero.droid.dutyfree.talent.widgets.RecommendGoodsLayout;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/11
 * Desc :
 */
public class GoodsDetailInteractorImpl extends MyCollectInteractorImpl implements GoodsDetailInteractor {
    private Activity activity;
    private BaseMultiLoadedListener<Object> multiLoadedListener;

    public GoodsDetailInteractorImpl(Activity activity, BaseMultiLoadedListener<Object> multiLoadedListener) {
        super(multiLoadedListener);
        this.activity = activity;
        this.multiLoadedListener = multiLoadedListener;
    }

    @Override
    public void getGoodsDetail(String log_tag, final int event_tag, HashMap<String, String> params) {
//        String json = AssetsUtil.getFromAssets(activity, "goodsDetails.json");
//        try {
//            JSONObject result = new JSONObject(json.trim());
//            JSONObject data = result.optJSONObject("JSON");
//            GoodsDetail detail = JsonAnalysis.getGoodsDetail(data.optJSONObject("goodsDetail"));
//            multiLoadedListener.onSuccess(event_tag, detail);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        OkHttpRequest.okHttpGet(activity, Url.GOODSDETAIL, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "商品详情 = " + data.toString());
                    if (data.has("goodsDetail")) {
                        GoodsDetail detail = JsonAnalysis.getGoodsDetail(data.optJSONObject("goodsDetail"));
                        multiLoadedListener.onSuccess(event_tag, detail);
                    } else {
                        multiLoadedListener.onError(event_tag,msg);
                    }
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }

    @Override
    public void getRecommendList(String log_tag, final int event_tag, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(activity, Url.SEARCHGOODS, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "商品详情中(推荐/同品牌商品) = " + data.toString());
                if ("0".equals(code)) {
                    if (data.has("goodsList")) {
                        List<GoodsInfo> infos = JsonAnalysis.getGoodsInfoList(data.optJSONArray("goodsList"));
                        multiLoadedListener.onSuccess(event_tag, infos);
                    } else {
                        multiLoadedListener.onError(event_tag,msg);
                    }
                } else {
                    multiLoadedListener.onError(event_tag,msg);
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }

    @Override
    public void addShopBag(String log_tag, final int event_tag, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(activity, Url.ADDSHOPBAG, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "添加购物袋 = " + data.toString());
                multiLoadedListener.onSuccess(event_tag,msg);
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }

    @Override
    public void addCollect(String log_tag, final int event_tag, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(activity, Url.ADDFAVORITE, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "添加收藏 = " + data.toString());
                multiLoadedListener.onSuccess(event_tag,msg);
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }


    @Override
    public List<HandpickBanner> getImgs(String log_tag, GoodsDetail detail) {
        List<HandpickBanner> banners = new ArrayList<>();
        try {
            JSONArray imgArray = new JSONArray(detail.getGoodsImags());
            for (int i = 0; i < imgArray.length(); i++) {
                JSONObject img = imgArray.getJSONObject(i);
                if (img.has("img")) {
                    HandpickBanner handpickBanner = new HandpickBanner();
                    handpickBanner.setImg(img.optString("img"));
                    handpickBanner.setAcParams(img.optString("img"));
                    handpickBanner.setAcType("0");
                    banners.add(handpickBanner);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return banners;
    }



}
