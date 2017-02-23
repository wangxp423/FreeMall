package com.aero.droid.dutyfree.talent.view;

/**
 * Author : wangxp
 * Date : 2016/1/12
 * Desc : 反馈View
 */
public interface FeedbackView {
    /**
     * 反馈成功
     * @param msg
     */
    void feedbackSuccess(String msg);

    /**
     * 反馈失败
     * @param msg
     */
    void feedbackFail(String msg);
}
