package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.bean.DiscountInfo;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/10
 * Desc : 折扣专区页面
 */
public interface DiscountView extends BaseView{

    /**
     * 显示折扣商品
     * @param infoList
     */
    void showDiscountList(List<DiscountInfo> infoList);

    /**
     * 刷新折扣商品
     * @param infoList
     */
    void refreshDiscountList(List<DiscountInfo> infoList);

    /**
     * 请求失败
     * @param msg
     */
    void requestError(String msg);
}
