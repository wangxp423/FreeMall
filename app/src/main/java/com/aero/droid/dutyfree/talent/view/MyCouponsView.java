package com.aero.droid.dutyfree.talent.view;

import android.support.v4.app.Fragment;

import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/5
 * Desc : 我的优惠券页面
 */
public interface MyCouponsView extends BaseView{
    /**
     * 显示 优惠券列表
     * @param infoList
     */
    void showCouponsList(List<CouponsInfo> infoList);

    /**
     * 删除单个优惠券
     * @param msg
     */
    void deleteCouponsSuccess(String msg);
    /**
     * 添加/兑换 单个优惠券
     * @param msg
     */
    void addCouponsSuccess(String msg);

    /**
     * 请求失败
     * @param msg
     */
    void requestError(String msg);


}
