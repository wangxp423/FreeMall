package com.aero.droid.dutyfree.talent.bean;

/**
 * Author : wangxp
 * Date : 2015/12/18
 * Desc : 特权任务(任务进度)
 */
public class TaskInfo {
    private String condName; //条件名称
    private String condValue; //条件值
    private String finishValue; //完成值
    private String acType; //全局ActionType（同Banner）
    private String acParams; //动作参数
    private String acName; //去做任务名称

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getCondName() {
        return condName;
    }

    public void setCondName(String condName) {
        this.condName = condName;
    }

    public String getCondValue() {
        return condValue;
    }

    public void setCondValue(String condValue) {
        this.condValue = condValue;
    }

    public String getFinishValue() {
        return finishValue;
    }

    public void setFinishValue(String finishValue) {
        this.finishValue = finishValue;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getAcParams() {
        return acParams;
    }

    public void setAcParams(String acParams) {
        this.acParams = acParams;
    }
}
