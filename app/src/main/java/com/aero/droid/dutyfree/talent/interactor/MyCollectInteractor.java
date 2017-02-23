package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的收藏 交互器
 */
public interface MyCollectInteractor {
    /**
     * 获取收藏列表
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void getCollectList(String log_tag, int event_tag, Context context, HashMap<String, String> params);
    /**
     * 删除 单个收藏
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void deleteCollect(String log_tag, int event_tag, Context context, HashMap<String, String> params);
}
