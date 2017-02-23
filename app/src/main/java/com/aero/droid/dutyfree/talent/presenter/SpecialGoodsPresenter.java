package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : (专场，专题，海报) 逻辑控制器
 */
public interface SpecialGoodsPresenter {
    /**
     * 获取活动商品列表
     *
     * @param log_tag
     * @param activeId 活动Id
     * @param url 活动url
     */
    void getGoodsList(String log_tag,String activeId,String url);

    /**
     * 下拉刷新更多商品列表
     *
     * @param log_tag
     * @param activeId 活动Id
     * @param url 活动url
     */
    void refreshGoodsList(String log_tag,String activeId,String url);

    /**
     * 上拉加载更多商品列表
     *
     * @param log_tag
     * @param activeId 活动Id
     * @param url 活动url
     */
    void loadGoodsList(String log_tag,String activeId,String url);


}
