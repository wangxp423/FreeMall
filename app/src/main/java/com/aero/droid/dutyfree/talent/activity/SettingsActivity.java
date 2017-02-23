package com.aero.droid.dutyfree.talent.activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.util.AppUtil;
import com.aero.droid.dutyfree.talent.util.CacheQueryCleanManager;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.UpdateManager;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.umeng.update.UmengUpdateAgent;

import butterknife.Bind;

/**
 * Author : rongcl
 * Date : 2015/12/12
 * Desc : 系统设置
 */
public class SettingsActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.about_us_rl_set)
    RelativeLayout mAboutUsRl;
    @Bind(R.id.version_check_tv_set)
    TextView mVersionCheckTv;
    @Bind(R.id.version_check_rl_set)
    RelativeLayout mVersionCheckRl;
    @Bind(R.id.feed_back_rl_set)
    RelativeLayout mFeedBackRl;
    @Bind(R.id.clear_cache_tv_set)
    TextView mClearCacheTv;
    @Bind(R.id.clear_cache_rl_set)
    RelativeLayout mClearCacheRl;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.settings_layout;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        initTitle(getResources().getString(R.string.title_setting));
        try {
            mClearCacheTv.setText(CacheQueryCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mVersionCheckTv.setText(AppUtil.getVersionName(mContext));
        mAboutUsRl.setOnClickListener(this);
        mVersionCheckRl.setOnClickListener(this);
        mFeedBackRl.setOnClickListener(this);
        mClearCacheRl.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_us_rl_set:
                readyGo(AboutUsActivity.class);
                break;
            case R.id.version_check_rl_set:
                UmengUpdateAgent.forceUpdate(mContext);
                break;
            case R.id.feed_back_rl_set:
                readyGo(FeedBackActivity.class);
                break;
            case R.id.clear_cache_rl_set:
                getLoading();
                CacheQueryCleanManager.clearCache(this);
                mClearCacheTv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoading();
                        mClearCacheTv.setText("0K");
                    }
                }, 1000);
                break;
        }
    }
}
