package com.aero.droid.dutyfree.talent.presenter;

import android.view.View;
import android.widget.ImageView;

import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/11
 * Desc : 商品详情 逻辑控制交互器
 */
public interface GoodsDetailPresenter {
    /**
     * 获取商品详情
     * @param log_tag
     * @param goodId 商品ID
     * @param srcType 来源类型
     * @param srcId 来源ID
     */
    void getGoodsDetail(String log_tag,String goodId,String srcType,String srcId);

    /**
     * 获取推荐商品
     *
     * @param log_tag
     */
    void getRecommendList(String log_tag,String paramType,String param,int curPage,int pageSize);
    /**
     * 获取同品牌商品
     *
     * @param log_tag
     */
    void getSameBrandList(String log_tag,String paramType,String param,int curPage,int pageSize);


    /**
     * 添加购物车
     * @param log_tag
     * @param goodId
     */
    void addShopBag(String log_tag,String goodId);

    /**
     * 添加收藏
     * @param log_tag
     * @param goodId
     */
    void addFavorite(String log_tag,String goodId);
    /**
     * 移除收藏
     * @param log_tag
     * @param goodId
     */
    void removeFavorite(String log_tag,String goodId);



    /**
     * 如果商品商品详情只有一个图片  得到该图片
     *
     * @param log_tag
     */
    void getImageView(String log_tag);

    /**
     * 如果商品时多图，得到 多图需要的数据
     * @param log_tag
     * @param detail
     */
    void getImgs(String log_tag, GoodsDetail detail);

    /**
     * 获取 商品描述View
     *
     * @param log_tag
     */
    void getGoodsDescView(String log_tag);

    /**
     * 获取 商品评论View
     *
     * @param log_tag
     */
    void getComentsView(String log_tag);

    /**
     * 获取 同品牌商品V
     *
     * @param log_tag
     */
    void getSameBrandView(String log_tag);

    /**
     * 获取 推荐商品V
     *
     * @param log_tag
     */
    void getRecommendGoodsView(String log_tag);
}
