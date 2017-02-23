package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 精选 逻辑控制器
 */
public interface HandpickPresenter {
    /**
     * 获取轮播图商品列表
     *
     * @param log_tag
     */
    void getGoodsList(String log_tag);
    /**
     * 获取其他样式分类商品列表
     *
     * @param log_tag
     */
    void getOtherGoodsList(String log_tag);

    /**
     * 添加收藏
     * @param log_tag
     * @param goodId
     */
    void addFavorite(String log_tag,String goodId);

}
