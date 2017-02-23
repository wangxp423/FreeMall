package com.aero.droid.dutyfree.talent.interactor;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : (专场，专题，海报)页 交互器
 */
public interface SpecialGoodsInteractor {
    /**
     * 商品列表
     * @param log_tag
     * @param event_tag
     * @param url
     * @param params
     */
    void getGoodsList(String log_tag, int event_tag,String url, HashMap<String, String> params);
}
