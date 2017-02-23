package com.aero.droid.dutyfree.talent.presenter;

import com.aero.droid.dutyfree.talent.bean.GoodsInfo;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/15
 * Desc : 订单确认 逻辑控制器
 */
public interface OrderConfrimPresenter {
    /**
     * 获取航空公司列表
     *
     * @param log_tag
     */
    void getAirportList(String log_tag);

    /**
     * 获取 可使用优惠券列表
     *
     * @param log_tag
     */
    void getCouponsList(String log_tag, List<GoodsInfo> goodsInfoList);

    /**
     * 查询国航航班信息
     *
     * @param log_tag
     */
    void checkCAAirport(String log_tag, String twoCode, String flightDate, String flightNum);

    /**
     * 查询海航航班信息
     *
     * @param log_tag
     */
    void checkHUAirport(String log_tag, String twoCode, String flightDate, String flightNum);

    /**
     * 提交订单
     *
     * @param log_tag
     * @param mobile        用户电话
     * @param tekeOffDate   起飞时间
     * @param fightNo       航班号
     * @param payMoney      订单金额
     * @param memberName    用户名字
     * @param couponId      优惠券Id
     * @param goodsInfoList 商品集合
     */
    void commitOrder(String log_tag, String mobile, String tekeOffDate, String fightNo, String payMoney, String memberName, String couponId, List<GoodsInfo> goodsInfoList);


}
