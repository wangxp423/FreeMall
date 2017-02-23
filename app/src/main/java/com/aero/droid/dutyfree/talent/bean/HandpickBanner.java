package com.aero.droid.dutyfree.talent.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangxp on 2015/8/21. 精选 轮播图
 */
public class HandpickBanner implements Parcelable {
    private String acType; //动作类型 0 无事件或者查看大图， 1 H5 ,2 转活动(1.专场;2.海报;3.专题)，3 转商品详情，41 转折扣专区，42 邀请得奖励，43 特权任务，44 个人资料，45 分类，46 品牌，47 个人中心
    private String acParams; //动作参数
    private String insideLayout; //只有 acType = 2 时候为活动跳转 三种不同页面
    private String img; //图片
    private String sno; //排序号
    private String shareImg; //分享图片
    private String shareDesc; //分享描述
    private String shareTheme;//分享主题

    public String getInsideLayout() {
        return insideLayout;
    }

    public void setInsideLayout(String insideLayout) {
        this.insideLayout = insideLayout;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getShareTheme() {
        return shareTheme;
    }

    public void setShareTheme(String shareTheme) {
        this.shareTheme = shareTheme;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getAcParams() {
        return acParams;
    }

    public void setAcParams(String acParams) {
        this.acParams = acParams;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public HandpickBanner() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.acType);
        dest.writeString(this.acParams);
        dest.writeString(this.insideLayout);
        dest.writeString(this.img);
        dest.writeString(this.sno);
        dest.writeString(this.shareImg);
        dest.writeString(this.shareDesc);
        dest.writeString(this.shareTheme);
    }

    protected HandpickBanner(Parcel in) {
        this.acType = in.readString();
        this.acParams = in.readString();
        this.insideLayout = in.readString();
        this.img = in.readString();
        this.sno = in.readString();
        this.shareImg = in.readString();
        this.shareDesc = in.readString();
        this.shareTheme = in.readString();
    }

    public static final Creator<HandpickBanner> CREATOR = new Creator<HandpickBanner>() {
        public HandpickBanner createFromParcel(Parcel source) {
            return new HandpickBanner(source);
        }

        public HandpickBanner[] newArray(int size) {
            return new HandpickBanner[size];
        }
    };
}
