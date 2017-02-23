package com.aero.droid.dutyfree.talent.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.DiscountAeraActivity;
import com.aero.droid.dutyfree.talent.activity.InviteFriendActivity;
import com.aero.droid.dutyfree.talent.activity.LoginActivity;
import com.aero.droid.dutyfree.talent.activity.MyTaskActivity;
import com.aero.droid.dutyfree.talent.activity.TitleSearchActivity;
import com.aero.droid.dutyfree.talent.activity.WebViewActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.base.BaseFragment;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.presenter.impl.HandpickPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.PopWindowUtil;
import com.aero.droid.dutyfree.talent.util.TimeFormatUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.HandpickView;
import com.aero.droid.dutyfree.talent.widgets.DownTimerView;
import com.aero.droid.dutyfree.talent.widgets.ExpandHeaderView;
import com.aero.droid.dutyfree.talent.widgets.HandpickLayout;
import com.bumptech.glide.Glide;
import com.ybao.pullrefreshview.layout.BaseFooterView;
import com.ybao.pullrefreshview.layout.BaseHeaderView;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : 首页精选页
 */
public class HandPickFragment extends BaseFragment implements HandpickView, BaseHeaderView.OnRefreshListener, BaseFooterView.OnLoadListener, PullableScrollView.OnMyScrollListener, View.OnClickListener {
    @Bind(R.id.handpick_header)
    ExpandHeaderView layoutHeader;
    //    @Bind(R.id.handpick_footer)
//    ExpandFooterView layoutFooter;
    @Bind(R.id.handpick_scroll_goods)
    FrameLayout layoutViewPagerGoods;
    @Bind(R.id.handpick_other_goods)
    FrameLayout layoutOtherGoods;
    @Bind(R.id.handpick_title_layout)
    RelativeLayout layoutTitleLayout;
    @Bind(R.id.handpick_loading_layout)
    RGPullRefreshLayout loadingLayout;
    @Bind(R.id.handpick_scroll_view)
    PullableScrollView mScrollView;
    @Bind(R.id.handpick_search)
    ImageView titleSearch;
    @Bind(R.id.handpick_discount)
    ImageView discountIv;
    @Bind(R.id.handpick_invite)
    ImageView inviteIv;
    @Bind(R.id.handpick_vip)
    ImageView vipIv;
    @Bind(R.id.handpick_title_appname)
    TextView appnameTv;


    /****************************************/
    private HandpickPresenterImpl presenter;
    private ViewPagerFragment pagerFragment;
    private float titleHeight;

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
    public void onResume() {
        super.onResume();
    }

    @Override
    protected View getLoadingTargetView() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mScreenWidth, mScreenHeight - UiUtil.dip2px(getActivity(), 65));
        loadingLayout.setLayoutParams(params);
        return loadingLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        titleHeight = UiUtil.dip2px(getActivity(), 200);
        layoutTitleLayout.setAlpha(0.0f);
        layoutHeader.setOnRefreshListener(this);
        mScrollView.setMyScrollListener(this);
