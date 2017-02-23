package com.aero.droid.dutyfree.talent.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangxp on 2015/8/22.  航班信息
 */
public class FlightInfo implements Parcelable {
	private String airline; //航空公司
	private String flightNo; //航班号
	private String depart; //起飞机场
	private String arrive;  //降落机场
	private String departDate; //起飞日期
	private String departTime; //起飞时间
	private String arriveDate; //到达日期
	private String arriveTime; //到达时间

	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getArrive() {
		return arrive;
	}
	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
	public String getDepartDate() {
		return departDate;
	}
	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}
	public String getDepartTime() {
		return departTime;
	}
	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.airline);
		dest.writeString(this.flightNo);
		dest.writeString(this.depart);
		dest.writeString(this.arrive);
		dest.writeString(this.departDate);
		dest.writeString(this.departTime);
		dest.writeString(this.arriveDate);
		dest.writeString(this.arriveTime);
	}

	public FlightInfo() {
	}

	protected FlightInfo(Parcel in) {
		this.airline = in.readString();
		this.flightNo = in.readString();
		this.depart = in.readString();
		this.arrive = in.readString();
		this.departDate = in.readString();
		this.departTime = in.readString();
		this.arriveDate = in.readString();
		this.arriveTime = in.readString();
	}

	public static final Parcelable.Creator<FlightInfo> CREATOR = new Parcelable.Creator<FlightInfo>() {
		public FlightInfo createFromParcel(Parcel source) {
			return new FlightInfo(source);
		}

		public FlightInfo[] newArray(int size) {
			return new FlightInfo[size];
		}
	};
}
