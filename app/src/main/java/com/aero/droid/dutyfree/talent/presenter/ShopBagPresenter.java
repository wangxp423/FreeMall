package com.aero.droid.dutyfree.talent.presenter;

import com.aero.droid.dutyfree.talent.bean.GoodsInfo;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/29
 * Desc : 购物袋 任命类
 */
public interface ShopBagPresenter {
    /**
     * 获取购物车数据
     * @param log_tag
     */
    void getShopBagData(String log_tag);
    /**
     * 获取排行商品
     * @param log_tag
     */
    void getTopGoods(String log_tag);

    /**
     * 显示删除按钮
     */
    void showDeleteBtn();

    /**
     * 隐藏删除按钮
     */
    void hideDeleteBtn();

    /**
     * 点击删除购物袋
     * @param log_tag
     */
    void clickDeleteShopBag(String log_tag,List<GoodsInfo> infos);

    /**
     * 选中该商品
     * @param position
     */
    void checkGoods(int position);

    /**
     * 取消选中该商品
     * @param position
     */
    void noCheckGoods(int position);

    /**
     * 添加商品到购物车
     * @param log_tag
     * @param id  商品ID
     * @param num 当前商品数量
     */
    void clickAddGood(String log_tag,String id,int num);

    /**
     * 移除购物车中的商品
     * *@param log_tag
     * @param id  商品ID
     * @param num 当前商品数量
     */
    void clickRemoveGood(String log_tag,String id,int num);
    /**
     * 添加商品到收藏
     * *@param log_tag
     * @param id  商品ID
     */
    void clickAddCollect(String log_tag,String id);

    /**
     * 提交购物车
     */
    void clickConfirmShopBag(List<GoodsInfo> shopbagGoods,int totalPrice);
}
