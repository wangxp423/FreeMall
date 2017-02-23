package com.aero.droid.dutyfree.talent.interactor.impl;

import android.app.Activity;
import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.interactor.HandpickInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.AssetsUtil;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 搜索商品 交互器实现类
 */
public class HandpickInteractorImpl extends GoodsDetailInteractorImpl implements HandpickInteractor {
    protected BaseMultiLoadedListener<Object> multiLoadedListener;

    public HandpickInteractorImpl(Activity activity, BaseMultiLoadedListener<Object> multiLoadedListener) {
        super(activity, multiLoadedListener);
        this.multiLoadedListener = multiLoadedListener;
    }

    @Override
    public void getGoodsList(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
//        String json = AssetsUtil.getFromAssets(context, "HandpickBanner.json");
//        try {
//            JSONObject result = new JSONObject(json.trim());
//            JSONObject data = result.optJSONObject("JSON");
//            List<HandpickBanner> infos = JsonAnalysis.getHandpickList(data.optJSONArray("mainBanner"));
//            multiLoadedListener.onSuccess(event_tag, infos);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        OkHttpRequest.okHttpGet(context, Url.MAINBANNER, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "精选轮播图列表 = " + data.toString());
                if ("0".equals(code)) {
                    if (data.has("mainBanner")) {
                        List<HandpickBanner> infos = JsonAnalysis.getHandpickList(data.optJSONArray("mainBanner"));
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
    public void getOtherGoodsList(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.MAINPAGE, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "精选其他分类商品列表 = " + data.toString());
                if (data.has("indexInfo")) {
                    List<HandpickType> infos = JsonAnalysis.getHandpickTypes(data.optJSONArray("indexInfo"));
                    Collections.sort(infos, new TypeComparator());
                    multiLoadedListener.onSuccess(event_tag, infos);
                } else {
                    multiLoadedListener.onError(event_tag,msg);
                }
            }

            @Override
            public void onRespError(String code, String msg) {
                multiLoadedListener.onError(event_tag,msg);
            }
        });
//        String json = AssetsUtil.getFromAssets(context,"mainPage.json");
//        try {
//            JSONObject result = new JSONObject(json.trim());
//            JSONObject data = result.optJSONObject("JSON");
//            List<HandpickType> infos = JsonAnalysis.getHandpickTypes(data.optJSONArray("indexInfo"));
//            Collections.sort(infos,new TypeComparator());
//            multiLoadedListener.onSuccess(event_tag, infos);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    public class TypeComparator implements Comparator<HandpickType> {

        public int compare(HandpickType o1, HandpickType o2) {
            return o1.getSno().compareTo(o2.getSno());
        }
    }


}
