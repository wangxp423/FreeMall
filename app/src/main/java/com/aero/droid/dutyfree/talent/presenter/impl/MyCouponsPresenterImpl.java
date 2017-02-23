package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.fragment.CouponsFragment;
import com.aero.droid.dutyfree.talent.interactor.MyCouponsInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.MyCouponsInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.MyCouponsPresenter;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.view.MyCouponsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的优惠券 逻辑控制 实现类
 */
public class MyCouponsPresenterImpl implements MyCouponsPresenter, BaseMultiLoadedListener<Object> {
    private Activity activity;
    private MyCouponsView couponsView;
    private MyCouponsInteractor interactor;

    public MyCouponsPresenterImpl(Activity activity, MyCouponsView couponsView) {
        this.activity = activity;
        this.couponsView = couponsView;
        interactor = new MyCouponsInteractorImpl(this);
    }

    @Override
    public void getCouponsList(String log_tag) {
        HashMap<String, String> params = new HashMap<>();
        String memberId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(memberId) ? "0" : memberId);
        interactor.getCouponsList(log_tag, Constants.EVENT_GET_DATA, activity, params);
    }

    @Override
    public void clickDeleteCoupons(String log_tag, String couponsId) {
        HashMap<String, String> params = new HashMap<>();
        String memberId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(memberId) ? "0" : memberId);
        params.put("couponId", couponsId);
        interactor.deleteCoupons(log_tag, Constants.EVENT_DELETE_DATA, activity, params);
    }

    @Override
    public void clickAddCoupons(String log_tag, String couponsId) {
        HashMap<String, String> params = new HashMap<>();
        String memberId = UserUtil.getUserId(activity);
        params.put("memberId", TextUtils.isEmpty(memberId) ? "0" : memberId);
        params.put("couponId", couponsId);
        interactor.addCoupons(log_tag, Constants.EVENT_ADD_DATA, activity, params);
    }

    @Override
    public void setFragmentData(List<CouponsInfo> infoList, List<CouponsFragment> fragments) {
        List<CouponsInfo> unUseCoupon = new ArrayList<>();
        List<CouponsInfo> useCoupon = new ArrayList<>();
        List<CouponsInfo> outDateCoupon = new ArrayList<>();
        for (CouponsInfo couponsInfo : infoList) {
            if ("0".equals(couponsInfo.getStatus())) {
                useCoupon.add(couponsInfo);
            } else if ("1".equals(couponsInfo.getStatus())) {
                unUseCoupon.add(couponsInfo);
            } else if ("2".equals(couponsInfo.getStatus())) {
                outDateCoupon.add(couponsInfo);
            }
        }
        //未使用优惠券
        CouponsFragment unUsedFragment = new CouponsFragment();
        Bundle unUserBundle = new Bundle();
        unUserBundle.putParcelableArrayList("couponsList", (ArrayList<? extends Parcelable>) unUseCoupon);
        unUsedFragment.setArguments(unUserBundle);
        //已使用优惠券
        CouponsFragment usedFragment = new CouponsFragment();
        Bundle usedBundle = new Bundle();
        usedBundle.putParcelableArrayList("couponsList", (ArrayList<? extends Parcelable>) useCoupon);
        usedFragment.setArguments(usedBundle);
        //过期优惠券
        CouponsFragment outDateFragment = new CouponsFragment();
        Bundle outDateBundle = new Bundle();
        outDateBundle.putParcelableArrayList("couponsList", (ArrayList<? extends Parcelable>) outDateCoupon);
        outDateFragment.setArguments(outDateBundle);
        fragments.add(unUsedFragment);
        fragments.add(usedFragment);
        fragments.add(outDateFragment);
    }

    @Override
    public void onSuccess(int event_tag, Object data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            List<CouponsInfo> infoList = (List<CouponsInfo>) data;
            couponsView.showCouponsList(infoList);
        } else if (event_tag == Constants.EVENT_DELETE_DATA) {
            couponsView.deleteCouponsSuccess((String) data);
        } else if (event_tag == Constants.EVENT_ADD_DATA) {
            couponsView.addCouponsSuccess((String) data);
        }
    }

    @Override
    public void onEmpty(int event_tag, Object data) {
    }

    @Override
    public void onError(int event_tag, String msg) {
        couponsView.requestError(msg);
    }

    @Override
    public void onException(String msg) {

    }
}
