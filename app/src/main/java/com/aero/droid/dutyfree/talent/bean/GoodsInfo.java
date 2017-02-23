package com.aero.droid.dutyfree.talent.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangxp on 2015/8/17. 商品信息类
 */
public class GoodsInfo implements Parcelable {
    private String id;//商品ID
    private String activeId; //活动ID
    private String goodsName;//商品名称
    private String goodsImg;//商品图片
    private String goodsDes; //商品描述
    private String markId;//品牌ID
    private String markName;//品牌名称
    private String markLogo;//品牌Logo
    private String markDes;//品牌描述
    private String lTagText;//标签text 左侧
    private String lTagModel;//标签URL 左侧
    private String rTagText;//标签text 右侧
    private String rTagModel;//标签URL 右侧
    private String price_airport_rmb;//机上价格RMB
    private String price_airport_dollar;//机上价格＄
    private String price_app_rmb;//app价格RMB
    private String price_app_dollar;//app价格＄
    private String price_ref_rmb;//市场价格RMB
    private String price_ref_dollar;//市场价格＄
    private String shareHtml; //分享链接
    private String shareImage; //分享图片链接
    private String shareText; //分享描述
    private String quantity;//商品数量
    private String spotprice_dollar;//当前价格(特惠价格)
    private String isBuy;//砍价活动中用户是否购买了此商品
    private String goodsPrice;//特惠价格
    private String discSepcName;//特惠名称
    private String status;//状态 0 已开始  1 预告中
    private String beginTime;//商品参加活动 开始时间
    private String endTime;//商品参加活动 结束时间
    private String stoke;//库存
    private String surStock;//商品剩余库存
    private String saleCount;//销售数量
    private String favoQty; //收藏/喜欢的数量
    private String discSpecId; //特惠/折扣Id
    private String topId; //排行id
    private String srcType;//商品来源类型
    private String srcId;//商品来源Id
    private String categoryId;//分类Id(商品详情下推荐商品)
    private String discount;//折扣力度

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSrcType() {
        return srcType;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId;
    }

    public String getDiscSpecId() {
        return discSpecId;
    }

    public void setDiscSpecId(String discSpecId) {
        this.discSpecId = discSpecId;
    }

    public String getFavoQty() {
        return favoQty;
    }

