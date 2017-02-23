package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.ActiveInfo;
import com.aero.droid.dutyfree.talent.bean.ComentsInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : 商品(专场，专题，海报)列表页面(因为数据格式一样，操作大同小异，可以在presenter里面做处理)
 */
public interface SpecialGoodsView extends BaseView{

    /**
     * 显示商品列表
     * @param activeInfo
     */
    void showGoodsListData(ActiveInfo activeInfo);
    /**
     * 下拉 刷新更多数据
     * @param goodsInfoList
     */
    void refreshGoodsListData(List<GoodsDetail> goodsInfoList);

    /**
     * 上拉 加载更多数据
     * @param goodsInfoList
     */
    void loadGoodsListData(List<GoodsDetail> goodsInfoList);

    /**
     * 请求失败
     * @param msg
     */
    void requestError(String msg);
}
