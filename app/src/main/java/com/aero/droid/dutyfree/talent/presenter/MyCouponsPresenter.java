package com.aero.droid.dutyfree.talent.presenter;

import android.support.v4.app.Fragment;

import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.fragment.CouponsFragment;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的优惠券 逻辑控制器
 */
public interface MyCouponsPresenter {
    /**
     * 获取优惠券列表
     *
     * @param log_tag
     */
    void getCouponsList(String log_tag);

    /**
     * 删除 优惠券
     *
     * @param log_tag
     * @param couponsId
     */
    void clickDeleteCoupons(String log_tag, String couponsId);
    /**
     * 添加/兑换 优惠券
     *
     * @param log_tag
     * @param couponsId
     */
    void clickAddCoupons(String log_tag, String couponsId);


    /**
     * 给Fragment填充数据
     * @param infoList
     */
    void setFragmentData(List<CouponsInfo> infoList,List<CouponsFragment> fragments);
}
