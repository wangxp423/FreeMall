package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import com.aero.droid.dutyfree.talent.listener.TimeCountListener;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/01/06
 * Desc : 首页 我的 交互器
 */
public interface MeInteractor {
    /**
     * 获取 用户信息
     * @param log_tag
     * @param event_tag
     * @param params
     */
    void getUserInfo(String log_tag, int event_tag, HashMap<String, String> params);
}
