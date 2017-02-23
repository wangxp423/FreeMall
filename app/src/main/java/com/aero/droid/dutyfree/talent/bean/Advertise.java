package com.aero.droid.dutyfree.talent.bean;

/**
 * 广告类
 * Created by wangxp on 2015/7/25.
 */
public class Advertise {
    private String id; //广告ID
    private String advertImage; //广告图片
    private String advertName; //广告名称
    private String advertTitle; //广告标题
    private String advertArea; //"广告位置：0:首页，1:进入购物车2:访问我的优惠券 3:结算后 4:查看活动"
    private String postpone; //广告推迟时间（单位：秒） 0为不推迟
    private String pushTimes; //推送次数  -1为不限次数
    private String ruleType; //广告规则：1:进入活动 2:进入商品详情 3:打开分组 4:领取优惠券",
    private String ruleItemId; //规则对应的项目ID （规则:ID 1:活动Id，2:商品Id, 3:发现Id 4:优惠券Id）",
    private String ruleItemType; //"项目类型：(只有规则为1的时候字段才有效：1:专场 2:海报 3:专题)"
    private String beginTime;//开始时间
    private String endTime;//结束时间
    private String btnImage;//广告按钮图片
    private String sort;//广告优先级

    public String getBtnImage() {
        return btnImage;
    }

    public void setBtnImage(String btnImage) {
        this.btnImage = btnImage;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdvertImage() {
        return advertImage;
    }

    public void setAdvertImage(String advertImage) {
        this.advertImage = advertImage;
    }

    public String getAdvertName() {
        return advertName;
    }

    public void setAdvertName(String advertName) {
        this.advertName = advertName;
    }

    public String getAdvertTitle() {
        return advertTitle;
    }

    public void setAdvertTitle(String advertTitle) {
        this.advertTitle = advertTitle;
    }

    public String getAdvertArea() {
        return advertArea;
    }

    public void setAdvertArea(String advertArea) {
        this.advertArea = advertArea;
    }

    public String getPostpone() {
        return postpone;
    }

    public void setPostpone(String postpone) {
        this.postpone = postpone;
    }

    public String getPushTimes() {
        return pushTimes;
    }

    public void setPushTimes(String pushTimes) {
        this.pushTimes = pushTimes;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleItemId() {
        return ruleItemId;
    }

    public void setRuleItemId(String ruleItemId) {
        this.ruleItemId = ruleItemId;
    }

    public String getRuleItemType() {
        return ruleItemType;
    }

    public void setRuleItemType(String ruleItemType) {
        this.ruleItemType = ruleItemType;
    }
}
