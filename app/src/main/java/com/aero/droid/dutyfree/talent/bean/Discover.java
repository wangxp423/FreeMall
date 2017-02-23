package com.aero.droid.dutyfree.talent.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxp on 2015/8/22.  发现 item
 */
public class Discover implements Parcelable {
    private String findName;//一级发现名称
    private String findId;//一级发现ID
    private String imgUrl;//图片地址
    private String bgColor;//背景颜色
    private String squareImg;//方形图片
    private String outerLayout;//外部布局方式 1 banner 2复合banner
    private List<SubFind> subFinds;

    public String getOuterLayout() {
        return outerLayout;
    }

    public void setOuterLayout(String outerLayout) {
        this.outerLayout = outerLayout;
    }

    public String getSquareImg() {
        return squareImg;
    }

    public void setSquareImg(String squareImg) {
        this.squareImg = squareImg;
    }

    public String getFindName() {
        return findName;
    }

    public void setFindName(String findName) {
        this.findName = findName;
    }

    public String getFindId() {
        return findId;
    }

    public void setFindId(String findId) {
        this.findId = findId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<SubFind> getSubFinds() {
        return subFinds;
    }

    public void setSubFinds(List<SubFind> subFinds) {
        this.subFinds = subFinds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.findName);
        dest.writeString(this.findId);
        dest.writeString(this.imgUrl);
        dest.writeString(this.bgColor);
        dest.writeList(this.subFinds);
    }

    public Discover() {
    }

    protected Discover(Parcel in) {
        this.findName = in.readString();
        this.findId = in.readString();
        this.imgUrl = in.readString();
        this.bgColor = in.readString();
        this.subFinds = new ArrayList<SubFind>();
        in.readList(this.subFinds, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<Discover> CREATOR = new Parcelable.Creator<Discover>() {
        public Discover createFromParcel(Parcel source) {
            return new Discover(source);
        }

        public Discover[] newArray(int size) {
            return new Discover[size];
        }
    };
}
