package com.aero.droid.dutyfree.talent.interactor;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2016/1/12
 * Desc : 反馈 交互器
 */
public interface FeedbackInteractor {
    /**
     * 提交反馈
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void commitFeedback(String log_tag, int event_tag, HashMap<String, String> params,HashMap<String, String> imgParams);
}
