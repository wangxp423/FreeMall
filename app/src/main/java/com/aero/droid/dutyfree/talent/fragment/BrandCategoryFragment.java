package com.aero.droid.dutyfree.talent.fragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragment;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.GoodsBrand;
import com.aero.droid.dutyfree.talent.presenter.BrandCategoryPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.BrandCategoryPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.BrandCategoryView;
import com.aero.droid.dutyfree.talent.widgets.ExpandFooterView;
import com.aero.droid.dutyfree.talent.widgets.ExpandHeaderView;
import com.bumptech.glide.Glide;
import com.ybao.pullrefreshview.layout.BaseFooterView;
import com.ybao.pullrefreshview.layout.BaseHeaderView;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : 品牌分类页
 */
public class BrandCategoryFragment extends BaseFragment implements BaseHeaderView.OnRefreshListener, BaseFooterView.OnLoadListener, BrandCategoryView, AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener {
//    @Bind(R.id.brand_category_header)
//    ExpandHeaderView brandCategoryHeader;
//    @Bind(R.id.brand_category_footer)
//    ExpandFooterView brandCategoryFooter;
    @Bind(R.id.brand_radio_group)
    RadioGroup brandRadioGroup;
    @Bind(R.id.brand_category_gv)
    PullableGridView brandCategoryGv;
    @Bind(R.id.brand_category_pull_layout)
    RGPullRefreshLayout brandCategoryPullLayout;

    /***********************************/
    private BrandCategoryPresenter presenter;
    private JavaBeanBaseAdapter<GoodsBrand> adapter;
    private List<GoodsBrand> brandList;
    private List<GoodsBrand> pointPrandList; //指定分类品牌集合

    @Override
    protected void onFirstUserVisible() {
        presenter = new BrandCategoryPresenterImpl(getActivity(), this);
        if (NetUtils.isNetworkConnected(getActivity())) {
            presenter.getData(TAG_LOG,getActivity(), 0, 0);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getData(TAG_LOG,getActivity(), 0, 0);
                }
            });
        }
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return brandCategoryPullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
//        brandCategoryHeader.setOnRefreshListener(this);
//        brandCategoryFooter.setOnLoadListener(this);
        brandCategoryGv.setOnItemClickListener(this);
        brandRadioGroup.setClickable(false);
        brandRadioGroup.setOnCheckedChangeListener(this);
        if (null == pointPrandList)
            pointPrandList = new ArrayList<>();
        adapter = new JavaBeanBaseAdapter<GoodsBrand>(getActivity(), R.layout.item_brand, pointPrandList) {
            @Override
            protected void bindView(int position, ViewHolder holder, GoodsBrand object) {
                ImageView brandLogo = holder.getView(R.id.brand_logo);
                ViewGroup.LayoutParams params = brandLogo.getLayoutParams();
                int mWidth = UiUtil.getWindowWidth(mContext) - UiUtil.dip2px(mContext, 40);
                params.width = mWidth / 3;
                params.height = mWidth / 3 * 4 / 5;
                brandLogo.setLayoutParams(params);
                brandLogo.setScaleType(ImageView.ScaleType.FIT_XY);
                GoodsBrand brand = getItem(position);
                Glide.with(mContext).load(brand.getMarkImg()).into(brandLogo);
            }
        };
        brandCategoryGv.setAdapter(adapter);

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fm_brand_category;
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
    public void showBrandCategory(List<GoodsBrand> brandList) {
        this.brandList = brandList;
        brandRadioGroup.setClickable(true);
        presenter.clickA2G(BrandCategoryFragment.this.brandList,pointPrandList);

    }

    @Override
    public void showRefreshBrandCategory(List<GoodsBrand> brandList) {

    }

    @Override
    public void showloadBrandCategory(List<GoodsBrand> brandList) {

    }

    @Override
    public void showagBrandCategory(List<GoodsBrand> brandList) {
        refreshAdapter();

    }

    @Override
    public void showhnBrandCategory(List<GoodsBrand> brandList) {
        refreshAdapter();
    }

    @Override
    public void showotBrandCategory(List<GoodsBrand> brandList) {
        refreshAdapter();
    }

    @Override
    public void showuzBrandCategory(List<GoodsBrand> brandList) {
        refreshAdapter();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GoodsBrand brand = pointPrandList.get(position);
        presenter.clickGoodsBrand(brand);
    }

    @Override
    public void onLoad(BaseFooterView baseFooterView) {
//        baseFooterView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                brandCategoryFooter.stopLoad();
//            }
//        }, 2000);

    }

    @Override
    public void onRefresh(final BaseHeaderView baseHeaderView) {
//        baseHeaderView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                brandCategoryHeader.stopRefresh();
//            }
//        }, 2000);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (pointPrandList.size() > 0)
            pointPrandList.clear();
        switch (checkedId) {
            case R.id.brand_radio_a_g:
                presenter.clickA2G(brandList,pointPrandList);
                break;
            case R.id.brand_radio_h_n:
                presenter.clickH2N(brandList,pointPrandList);
                break;
            case R.id.brand_radio_o_t:
                presenter.clickO2T(brandList,pointPrandList);
                break;
            case R.id.brand_radio_u_z:
                presenter.clickU2Z(brandList,pointPrandList);
                break;
        }
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getData(TAG_LOG,getActivity(), 0, 0);
            }
        });
    }

    @Override
    public void showEmpty(String msg,int imgId) {
        toggleShowEmpty(true, null,imgId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getData(TAG_LOG,getActivity(), 0, 0);
            }
        });
    }

    /**
     * 刷新adapter数据
     */
    private void refreshAdapter(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

}
