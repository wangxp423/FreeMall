
package com.aero.droid.dutyfree.talent.listener;

/**
 * Author:  wangxp
 * Date:    2015/12/1.
 * Description:  Presenter 里面只有一个请求的时候
 */
public interface BaseSingleLoadedListener<T> {
    /**
     * when data call back success
     *
     * @param data
     */
    void onSuccess(T data);

    /**
     * when data call back success but data's size is zero
     *
     * @param data
     */
    void onEmpty(T data);

    /**
     * when data call back error
     *
     * @param msg
     */
    void onError(String msg);

    /**
     * when data call back occurred exception
     *
     * @param msg
     */
    void onException(String msg);
}
