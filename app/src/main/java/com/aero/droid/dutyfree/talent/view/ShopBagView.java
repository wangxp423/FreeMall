package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/11/29
 * Desc : 主页购物袋Veiw
 */
public interface ShopBagView extends BaseView {
    /**
     * 显示购物车商品
     *
     * @param infos
     */
    void showShopBagData(List<GoodsInfo> infos);

    /**
     * 显示排行商品
     *
     * @param infos
     */
    void showTopGoods(List<GoodsInfo> infos);

    /**
     * 显示删除按钮
     */
    void showDeleteBtn();

    /**
     * 隐藏删除按钮
     */
    void hideDeleteBtn();

    /**
     * 删除商品成功
     */
    void deleteShopBagSucc(String msg);


    /**
     * 提交购物车
     */
    void confirmShopBag();

    /**
     * 购物车添加商品成功
     */
    void addGoodsSucc(String msg);


    /**
     * 购物车移除商品成功
     */
    void removeGoodsSucc(String msg);

    /**
     * 添加收藏成功
     */
    void addCollectSucc(String msg);


    /**
     * 选中购物袋商品
     */
    void checkGoods(int position);

    /**
     * 取消购物袋中选中的商品
     */
    void noCheckGoods(int position);

    /**
     * 请求购物车数据异常
     *
     * @param msg
     */
    void requestDataError(String msg);

    /**
     * chang购物车数量，删除购物车请求异常
     *
     * @param msg
     */
    void requestError(String msg);
}
