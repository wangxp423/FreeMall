package com.aero.droid.dutyfree.talent.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author : wangxp
 * Date : 2016/1/11
 * Desc : 消息中心 消息实体类
 */
public class MessageInfo implements Parcelable {
    private String msgId;//消息Id
    private String msgType;//消息类型 1.官方公告 2.订单助手 3.购物提醒 4.钱包动态
    private String isReaded;//0未读；1已读
    private String msgContent;//消息内容
    private String msgTitle;//消息标题
    private String msgTime;//消息时间
    private String jumpType;//跳转类型(全局)
    private String jumpParams;//跳转参数

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(String isReaded) {
        this.isReaded = isReaded;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getJumpParams() {
        return jumpParams;
    }

    public void setJumpParams(String jumpParams) {
        this.jumpParams = jumpParams;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msgId);
        dest.writeString(this.msgType);
        dest.writeString(this.isReaded);
        dest.writeString(this.msgContent);
        dest.writeString(this.msgTitle);
        dest.writeString(this.msgTime);
        dest.writeString(this.jumpType);
        dest.writeString(this.jumpParams);
    }

    public MessageInfo() {
    }

    protected MessageInfo(Parcel in) {
        this.msgId = in.readString();
        this.msgType = in.readString();
        this.isReaded = in.readString();
        this.msgContent = in.readString();
        this.msgTitle = in.readString();
        this.msgTime = in.readString();
        this.jumpType = in.readString();
        this.jumpParams = in.readString();
    }

    public static final Parcelable.Creator<MessageInfo> CREATOR = new Parcelable.Creator<MessageInfo>() {
        public MessageInfo createFromParcel(Parcel source) {
            return new MessageInfo(source);
        }

        public MessageInfo[] newArray(int size) {
            return new MessageInfo[size];
        }
    };
}
