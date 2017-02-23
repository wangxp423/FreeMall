package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/6
 * Desc : 精选页
 */
public interface  HandpickView extends BaseView{
    /**
     * 轮播图商品列表
     * @param handpicksList
     */
    void showGoodsList(List<HandpickBanner> handpicksList);
    /**
     * 其他分类商品列表
     * @param typeList
     */
    void showOhterGoodsList(List<HandpickType> typeList);
    /**
     * 刷新轮播图商品列表
     * @param handpicksList
     */
    void refreshGoodsList(List<HandpickBanner> handpicksList);
    /**
     * 刷新其他分类商品列表
     * @param typeList
     */
    void refreshOhterGoodsList(List<HandpickType> typeList);

    /**
     * 添加到我的喜欢成功
     * @param msg
     */
    void addFavoSuccess(String msg);

    /**
     * 请求失败
     */
    void requestError(String msg);



}
