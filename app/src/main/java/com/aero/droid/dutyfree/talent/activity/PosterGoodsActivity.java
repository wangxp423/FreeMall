package com.aero.droid.dutyfree.talent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.adapter.MyFragmentPagerAdapter;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.ActiveInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.fragment.PosterFragment;
import com.aero.droid.dutyfree.talent.presenter.SpecialGoodsPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.SpecialGoodsPresenterImpl;
import com.aero.droid.dutyfree.talent.util.DepthPageTransformer;
import com.aero.droid.dutyfree.talent.util.DownTimeUtil;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.PopWindowUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.SpecialGoodsView;
import com.aero.droid.dutyfree.talent.widgets.DownTimerView;
import com.bumptech.glide.Glide;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc : 海报 商品页
 */
public class PosterGoodsActivity extends BaseFragmentActivity implements SpecialGoodsView, ViewPager.OnPageChangeListener {


    @Bind(R.id.poster_good_pager)
    ViewPager posterGoodPager;
    @Bind(R.id.title_content)
    TextView titleContent;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;

    /******************************************/
    private SpecialGoodsPresenter presenter;
    private List<GoodsDetail> goodsList;
    private ActiveInfo activeInfo;
    private List<Fragment> fragments = new ArrayList<>();
    private MyFragmentPagerAdapter adapter;
    private String activeId;
    private String title;

    @Override
    protected void getBundleExtras(Bundle extras) {
        activeId = extras.getString("activeId");
        title = extras.getString("title");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_poster;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return posterGoodPager;
    }

    @Override
    protected void initViewsAndEvents() {
        titleLayout.setBackgroundResource(0);
        initTitleRightImg(TextUtils.isEmpty(title) ? "" : title, R.drawable.title_share_selector, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopWindowUtil.showSharePop(PosterGoodsActivity.this, "", activeInfo.getShareImage(), activeInfo.getShareHtml(), activeInfo.getShareText(), mShareListener);
            }
        });
        posterGoodPager.setPageTransformer(true, new DepthPageTransformer());
        presenter = new SpecialGoodsPresenterImpl(PosterGoodsActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getGoodsList(TAG_LOG, activeId, Url.POSTERGOODS);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getGoodsList(TAG_LOG, activeId, Url.POSTERGOODS);
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
                goodsList = activeInfo.getGoodsInfoList();
                if (null != goodsList && goodsList.size() > 0) {
                    for (int i = 0; i < goodsList.size(); i++) {
                        GoodsDetail goodDetail = goodsList.get(i);
                        PosterFragment fragment = new PosterFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("goodDetail", goodDetail);
                        fragment.setArguments(bundle);
                        fragments.add(fragment);
                    }
                    if (null == adapter) ;
                    adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mContext, fragments);
                }
                posterGoodPager.setAdapter(adapter);
            }
        });
    }

    @Override
    public void refreshGoodsListData(final List<GoodsDetail> goodsInfoList) {
    }

    @Override
    public void loadGoodsListData(final List<GoodsDetail> goodsInfoList) {
    }

    @Override
    public void requestError(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleNetworkError(true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toggleShowLoading(true,null);
                        presenter.getGoodsList(TAG_LOG, activeId, Url.POSTERGOODS);
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
                presenter.getGoodsList(TAG_LOG, activeId, Url.POSTERGOODS);
            }
        });
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public ActiveInfo getAcitiveInfo() {
        return activeInfo;
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
