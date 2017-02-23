package com.aero.droid.dutyfree.talent.interactor.impl;

import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.DiscountInfo;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.interactor.DiscountInteractor;
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
 * Desc : 折扣专区 交互器实现类
 */
public class DiscountInteractorImpl implements DiscountInteractor {
    protected BaseMultiLoadedListener<Object> multiLoadedListener;

    public DiscountInteractorImpl(BaseMultiLoadedListener loadedListener) {
        multiLoadedListener = loadedListener;
    }

    @Override
    public void getDiscountList(String log_tag, final int event_tag, Context context, HashMap<String, String> params) {
//        String json = AssetsUtil.getFromAssets(context,"discountArea.json");
//        try {
//            JSONObject result = new JSONObject(json.trim());
//            JSONObject data = result.optJSONObject("JSON");
//            List<DiscountInfo> infos = JsonAnalysis.getDiscountList(data.optJSONArray("discList"));
//            multiLoadedListener.onSuccess(event_tag, infos);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        OkHttpRequest.okHttpGet(context, Url.DISCOUNTAREA, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "折扣专区列表 = " + data.toString());
                if (data.has("discList")) {
                    List<DiscountInfo> infos = JsonAnalysis.getDiscountList(data.optJSONArray("discList"));
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


    public class TypeComparator implements Comparator<HandpickType> {

        public int compare(HandpickType o1, HandpickType o2) {
            return o1.getSno().compareTo(o2.getSno());
        }
    }


}
