package com.aero.droid.dutyfree.talent.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author : wangxp
 * Date : 2015/12/21
 * Desc : 特权等级 信息
 */
public class SpecialInfo implements Parcelable {
    private String spId;//当前特权等级Id
    private String spDesc;//当前特权描述

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getSpDesc() {
        return spDesc;
    }

    public void setSpDesc(String spDesc) {
        this.spDesc = spDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.spId);
        dest.writeString(this.spDesc);
    }

    public SpecialInfo() {
    }

    protected SpecialInfo(Parcel in) {
        this.spId = in.readString();
        this.spDesc = in.readString();
    }

    public static final Parcelable.Creator<SpecialInfo> CREATOR = new Parcelable.Creator<SpecialInfo>() {
        public SpecialInfo createFromParcel(Parcel source) {
            return new SpecialInfo(source);
        }

        public SpecialInfo[] newArray(int size) {
            return new SpecialInfo[size];
        }
    };
}
