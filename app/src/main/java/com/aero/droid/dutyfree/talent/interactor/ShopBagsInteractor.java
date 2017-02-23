package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import com.aero.droid.dutyfree.talent.util.network.RespListener;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/11/27
 * Desc : 购物袋 交互器 类
 */
public interface ShopBagsInteractor {
    /**
     * 获取购物袋数据
     * @param log_tag log标签
     * @param event_tag  请求标签
     * @param context
     * @param params
     */
    void getShopBagData(String log_tag, int event_tag, Context context, HashMap<String, String> params);

    /**
     * 删除购物袋数据
     * @param log_tag log标签
     * @param event_tag  请求标签
     * @param context
     * @param params
     */
    void getDeleteShopBagData(String log_tag, int event_tag, Context context, HashMap<String, String> params);

    /**
     * 添加或者移除购物袋商品
     * @param log_tag log标签
     * @param event_tag  请求标签
     * @param context
     * @param params
     */
    void changeShopbag(String log_tag, int event_tag, Context context, HashMap<String, String> params);
    /**
     * 添加收藏
     *
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void addCollect(String log_tag, int event_tag,Context context, HashMap<String, String> params);

    /**
     * 购物袋为空的时候 显示 推荐排行商品
     * @param log_tag log标签
     * @param event_tag  请求标签
     * @param context
     * @param params
     */
    void getTopGoods(String log_tag, int event_tag, Context context, HashMap<String, String> params);
}
