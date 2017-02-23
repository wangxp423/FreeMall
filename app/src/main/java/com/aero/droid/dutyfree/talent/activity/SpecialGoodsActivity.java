package com.aero.droid.dutyfree.talent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.ActiveInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.presenter.SpecialGoodsPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.SpecialGoodsPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.PopWindowUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.SpecialGoodsView;
import com.aero.droid.dutyfree.talent.widgets.ExpandFooterView;
import com.aero.droid.dutyfree.talent.widgets.ExpandHeaderView;
import com.aero.droid.dutyfree.talent.widgets.GridViewForScrollView;
import com.bumptech.glide.Glide;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;
import com.ybao.pullrefreshview.layout.BaseFooterView;
import com.ybao.pullrefreshview.layout.BaseHeaderView;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableScrollView;

import java.util.List;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : 专场 商品页
 */
public class SpecialGoodsActivity extends BaseFragmentActivity implements SpecialGoodsView, BaseFooterView.OnLoadListener, BaseHeaderView.OnRefreshListener {
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
//    @Bind(R.id.special_footer_layout)
//    ExpandFooterView footerLayout;
//    @Bind(R.id.special_header_layout)
//    ExpandHeaderView headerLayout;
    @Bind(R.id.special_title_img)
    ImageView specialTitleIv;
    @Bind(R.id.specila_title_name)
    TextView specilaTitleNameTv;
    @Bind(R.id.specila_title_desc)
    TextView specilaTitleDescTv;
    @Bind(R.id.special_gv)
    GridViewForScrollView specialGv;
    @Bind(R.id.special_pull_scrollview)
    PullableScrollView specialPullScrollview;
    @Bind(R.id.special_pull_layout)
    RGPullRefreshLayout specialPullLayout;
    /******************************************/
    private SpecialGoodsPresenter presenter;
    private JavaBeanBaseAdapter<GoodsInfo> adapter;
    private String activeId;
    private String title;
    private ActiveInfo activeInfo;

    @Override
    protected void getBundleExtras(Bundle extras) {
        activeId = extras.getString("activeId");
        title = extras.getString("title");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_special;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mScreenWidth, mScreenHeight - UiUtil.dip2px(mContext, 65));
//        specialPullLayout.setLayoutParams(params);
        return specialPullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
//        footerLayout.setOnLoadListener(this);
//        headerLayout.setOnRefreshListener(this);
        initTitleRightImg(TextUtils.isEmpty(title) ? "" : title, R.drawable.title_share_selector, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopWindowUtil.showSharePop(SpecialGoodsActivity.this,"",activeInfo.getShareImage(),activeInfo.getShareHtml(),activeInfo.getShareText(),mShareListener);
            }
        });
        adapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_recommend_good) {
            @Override
            protected void bindView(int position, ViewHolder holder, final GoodsInfo info) {
                //商品图片
                ImageView goodImg = holder.getView(R.id.item_recommend_good_img);
                int width = mScreenWidth - UiUtil.dip2px(mContext, 40);
                LinearLayout.LayoutParams goodImgParams = new LinearLayout.LayoutParams(width/2, width/2);
                goodImg.setLayoutParams(goodImgParams);
                Glide.with(mContext).load(info.getGoodsImg()).centerCrop().into(goodImg);
                goodImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("goodId",info.getId());
                        bundle.putString("srcType", TextUtils.isEmpty(info.getSrcType()) ? "0" : info.getSrcType());
                        bundle.putString("srcId", TextUtils.isEmpty(info.getSrcId()) ? "0" : info.getSrcId());
                        readyGo(GoodsDetailActivity.class, bundle);
                    }
                });
                //商品名称
                TextView goodName = holder.getView(R.id.item_recommend_good_name);
                goodName.setText(info.getGoodsName());
                //商品描述
                TextView goodDesc = holder.getView(R.id.item_recommend_good_desc);
                goodDesc.setText(info.getGoodsDes());
                //app价格
                TextView appPirce = holder.getView(R.id.item_recommend_good_price1);
                appPirce.setText(info.getPrice_app_dollar());
                //市场价格
                TextView refPrice = holder.getView(R.id.item_recommend_good_price2);
                refPrice.setText(info.getPrice_ref_dollar());
            }
        };
        specialGv.setAdapter(adapter);
        presenter = new SpecialGoodsPresenterImpl(SpecialGoodsActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getGoodsList(TAG_LOG, activeId, Url.SPECIALGOODS);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getGoodsList(TAG_LOG, activeId, Url.SPECIALGOODS);
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
    public void showGoodsListData(final ActiveInfo activeInfo) {
        this.activeInfo = activeInfo;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                toggleShowLoading(false, null);
                Glide.with(mContext).load(activeInfo.getHeaderImg()).placeholder(R.mipmap.square_normal_img).into(specialTitleIv);
                specilaTitleNameTv.setText(activeInfo.getActiveName());
                specilaTitleDescTv.setText(activeInfo.getMemo());
                adapter.addAll(activeInfo.getGoodsInfoList());
            }
        });
    }

    @Override
    public void refreshGoodsListData(final List<GoodsDetail> goodsInfoList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                headerLayout.stopRefresh();
                adapter.addAll(goodsInfoList);
            }
        });
    }

    @Override
    public void loadGoodsListData(final List<GoodsDetail> goodsInfoList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                footerLayout.stopLoad();
                adapter.addAll(goodsInfoList);
            }
        });
    }

    @Override
    public void onLoad(BaseFooterView baseFooterView) {
        presenter.loadGoodsList(TAG_LOG, activeId, Url.SPECIALGOODS);
    }

    @Override
    public void onRefresh(BaseHeaderView baseHeaderView) {
        presenter.refreshGoodsList(TAG_LOG, activeId, Url.SPECIALGOODS);
    }

    @Override
    public void requestError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(mContext, msg);
                toggleNetworkError(true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.getGoodsList(TAG_LOG, activeId, Url.SPECIALGOODS);
                    }
                });
            }
        });
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showEmpty(String msg,int imgId) {

    }

    @Override
    public void showException(String msg) {

    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getGoodsList(TAG_LOG, activeId, Url.SPECIALGOODS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMSocialService mController = (UMSocialService) UMServiceFactory
                .getUMSocialService("com.umeng.login");
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }


}
