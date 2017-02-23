package com.aero.droid.dutyfree.talent.app;

/**
 * Author : wangxp
 * Date : 2015/12/1
 * Desc :  常量
 */
public class Constants {
    //全局的请求 event_tag
    public static final int EVENT_GET_DATA = 0X100;
    public static final int EVENT_COMMIT_DATA = 0X100 + 120;
    public static final int EVENT_REFRESH_DATA = EVENT_GET_DATA + 10;
    public static final int EVENT_LOAD_DATA = EVENT_GET_DATA + 20;
    public static final int EVENT_DELETE_DATA = EVENT_GET_DATA + 30;
    public static final int EVENT_ADD_DATA = EVENT_GET_DATA + 40;
    public static final int EVENT_REMOVE_DATA = EVENT_GET_DATA + 50;
    public static final int EVENT_ADD_FAVO = EVENT_GET_DATA + 60;
    public static final int EVENT_ADD_SHOPBAG = EVENT_GET_DATA + 70;
    public static final int EVENT_START_ACTIVITY = EVENT_GET_DATA + 80;
    public static final int EVENT_START_CAMERA_ACTIVITY = EVENT_GET_DATA + 90;
    public static final int EVENT_START_PHOTO_ACTIVITY = EVENT_GET_DATA + 100;
    public static final int EVENT_CUT_PHOTO = EVENT_GET_DATA + 110;

    //全局页面跳转
    /**
     * 无事件或查看大图
     */
    public static final int ACTIVITY_TO_IMG = 0;
    /**
     * 跳转到H5页面
     */
    public static final int ACTIVITY_TO_H5 = 1;
    /**
     * 跳转到活动
     */
    public static final int ACTIVITY_TO_ACTIVE = 2;
    /**
     * 活动1(专场)
     */
    public static final int ACTIVE_TO_SPECIAL = 1;
    /**
     * 活动1(海报)
     */
    public static final int ACTIVE_TO_POSTER = 2;
    /**
     * 活动1(专题)
     */
    public static final int ACTIVE_TO_TOPIC = 3;
    /**
     * 跳转商品详情
     */
    public static final int ACTIVITY_TO_GOODDETAIL = 3;
    /**
     * 跳转到折扣专区
     */
    public static final int ACTIVITY_TO_DISCOUNT = 41;
    /**
     * 邀请得奖
     */
    public static final int ACTIVITY_TO_INVITE = 42;
    /**
     * 跳转到特权任务
     */
    public static final int ACTIVITY_TO_TASK = 43;
    /**
     * 跳转个人资料
     */
    public static final int ACTIVITY_TO_USERDETAIL = 44;
    /**
     * 商品分类
     */
    public static final int ACTIVITY_TO_GOODCATEGORY = 45;
    /**
     * 品牌分类
     */
    public static final int ACTIVITY_TO_BRANDCATEGORY = 46;
    /**
     * 跳转首页个人中心
     */
    public static final int ACTIVITY_TO_USER = 47;
    /**
     * 跳转到首页(精选)
     */
    public static final int ACTIVITY_TO_MAIN = 48;
    /**
     * 跳转认证(注册)
     */
    public static final int ACTIVITY_TO_REGISTER = 49;
}
