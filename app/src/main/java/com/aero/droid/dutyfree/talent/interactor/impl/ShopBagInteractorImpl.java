package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.interactor.ShopBagsInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.impl.ShopBagPresenterImpl;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.SharePreUtil;
import com.aero.droid.dutyfree.talent.util.StringUtils;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/29
 * Desc :
 */
public class ShopBagInteractorImpl implements ShopBagsInteractor {
    protected BaseMultiLoadedListener<Object> multiLoadedListener;

    public ShopBagInteractorImpl(BaseMultiLoadedListener<Object> listener) {
        this.multiLoadedListener = listener;
    }

    @Override
    public void getShopBagData(String log_tag, final int event_tag, final Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.SHOPBAGDETAIL, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "购物袋商品 = " + data.toString());
                if (data.has("goodsList"))
                    try {
                        List<GoodsInfo> goodsInfoList = JsonAnalysis.getGoodsInfoList(data.getJSONArray("goodsList"));
                        if (goodsInfoList.size() > 0) {
                            multiLoadedListener.onSuccess(event_tag, goodsInfoList);
                        } else {
                            multiLoadedListener.onEmpty(event_tag, goodsInfoList);
                        }
                        int totalNum = 0;
                        for (GoodsInfo item : goodsInfoList) {
                            totalNum += StringUtils.parseInt(item.getQuantity());
                        }
                        SharePreUtil.saveStringData(context, "shopCarNum", totalNum + "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }

    @Override
    public void getDeleteShopBagData(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.DELSHOPBAG, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "删除购物袋 = " + data.toString());
                if ("0".equals(code)) {
                    multiLoadedListener.onSuccess(event_tag, msg);
                } else {
                    multiLoadedListener.onException(msg);
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onException(msg);
            }
        });
    }

    @Override
    public void changeShopbag(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.MODIFYSHOPBAG, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "增减商品数量 = " + data.toString());
                if ("0".equals(code)) {
                    multiLoadedListener.onSuccess(event_tag, msg);
                } else {
                    multiLoadedListener.onException(msg);
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onException(msg);
            }
        });
    }

    @Override
    public void addCollect(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.ADDFAVORITE, params, new RespListener() {
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
    public void getTopGoods(String log_tag, final int event_tag, final Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.SHOPBAGTOPGOODS, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "top排行商品  = " + data.toString());
                if (data.has("goodsList"))
                    try {
                        List<GoodsInfo> goodsInfoList = JsonAnalysis.getGoodsInfoList(data.getJSONArray("goodsList"));
                        multiLoadedListener.onSuccess(event_tag, goodsInfoList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
    }

}
