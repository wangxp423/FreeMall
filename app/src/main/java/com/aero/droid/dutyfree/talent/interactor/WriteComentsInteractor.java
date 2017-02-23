package com.aero.droid.dutyfree.talent.interactor;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/12
 * Desc : 写商品评论 交互器
 */
public interface WriteComentsInteractor {
    /**
     * 写商品评论列表
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void writeComents(String log_tag, int event_tag, HashMap<String, String> params);
}
