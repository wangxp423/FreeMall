package com.aero.droid.dutyfree.talent.presenter;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2016/1/12
 * Desc : 反馈 逻辑控制器
 */
public interface FeedbackPresenter {
    /**
     * 提交 反馈
     * @param log_tag
     * @param startQty 评级
     * @param content 内容
     * @param imgs 图片
     */
    void clickCommit(String log_tag,String startQty,String content,List<String> imgs);

    /**
     * 检查提交数据
     * @param startQty
     * @param content
     * @return
     */
    boolean checkData(String startQty,String content);
}
