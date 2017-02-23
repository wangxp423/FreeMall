package com.aero.droid.dutyfree.talent.bean;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : 活动商品信息(专场，专题，海报)
 */
public class ActiveInfo {
    private String activeId;//活动Id
    private String activeName;//活动名称
    private String startTime;//活动开始时间
    private String endTime;//活动结束时间
    private String headerImg;//活动图片
    private String memo;//活动简介
    private String shareImage;//活动分享图片
    private String shareText;//活动分享文字
    private String shareHtml;//活动分享链接
    private List<GoodsDetail> goodsInfoList; //活动商品集合

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
    }

    public String getShareText() {
        return shareText;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText;
    }

    public String getShareHtml() {
        return shareHtml;
    }

    public void setShareHtml(String shareHtml) {
        this.shareHtml = shareHtml;
    }

    public List<GoodsDetail> getGoodsInfoList() {
        return goodsInfoList;
    }

    public void setGoodsInfoList(List<GoodsDetail> goodsInfoList) {
        this.goodsInfoList = goodsInfoList;
    }
}
