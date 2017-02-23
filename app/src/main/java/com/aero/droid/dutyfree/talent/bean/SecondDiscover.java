package com.aero.droid.dutyfree.talent.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/28
 * Desc : 新接口下的  二级发现 实体类
 */
public class SecondDiscover implements Parcelable {
    private SubFind subFind; //二级发现 实体类
    private List<GoodsInfo> goodsInfoList;//二级发现下商品集合

    public SubFind getSubFind() {
        return subFind;
    }

    public void setSubFind(SubFind subFind) {
        this.subFind = subFind;
    }

    public List<GoodsInfo> getGoodsInfoList() {
        return goodsInfoList;
    }

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        this.goodsInfoList = goodsInfoList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.subFind, 0);
        dest.writeTypedList(goodsInfoList);
    }

    public SecondDiscover() {
    }

    protected SecondDiscover(Parcel in) {
        this.subFind = in.readParcelable(SubFind.class.getClassLoader());
        this.goodsInfoList = in.createTypedArrayList(GoodsInfo.CREATOR);
    }

    public static final Parcelable.Creator<SecondDiscover> CREATOR = new Parcelable.Creator<SecondDiscover>() {
        public SecondDiscover createFromParcel(Parcel source) {
            return new SecondDiscover(source);
        }

        public SecondDiscover[] newArray(int size) {
            return new SecondDiscover[size];
        }
    };
}
