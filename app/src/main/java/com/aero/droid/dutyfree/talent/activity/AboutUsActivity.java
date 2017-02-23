package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : rongcl
 * Date : 2015/12/12
 * Desc : 关于我们
 */
public class AboutUsActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.back_to_set_iv_au)
    ImageView mBackToSetIv;
    @Bind(R.id.pruduct_version_tv_au)
    TextView mPruductVersionTv;
    @Bind(R.id.pruduct_description_tv_au)
    TextView mPruductDescriptionTv;
    @Bind(R.id.show_services_agreement_tv_au)
    TextView mShowServicesAgreementTv;
    @Bind(R.id.to_attention_weixin_tv_au)
    TextView mToAttentionWeixinTv;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.about_us_layout;
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
        mBackToSetIv.setOnClickListener(this);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_to_set_iv_au :
                finish();
                break;
        }
    }
}
