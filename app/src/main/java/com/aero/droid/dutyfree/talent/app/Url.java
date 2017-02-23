package com.aero.droid.dutyfree.talent.app;

/**
 * Created by wangxp on 2015/08/18.
 */
public class Url {


    //coogo 项目
    public static String COOGOBASE = "http://115.28.49.160:8890/coogo"; //测试
//    public static String COOGOBASE = "http://42.62.105.233:8895/coogo"; //正式


    /**
     * 首页(轮播图)
     */
    public static String MAINBANNER = COOGOBASE + "/mainBanner.json";
    /**
     * 首页推广商品
     */
    public static String MAINPAGE = COOGOBASE + "/mainPage.json";
    /**
     * 折扣专区
     */
    public static String DISCOUNTAREA = COOGOBASE + "/discountArea.json";
    /**
     * 商品详情
     */
    public static String GOODSDETAIL = COOGOBASE + "/goods/goodsDetail.json";
    /**
     * 登陆
     */
    public static String LOGIN = COOGOBASE + "/login.json";
    /**
     * 登陆验证码
     */
    public static String LOGINSECURITY = COOGOBASE + "/loginSecurityCode.json";
    /**
     * 空乘认证(校验工号/姓名)
     */
    public static String AUTHUSER = COOGOBASE + "/authIsExistUser.json";
    /**
     * 空乘手机验证
     */
    public static String AUTHSECURITY = COOGOBASE + "/authSecurityCode.json";
    /**
     * 空乘完成认证
     */
    public static String AUTHENTICATION = COOGOBASE + "/authentication.json";
    /**
     * 特权任务列表
     */
    public static String PRIVILEGELIST = COOGOBASE + "/sp/getSpecPrivList.json";
    /**
     * 领取特权任务
     */
    public static String OBTAINPRIVILEGE = COOGOBASE + "/sp/obtainSpecPriv.json";
    /**
     * 活动 专场
     */
    public static String SPECIALGOODS = COOGOBASE + "/activity/specialGoods.json";
    /**
     * 活动 专题
     */
    public static String TOPICGOODS = COOGOBASE + "/activity/subjectGoods.json";
    /**
     * 活动 海报
     */
    public static String POSTERGOODS = COOGOBASE + "/activity/posterGoods.json";
    /**
     * 发现详情列表
     */
    public static String SUBFINDS = COOGOBASE + "/subFinds.json";
    /**
     * 分类(商品/品牌)
     */
    public static String CATEGORYLIST = COOGOBASE + "/markCategoryList.json";
    /**
     * 邀请分享
     */
    public static String INVITESHARE = COOGOBASE + "/invitation/logEvent.json";
    /**
     * 商品评论列表
     */
    public static String COMMENTLIST = COOGOBASE + "/comment/getCmtList.json";
    /**
     * 提交评论
     */
    public static String COMMITCMT = COOGOBASE + "/comment/submitCmt.json";
    /**
     * 查看购物袋
     */
    public static String SHOPBAGDETAIL = COOGOBASE + "/cartDetail.json";
    /**
     * 加入购物袋
     */
    public static String ADDSHOPBAG = COOGOBASE + "/addShoppingCart.json";
    /**
     * 删除购物袋商品
     */
    public static String DELSHOPBAG = COOGOBASE + "/delCart.json";
    /**
     * 修改购物袋商品
     */
    public static String MODIFYSHOPBAG = COOGOBASE + "/modifyCart.json";
    /**
     * 查询排行商品(购物袋为空的时候)
     */
    public static String SHOPBAGTOPGOODS = COOGOBASE + "/goods/topGoods.json";
    /**
     * 添加收藏
     */
    public static String ADDFAVORITE = COOGOBASE + "/user/addMyFavorite.json";
    /**
     * 搜索商品(按分类，按品牌，按属性)
     */
    public static String SEARCHGOODS = COOGOBASE + "/searchGoods.json";
    /**
     * 获取航空公司列表
     */
    public static String AIRLINELIST = COOGOBASE + "/airlineList.json";
    /**
     * 可使用优惠券
     */
    public static String CANUSECOUPON = COOGOBASE + "/user/availableCoupon.json";
    /**
     * 提交订单
     */
    public static String SAVEORDER = COOGOBASE + "/order/saveOrder.json";
    /**
     * 查询航班
     */
    public static String SEARCHFLIGHT = COOGOBASE + "/flightInfo.json";
    /**
     * 广告
     */
    public static String ADVERTISE = COOGOBASE + "/availableAdvertise.json";
    /**
     * 全局通用的一些 H5 url
     */
    public static String GLOBAL = COOGOBASE + "/sys/globalVariable.json";
    /**
     * 我的订单列表
     */
    public static String ORDERLIST = COOGOBASE + "/order/orderList.json";
    /**
     * 删除订单
     */
    public static String DELORDER = COOGOBASE + "/order/delOrder.json";
    /**
     * 订单详情
     */
    public static String ORDERDETAIL = COOGOBASE + "/order/orderDetail.json";
    /**
     * 手机登录
     */
    public static String PHONELOGIN = COOGOBASE + "/user/phoneLogin.json";
    /**
     * 手机注册验证码
     */
    public static String PHONESECURITYCODE = COOGOBASE + "/phoneSecurityCode.json";
    /**
     * 手机注册
     */
    public static String PHONEREGISTER = COOGOBASE + "/user/phoneRegister.json";
    /**
     * 第三方登录
     */
    public static String THIRDLOGIN = COOGOBASE + "/user/thirdUserLogin.json";
    /**
     * 我的账号
     */
    public static String MEMBERCENTER = COOGOBASE + "/user/memberCenter.json";
    /**
     * 我的优惠券列表
     */
    public static String MYCOUPON = COOGOBASE + "/user/myCoupon.json";
    /**
     * 删除优惠券
     */
    public static String DELETECOUPON = COOGOBASE + "/user/delCoupon.json";
    /**
     * 添加优惠券
     */
    public static String ADDCOUPON = COOGOBASE + "/user/addCoupon.json";
    /**
     * 移除收藏
     */
    public static String REMOVEMYFAVORITE = COOGOBASE + "/user/removeMyFavorite.json";
    /**
     * 我的收藏
     */
    public static String MYFAVORITE = COOGOBASE + "/user/myFavorite.json";
    /**
     * 上传头像
     */
    public static String UPLOADPHOTO = COOGOBASE + "/user/changeIcon.json";
    /**
     * 提交反馈
     */
    public static String FEEDBACK = COOGOBASE + "/sys/feedback.json";
    /**
     * 消息中心
     */
    public static String MSGCENTER = COOGOBASE + "/msg/msgCenter.json";
    /**
     * 设置消息已读
     */
    public static String MSGREADED = COOGOBASE + "/msg/setMsgReaded.json";
    /**
     * 绑定手机号
     */
    public static String BINDPHONE = COOGOBASE + "/user/bindPhone.json";
    /**
     * 绑定微信
     */
    public static String BINDWX = COOGOBASE + "/user/bindWX.json";
    /**
     * 设置密码/重置密码
     */
    public static String RESETPASSWORD = COOGOBASE + "/user/resetPassword.json";
    /**
     * 合并账号
     */
    public static String MERGEMEMBER = COOGOBASE + "/user/mergeMember.json";
    /**
     * 修改用户昵称
     */
    public static String MODIFYNICKNAME = COOGOBASE + "/user/modifyNickName.json";
}
