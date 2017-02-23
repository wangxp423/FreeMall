package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 品牌下商品列表 交互器
 */
public interface BrandGoodsInteractor {
    /**
     * 品牌商品列表
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void getGoodsList(String log_tag, int event_tag, Context context, HashMap<String, String> params);
}
