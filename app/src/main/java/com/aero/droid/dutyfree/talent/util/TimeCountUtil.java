package com.aero.droid.dutyfree.talent.util;

import android.os.CountDownTimer;

import com.aero.droid.dutyfree.talent.listener.TimeCountListener;

/**
 * Author : wangxp
 * Date : 2015/12/3
 * Desc : 按钮倒计时 类
 */
public class TimeCountUtil extends CountDownTimer {
    private TimeCountListener listener;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimeCountUtil(long millisInFuture, long countDownInterval, TimeCountListener mListener) {
        super(millisInFuture, countDownInterval);
        this.listener = mListener;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        listener.onTick(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        listener.onFinish();
    }
}
