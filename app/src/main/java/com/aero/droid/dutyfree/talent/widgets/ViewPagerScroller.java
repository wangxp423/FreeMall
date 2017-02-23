package com.aero.droid.dutyfree.talent.widgets;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 自定义Scroller，用于调节ViewPager滑动速度
 *
 */
public class ViewPagerScroller extends Scroller {
    private int mDuration = 500;

    public ViewPagerScroller(Context context) {
        super(context);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}