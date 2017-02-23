package com.aero.droid.dutyfree.talent.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Model：用户信息
 * @author wangxp
 * 用户信息
 */
public class User implements Parcelable {
	private String memberId;//用户Id
	private String name;//用户名
	private String nickName;//昵称
	private String phone;//电话
	private String tpno;//第三方AppId
	private String unionId;//微信unionid
	private String usid;//微信appid
	private String photo;//头像
	private String vipId;//vip等级ID
	private String bindStatus;//绑定状态  0：手机、微信以绑定,1:没有绑定手机，2：没有绑定微信",
	private String jobNum;//工号
	private String twoCode;//二字码
	private String boardingPass;//登机牌
	private String isAuth;//0：未认证；1：已认证",
	private List<SpecialInfo> specialInfos; //用户拥有的特权信息
	private String unCmtQty;//已完成订单未评论的商品数量
	private String couponQty;//优惠券数量
	private String favoQty;//收藏数量
	private String msgQty;//消息总数量
	private String unreadMsgQty;//未读消息总数量

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public List<SpecialInfo> getSpecialInfos() {
		return specialInfos;
	}

	public void setSpecialInfos(List<SpecialInfo> specialInfos) {
		this.specialInfos = specialInfos;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTpno() {
		return tpno;
	}

	public void setTpno(String tpno) {
		this.tpno = tpno;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getUsid() {
		return usid;
	}

	public void setUsid(String usid) {
		this.usid = usid;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(String bindStatus) {
		this.bindStatus = bindStatus;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getTwoCode() {
		return twoCode;
	}

	public void setTwoCode(String twoCode) {
		this.twoCode = twoCode;
	}

	public String getBoardingPass() {
		return boardingPass;
	}

	public void setBoardingPass(String boardingPass) {
		this.boardingPass = boardingPass;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}


	public String getUnCmtQty() {
		return unCmtQty;
	}

	public void setUnCmtQty(String unCmtQty) {
		this.unCmtQty = unCmtQty;
	}

	public String getCouponQty() {
		return couponQty;
	}

	public void setCouponQty(String couponQty) {
		this.couponQty = couponQty;
	}

	public String getFavoQty() {
		return favoQty;
	}

	public void setFavoQty(String favoQty) {
		this.favoQty = favoQty;
	}

	public String getMsgQty() {
		return msgQty;
	}

	public void setMsgQty(String msgQty) {
		this.msgQty = msgQty;
	}

	public String getUnreadMsgQty() {
		return unreadMsgQty;
	}

	public void setUnreadMsgQty(String unreadMsgQty) {
		this.unreadMsgQty = unreadMsgQty;
	}

	public User() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.memberId);
		dest.writeString(this.name);
		dest.writeString(this.nickName);
		dest.writeString(this.phone);
		dest.writeString(this.tpno);
		dest.writeString(this.unionId);
		dest.writeString(this.usid);
		dest.writeString(this.photo);
		dest.writeString(this.vipId);
		dest.writeString(this.bindStatus);
		dest.writeString(this.jobNum);
		dest.writeString(this.twoCode);
		dest.writeString(this.boardingPass);
		dest.writeString(this.isAuth);
		dest.writeTypedList(specialInfos);
		dest.writeString(this.unCmtQty);
		dest.writeString(this.couponQty);
		dest.writeString(this.favoQty);
		dest.writeString(this.msgQty);
		dest.writeString(this.unreadMsgQty);
	}

	protected User(Parcel in) {
		this.memberId = in.readString();
		this.name = in.readString();
		this.nickName = in.readString();
		this.phone = in.readString();
		this.tpno = in.readString();
		this.unionId = in.readString();
		this.usid = in.readString();
		this.photo = in.readString();
		this.vipId = in.readString();
		this.bindStatus = in.readString();
		this.jobNum = in.readString();
		this.twoCode = in.readString();
		this.boardingPass = in.readString();
		this.isAuth = in.readString();
		this.specialInfos = in.createTypedArrayList(SpecialInfo.CREATOR);
		this.unCmtQty = in.readString();
		this.couponQty = in.readString();
		this.favoQty = in.readString();
		this.msgQty = in.readString();
		this.unreadMsgQty = in.readString();
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
