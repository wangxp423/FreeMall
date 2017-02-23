package com.aero.droid.dutyfree.talent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.FeedBackActivity;
import com.aero.droid.dutyfree.talent.activity.InviteFriendActivity;
import com.aero.droid.dutyfree.talent.activity.LoginActivity;
import com.aero.droid.dutyfree.talent.activity.MessageCategoryActivity;
import com.aero.droid.dutyfree.talent.activity.MyCollectActivity;
import com.aero.droid.dutyfree.talent.activity.MyCouponsActivity;
import com.aero.droid.dutyfree.talent.activity.MyOrderActivity;
import com.aero.droid.dutyfree.talent.activity.SettingsActivity;
import com.aero.droid.dutyfree.talent.activity.UserInfoActivity;
import com.aero.droid.dutyfree.talent.base.BaseFragment;
import com.aero.droid.dutyfree.talent.bean.User;
import com.aero.droid.dutyfree.talent.presenter.MePresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.MePresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.glidetrans.GlideCircleTransform;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.MyCenterView;
import com.bumptech.glide.Glide;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/11/29
 * Desc : 主页 我的页面
 */
public class MeFragment extends BaseFragment implements View.OnClickListener, MyCenterView {

    @Bind(R.id.photo_cv_my)
    ImageView mPhotoCv;
    @Bind(R.id.me_head_layout)
    LinearLayout mHeadLayout;
    @Bind(R.id.login_name_tv_my)
    TextView mLoginNameTv;
//    @Bind(R.id.prerogative_key_iv_my)
//    ImageView mPrerogativeKeyIv;
//    @Bind(R.id.prerogative_description_tv_my)
//    TextView mPrerogativeDescriptionTv;
    @Bind(R.id.order_list_iv_my)
    ImageView mOrderListIv;
    @Bind(R.id.orders_rl_my)
    RelativeLayout mOrdersRl;
    @Bind(R.id.wallet_iv_my)
    ImageView mWalletIv;
    @Bind(R.id.wallet_rl_my)
    RelativeLayout mWalletRl;
    @Bind(R.id.collect_iv_my)
    ImageView mCollectIv;
    @Bind(R.id.collect_rl_my)
    RelativeLayout mCollectRl;
    @Bind(R.id.invite_iv_my)
    ImageView mInviteIv;
    @Bind(R.id.invite_rl_my)
    RelativeLayout mInviteRl;
    @Bind(R.id.welfare_iv_my)
    ImageView mWelfareIv;
    @Bind(R.id.welfare_rl_my)
    RelativeLayout mWelfareRl;
    @Bind(R.id.feedback_iv_my)
    ImageView mFeedbackIv;
    @Bind(R.id.opinion_rl_my)
    RelativeLayout mOpinionRl;
    @Bind(R.id.hot_line_iv_my)
    ImageView mHotLineIv;
    @Bind(R.id.customer_service_rl_my)
    RelativeLayout mCustomerServiceRl;
    @Bind(R.id.help_iv_my)
    ImageView mHelpIv;
    @Bind(R.id.help_center_rl_my)
    RelativeLayout mHelpCenterRl;
    @Bind(R.id.orders_description_tv_my)
    TextView mOrdersDescriptionTv;
    @Bind(R.id.wallet_description_tv_my)
    TextView mWalletDescriptionTv;
    @Bind(R.id.collect_description_tv_my)
    TextView mCollectDescriptionTv;
    @Bind(R.id.hot_line_num_tv_my)
    TextView mHotLineNumTv;
    @Bind(R.id.order_tv_my)
    TextView mOrderTv;
    @Bind(R.id.wallet_tv_my)
    TextView mWalletTv;
    @Bind(R.id.collect_tv_my)
    TextView mCollectTv;
    @Bind(R.id.invite_tv_my)
    TextView mInviteTv;
    @Bind(R.id.welfare_tv_my)
    TextView mWelfareTv;
    @Bind(R.id.feebback_tv_my)
    TextView mFeebbackTv;
    @Bind(R.id.hotline_tv_my)
    TextView mHotlineTv;
    @Bind(R.id.help_tv_my)
    TextView mHelpTv;
    @Bind(R.id.me_head_bg)
    ImageView meHeadBg;
    @Bind(R.id.me_pull_scrollview)
    PullableScrollView mePullScrollview;
    @Bind(R.id.me_title_bg_layout)
    FrameLayout meTitleBgLayout;
    @Bind(R.id.title_left_layout)
    RelativeLayout titleLeftLayout;
    @Bind(R.id.title_content)
    TextView titleContent;
    @Bind(R.id.me_msg_num)
    TextView meMsgNum;
    @Bind(R.id.title_right_layout)
    RelativeLayout titleRightLayout;
    @Bind(R.id.me_pull_layout)
    RGPullRefreshLayout mePullLayout;
    /*********************************************/
    private MePresenter presenter;
    private User user;
    Activity mActivity;
    private boolean isFirst = true;

