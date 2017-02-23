package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.GoodsBrand;
import com.aero.droid.dutyfree.talent.interactor.BrandCategoryInteractor;
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
 * Desc : 品牌分类 实现类
 */
public class BrandCategoryInteractorImpl implements BrandCategoryInteractor {
    private BaseMultiLoadedListener<List<GoodsBrand>> multiLoadedListener;

    public BrandCategoryInteractorImpl(BaseMultiLoadedListener<List<GoodsBrand>> listener) {
        this.multiLoadedListener = listener;
    }

    @Override
    public void getBrandCategoryData(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.CATEGORYLIST, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "品牌分类 = " + data.toString());
                if (data.has("markList")) {
                    JSONArray categoryArray = data.optJSONArray("markList");
                    List<GoodsBrand> brands = JsonAnalysis.getGoodsBrands(categoryArray);
                    if (brands.size() > 0) {
                        multiLoadedListener.onSuccess(event_tag, brands);
                    } else {
                        multiLoadedListener.onEmpty(event_tag, brands);
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
