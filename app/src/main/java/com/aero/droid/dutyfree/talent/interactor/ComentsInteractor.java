package com.aero.droid.dutyfree.talent.interactor;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/12
 * Desc : 商品评论 交互器
 */
public interface ComentsInteractor {
    /**
     * 商品评论列表
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void getComentsList(String log_tag, int event_tag, HashMap<String, String> params);
}
