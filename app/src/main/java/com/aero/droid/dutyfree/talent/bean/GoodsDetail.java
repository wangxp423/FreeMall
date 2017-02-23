package com.aero.droid.dutyfree.talent.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangxp on 2015/9/2. 商品详情
 */
public class GoodsDetail extends GoodsInfo{
    private String activeName;//活动名称
    private String prop;//商品规格信息(大小，功能等 里面是JSONArray)
    private String inventory;//充裕|少量|缺货
    private String isReserve;//添加购物车按钮是否可用 0：可用，1不可用
    private String isFavorite;//是否收藏 0:未收藏，1:已收藏
    private String showPriceDesc;//是否显示价格描述信息  0否   1是
    private String priceDesc;//价格描述信息
    private String priceIndicator;//各个商家价格集合(里面是JSONArray)
    private String goodsImags;//商品图片集合(里面是JSONArray)
    private GoodComents coments;//商品评论


    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }


    public GoodComents getComents() {
        return coments;
    }

    public void setComents(GoodComents coments) {
        this.coments = coments;
    }

    public String getGoodsImags() {
        return goodsImags;
    }

    public void setGoodsImags(String goodsImags) {
        this.goodsImags = goodsImags;
    }

    public String getPriceIndicator() {
        return priceIndicator;
    }

    public void setPriceIndicator(String priceIndicator) {
        this.priceIndicator = priceIndicator;
    }

    public String getShowPriceDesc() {
        return showPriceDesc;
    }

    public void setShowPriceDesc(String showPriceDesc) {
        this.showPriceDesc = showPriceDesc;
    }

    public String getPriceDesc() {
        return priceDesc;
    }

    public void setPriceDesc(String priceDesc) {
        this.priceDesc = priceDesc;
    }




    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getIsReserve() {
        return isReserve;
    }

    public void setIsReserve(String isReserve) {
        this.isReserve = isReserve;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public GoodsDetail() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.activeName);
        dest.writeString(this.prop);
        dest.writeString(this.inventory);
        dest.writeString(this.isReserve);
        dest.writeString(this.isFavorite);
        dest.writeString(this.showPriceDesc);
        dest.writeString(this.priceDesc);
        dest.writeString(this.priceIndicator);
        dest.writeString(this.goodsImags);
        dest.writeParcelable(this.coments, 0);
    }

    protected GoodsDetail(Parcel in) {
        super(in);
        this.activeName = in.readString();
        this.prop = in.readString();
        this.inventory = in.readString();
        this.isReserve = in.readString();
        this.isFavorite = in.readString();
        this.showPriceDesc = in.readString();
        this.priceDesc = in.readString();
        this.priceIndicator = in.readString();
        this.goodsImags = in.readString();
        this.coments = in.readParcelable(GoodComents.class.getClassLoader());
    }

    public static final Creator<GoodsDetail> CREATOR = new Creator<GoodsDetail>() {
        public GoodsDetail createFromParcel(Parcel source) {
            return new GoodsDetail(source);
        }

        public GoodsDetail[] newArray(int size) {
            return new GoodsDetail[size];
        }
    };
}
