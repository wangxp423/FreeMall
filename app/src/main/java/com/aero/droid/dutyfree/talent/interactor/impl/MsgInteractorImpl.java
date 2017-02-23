package com.aero.droid.dutyfree.talent.interactor.impl;

import android.app.Activity;
import android.content.Context;

import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.DiscountInfo;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.bean.MessageInfo;
import com.aero.droid.dutyfree.talent.interactor.DiscountInteractor;
import com.aero.droid.dutyfree.talent.interactor.MsgInteractor;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONObject;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2016/1/11
 * Desc : 消息中心 交互器实现类
 */
public class MsgInteractorImpl implements MsgInteractor {
    private Activity activity;
    protected BaseMultiLoadedListener<Object> multiLoadedListener;

    public MsgInteractorImpl(Activity activity,BaseMultiLoadedListener loadedListener) {
        this.activity = activity;
        multiLoadedListener = loadedListener;
    }

    @Override
    public void getMsgList(final String log_tag, final int event_tag, HashMap<String, String> params) {
        OkHttpRequest.okHttpGet(activity, Url.MSGCENTER, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "消息中心列表 = " + data.toString());
                if (data.has("msgInfo")) {
                    List<MessageInfo> infos = JsonAnalysis.getMsgList(data.optJSONArray("msgInfo"));
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
