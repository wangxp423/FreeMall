package com.aero.droid.dutyfree.talent.bean;

/**
 * author wangxp 商品排序
 */
public class GoodsSort {
	private String sortParam;// 筛选参数
	private String sortName;// 筛选分类名称
	private String sortType;// 筛选分类类型

	public String getSortParam() {
		return sortParam;
	}

	public void setSortParam(String sortParam) {
		this.sortParam = sortParam;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
}
