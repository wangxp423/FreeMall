package com.aero.droid.dutyfree.talent.listener;

/**
 * Author : wangxp
 * Date : 2015/12/3
 * Desc : 倒计时按钮监听
 */
public interface TimeCountListener {
    /**
     * 倒计时结束
     */
    void onFinish();

    /**
     * 倒计时 ing
     * @param millisUntilFinished 当前时间
     */
    void onTick(long millisUntilFinished);
}
