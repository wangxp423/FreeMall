package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/12/12
 * Desc : 商品评论 逻辑控制器
 */
public interface ComentsPresenter {
    /**
     * 获取评论列表
     *
     * @param log_tag
     * @param goodId   商品Id
     * @param cmtId    评论Id
     * @param curPage  当前页
     * @param pageSize 每页几条数据
     */
    void getComentsList(String log_tag, String goodId, String cmtId, int curPage, int pageSize);

    /**
     * 上拉加载评论列表
     *
     * @param log_tag
     * @param goodId   商品Id
     * @param cmtId    评论Id
     * @param curPage  当前页
     * @param pageSize 每页几条数据
     */
    void loadComentsList(String log_tag, String goodId, String cmtId, int curPage, int pageSize);

    /**
     * 写评论按钮按钮
     * @param goodId
     * @param goodName
     * @param goodImg
     */
    void clickWriteComtensBtn(String goodId,String goodName,String goodImg);


}
