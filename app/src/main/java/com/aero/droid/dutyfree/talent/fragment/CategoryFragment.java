package com.aero.droid.dutyfree.talent.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.TitleSearchActivity;
import com.aero.droid.dutyfree.talent.adapter.MyFragmentPagerAdapter;
import com.aero.droid.dutyfree.talent.base.BaseFragment;
import com.aero.droid.dutyfree.talent.presenter.MainCategoryPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.MainCategoryPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.view.MainCategoryView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : 首页，分类页
 */
public class CategoryFragment extends BaseFragment implements MainCategoryView, View.OnClickListener, ViewPager.OnPageChangeListener {
    @Bind(R.id.filter_category)
    TextView filterCategory;
    @Bind(R.id.filter_category_line)
    ImageView filterCategoryLine;
    @Bind(R.id.filter_brand)
    TextView filterBrand;
    @Bind(R.id.filter_brand_line)
    ImageView filterBrandLine;
    @Bind(R.id.filter_search)
    ImageView filterSearch;
    @Bind(R.id.filter_viewpager)
    ViewPager filterViewpager;
    @Bind(R.id.filter_category_layout)
    LinearLayout filterCategoryLayout;
    @Bind(R.id.filter_brand_layout)
    LinearLayout filterBrandLayout;

    /********************************/
    private MyFragmentPagerAdapter adapter;
    private List<Fragment> fragments;
    private MainCategoryPresenter presenter;

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        filterCategoryLayout.setOnClickListener(this);
        filterBrandLayout.setOnClickListener(this);
        filterSearch.setOnClickListener(this);
        filterViewpager.setOnPageChangeListener(this);
        if (null == fragments)
            fragments = new ArrayList<>();
        GoodsCategoryFragment categoryFragment = new GoodsCategoryFragment();
        BrandCategoryFragment brandCategoryFragment = new BrandCategoryFragment();
        fragments.add(categoryFragment);
        fragments.add(brandCategoryFragment);
        adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), getActivity(), fragments);
        filterViewpager.setAdapter(adapter);
        presenter = new MainCategoryPresenterImpl(getActivity(), this);
//        presenter.initAllFragments();

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fm_main_category;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected void onEventBackgroundComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void showAllFragment(List<Fragment> fragments) {
//        this.fragments = fragments;
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void showGoodsCategory() {
        filterCategory.setTextColor(getResources().getColor(R.color.black));
        filterCategoryLine.setVisibility(View.VISIBLE);
        filterBrand.setTextColor(getResources().getColor(R.color.gray_text));
        filterBrandLine.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showBrandCategory() {
        filterBrand.setTextColor(getResources().getColor(R.color.black));
        filterBrandLine.setVisibility(View.VISIBLE);
        filterCategory.setTextColor(getResources().getColor(R.color.gray_text));
        filterCategoryLine.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showShopBags() {

    }

    @Override
    public void showSearch() {
        readyGo(TitleSearchActivity.class);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_category_layout:
                filterViewpager.setCurrentItem(0);
                break;
            case R.id.filter_brand_layout:
                filterViewpager.setCurrentItem(1);
                break;
            case R.id.filter_search:
                presenter.clickSearch();
                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (0 == position) {
            presenter.clickCategory();
        } else if (1 == position) {
            presenter.clickBrand();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
