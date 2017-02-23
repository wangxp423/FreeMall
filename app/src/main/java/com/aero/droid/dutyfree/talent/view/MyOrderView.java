package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.OrderInfo;
import com.aero.droid.dutyfree.talent.bean.OrderListInfo;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的订单页
 */
public interface MyOrderView extends BaseView{
    /**
     * 显示订单列表
     * @param infoList
     */
    void showOrderList(List<OrderListInfo> infoList);

    /**
     * 删除单个订单成功
     */
    void deleteOrderSuccess(String msg);
    /**
     * 取消订单成功
     */
    void cancelOrderSuccess(String msg);

    /**
     * 请求失败
     */
    void requestError(String msg);
}
