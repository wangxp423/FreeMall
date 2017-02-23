package com.aero.droid.dutyfree.talent.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/10
 * Desc : 商品评论实体类
 */
public class GoodComents implements Parcelable {
    private String starQty;//评论红心数
    private String niceRate;//好评率(0-1)
    private String niceDesc;//好评率
    private String sumQty;//总数
    private List<ComentsInfo> comentsList;//评论条目


    public String getStarQty() {
        return starQty;
    }

    public void setStarQty(String starQty) {
        this.starQty = starQty;
    }

    public String getNiceRate() {
        return niceRate;
    }

    public void setNiceRate(String niceRate) {
        this.niceRate = niceRate;
    }

    public String getNiceDesc() {
        return niceDesc;
    }

    public void setNiceDesc(String niceDesc) {
        this.niceDesc = niceDesc;
    }

    public String getSumQty() {
        return sumQty;
    }

    public void setSumQty(String sumQty) {
        this.sumQty = sumQty;
    }

    public List<ComentsInfo> getComentsList() {
        return comentsList;
    }

    public void setComentsList(List<ComentsInfo> comentsList) {
        this.comentsList = comentsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.starQty);
        dest.writeString(this.niceRate);
        dest.writeString(this.niceDesc);
        dest.writeString(this.sumQty);
        dest.writeTypedList(comentsList);
    }

    public GoodComents() {
    }

    protected GoodComents(Parcel in) {
        this.starQty = in.readString();
        this.niceRate = in.readString();
        this.niceDesc = in.readString();
        this.sumQty = in.readString();
        this.comentsList = in.createTypedArrayList(ComentsInfo.CREATOR);
    }

    public static final Parcelable.Creator<GoodComents> CREATOR = new Parcelable.Creator<GoodComents>() {
        public GoodComents createFromParcel(Parcel source) {
            return new GoodComents(source);
        }

        public GoodComents[] newArray(int size) {
            return new GoodComents[size];
        }
    };
}
