package com.aero.droid.dutyfree.talent.bean;

/**
 * 商品 分类类
 * Created by wangxp on 2015/7/26.
 */
public class GoodsCategory {
    private String categoryId;//分类Id
    private String categoryName;//分类name
    private String categoryImg;//分类图标
    private String headerImg;//顶部图标

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }
}
