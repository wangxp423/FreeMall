package com.aero.droid.dutyfree.talent.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.PopWindowUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.SpecialGoodsView;
import com.aero.droid.dutyfree.talent.widgets.ExpandFooterView;
import com.aero.droid.dutyfree.talent.widgets.ExpandHeaderView;
import com.aero.droid.dutyfree.talent.widgets.JustifyTextView;
import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;
import com.ybao.pullrefreshview.layout.BaseFooterView;
import com.ybao.pullrefreshview.layout.BaseHeaderView;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableListView;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : 专题 商品页
 */
public class TopicGoodsActivity extends BaseFragmentActivity implements SpecialGoodsView, BaseFooterView.OnLoadListener, BaseHeaderView.OnRefreshListener {
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
//    @Bind(R.id.topic_footer_layout)
//    ExpandFooterView footerLayout;
//    @Bind(R.id.topic_header_layout)
//    ExpandHeaderView headerLayout;
    @Bind(R.id.topic_goods_listview)
    PullableListView topicGoodsListview;
    @Bind(R.id.topic_pull_layout)
    RGPullRefreshLayout topicPullLayout;
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
        return R.layout.activity_topic;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return topicPullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
//        footerLayout.setOnLoadListener(this);
//        headerLayout.setOnRefreshListener(this);
        initTitleRightImg(TextUtils.isEmpty(title) ? "" : title, R.drawable.title_share_selector, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopWindowUtil.showSharePop(TopicGoodsActivity.this, getResources().getString(R.string.share), activeInfo.getShareImage(), activeInfo.getShareHtml(), activeInfo.getShareText(), mShareListener);
            }
        });
        adapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_topic) {
            @Override
            protected void bindView(int position, ViewHolder holder, final GoodsInfo info) {
                //商品图片
                final ImageView goodImg = holder.getView(R.id.item_topic_good_img);
//                Glide.with(mContext).load(info.getGoodsImg()).fitCenter().into(goodImg);
                Glide.with(mContext).load(info.getGoodsImg()).downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        Bitmap bitmap = BitmapFactory.decodeFile(resource.getAbsolutePath());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth * bitmap.getHeight() / bitmap.getWidth());
                        goodImg.setLayoutParams(params);
//                        goodImg.setImageBitmap(bitmap);
                        Glide.with(mContext).load(info.getGoodsImg()).placeholder(R.mipmap.holder_place_rect1).fitCenter().into(goodImg);
                    }
                });
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
                TextView goodName = holder.getView(R.id.item_topic_good_name);
                goodName.setText(info.getGoodsName());
                //app价格
                TextView appPrice = holder.getView(R.id.item_topic_good_price1);
                appPrice.setText("＄"+info.getPrice_app_dollar());
                //机上价格
                TextView airportPirce = holder.getView(R.id.item_topic_good_price2);
                airportPirce.setText("＄"+info.getPrice_airport_dollar());
                //商品描述
                JustifyTextView goodDesc = holder.getView(R.id.item_topic_good_desc);
                goodDesc.setText(info.getGoodsDes());


            }
        };
        topicGoodsListview.setAdapter(adapter);
        presenter = new SpecialGoodsPresenterImpl(TopicGoodsActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getGoodsList(TAG_LOG, activeId, Url.TOPICGOODS);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getGoodsList(TAG_LOG, activeId, Url.TOPICGOODS);
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
    public void onRefresh(BaseHeaderView baseHeaderView) {
        presenter.refreshGoodsList(TAG_LOG, activeId, Url.TOPICGOODS);
    }

    @Override
    public void onLoad(BaseFooterView baseFooterView) {
        presenter.loadGoodsList(TAG_LOG, activeId, Url.TOPICGOODS);
    }

    @Override
    public void showGoodsListData(final ActiveInfo activeInfo) {
        this.activeInfo = activeInfo;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false, null);
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
    public void requestError(String msg) {
        toggleNetworkError(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getGoodsList(TAG_LOG, activeId, Url.TOPICGOODS);
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
                presenter.getGoodsList(TAG_LOG, activeId, Url.TOPICGOODS);
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
