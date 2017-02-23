package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.OrderInfo;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的收藏页
 */
public interface MyCollectView extends BaseView{
    /**
     * 显示收藏列表
     * @param infoList
     */
    void showCollectList(List<GoodsInfo> infoList);

    /**
     * 删除单个收藏成功
     */
    void deleteCollectSuccess(String msg);

    /**
     * 请求失败
     */
    void requestError(String msg);
}
