package com.aero.droid.dutyfree.talent.util;

/**
 * Author:  wangxp
 * Date:    2015/11/26.
 * Description: EventBus控制中心
 */
public class EventCenter<T> {

    /**
     * reserved data
     */
    private T data;

    /**
     * this code distinguish between different events
     */
    private int eventCode = -1;

    public EventCenter(int eventCode) {
        this(eventCode, null);
    }

    public EventCenter(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    /**
     * get event code
     *
     * @return
     */
    public int getEventCode() {
        return this.eventCode;
    }

    /**
     * get event reserved data
     *
     * @return
     */
    public T getData() {
        return this.data;
    }
}
