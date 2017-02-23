package com.aero.droid.dutyfree.talent.bean;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/8
 * Desc : 精选 活动分类
 */
public class HandpickType {
    private String sno; //排序号  控制分类View 顺序
    private String type;//活动类型 1特惠 2畅销商品 3活动 4发现分组
    private String title;//活动标题
    private String isMore;//是否有查看更多 1是 2否
    private List<GoodsInfo> infoList; //活动商品列表

    //以下 字段 是 type=3的时候才有
    private String activeId; //活动id
    private String activeName; //活动名字
    private String activeImg; //活动图片
    private String outerLayout; //活动显示形式 1banner 2复合banner
    private String insideLayout;// 1专场 2海报 3专题
    //一下字段在 type=4的时候用
    private Discover discover; //发现（里面包含二级发现，二级发现里面跳转到商品）

    public Discover getDiscover() {
        return discover;
    }

    public void setDiscover(Discover discover) {
        this.discover = discover;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsMore() {
        return isMore;
    }

    public void setIsMore(String isMore) {
        this.isMore = isMore;
    }

    public String getOuterLayout() {
        return outerLayout;
    }

    public void setOuterLayout(String outerLayout) {
        this.outerLayout = outerLayout;
    }

    public String getInsideLayout() {
        return insideLayout;
    }

    public void setInsideLayout(String insideLayout) {
        this.insideLayout = insideLayout;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<GoodsInfo> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<GoodsInfo> infoList) {
        this.infoList = infoList;
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

    public String getActiveImg() {
        return activeImg;
    }

    public void setActiveImg(String activeImg) {
        this.activeImg = activeImg;
    }

}
