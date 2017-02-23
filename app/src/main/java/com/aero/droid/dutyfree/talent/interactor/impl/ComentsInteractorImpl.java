package com.aero.droid.dutyfree.talent.interactor.impl;

import android.app.Activity;
import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.DiscountInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.interactor.ComentsInteractor;
import com.aero.droid.dutyfree.talent.interactor.DiscountInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.AssetsUtil;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/12
 * Desc : 商品评论 交互器实现类
 */
public class ComentsInteractorImpl implements ComentsInteractor {
    protected BaseMultiLoadedListener<Object> multiLoadedListener;
    protected Activity activity;

    public ComentsInteractorImpl(Activity activity, BaseMultiLoadedListener loadedListener) {
        this.activity = activity;
        multiLoadedListener = loadedListener;
    }


    @Override
    public void getComentsList(String log_tag, final int event_tag, HashMap<String, String> params) {
//        String json = AssetsUtil.getFromAssets(activity, "comentsList.json");
//        try {
//            JSONObject result = new JSONObject(json.trim());
//            JSONObject data = result.optJSONObject("JSON");
//            GoodComents coments = JsonAnalysis.getGoodComents(data.optJSONObject("coments"));
//            multiLoadedListener.onSuccess(event_tag, coments);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        OkHttpRequest.okHttpGet(activity, Url.COMMENTLIST, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "商品评论列表 = " + data.toString());
                    if (data.has("coments")) {
                        GoodComents coments = JsonAnalysis.getGoodComents(data.optJSONObject("coments"));
                        multiLoadedListener.onSuccess(event_tag, coments);
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
}
