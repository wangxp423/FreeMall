package com.aero.droid.dutyfree.talent.bean;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/10
 * Desc : 折扣专区 实体类
 */
public class DiscountInfo {
    private String name; //专区名
    private String beginTime; //开始时间
    private String endTime; //结束时间
    private String sno; //排序号
    private String discount; //折扣
    private String status; //状态 0 可以购买 1 不可以购买
    private String des; //描述
    private String privId; //特权等级ID
    private String privName; //特权名称
    private String privDesc; //特权描述（vip1及以上）
    private List<GoodsInfo> goodsList; //折扣商品

    public String getPrivId() {
        return privId;
    }

    public void setPrivId(String privId) {
        this.privId = privId;
    }

    public String getPrivName() {
        return privName;
    }

    public void setPrivName(String privName) {
        this.privName = privName;
    }

    public String getPrivDesc() {
        return privDesc;
    }

    public void setPrivDesc(String privDesc) {
        this.privDesc = privDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<GoodsInfo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsInfo> goodsList) {
        this.goodsList = goodsList;
    }
}
