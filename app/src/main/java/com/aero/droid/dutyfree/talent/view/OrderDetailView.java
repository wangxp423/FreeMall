package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.AirportCompany;
import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.OrderDetail;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/10
 * Desc : 订单详情页面
 */
public interface OrderDetailView extends BaseView {

    /**
     * 显示订单商品列表
     *
     * @param detail
     */
    void showGoodsListData(OrderDetail detail);


    /**
     * 请求失败
     *
     * @param msg
     */
    void requestError(String msg);
}
