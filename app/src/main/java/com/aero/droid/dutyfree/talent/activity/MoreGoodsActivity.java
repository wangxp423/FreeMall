package com.aero.droid.dutyfree.talent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.bumptech.glide.Glide;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/12/14
 * Desc :  更多商品页面
 */
public class MoreGoodsActivity extends BaseFragmentActivity {
    @Bind(R.id.more_gv)
    PullableGridView moreGv;
    @Bind(R.id.more_pull_layout)
    RGPullRefreshLayout morePullLayout;

    /********************************************/
    private String title;
    private List<GoodsInfo> moreGoodsList = new ArrayList<>();
    private JavaBeanBaseAdapter<GoodsInfo> adapter;

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
        List<GoodsInfo> goodsList = extras.getParcelableArrayList("goodsList");
        moreGoodsList.addAll(goodsList);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_more;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return morePullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        initTitle(title);
        adapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_recommend_good,moreGoodsList) {
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
        moreGv.setAdapter(adapter);

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