//        layoutFooter.setOnLoadListener(this);
        titleSearch.setOnClickListener(this);
        discountIv.setOnClickListener(this);
        inviteIv.setOnClickListener(this);
        vipIv.setOnClickListener(this);
        LinearLayout.LayoutParams scrollViewParams = new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth / 5*4);
        layoutViewPagerGoods.setLayoutParams(scrollViewParams);
        LinearLayout.LayoutParams discountIvParams = new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth / 32 * 9);
        discountIv.setLayoutParams(discountIvParams);
        Glide.with(mContext).load(R.mipmap.handpick_discount).centerCrop().into(discountIv);
        LinearLayout.LayoutParams inviteIvParams = new LinearLayout.LayoutParams(mScreenWidth / 2, mScreenWidth / 16 * 3);
        inviteIvParams.rightMargin = UiUtil.dip2px(mContext, 0.5f);
        inviteIv.setLayoutParams(inviteIvParams);
        LinearLayout.LayoutParams vipIvParams = new LinearLayout.LayoutParams(mScreenWidth / 2, mScreenWidth / 16 * 3);
        vipIvParams.leftMargin = UiUtil.dip2px(mContext, 0.5f);
        vipIv.setLayoutParams(vipIvParams);
        Glide.with(mContext).load(R.mipmap.handpick_invite_friend).centerCrop().into(inviteIv);
        Glide.with(mContext).load(R.mipmap.handpick_privilege).centerCrop().into(vipIv);

        presenter = new HandpickPresenterImpl(getActivity(), this);
        if (NetUtils.isNetworkConnected(getActivity())) {
            presenter.getGoodsList(TAG_LOG);
            presenter.getOtherGoodsList(TAG_LOG);
        } else {
            //没有网络的处理
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getGoodsList(TAG_LOG);
                }
            });
        }

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fm_handpick;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == Constants.EVENT_ADD_FAVO) {
            presenter.addFavorite(TAG_LOG, (String) eventCenter.getData());
        }
    }

    @Override
    protected void onEventBackgroundComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }


    @Override
    public void showGoodsList(final List<HandpickBanner> handpicksList) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pagerFragment = new ViewPagerFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("handpickList", (ArrayList<? extends Parcelable>) handpicksList);
                pagerFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.handpick_scroll_goods, pagerFragment).commit();
            }
        });
    }

    @Override
    public void showOhterGoodsList(final List<HandpickType> typeList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HandpickLayout layout = new HandpickLayout(getActivity(), mScreenWidth);
                layout.setData(typeList);
                layoutOtherGoods.addView(layout);
//                mScrollView.smoothScrollTo(0, 500);
//                mScrollView.smoothScrollBy(0,0);
            }
        });

    }

    @Override
    public void refreshGoodsList(final List<HandpickBanner> handpicksList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutHeader.stopRefresh();
                pagerFragment.refreshData(handpicksList);
            }
        });

    }

    @Override
    public void refreshOhterGoodsList(final List<HandpickType> typeList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutOtherGoods.removeAllViews();
                HandpickLayout layout = new HandpickLayout(getActivity(), mScreenWidth);
                layout.setData(typeList);
                layoutOtherGoods.addView(layout);
//                mScrollView.smoothScrollTo(0, 500);
//                mScrollView.smoothScrollBy(0, 0);
            }
        });
    }

    @Override
    public void addFavoSuccess(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PopWindowUtil.showNotifyPop(mContext, R.mipmap.notify_icon_success, getResources().getString(R.string.notify_add_favo_success));
            }
        });
    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getGoodsList(TAG_LOG);
                presenter.getOtherGoodsList(TAG_LOG);
            }
        });
    }

    @Override
    public void onLoad(BaseFooterView baseFooterView) {
//        baseFooterView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                layoutFooter.stopLoad();
//            }
//        }, 2000);
    }

    @Override
    public void onRefresh(BaseHeaderView baseHeaderView) {
        baseHeaderView.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.refreshGoodsList(TAG_LOG);
                presenter.refreshOtherGoodsList(TAG_LOG);
            }
        }, 2000);
    }


    @Override
    public void onMyScrollChange(int l, int t, int oldl, int oldt) {
        float alpha = t / titleHeight;
        if (alpha > 0.5) {
            layoutTitleLayout.setAlpha(0.5f);
        } else {
            layoutTitleLayout.setAlpha(t / titleHeight);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.handpick_search:
                readyGo(TitleSearchActivity.class);
                break;
            case R.id.handpick_discount:
                readyGo(DiscountAeraActivity.class);
                break;
            case R.id.handpick_invite:
                if (TextUtils.isEmpty(UserUtil.getUserId(getActivity()))) {
                    readyGo(LoginActivity.class);
                } else {
                    readyGo(InviteFriendActivity.class);
                }
                break;
            case R.id.handpick_vip:
                if (TextUtils.isEmpty(UserUtil.getUserId(getActivity()))) {
                    readyGo(LoginActivity.class);
                } else {
                    readyGo(MyTaskActivity.class);
                }
                break;

        }
    }

}
