package com.aero.droid.dutyfree.talent.interactor;

import android.widget.ImageView;

import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.widgets.ComentsLayout;
import com.aero.droid.dutyfree.talent.widgets.GoodsIntroduceLayout;
import com.aero.droid.dutyfree.talent.widgets.RecommendGoodsLayout;

import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 商品详情 交互器
 */
public interface GoodsDetailInteractor {
    /**
     * 获取商品详情
     *
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void getGoodsDetail(String log_tag, int event_tag, HashMap<String, String> params);

    /**
     * 获取推荐商品
     *
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void getRecommendList(String log_tag, int event_tag, HashMap<String, String> params);
    /**
     * 添加购物车
     *
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void addShopBag(String log_tag, int event_tag, HashMap<String, String> params);
    /**
     * 添加收藏
     *
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void addCollect(String log_tag, int event_tag, HashMap<String, String> params);


    /**
     * 如果商品时多图，将图片url 放入ViewPagerFragment 需要的参数中
     * @param log_tag
     * @param detail
     * @return
     */
    List<HandpickBanner> getImgs(String log_tag, GoodsDetail detail);

}