    @Override
    protected void onFirstUserVisible() {
    }

    @Override
    protected void onUserVisible() {
        String memberId = UserUtil.getUserId(getActivity());
        if (!TextUtils.isEmpty(memberId)){
            presenter.getUserData(TAG_LOG, memberId);
        }else{
            nonLoginView();
        }

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
        mActivity = getActivity();
        titleLeftLayout.setOnClickListener(this);
        titleRightLayout.setOnClickListener(this);
        mLoginNameTv.setOnClickListener(this);
        mHeadLayout.setOnClickListener(this);
        mLoginNameTv.setOnClickListener(this);
        mOrdersRl.setOnClickListener(this);
        mWalletRl.setOnClickListener(this);
        mCollectRl.setOnClickListener(this);
        mInviteRl.setOnClickListener(this);
        mWelfareRl.setOnClickListener(this);
        mOpinionRl.setOnClickListener(this);
        mCustomerServiceRl.setOnClickListener(this);
        mHelpCenterRl.setOnClickListener(this);
        FrameLayout.LayoutParams headParams = new FrameLayout.LayoutParams(mScreenWidth, mScreenWidth / 2);
        meHeadBg.setLayoutParams(headParams);
        Glide.with(getActivity()).load(R.mipmap.me_head_bg).centerCrop().into(meHeadBg);
        nonLoginView();
        presenter = new MePresenterImpl(getActivity(), this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fm_me;
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
    public void showUserData(User user) {
        this.user = user;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginView();
            }
        });
    }

    @Override
    public void requestError(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(getActivity(), msg);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {

            if (NetUtils.isNetworkConnected(getActivity())) {
                String memberId = UserUtil.getUserId(getActivity());
                if (!TextUtils.isEmpty(memberId)){
                    presenter.getUserData(TAG_LOG, memberId);
                }else {
                    nonLoginView();
                }
            } else {
                toggleNetworkError(true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String memberId = UserUtil.getUserId(getActivity());
                        if (!TextUtils.isEmpty(memberId)){
                            presenter.getUserData(TAG_LOG, memberId);
                        }else{
                            nonLoginView();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_head_layout:
                if (TextUtils.isEmpty(UserUtil.getUserId(getActivity()))) {
                    readyGo(LoginActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user",user);
                    readyGo(UserInfoActivity.class,bundle);
                }
                break;
            case R.id.orders_rl_my:
                if (TextUtils.isEmpty(UserUtil.getUserId(getActivity()))) {
                    readyGo(LoginActivity.class);
                } else {
                    readyGo(MyOrderActivity.class);
                }
                break;
            case R.id.wallet_rl_my:
                if (TextUtils.isEmpty(UserUtil.getUserId(getActivity()))) {
                    readyGo(LoginActivity.class);
                } else {
                    readyGo(MyCouponsActivity.class);
                }
                break;
            case R.id.collect_rl_my:
                if (TextUtils.isEmpty(UserUtil.getUserId(getActivity()))) {
                    readyGo(LoginActivity.class);
                } else {
                    readyGo(MyCollectActivity.class);
                }
                break;
            case R.id.title_left_layout:
                readyGo(SettingsActivity.class);
                break;
            case R.id.title_right_layout:
                readyGo(MessageCategoryActivity.class);
                break;
            case R.id.invite_rl_my:
                readyGo(InviteFriendActivity.class);
                break;
            case R.id.welfare_rl_my:
                ToastUtil.showShortToast(getActivity(), getResources().getString(R.string.me_please_wait));
                break;
            case R.id.opinion_rl_my:
                readyGo(FeedBackActivity.class);
                break;
            case R.id.customer_service_rl_my:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:62966666"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.help_center_rl_my:
                ToastUtil.showShortToast(getActivity(),"跳转帮助页面H5");
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 已经登陆
     */
    private void loginView() {
        mOrderListIv.setImageResource(R.mipmap.order_list_my);
        mWalletIv.setImageResource(R.mipmap.wallet_my);
        mCollectIv.setImageResource(R.mipmap.collect_my);
        mInviteIv.setImageResource(R.mipmap.invite_my);
        mWelfareIv.setImageResource(R.mipmap.welfare_my);
        mFeedbackIv.setImageResource(R.mipmap.feedback_my);
        mHotLineIv.setImageResource(R.mipmap.hot_line_my);
        mHelpIv.setImageResource(R.mipmap.help_my);

        mLoginNameTv.setText(TextUtils.isEmpty(user.getName()) ? user.getNickName() : user.getName());
        if (null != user.getSpecialInfos() && user.getSpecialInfos().size() > 0) {
//            mPrerogativeKeyIv.setVisibility(View.VISIBLE);
//            mPrerogativeDescriptionTv.setVisibility(View.VISIBLE);
//            mPrerogativeDescriptionTv.setText(user.getSpecialInfos().get(user.getSpecialInfos().size() - 1).getSpDesc());
        }
        if (TextUtils.isEmpty(user.getPhoto())) {
            mPhotoCv.setImageResource(R.mipmap.default_photo_1_my_center);
        } else {
            Glide.with(getActivity()).load(user.getPhoto()).transform(new GlideCircleTransform(getActivity())).placeholder(R.mipmap.default_photo_1_my_center).into(mPhotoCv);
        }
        mOrdersDescriptionTv.setVisibility(View.VISIBLE);
        mOrdersDescriptionTv.setText(getResources().getString(R.string.me_non_evaluation) + " " + user.getUnCmtQty());
        mWalletDescriptionTv.setVisibility(View.VISIBLE);
        mWalletDescriptionTv.setText(getResources().getString(R.string.me_coupons) + " " + user.getCouponQty());
        mCollectDescriptionTv.setVisibility(View.VISIBLE);
        mCollectDescriptionTv.setText(user.getFavoQty());
        if (!TextUtils.isEmpty(user.getUnreadMsgQty()) && Integer.parseInt(user.getUnreadMsgQty())>0){
            meMsgNum.setVisibility(View.VISIBLE);
            meMsgNum.setText(user.getUnreadMsgQty());
        }
        mHotLineNumTv.setTextColor(0xff0000ee);
        int blackColor = getResources().getColor(R.color.black);
        mOrderTv.setTextColor(blackColor);
        mWalletTv.setTextColor(blackColor);
        mCollectTv.setTextColor(blackColor);
        mInviteTv.setTextColor(blackColor);
        mWelfareTv.setTextColor(blackColor);
        mFeebbackTv.setTextColor(blackColor);
        mHotlineTv.setTextColor(blackColor);
        mHelpTv.setTextColor(blackColor);
    }

    /**
     * 没有登陆
     */
    private void nonLoginView() {
        mOrderListIv.setImageResource(R.mipmap.order_list_un_my);
        mWalletIv.setImageResource(R.mipmap.wallet_un_my);
        mCollectIv.setImageResource(R.mipmap.collect_un_my);
        mInviteIv.setImageResource(R.mipmap.invite_un_my);
        mWelfareIv.setImageResource(R.mipmap.welfare_un_my);
        mFeedbackIv.setImageResource(R.mipmap.feedback_un_my);
        mHotLineIv.setImageResource(R.mipmap.hot_line_un_my);
        mHelpIv.setImageResource(R.mipmap.help_un_my);

        mLoginNameTv.setText(getResources().getString(R.string.me_not_login));
        mPhotoCv.setImageResource(R.mipmap.default_photo_1_my_center);
        meMsgNum.setVisibility(View.GONE);
//        mPrerogativeKeyIv.setVisibility(View.GONE);
//        mPrerogativeDescriptionTv.setVisibility(View.GONE);
        mOrdersDescriptionTv.setVisibility(View.GONE);
        mWalletDescriptionTv.setVisibility(View.GONE);
        mCollectDescriptionTv.setVisibility(View.GONE);

        int grayColor = getResources().getColor(R.color.black);
        mHotLineNumTv.setTextColor(grayColor);
        mOrderTv.setTextColor(grayColor);
        mWalletTv.setTextColor(grayColor);
        mCollectTv.setTextColor(grayColor);
        mInviteTv.setTextColor(grayColor);
        mWelfareTv.setTextColor(grayColor);
        mFeebbackTv.setTextColor(grayColor);
        mHotlineTv.setTextColor(grayColor);
        mHelpTv.setTextColor(grayColor);
    }
}
