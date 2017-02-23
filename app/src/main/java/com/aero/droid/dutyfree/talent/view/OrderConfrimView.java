package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.AirportCompany;
import com.aero.droid.dutyfree.talent.bean.ComentsInfo;
import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.bean.FlightInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/10
 * Desc : 订单确认页面
 */
public interface OrderConfrimView extends BaseView {

    /**
     * 显示航空公司
     *
     * @param companyList
     */
    void showAirportListData(List<AirportCompany> companyList);

    /**
     * 显示优惠券列表
     *
     * @param couponsInfoList
     */
    void showCouponsListData(List<CouponsInfo> couponsInfoList);

    /**
     * 提交订单成功
     *
     * @param msg
     */
    void confrimOrderSuccess(String msg);

    /**
     * 检查国航航班信息成功
     *
     * @param info
     */
    void checkCAAirportSuccess(FlightInfo info);

    /**
     * 检查国航航班信息成功
     *
     * @param info
     */
    void checkHUAirportSuccess(FlightInfo info);
    /**
     * 检查 国航/海航 航班信息失败
     *
     * @param msg
     */
    void checkHUAirportFail(String msg);

    /**
     * 请求失败
     *
     * @param msg
     */
    void requestError(String msg);
}
