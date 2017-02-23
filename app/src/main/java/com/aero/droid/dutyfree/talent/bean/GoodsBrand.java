package com.aero.droid.dutyfree.talent.bean;

public class GoodsBrand {
	private String markId;//品牌Id
	private String markName;//品牌name
	private String markInitial;//品牌首字母
	private String markImg;//品牌图标
	private String sortLetters;  //显示数据拼音的首字母
	private String headerImg;  //顶部图片

	public String getHeaderImg() {
		return headerImg;
	}

	public void setHeaderImg(String headerImg) {
		this.headerImg = headerImg;
	}

	public String getMarkId() {
		return markId;
	}

	public void setMarkId(String markId) {
		this.markId = markId;
	}

	public String getMarkName() {
		return markName;
	}

	public void setMarkName(String markName) {
		this.markName = markName;
	}

	public String getMarkInitial() {
		return markInitial;
	}

	public void setMarkInitial(String markInitial) {
		this.markInitial = markInitial;
	}

	public String getMarkImg() {
		return markImg;
	}

	public void setMarkImg(String markImg) {
		this.markImg = markImg;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
