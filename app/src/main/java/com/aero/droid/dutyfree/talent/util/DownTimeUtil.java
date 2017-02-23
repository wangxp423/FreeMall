package com.aero.droid.dutyfree.talent.util;

/**
 * Author : wangxp
 * Date : 2015/12/10
 * Desc : 倒计时中用来获取 天，时，分，秒
 */
public class DownTimeUtil {
    private int day = 0;
    private int hour = 0;
    private int min = 0;
    private int sec = 0;
    private long l;

    public DownTimeUtil(long time) {
        long curTime = System.currentTimeMillis();
        if (time * 1000 > curTime) {
            l = time * 1000 - curTime;
        } else {
            l = curTime - time * 1000;
        }

        day = (int) (l / (24 * 60 * 60 * 1000));
        hour = (int) (l / (60 * 60 * 1000) - day * 24);
        min = (int) ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (int) (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }
}
