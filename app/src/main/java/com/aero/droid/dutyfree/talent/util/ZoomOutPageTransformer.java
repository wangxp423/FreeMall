package com.aero.droid.dutyfree.talent.util;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Viewpager 滑动缩放动画
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.9f;//最小缩放比例：变化，也是从 0.9~1.0进行变化
    private static final float SCALE_REGION = 0.1f;
    private final Interpolator sInterpolator = new AccelerateDecelerateInterpolator();

    @SuppressLint("NewApi")
    public void transformPage(View view, float position) {

        //view的宽度
        int pageWidth = view.getWidth();
        //margin的值：点view宽度的一个比例
        float margeLeft = pageWidth * ((1 - MIN_SCALE) / 5);
        float scaleFactor = 1;//0.1变化值的加速比例值

        if (position < -1) {
            // This page is way off-screen to the left.
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        } else if (position <= 1) {

            //两个数值:加速后的值
            float accPosition = sInterpolator.getInterpolation(Math.abs(position));
            scaleFactor = SCALE_REGION * accPosition;

            if (position < 0) {
                float fResult = 1 - scaleFactor;
                float fMerginLeft = margeLeft * scaleFactor;

                view.setScaleX(fResult);
                view.setScaleY(fResult);
                view.setTranslationX(fMerginLeft);
            } else {
                float fResult = MIN_SCALE + (SCALE_REGION - scaleFactor);
                float fMerginLeft = margeLeft * (SCALE_REGION - scaleFactor);

                view.setScaleX(fResult);
                view.setScaleY(fResult);
                view.setTranslationX(-fMerginLeft);
            }
        } else {
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        }
    }

}  
