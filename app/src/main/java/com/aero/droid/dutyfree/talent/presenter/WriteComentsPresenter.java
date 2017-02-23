package com.aero.droid.dutyfree.talent.presenter;

import com.aero.droid.dutyfree.talent.bean.ComentsInfo;

/**
 * Author : wangxp
 * Date : 2015/12/12
 * Desc : 商品评论 逻辑控制器
 */
public interface WriteComentsPresenter {
    /**
     * 提交评论
     * @param log_tag
     * @param cmtInfo
     */
    void commitComtens(String log_tag,String cmtInfo);

    /**
     * 点击说明按钮
     */
    void clickIntroduceBtn();


}
