package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.interactor.BrandGoodsInteractor;
import com.aero.droid.dutyfree.talent.interactor.MyCollectInteractor;
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
 * Desc : 品牌or分类商品 交互器实现类
 */
public class BrandGoodsInteractorImpl implements BrandGoodsInteractor {
    protected BaseMultiLoadedListener<List<GoodsInfo>> multiLoadedListener;

    public BrandGoodsInteractorImpl(BaseMultiLoadedListener loadedListener) {
        multiLoadedListener = loadedListener;
    }

    private String headerImg;
    private String categoryDesc;
    private String totalNum;
    private boolean isEnd;

    @Override
    public void getGoodsList(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(context, Url.SEARCHGOODS, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "获取分类商品列表 = " + data.toString());
                if (data.has("headerImg"))
                    headerImg = data.optString("headerImg");
                if (data.has("memo"))
                    categoryDesc = data.optString("memo");
                if (data.has("totalCount"))
                    totalNum = data.optString("totalCount");
                if (data.has("end")) {
                    if ("0".equals(data.optString("end"))) {
                        isEnd = false;
                    } else if ("1".equals(data.optString("end"))) {
                        isEnd = true;
                    }
                }
                if (data.has("goodsList")) {
                    List<GoodsInfo> infos = JsonAnalysis.getGoodsInfoList(data.optJSONArray("goodsList"));
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
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public boolean isEnd() {
        return isEnd;
    }
}
