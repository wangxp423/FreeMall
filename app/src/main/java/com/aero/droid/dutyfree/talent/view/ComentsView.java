package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.ComentsInfo;
import com.aero.droid.dutyfree.talent.bean.DiscountInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/10
 * Desc : 商品评论列表页面
 */
public interface ComentsView extends BaseView{

    /**
     * 显示商品评论
     * @param goodComents
     */
    void showComentsListData(GoodComents goodComents);

    /**
     * 上拉 加载更多数据
     * @param comentsInfoList
     */
    void loadComtensListData(List<ComentsInfo> comentsInfoList);

    /**
     * 请求失败
     * @param msg
     */
    void requestError(String msg);
}
