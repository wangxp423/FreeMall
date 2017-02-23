package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 精选页 交互器
 */
public interface HandpickInteractor {
    /**
     * 商品列表(轮播图样式接口)
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void getGoodsList(String log_tag, int event_tag, Context context, HashMap<String, String> params);
    /**
     * 商品列表(下面其他分类显示样式接口)
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void getOtherGoodsList(String log_tag, int event_tag, Context context, HashMap<String, String> params);
}
