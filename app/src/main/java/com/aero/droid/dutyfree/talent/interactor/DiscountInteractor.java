package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 折扣专区页 交互器
 */
public interface DiscountInteractor {
    /**
     * 折扣专区列表
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void getDiscountList(String log_tag, int event_tag, Context context, HashMap<String, String> params);
}
