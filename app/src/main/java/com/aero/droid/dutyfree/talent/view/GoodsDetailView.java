package com.aero.droid.dutyfree.talent.view;

import android.view.View;
import android.widget.ImageView;

import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.widgets.ComentsLayout;
import com.aero.droid.dutyfree.talent.widgets.GoodsIntroduceLayout;
import com.aero.droid.dutyfree.talent.widgets.RecommendGoodsLayout;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/10
 * Desc : 商品详情View
 */
public interface GoodsDetailView {
    /**
     * 得到 要 显示 的商品详情 数据
     *
     * @param detail
     */
    void showGoodsDetailData(GoodsDetail detail);

    /**
     * 得到 要 显示 同品牌商品
     *
     * @param goodsInfoList
     */
    void getSameBrandGoodsData(List<GoodsInfo> goodsInfoList);

    /**
     * 得到 要 显示 的推荐推荐
     *
     * @param goodsInfoList
     */
    void getRecommendGoodsData(List<GoodsInfo> goodsInfoList);

    /**
     * 显示单张图片
     */
    void showSingleImg();

    /**
     * 显示轮播商品图
     *
     * @param bannerList
     */
    void showPagerImgs(List<HandpickBanner> bannerList);

    /**
     * 显示评论view
     */
    void showComentsView();

    /**
     * 显示商品/品牌介绍view
     */
    void showIntroduceView();

    /**
     * 显示同品牌商品View
     */
    void showSameBrandView();

    /**
     * 显示推荐商品view
     */
    void showRecommendView();

    /**
     * 添加购物车成功
     *
     * @param msg
     */
    void addShopBagSuccess(String msg);

    /**
     * 添加收藏成功
     *
     * @param msg
     */
    void addFavoriteSuccess(String msg);
    /**
     * 移除收藏成功
     *
     * @param msg
     */
    void removeFavoriteSuccess(String msg);

    /**
     * 请求失败
     *
     * @param msg
     */
    void requestError(String msg);
}
