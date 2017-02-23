package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.GoodsCategory;
import com.aero.droid.dutyfree.talent.interactor.GoodsCategoryInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : 商品分类 实现类
 */
public class GoodsCategoryInteractorImpl implements GoodsCategoryInteractor {
    private BaseMultiLoadedListener<List<GoodsCategory>> multiLoadedListener;

    public GoodsCategoryInteractorImpl(BaseMultiLoadedListener<List<GoodsCategory>> listener) {
        multiLoadedListener = listener;
    }

    @Override
    public void getGoodsCategoryData(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.CATEGORYLIST, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "商品分类 = " + data.toString());
                if (data.has("categoryList")) {
                    JSONArray categoryArray = data.optJSONArray("categoryList");
                    List<GoodsCategory> categories = JsonAnalysis.getGoodsCategorys(categoryArray);
                    if (categories.size() > 0) {
                        multiLoadedListener.onSuccess(event_tag, categories);
                    } else {
                        multiLoadedListener.onEmpty(event_tag, categories);
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
}
