package com.aero.droid.dutyfree.talent.bean;

/**
 * Author : rongCL
 * Date : 2015/12/16
 * Desc : 优惠券
 */
public class CouponInfo {
    private String name;//名称：满200减30
    private String dateOut;//过期时间：还有3天过期
    private String validityDays;//有效期：2015.12.20 - 2015.12.23
    private String type;//类型：满减券1/优惠券0
    private String price;//价值：￥30
    private String useArea;//适用范围：全场通用
    private String useStatus;//使用状态：未使用-0 / 已使用-1 / 已过期-2

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(String validityDays) {
        this.validityDays = validityDays;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUseArea() {
        return useArea;
    }

    public void setUseArea(String useArea) {
        this.useArea = useArea;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }
}
