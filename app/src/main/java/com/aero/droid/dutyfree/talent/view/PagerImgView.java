package com.aero.droid.dutyfree.talent.view;


import java.util.List;

/**
 * Created by wangxp on 2015/11/26.
 * App介绍轮播图页
 */
public interface PagerImgView {
    /**
     * 返回ViewPager需要的数据
     *
     * @param imgs
     */
    void showPager(List<?> imgs);

    /**
     * 请求数据失败
     *
     * @param code
     * @param msg
     */
    void showPagerError(String code, String msg);

    /**
     * 点击最后一个图片
     */
    void clickLastPager();
}
