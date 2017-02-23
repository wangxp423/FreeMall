package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 搜索商品 逻辑控制器
 */
public interface SearchGoodsPresenter {
    /**
     * 获取品牌商品列表
     *
     * @param log_tag
     */
    void getGoodsList(String log_tag,String result,int curPage,int pageSize);

}
