package com.aero.droid.dutyfree.talent.interactor.impl;

import android.app.Activity;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.ActiveInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.interactor.ComentsInteractor;
import com.aero.droid.dutyfree.talent.interactor.SpecialGoodsInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.AssetsUtil;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : (专场，专题，海报) 交互器实现类
 */
public class SpecialGoodsInteractorImpl implements SpecialGoodsInteractor {
    protected BaseMultiLoadedListener<Object> multiLoadedListener;
    protected Activity activity;

    public SpecialGoodsInteractorImpl(Activity activity, BaseMultiLoadedListener loadedListener) {
        this.activity = activity;
        multiLoadedListener = loadedListener;
    }


    @Override
    public void getGoodsList(String log_tag, final int event_tag,String url, HashMap<String, String> params) {
//        String json = AssetsUtil.getFromAssets(activity, "activeGoodsList.json");
//        try {
//            JSONObject result = new JSONObject(json.trim());
//            JSONObject data = result.optJSONObject("JSON");
//            ActiveInfo info = JsonAnalysis.getActiveGoodsList(data);
//            multiLoadedListener.onSuccess(event_tag, info);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        OkHttpRequest.okHttpGet(activity, url, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "活动专场列表 = " + data.toString());
                    if (data.has("goodsList")) {
                        ActiveInfo info = JsonAnalysis.getActiveGoodsList(data);
                        multiLoadedListener.onSuccess(event_tag, info);
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
