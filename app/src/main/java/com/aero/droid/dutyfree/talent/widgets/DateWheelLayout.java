package com.aero.droid.dutyfree.talent.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.widgets.wheel.OnWheelChangedListener;
import com.aero.droid.dutyfree.talent.widgets.wheel.OnWheelClickedListener;
import com.aero.droid.dutyfree.talent.widgets.wheel.WheelView;
import com.aero.droid.dutyfree.talent.widgets.wheel.adapter.ArrayWheelAdapter;
import com.aero.droid.dutyfree.talent.widgets.wheel.adapter.NumericWheelAdapter;

import java.util.Calendar;

/**
 * Author : wangxp
 * Date : 2015/12/16
 * Desc :
 */
public class DateWheelLayout extends FrameLayout {
    private Context mContext;
    private WheelView mYear;
    private WheelView mMonth;
    private WheelView mDay;
    private Calendar mCalendar;
    private OnWheelChangedListener listener;
    private OnWheelClickedListener clickListener;
    public DateWheelLayout(Context context) {
        super(context);
        init(context);
    }

    public DateWheelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DateWheelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DateWheelLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        mContext = context;
        View view = View.inflate(mContext, R.layout.view_date_wheel,null);
        mMonth = (WheelView) view.findViewById(R.id.date_month);
        mYear = (WheelView) view.findViewById(R.id.date_year);
        mDay = (WheelView) view.findViewById(R.id.date_day);
        listener = new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(mMonth, mMonth, mDay);
            }
        };
        clickListener = new OnWheelClickedListener() {
            @Override
            public void onItemClicked(WheelView wheel, int itemIndex) {
                wheel.setCurrentItem(itemIndex, true);
            }
        };
        mYear.addClickingListener(clickListener);
        mMonth.addClickingListener(clickListener);
        mDay.addClickingListener(clickListener);
        initData();
        addView(view);
    }
    private void initData(){
        mCalendar = Calendar.getInstance();
        int curMonth = mCalendar.get(Calendar.MONTH);
        String months[] = new String[] {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};
        mMonth.setViewAdapter(new DateArrayAdapter(mContext, months, curMonth));
        mMonth.setCurrentItem(curMonth);
        mMonth.addChangingListener(listener);

        // year
        int curYear = mCalendar.get(Calendar.YEAR);
        LogUtil.t("curMonth = " + curMonth + "   curYear = " + curYear);
        mYear.setViewAdapter(new DateNumericAdapter(mContext, curYear, curMonth > 10 ? curYear + 1 : curYear, 0));
        mYear.setCurrentItem(curYear);
        mYear.addChangingListener(listener);

        //day
        updateDays(mYear, mMonth, mDay);
        mDay.setCurrentItem(mCalendar.get(Calendar.DAY_OF_MONTH) - 1);
        mDay.addChangingListener(listener);
    }

    public String getDate(){
        return String.format("%d-%02d-%02d", Calendar.getInstance().get(Calendar.YEAR)+mYear.getCurrentItem(), mMonth.getCurrentItem() + 1, mDay.getCurrentItem()+1);
    }

    /**
     * Updates day wheel. Sets max days according to selected month and year
     */
    void updateDays(WheelView year, WheelView month, WheelView day) {
        mCalendar.set(Calendar.YEAR, mCalendar.get(Calendar.YEAR) + year.getCurrentItem());
        mCalendar.set(Calendar.MONTH, month.getCurrentItem());

        int maxDays = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        LogUtil.t("   maxDays = " + maxDays);
        day.setViewAdapter(new DateNumericAdapter(mContext, 1, maxDays, mCalendar.get(Calendar.DAY_OF_MONTH) - 1));
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
    }

    /**
     * Adapter for numeric wheels. Highlights the current value.
     */
    private class DateNumericAdapter extends NumericWheelAdapter {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;

        /**
         * Constructor
         */
        public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
            super(context, minValue, maxValue);
            this.currentValue = current;
            setTextSize(20);
        }

        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
                view.setTextColor(getResources().getColor(R.color.orange));
                view.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
            }
            view.setTypeface(Typeface.SANS_SERIF);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }

    /**
     * Adapter for string based wheel. Highlights the current value.
     */
    private class DateArrayAdapter extends ArrayWheelAdapter<String> {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;

        /**
         * Constructor
         */
        public DateArrayAdapter(Context context, String[] items, int current) {
            super(context, items);
            this.currentValue = current;
            setTextSize(20);
        }

        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
                view.setTextColor(getResources().getColor(R.color.orange));
                view.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
            }
            view.setTypeface(Typeface.SANS_SERIF);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }
}
