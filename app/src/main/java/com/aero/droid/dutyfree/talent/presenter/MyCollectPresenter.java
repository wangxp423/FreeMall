package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的收藏 逻辑控制器
 */
public interface MyCollectPresenter {
    /**
     * 获取收藏列表
     *
     * @param log_tag
     */
    void getCollectList(String log_tag);

    /**
     * 删除收藏
     *
     * @param log_tag
     * @param goodsId
     */
    void clickDeleteCollect(String log_tag, String goodsId);
}
