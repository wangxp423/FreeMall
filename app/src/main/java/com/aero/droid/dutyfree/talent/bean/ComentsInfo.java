package com.aero.droid.dutyfree.talent.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author : wangxp
 * Date : 2015/12/10
 * Desc : 评论信息
 */
public class ComentsInfo implements Parcelable {
    private String id; //评论id
    private String icon;//评论人头像
    private String name;//评论人名字
    private String content;//评论内容
    private String time;//评论时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.icon);
        dest.writeString(this.name);
        dest.writeString(this.content);
        dest.writeString(this.time);
    }

    public ComentsInfo() {
    }

    protected ComentsInfo(Parcel in) {
        this.id = in.readString();
        this.icon = in.readString();
        this.name = in.readString();
        this.content = in.readString();
        this.time = in.readString();
    }

    public static final Parcelable.Creator<ComentsInfo> CREATOR = new Parcelable.Creator<ComentsInfo>() {
        public ComentsInfo createFromParcel(Parcel source) {
            return new ComentsInfo(source);
        }

        public ComentsInfo[] newArray(int size) {
            return new ComentsInfo[size];
        }
    };
}
