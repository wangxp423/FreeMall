package com.aero.droid.dutyfree.talent.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.aero.droid.dutyfree.talent.util.UiUtil;


public class InnerViewPager extends ViewPager {
    private Context mContext;
    private float mWidth;

    public InnerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InnerViewPager(Context context) {
        super(context);
        init(context);
    }
    private void init(Context context){
        this.mContext = context;
        mWidth = UiUtil.getWindowWidth(context);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private float downX, downY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = event.getX();
            downY = event.getY();
            if (0 <= downX && downX < UiUtil.dip2px(mContext, 70)) {
                return true;
            } else if (mWidth - UiUtil.dip2px(mContext, 70) < downX && downX <= mWidth) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

}