    public void setFavoQty(String favoQty) {
        this.favoQty = favoQty;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getMarkDes() {
        return markDes;
    }

    public void setMarkDes(String markDes) {
        this.markDes = markDes;
    }

    public String getlTagText() {
        return lTagText;
    }

    public void setlTagText(String lTagText) {
        this.lTagText = lTagText;
    }

    public String getlTagModel() {
        return lTagModel;
    }

    public void setlTagModel(String lTagModel) {
        this.lTagModel = lTagModel;
    }

    public String getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(String saleCount) {
        this.saleCount = saleCount;
    }

    public String getStoke() {
        return stoke;
    }

    public void setStoke(String stoke) {
        this.stoke = stoke;
    }

    public String getSurStock() {
        return surStock;
    }

    public void setSurStock(String surStock) {
        this.surStock = surStock;
    }

    public String getDiscSepcName() {
        return discSepcName;
    }

    public void setDiscSepcName(String discSepcName) {
        this.discSepcName = discSepcName;
    }

    public String getrTagText() {
        return rTagText;
    }

    public void setrTagText(String rTagText) {
        this.rTagText = rTagText;
    }

    public String getrTagModel() {
        return rTagModel;
    }

    public void setrTagModel(String rTagModel) {
        this.rTagModel = rTagModel;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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





    public String getMarkLogo() {
        return markLogo;
    }

    public void setMarkLogo(String markLogo) {
        this.markLogo = markLogo;
    }

    public String getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(String isBuy) {
        this.isBuy = isBuy;
    }

    public String getSpotprice_dollar() {
        return spotprice_dollar;
    }

    public void setSpotprice_dollar(String spotprice_dollar) {
        this.spotprice_dollar = spotprice_dollar;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsDes() {
        return goodsDes;
    }

    public void setGoodsDes(String goodsDes) {
        this.goodsDes = goodsDes;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }


    public String getPrice_airport_rmb() {
        return price_airport_rmb;
    }

    public void setPrice_airport_rmb(String price_airport_rmb) {
        this.price_airport_rmb = price_airport_rmb;
    }

    public String getPrice_airport_dollar() {
        return price_airport_dollar;
    }

    public void setPrice_airport_dollar(String price_airport_dollar) {
        this.price_airport_dollar = price_airport_dollar;
    }

    public String getPrice_app_rmb() {
        return price_app_rmb;
    }

    public void setPrice_app_rmb(String price_app_rmb) {
        this.price_app_rmb = price_app_rmb;
    }

    public String getPrice_app_dollar() {
        return price_app_dollar;
    }

    public void setPrice_app_dollar(String price_app_dollar) {
        this.price_app_dollar = price_app_dollar;
    }

    public String getPrice_ref_rmb() {
        return price_ref_rmb;
    }

    public void setPrice_ref_rmb(String price_ref_rmb) {
        this.price_ref_rmb = price_ref_rmb;
    }

    public String getPrice_ref_dollar() {
        return price_ref_dollar;
    }

    public void setPrice_ref_dollar(String price_ref_dollar) {
        this.price_ref_dollar = price_ref_dollar;
    }

    public String getShareHtml() {
        return shareHtml;
    }

    public void setShareHtml(String shareHtml) {
        this.shareHtml = shareHtml;
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


    public GoodsInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.activeId);
        dest.writeString(this.goodsName);
        dest.writeString(this.goodsImg);
        dest.writeString(this.goodsDes);
        dest.writeString(this.markId);
        dest.writeString(this.markName);
        dest.writeString(this.markLogo);
        dest.writeString(this.markDes);
        dest.writeString(this.lTagText);
        dest.writeString(this.lTagModel);
        dest.writeString(this.rTagText);
        dest.writeString(this.rTagModel);
        dest.writeString(this.price_airport_rmb);
        dest.writeString(this.price_airport_dollar);
        dest.writeString(this.price_app_rmb);
        dest.writeString(this.price_app_dollar);
        dest.writeString(this.price_ref_rmb);
        dest.writeString(this.price_ref_dollar);
        dest.writeString(this.shareHtml);
        dest.writeString(this.shareImage);
        dest.writeString(this.shareText);
        dest.writeString(this.quantity);
        dest.writeString(this.spotprice_dollar);
        dest.writeString(this.isBuy);
        dest.writeString(this.goodsPrice);
        dest.writeString(this.discSepcName);
        dest.writeString(this.status);
        dest.writeString(this.beginTime);
        dest.writeString(this.endTime);
        dest.writeString(this.stoke);
        dest.writeString(this.surStock);
        dest.writeString(this.saleCount);
        dest.writeString(this.favoQty);
        dest.writeString(this.discSpecId);
        dest.writeString(this.topId);
        dest.writeString(this.srcType);
        dest.writeString(this.srcId);
        dest.writeString(this.categoryId);
        dest.writeString(this.discount);
    }

    protected GoodsInfo(Parcel in) {
        this.id = in.readString();
        this.activeId = in.readString();
        this.goodsName = in.readString();
        this.goodsImg = in.readString();
        this.goodsDes = in.readString();
        this.markId = in.readString();
        this.markName = in.readString();
        this.markLogo = in.readString();
        this.markDes = in.readString();
        this.lTagText = in.readString();
        this.lTagModel = in.readString();
        this.rTagText = in.readString();
        this.rTagModel = in.readString();
        this.price_airport_rmb = in.readString();
        this.price_airport_dollar = in.readString();
        this.price_app_rmb = in.readString();
        this.price_app_dollar = in.readString();
        this.price_ref_rmb = in.readString();
        this.price_ref_dollar = in.readString();
        this.shareHtml = in.readString();
        this.shareImage = in.readString();
        this.shareText = in.readString();
        this.quantity = in.readString();
        this.spotprice_dollar = in.readString();
        this.isBuy = in.readString();
        this.goodsPrice = in.readString();
        this.discSepcName = in.readString();
        this.status = in.readString();
        this.beginTime = in.readString();
        this.endTime = in.readString();
        this.stoke = in.readString();
        this.surStock = in.readString();
        this.saleCount = in.readString();
        this.favoQty = in.readString();
        this.discSpecId = in.readString();
        this.topId = in.readString();
        this.srcType = in.readString();
        this.srcId = in.readString();
        this.categoryId = in.readString();
        this.discount = in.readString();
    }

    public static final Creator<GoodsInfo> CREATOR = new Creator<GoodsInfo>() {
        public GoodsInfo createFromParcel(Parcel source) {
            return new GoodsInfo(source);
        }

        public GoodsInfo[] newArray(int size) {
            return new GoodsInfo[size];
        }
    };
}
