package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import com.aero.droid.dutyfree.talent.util.network.RespListener;

import java.util.HashMap;

/**
 * Created by wangxp on 2015/11/26.
 * app介绍轮播图交互器 类
 */
public interface PagerImgInteractor{
    /**
     * 获取轮播图数据
     * @param context
     * @param params
     * @param listener
     */
    void getPagerImgData(Context context,HashMap<String,String> params,RespListener listener);
}
