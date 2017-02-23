package com.aero.droid.dutyfree.talent.bean;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/17
 * Desc : 订单详情 实体类
 */
public class OrderDetail {
    private String phone; //订单电话
    private String memberName;//订单姓名
    private String payMoney;//付款金额
    private String orderMoney;//订单金额
    private String arrive;//到达地点
    private String depart;//出发地点
    private String takeoffDate;//出发时间
    private String orderStatus;//订单状态
    private List<GoodsInfo> goodsInfoList; //订单中包含的商品
    private List<StatusInfo> statusInfos; //订单状态信息

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getTakeoffDate() {
        return takeoffDate;
    }

    public void setTakeoffDate(String takeoffDate) {
        this.takeoffDate = takeoffDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<GoodsInfo> getGoodsInfoList() {
        return goodsInfoList;
    }

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        this.goodsInfoList = goodsInfoList;
    }

    public List<StatusInfo> getStatusInfos() {
        return statusInfos;
    }

    public void setStatusInfos(List<StatusInfo> statusInfos) {
        this.statusInfos = statusInfos;
    }
}
