package com.aero.droid.dutyfree.talent.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class CouponsInfo implements Parcelable {
	/**
	 * 优惠卷信息
	 */
	private String couponName;//优惠卷名称
	private String status;//状态：-1为已冻结，0为已使用，1为未使用，2为已失效
	private String type;//状态 1满减 2打折
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private String couponValue; //信息
	private String couponId; //优惠券Id
	private String subTitle; //条件信息
	private String minMoney; //条件金额
	private String maxMoney;//打折上限金额

	public String getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(String minMoney) {
		this.minMoney = minMoney;
	}

	public String getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(String maxMoney) {
		this.maxMoney = maxMoney;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getCouponValue() {
		return couponValue;
	}

	public void setCouponValue(String couponValue) {
		this.couponValue = couponValue;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.couponName);
		dest.writeString(this.status);
		dest.writeString(this.type);
		dest.writeString(this.beginTime);
		dest.writeString(this.endTime);
		dest.writeString(this.couponValue);
		dest.writeString(this.couponId);
		dest.writeString(this.subTitle);
		dest.writeString(this.minMoney);
		dest.writeString(this.maxMoney);
	}

	public CouponsInfo() {
	}

	protected CouponsInfo(Parcel in) {
		this.couponName = in.readString();
		this.status = in.readString();
		this.type = in.readString();
		this.beginTime = in.readString();
		this.endTime = in.readString();
		this.couponValue = in.readString();
		this.couponId = in.readString();
		this.subTitle = in.readString();
		this.minMoney = in.readString();
		this.maxMoney = in.readString();
	}

	public static final Parcelable.Creator<CouponsInfo> CREATOR = new Parcelable.Creator<CouponsInfo>() {
		public CouponsInfo createFromParcel(Parcel source) {
			return new CouponsInfo(source);
		}

		public CouponsInfo[] newArray(int size) {
			return new CouponsInfo[size];
		}
	};
}
