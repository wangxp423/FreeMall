package com.aero.droid.dutyfree.talent.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangxp on 2015/7/14. 二级发现 item
 */
public class SubFind implements Parcelable {
    private String subFindId;//二级发现Id
    private String subFindName;//二级发现名称
    private String subSquareImg;//二级发现图片
    private String end;//是否还有更多商品

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getSubSquareImg() {
        return subSquareImg;
    }

    public void setSubSquareImg(String subSquareImg) {
        this.subSquareImg = subSquareImg;
    }

    public String getSubFindId() {
        return subFindId;
    }

    public void setSubFindId(String subFindId) {
        this.subFindId = subFindId;
    }

    public String getSubFindName() {
        return subFindName;
    }

    public void setSubFindName(String subFindName) {
        this.subFindName = subFindName;
    }

    public SubFind() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subFindId);
        dest.writeString(this.subFindName);
        dest.writeString(this.subSquareImg);
        dest.writeString(this.end);
    }

    protected SubFind(Parcel in) {
        this.subFindId = in.readString();
        this.subFindName = in.readString();
        this.subSquareImg = in.readString();
        this.end = in.readString();
    }

    public static final Creator<SubFind> CREATOR = new Creator<SubFind>() {
        public SubFind createFromParcel(Parcel source) {
            return new SubFind(source);
        }

        public SubFind[] newArray(int size) {
            return new SubFind[size];
        }
    };
}
