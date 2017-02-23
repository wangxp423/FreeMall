package com.aero.droid.dutyfree.talent.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.presenter.BrandGoodsPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.BrandGoodsPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.glidetrans.GlideCircleTransform;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.BrandGoodsView;
import com.aero.droid.dutyfree.talent.widgets.CustomImageView;
import com.aero.droid.dutyfree.talent.widgets.ExpandFooterView;
import com.aero.droid.dutyfree.talent.widgets.GridViewForScrollView;
import com.bumptech.glide.Glide;
import com.ybao.pullrefreshview.layout.BaseFooterView;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 分类商品/品牌商品页
 */
public class BrandGoodsActivity extends BaseFragmentActivity implements BrandGoodsView, View.OnClickListener, BaseFooterView.OnLoadListener, AdapterView.OnItemClickListener {
    @Bind(R.id.brand_goods_footer_layout)
    ExpandFooterView footerLayout;
    @Bind(R.id.brand_pull_layout)
    RGPullRefreshLayout pullLayout;
    @Bind(R.id.brand_title_bg)
    ImageView brandTitleBg;
    @Bind(R.id.brand_img)
    ImageView brandIv;
    @Bind(R.id.brand_name)
    TextView brandNameTv;
    @Bind(R.id.brand_desc)
    TextView brandDescTv;
    @Bind(R.id.brand_more_desc)
    ImageView brandMoreDescIv;
    @Bind(R.id.brand_desc_layout)
    LinearLayout descLayout;
    @Bind(R.id.brand_goods_gv)
    GridViewForScrollView brandGoodsGv;
    @Bind(R.id.brand_title_layout)
    FrameLayout brandTitleLayout;
    /*****************************************/
    private JavaBeanBaseAdapter<GoodsInfo> adapter;
    private BrandGoodsPresenter presenter;
    private List<GoodsInfo> goodList = new ArrayList<>();
    private String paramType;  // 1分类  2品牌
    private String categoryId;  //商品分类Id/品牌分类Id
    private String categoryLogo;  //商品分类logo/品牌分类logo
    private String categoryName;  //商品分类名称/品牌分类名称
    private int curPage = 1;
    private int pageSize = 10;
    private boolean isOpen = false; //分类描述是否是展开状态

    @Override
    protected void getBundleExtras(Bundle extras) {
        paramType = extras.getString("paramType");
        categoryId = extras.getString("categoryId");
        categoryLogo = extras.getString("categoryLogo");
        categoryName = extras.getString("categoryName");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_brand_goods;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return pullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        if ("1".equals(paramType))
            descLayout.setVisibility(View.GONE);
        Glide.with(mContext).load(categoryLogo).transform(new GlideCircleTransform(mContext)).into(brandIv);
        brandNameTv.setText(TextUtils.isEmpty(categoryName) ? "" : categoryName);
        footerLayout.setOnLoadListener(this);
        brandMoreDescIv.setOnClickListener(this);
        brandDescTv.setOnClickListener(this);
        brandGoodsGv.setOnItemClickListener(this);
        adapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_brand_good, goodList) {
            @Override
            protected void bindView(int position, ViewHolder holder, GoodsInfo info) {
                ImageView goodImg = holder.getView(R.id.item_brand_good_img);
                int width = mScreenWidth - UiUtil.dip2px(mContext, 40);
                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(width/2,width/2);
                goodImg.setLayoutParams(imgParams);
                TextView goodName = holder.getView(R.id.item_brand_good_name);
                TextView goodDesc = holder.getView(R.id.item_brand_good_desc);
                TextView price1 = holder.getView(R.id.item_brand_good_price1);
                TextView price2 = holder.getView(R.id.item_brand_good_price2);
                Glide.with(mContext).load(info.getGoodsImg()).centerCrop().into(goodImg);
                goodName.setText(info.getGoodsName());
                goodDesc.setText(info.getGoodsDes());
                price1.setText(info.getPrice_app_dollar());
                price2.setText(info.getPrice_ref_dollar());
            }
        };
        brandGoodsGv.setAdapter(adapter);
        presenter = new BrandGoodsPresenterImpl(BrandGoodsActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getGoodsList(TAG_LOG, paramType, categoryId, curPage, pageSize);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getGoodsList(TAG_LOG, paramType, categoryId, curPage, pageSize);
                }
            });
        }
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void showGoodsList(final List<GoodsInfo> infoList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                curPage++;
                toggleShowLoading(false, null);
                goodList.addAll(infoList);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void refreshGoodsList(List<GoodsInfo> infoList) {

    }

    @Override
    public void loadGoodsList(final List<GoodsInfo> infoList) {
        curPage++;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                footerLayout.stopLoad();
                goodList.addAll(infoList);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void showTitleData(final String titleImg, final String desc, String totalNum) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams bgParams = new FrameLayout.LayoutParams(mScreenWidth, mScreenWidth / 2);
                brandTitleBg.setLayoutParams(bgParams);
                Glide.with(mContext).load(titleImg).centerCrop().into(brandTitleBg);
                brandDescTv.setText(desc);
            }
        });

    }

    @Override
    public void end() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                footerLayout.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void requestError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                footerLayout.stopLoad();
                toggleShowLoading(false, null);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.brand_desc:
            case R.id.brand_more_desc:
                if (!isOpen) {
                    isOpen = true;
                    brandMoreDescIv.setImageResource(R.mipmap.arraw_up_more_black);
                    brandDescTv.setMaxLines(100);
                } else {
                    isOpen = false;
                    brandMoreDescIv.setImageResource(R.mipmap.arraw_down_more_black);
                    brandDescTv.setMaxLines(3);
                }
                break;
        }
    }

    @Override
    public void onLoad(final BaseFooterView baseFooterView) {
        presenter.loadGoodsList(TAG_LOG, paramType, categoryId, curPage, pageSize);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GoodsInfo info = goodList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("goodId", info.getId());
        bundle.putString("srcType", TextUtils.isEmpty(info.getSrcType()) ? "0" : info.getSrcType());
        bundle.putString("srcId", TextUtils.isEmpty(info.getSrcId()) ? "0" : info.getSrcId());
        readyGo(GoodsDetailActivity.class, bundle);
    }
}
