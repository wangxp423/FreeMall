package com.aero.droid.dutyfree.talent.bean;

import android.widget.LinearLayout;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : 特权任务 实体类
 */
public class TaskListInfo {
    private String id; //特权id
    private String spImg; //特权图标
    private String spName; //特权名称
    private String spDesc; //特权描述
    private String status; //特权状态  0.拥有此特权 1.未领取，但已达成条件 2.未达成条件
    private String sno;//排序号
    private List<TaskInfo> taskInfoList; //具体任务

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpImg() {
        return spImg;
    }

    public void setSpImg(String spImg) {
        this.spImg = spImg;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSpDesc() {
        return spDesc;
    }

    public void setSpDesc(String spDesc) {
        this.spDesc = spDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public List<TaskInfo> getTaskInfoList() {
        return taskInfoList;
    }

    public void setTaskInfoList(List<TaskInfo> taskInfoList) {
        this.taskInfoList = taskInfoList;
    }
}
