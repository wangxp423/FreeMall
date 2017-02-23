package com.aero.droid.dutyfree.talent.bean;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/17
 * Desc : 我的订单 订单列表页
 */
public class OrderListInfo {
    private FlightInfo flightInfo;
    private List<OrderInfo> orderInfoList;

    public FlightInfo getFlightInfo() {
        return flightInfo;
    }

    public void setFlightInfo(FlightInfo flightInfo) {
        this.flightInfo = flightInfo;
    }

    public List<OrderInfo> getOrderInfoList() {
        return orderInfoList;
    }

    public void setOrderInfoList(List<OrderInfo> orderInfoList) {
        this.orderInfoList = orderInfoList;
    }
}
