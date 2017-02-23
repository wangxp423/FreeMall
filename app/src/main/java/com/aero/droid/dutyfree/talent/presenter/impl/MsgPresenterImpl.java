package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.activity.MessageListActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.DiscountInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.bean.MessageInfo;
import com.aero.droid.dutyfree.talent.interactor.DiscountInteractor;
import com.aero.droid.dutyfree.talent.interactor.MsgInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.DiscountInteractorImpl;
import com.aero.droid.dutyfree.talent.interactor.impl.MsgInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.DiscountPresenter;
import com.aero.droid.dutyfree.talent.presenter.MsgPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.DiscountView;
import com.aero.droid.dutyfree.talent.view.MsgCenterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/1/11
 * Desc : 消息中心 逻辑控制 实现类
 */
public class MsgPresenterImpl implements MsgPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private MsgCenterView msgCenterView;
    private MsgInteractor interactor;
    private List<MessageInfo> showMsgList = new ArrayList<>();//要显示的消息列表
    private List<MessageInfo> appMsgList = new ArrayList<>();//官方公告
    private List<MessageInfo> orderMsgList = new ArrayList<>();//订单助手
    private List<MessageInfo> shopMsgList = new ArrayList<>();//购物提醒
    private List<MessageInfo> walletMsgList = new ArrayList<>();//钱包动态

    public MsgPresenterImpl(Activity activity, MsgCenterView msgCenterView) {
        this.activity = activity;
        this.msgCenterView = msgCenterView;
        interactor = new MsgInteractorImpl(activity, this);
    }

    @Override
    public void getMsgList(String log_tag) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getMsgList(log_tag, Constants.EVENT_GET_DATA, params);
    }

    @Override
    public void refreshMsgList(String log_tag) {
        HashMap<String, String> params = new HashMap<>();
        String userId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        interactor.getMsgList(log_tag, Constants.EVENT_REFRESH_DATA, params);
    }

    @Override
    public void clickItem(String type) {
        Intent intent = new Intent();
        intent.setClass(activity, MessageListActivity.class);
        Bundle bundle = new Bundle();
        if ("1".equals(type)) {
            bundle.putParcelableArrayList("msgList", (ArrayList<? extends Parcelable>) appMsgList);
        } else if ("2".equals(type)) {
            bundle.putParcelableArrayList("msgList", (ArrayList<? extends Parcelable>) orderMsgList);
        } else if ("3".equals(type)) {
            bundle.putParcelableArrayList("msgList", (ArrayList<? extends Parcelable>) shopMsgList);
        } else if ("4".equals(type)) {
            bundle.putParcelableArrayList("msgList", (ArrayList<? extends Parcelable>) walletMsgList);
        }
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * 将消息进行分类排序
     *
     * @param infoList
     */
    private void categoryMsgList(List<MessageInfo> infoList) {
        showMsgList.clear();
        appMsgList.clear();
        orderMsgList.clear();
        shopMsgList.clear();
        walletMsgList.clear();
        for (MessageInfo info : infoList) {
            if ("1".equals(info.getMsgType())) {
                appMsgList.add(info);
            } else if ("2".equals(info.getMsgType())) {
                orderMsgList.add(info);
            } else if ("3".equals(info.getMsgType())) {
                shopMsgList.add(info);
            } else if ("4".equals(info.getMsgType())) {
                walletMsgList.add(info);
            }
        }
        TypeComparator comparator = new TypeComparator();
        Collections.sort(appMsgList, comparator);
        Collections.sort(orderMsgList, comparator);
        Collections.sort(shopMsgList, comparator);
        Collections.sort(walletMsgList, comparator);
        if (appMsgList.size() > 0)
            showMsgList.add(appMsgList.get(0));
        if (orderMsgList.size() > 0)
            showMsgList.add(orderMsgList.get(0));
        if (shopMsgList.size() > 0)
            showMsgList.add(shopMsgList.get(0));
        if (walletMsgList.size() > 0)
            showMsgList.add(walletMsgList.get(0));
    }

    public class TypeComparator implements Comparator<MessageInfo> {

        public int compare(MessageInfo o1, MessageInfo o2) {
            return o1.getMsgTime().compareTo(o2.getMsgTime());
        }
    }

    public int getAppMsgCount() {
        return appMsgList.size();
    }


    public int getOrderMsgCount() {
        return orderMsgList.size();
    }


    public int getShopMsgCount() {
        return shopMsgList.size();
    }


    public int getWalletMsgCount() {
        return walletMsgList.size();
    }
    public int getshowMsgCount() {
        return showMsgList.size();
    }


    @Override
    public void onSuccess(int event_tag, Object object) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            categoryMsgList((List<MessageInfo>) object);
            msgCenterView.showMsgList(showMsgList);
        } else if (event_tag == Constants.EVENT_REFRESH_DATA) {
            categoryMsgList((List<MessageInfo>) object);
            msgCenterView.showRefreshMsgList(showMsgList);
        }
    }


    @Override
    public void onEmpty(int event_tag, Object data) {

    }

    @Override
    public void onError(int event_tag, String msg) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            msgCenterView.requestMsgError(msg);
        } else if (event_tag == Constants.EVENT_REFRESH_DATA) {
            msgCenterView.refreshMsgError(msg);
        }
    }

    @Override
    public void onException(String msg) {

    }

}
