package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.interactor.BrandGoodsInteractor;
import com.aero.droid.dutyfree.talent.interactor.SearchGoodsInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 搜索商品 交互器实现类
 */
public class SearchGoodsInteractorImpl implements SearchGoodsInteractor {
    protected BaseMultiLoadedListener<List<GoodsInfo>> multiLoadedListener;

    public SearchGoodsInteractorImpl(BaseMultiLoadedListener loadedListener) {
        multiLoadedListener = loadedListener;
    }

    @Override
    public void getGoodsList(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.SEARCHGOODS, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "搜索商品列表 = " + data.toString());
                if (data.has("goodsList")) {
                    List<GoodsInfo> infos = JsonAnalysis.getGoodsInfoList(data.optJSONArray("goodsList"));
                    if (infos.size() > 0) {
                        multiLoadedListener.onSuccess(event_tag, infos);
                    } else {
                        multiLoadedListener.onEmpty(event_tag, infos);
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
