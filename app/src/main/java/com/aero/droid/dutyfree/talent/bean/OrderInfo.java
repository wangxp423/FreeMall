package com.aero.droid.dutyfree.talent.bean;

/**
 * 
 * @author wangxp(订单信息)
 * 
 */
public class OrderInfo {
	private String status;// 订单状态
	private String statusMsg;// 订单消息
	private String takeoffDate;// 起飞时间
	private String orderId;// 订单ID
	private String flightNo;// 航班号
	private String orderNo;// 订单号
	private String orderTime;// 下单时间
	private String orderPrice_rmb;// 订单总额rmb
	private String orderPrice_dollar;// 订单总额dollar "orderPrice"
	private String memberId;// 用户Id "MemberID"

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getTakeoffDate() {
		return takeoffDate;
	}

	public void setTakeoffDate(String takeoffDate) {
		this.takeoffDate = takeoffDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderPrice_rmb() {
		return orderPrice_rmb;
	}

	public void setOrderPrice_rmb(String orderPrice_rmb) {
		this.orderPrice_rmb = orderPrice_rmb;
	}

	public String getOrderPrice_dollar() {
		return orderPrice_dollar;
	}

	public void setOrderPrice_dollar(String orderPrice_dollar) {
		this.orderPrice_dollar = orderPrice_dollar;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}
