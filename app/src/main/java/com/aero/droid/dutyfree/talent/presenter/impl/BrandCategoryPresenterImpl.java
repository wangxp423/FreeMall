package com.aero.droid.dutyfree.talent.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.aero.droid.dutyfree.talent.activity.BrandGoodsActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.GoodsBrand;
import com.aero.droid.dutyfree.talent.bean.GoodsCategory;
import com.aero.droid.dutyfree.talent.interactor.BrandCategoryInteractor;
import com.aero.droid.dutyfree.talent.interactor.impl.BrandCategoryInteractorImpl;
import com.aero.droid.dutyfree.talent.listener.BaseMultiLoadedListener;
import com.aero.droid.dutyfree.talent.presenter.BrandCategoryPresenter;
import com.aero.droid.dutyfree.talent.view.BrandCategoryView;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc :
 */
public class BrandCategoryPresenterImpl extends BasePresenterImpl implements BrandCategoryPresenter, BaseMultiLoadedListener<List<GoodsBrand>> {
    private Activity activity;
    private BrandCategoryView brandCategoryView;
    private BrandCategoryInteractor interactor;
    private List<GoodsBrand> brandList;

    public BrandCategoryPresenterImpl(Activity activity, BrandCategoryView brandCategoryView) {
        super(activity, brandCategoryView);
        this.activity = activity;
        this.brandCategoryView = brandCategoryView;
        interactor = new BrandCategoryInteractorImpl(this);
    }

    @Override
    public void clickA2G(List<GoodsBrand> brandList, List<GoodsBrand> pointList) {
        brandCategoryView.showagBrandCategory(getPointData(brandList, pointList, 0));
    }

    @Override
    public void clickH2N(List<GoodsBrand> brandList, List<GoodsBrand> pointList) {
        if (this.brandList != null && this.brandList.size() > 0)
            this.brandList.clear();
        brandCategoryView.showhnBrandCategory(getPointData(brandList, pointList, 1));
    }

    @Override
    public void clickO2T(List<GoodsBrand> brandList, List<GoodsBrand> pointList) {
        if (this.brandList != null && this.brandList.size() > 0)
            this.brandList.clear();
        brandCategoryView.showotBrandCategory(getPointData(brandList, pointList, 2));
    }

    @Override
    public void clickU2Z(List<GoodsBrand> brandList, List<GoodsBrand> pointList) {
        if (this.brandList != null && this.brandList.size() > 0)
            this.brandList.clear();
        brandCategoryView.showuzBrandCategory(getPointData(brandList, pointList, 3));
    }

    @Override
    public void clickGoodsBrand(GoodsBrand brand) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("paramType","2");
        bundle.putString("categoryId", brand.getMarkId());
        bundle.putString("categoryLogo", brand.getMarkImg());
        bundle.putString("categoryName", brand.getMarkName());
        intent.setClass(activity, BrandGoodsActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public void getData(String log_tag, final Activity activity, int pageSize, int curPage) {
        brandCategoryView.showLoading("");
        HashMap<String, String> params = new HashMap<>();
        interactor.getBrandCategoryData(log_tag, Constants.EVENT_GET_DATA, activity, params);
    }

    @Override
    public void getRefreshData(String log_tag, final Activity activity, int pageSize, int curPage) {
        brandCategoryView.showLoading("");
        HashMap<String, String> params = new HashMap<>();
        interactor.getBrandCategoryData(log_tag, Constants.EVENT_REFRESH_DATA, activity, params);
    }

    @Override
    public void getLoadData(String log_tag, final Activity activity, int pageSize, int curPage) {
        brandCategoryView.showLoading("");
        HashMap<String, String> params = new HashMap<>();
        interactor.getBrandCategoryData(log_tag, Constants.EVENT_LOAD_DATA, activity, params);
    }

    @Override
    public void clickCategory(GoodsCategory category) {

    }


    /**
     * 获取指定段位的数据
     *
     * @param type
     */
    private List<GoodsBrand> getPointData(List<GoodsBrand> mBrandList, List<GoodsBrand> pointList, int type) {
        for (GoodsBrand brand : mBrandList) {
            if (isPoint(brand.getMarkInitial(), type))
                pointList.add(brand);
        }
        return pointList;
    }

    /**
     * 正则判断首字母范围
     *
     * @param point
     * @param type
     * @return
     */
    public static boolean isPoint(String point, int type) {
        String match = null;
        if (type == 0)
            match = "^[A-G]$";
        else if (type == 1)
            match = "^[H-N]$";
        else if (type == 2)
            match = "^[O-T]$";
        else if (type == 3)
            match = "^[U-Z]$";
        Pattern p = Pattern.compile(match);
        Matcher m = p.matcher(point);
        return m.matches();
    }

    @Override
    public void onSuccess(int event_tag, List<GoodsBrand> data) {
        if (event_tag == Constants.EVENT_GET_DATA) {
            brandCategoryView.showBrandCategory(data);
        } else if (event_tag == Constants.EVENT_REFRESH_DATA) {
            brandCategoryView.showRefreshBrandCategory(data);
        } else if (event_tag == Constants.EVENT_LOAD_DATA) {
            brandCategoryView.showloadBrandCategory(data);
        }
        getDataSuccess();

    }

    @Override
    public void onEmpty(int event_tag, List<GoodsBrand> data) {
        getDataEmpty();
    }

    @Override
    public void onError(int event_tag,String msg) {
        getDataError();
    }

    @Override
    public void onException(String msg) {

    }
}
