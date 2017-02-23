package com.aero.droid.dutyfree.talent.presenter;

import android.app.Activity;

import java.util.HashMap;

/**
 * Created by wangxp on 2015/11/26.
 */
public interface PagerImgPresenter {
    /**
     * 获取App介绍轮番图
     * @param params
     */
    void loadPagerImgData(HashMap<String,String> params);

    /**
     * 点击最后一张调转到主页
     */
    void go2MainActivity();
}
