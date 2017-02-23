
package com.aero.droid.dutyfree.talent.view.base;

/**
 * Author:  wangxp
 * Date:    2015/11/29.
 * Description: the base view
 */
public interface BaseView {

    /**
     * show loading message
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * hide loading
     */
    void hideLoading();

    /**
     * show empty message
     */
    void showEmpty(String msg,int imgId);

    /**
     * show exception message
     */
    void showException(String msg);

    /**
     * show net error
     */
    void showNetError();

}
