package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.ComentsInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/12
 * Desc : 写商品评论列表页面
 */
public interface WriteComentsView{

    /**
     * 写评论提交成功
     * @param msg
     */
    void writeComentsData(String msg);


    /**
     * 评论提交失败
     * @param msg
     */
    void requestError(String msg);
}
