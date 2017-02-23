package com.aero.droid.dutyfree.talent.widgets;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.listener.DownTimerListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author : wangxp
 * Date : 2015/12/7
 * Desc :  时间倒计时 View
 */
public class DownTimerView extends LinearLayout {
    // 天 十位 个位  容器 ，用于隐藏方便
    private LinearLayout day_layout;
    // 天，十位
    private TextView tv_day_decade;
    // 天，个位
    private TextView tv_day_unit;
    // 天 和小时之间的冒号  因为可能没有天
    private TextView tv_interval;
    // 小时，十位
    private TextView tv_hour_decade;
    // 小时，个位
    private TextView tv_hour_unit;
    // 分钟，十位
    private TextView tv_min_decade;
    // 分钟，个位
    private TextView tv_min_unit;
    // 秒，十位
    private TextView tv_sec_decade;
    // 秒，个位
    private TextView tv_sec_unit;

    private Context context;
    private int day_decade;
    private int day_unit;
    private int hour_decade;
    private int hour_unit;
    private int min_decade;
    private int min_unit;
    private int sec_decade;
    private int sec_unit;
    // 计时器
    private Timer timer;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            countDown();
        }

        ;
    };

    public DownTimerView(Context context) {
        super(context);
        init(context);
    }

    public DownTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DownTimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DownTimerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_downtimer, this);
        day_layout = (LinearLayout) view.findViewById(R.id.tv_day_layout);
        tv_day_decade = (TextView) view.findViewById(R.id.tv_day_decade);
        tv_day_unit = (TextView) view.findViewById(R.id.tv_day_unit);
        tv_interval = (TextView) view.findViewById(R.id.tv_day_interval);

        tv_hour_decade = (TextView) view.findViewById(R.id.tv_hour_decade);
        tv_hour_unit = (TextView) view.findViewById(R.id.tv_hour_unit);
        tv_min_decade = (TextView) view.findViewById(R.id.tv_min_decade);
        tv_min_unit = (TextView) view.findViewById(R.id.tv_min_unit);
        tv_sec_decade = (TextView) view.findViewById(R.id.tv_sec_decade);
        tv_sec_unit = (TextView) view.findViewById(R.id.tv_sec_unit);
    }

    /**
     * @param
     * @return void
     * @throws
     * @Description: 开始计时
     */
    public void start() {

        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, 0, 1000);
        }
    }

    private DownTimerListener listener;
    public void setTimerListener(DownTimerListener listener){
        this.listener = listener;
    }
    /**
     * @param
     * @return void
     * @throws
     * @Description: 停止计时
     */
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            if (null != listener)
                listener.stop();
        }
    }

    /**
     * @param day  天 小于 30
     * @param hour 小时 大于0 小于 24
     * @param min  大于 0 小于 60
     * @param sec  大于 0 小于 60
     */
    public void setTime(int day, int hour, int min, int sec) {

        if (hour >= 25 || min >= 60 || sec >= 60 || day < 0 || hour < 0 || min < 0
                || sec < 0) {
            throw new RuntimeException("Time format is error,please check out your code");
        }
        day_decade = day / 10;
        day_unit = day - day_decade * 10;

        hour_decade = hour / 10;
        hour_unit = hour - hour_decade * 10;

        min_decade = min / 10;
        min_unit = min - min_decade * 10;

        sec_decade = sec / 10;
        sec_unit = sec - sec_decade * 10;
        if (day == 0) {
            day_layout.setVisibility(GONE);
            tv_interval.setVisibility(GONE);
        } else if (day < 10) {
            tv_day_decade.setVisibility(GONE);
            tv_day_unit.setText(day_unit + "d");
        } else {
            tv_day_decade.setText(day_decade + "");
            tv_day_unit.setText(day_unit + "d");
        }

        tv_hour_decade.setText(hour_decade + "");
        tv_hour_unit.setText(hour_unit + "");
        tv_min_decade.setText(min_decade + "");
        tv_min_unit.setText(min_unit + "");
        tv_sec_decade.setText(sec_decade + "");
        tv_sec_unit.setText(sec_unit + "");

    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Description: 倒计时
     */
    private void countDown() {

        if (isCarry4SecUnit(tv_sec_unit)) {
            if (isCarry4Decade(tv_sec_decade)) {

                if (isCarry4Unit(tv_min_unit)) {
                    if (isCarry4Decade(tv_min_decade)) {

                        if (isCarry4HourUnit(tv_hour_decade, tv_hour_unit)) {
                            if (isCarry4HourDecade(tv_hour_decade)) {

                                if (isCarry4DayUnit(tv_day_unit)) {
                                    if (isCarry4DayDecade(tv_day_decade)){
//                                        stop();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Description: 分，秒变化十位，并判断是否需要进位
     */
    private boolean isCarry4Decade(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 5;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }

    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Description: 分，秒变化个位，并判断是否需要进位
     */
    private boolean isCarry4Unit(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 9;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }

    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Description: 小时变化十位，并判断是否需要进位
     */
    private boolean isCarry4HourDecade(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 2;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }

    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Description: 小时变化个位，并判断是否需要进位
     */
    private boolean isCarry4HourUnit(TextView dayDecadeTv, TextView dayUnittv) {

        int unitTime = Integer.valueOf(dayUnittv.getText().toString());  //小时 个位
        int decadeTime = Integer.valueOf(dayUnittv.getText().toString()); //小时 十位
        unitTime = unitTime - 1;
        if (unitTime < 0) {
            if (decadeTime == 0) {
                unitTime = 3;
            } else {
                unitTime = 9;
            }
            dayUnittv.setText(unitTime + "");
            return true;
        } else {
            dayUnittv.setText(unitTime + "");
            return false;
        }
    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Description: 天 变化十位，并判断是否需要进位 包括 加 d  显示 和 隐藏
     */
    private boolean isCarry4DayDecade(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 5;
            tv.setText(time + "");
            return true;
        } else {
            if (time == 0)
                tv.setVisibility(GONE);
            tv.setText(time + "");
            return false;
        }

    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Description: 天 变化个位，并判断是否需要进位 需要去掉后面的 d
     */
    private boolean isCarry4DayUnit(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString().substring(0, 1));
        time = time - 1;
        if (time < 0) {
            time = 9;
            tv.setText(time + "d");
            return true;
        } else {
            tv.setText(time + "d");
            return false;
        }
    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Description: 秒变化个位，并判断是否需要进位
     */
    private boolean isCarry4SecUnit(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 9;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            if (time == 0 && isZero())
                stop();
            return false;
        }

    }

    /**
     * 判断 秒 以上位数 是否为零 然后是否停止 计数
     *
     * @return
     */
    private boolean isZero() {
        if (0 == Integer.valueOf(tv_day_decade.getText().toString())) {
            if (0 == Integer.valueOf(tv_day_unit.getText().toString().substring(0, 1))) {
                if (0 == Integer.valueOf(tv_hour_decade.getText().toString())) {
                    if (0 == Integer.valueOf(tv_hour_unit.getText().toString())) {
                        if (0 == Integer.valueOf(tv_min_decade.getText().toString())) {
                            if (0 == Integer.valueOf(tv_min_unit.getText().toString())) {
                                if (0 == Integer.valueOf(tv_sec_decade.getText().toString())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}
