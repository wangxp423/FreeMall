package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import com.aero.droid.dutyfree.talent.listener.TimeCountListener;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/1/8
 * Desc : 用户资料 交互器
 */
public interface UserInfoInteractor {
    /**
     * 上传头像
     * @param log_tag
     * @param event_tag
     * @param params
     * @param path 图片路径
     */
    void uploadPhoto(String log_tag, int event_tag, HashMap<String, String> params,String path);
}
