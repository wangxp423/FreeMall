package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.ComentsInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.presenter.ComentsPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.ComentsPresenterImpl;
import com.aero.droid.dutyfree.talent.presenter.impl.DiscountPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.TimeFormatUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.glidetrans.GlideCircleTransform;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.ComentsView;
import com.aero.droid.dutyfree.talent.widgets.ExpandFooterView;
import com.aero.droid.dutyfree.talent.widgets.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.ybao.pullrefreshview.layout.BaseFooterView;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableExpandableListView;
import com.ybao.pullrefreshview.view.PullableScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/12/12
 * Desc : 商品评论列表页
 */
public class ComentsActivity extends BaseFragmentActivity implements ComentsView, BaseFooterView.OnLoadListener, View.OnClickListener {
    @Bind(R.id.coments_star_num)
    TextView comentsStarNumTv;
    @Bind(R.id.coments_star)
    LinearLayout comentsStarLayout;
    @Bind(R.id.coments_nice_desc)
    TextView comentsNiceDescTv;
    @Bind(R.id.coments_listview)
    ListViewForScrollView comentsListview;
    @Bind(R.id.comtents_pull_layout)
    RGPullRefreshLayout comtentsPullLayout;
    @Bind(R.id.comtents_footer_layout)
    ExpandFooterView footerLayout;
    @Bind(R.id.coments_num)
    TextView comentsNumTv;
    @Bind(R.id.coments_goto_write)
    ImageView gotoWriteIv;
    @Bind(R.id.coments_pull_scrollview)
    PullableScrollView pullScrollView;
    /*************************************/
    private ComentsPresenter presenter;
    private JavaBeanBaseAdapter<ComentsInfo> adapter;
    private List<ComentsInfo> comentsList = new ArrayList<>();
    private String goodId,goodName,goodImg;
    private String cmtId = "";
    private int curPage = 1;
    private int pageSize = 10;

    @Override
    protected void getBundleExtras(Bundle extras) {
        goodId = extras.getString("goodId");
        goodName = extras.getString("goodName");
        goodImg = extras.getString("goodImg");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_coments;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mScreenWidth, mScreenHeight - UiUtil.dip2px(mContext, 65));
//        comtentsPullLayout.setLayoutParams(params);
        return comtentsPullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        initTitle(getResources().getString(R.string.coments_detail));
        footerLayout.setOnLoadListener(this);
        gotoWriteIv.setOnClickListener(this);
        adapter = new JavaBeanBaseAdapter<ComentsInfo>(mContext, R.layout.item_coments, comentsList) {
            @Override
            protected void bindView(int position, ViewHolder holder, ComentsInfo info) {
                //头像
                ImageView userImg = holder.getView(R.id.coments_user_icon);
                Glide.with(mContext).load(info.getIcon()).transform(new GlideCircleTransform(mContext)).into(userImg);
                //用户名
                TextView userName = holder.getView(R.id.coments_user_name);
                userName.setText(info.getName());
                //评论描述
                TextView desc = holder.getView(R.id.coments_nice_desc);
                desc.setText(info.getContent());
                //时间
                TextView time = holder.getView(R.id.coments_user_time);
                time.setText(TimeFormatUtil.getTimestamp(mContext, Long.parseLong(info.getTime()) * 1000));
                //底部线条
                ImageView bottomLine = holder.getView(R.id.coments_bottom_line);
                if (comentsList.size() > 0 && position == comentsList.size() - 1) {
                    bottomLine.setVisibility(View.GONE);
                } else {
                    bottomLine.setVisibility(View.VISIBLE);
                }
            }
        };
        comentsListview.setAdapter(adapter);
        presenter = new ComentsPresenterImpl(ComentsActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getComentsList(TAG_LOG,goodId,cmtId,curPage,pageSize);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getComentsList(TAG_LOG,goodId,cmtId,curPage,pageSize);
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
    public void showComentsListData(final GoodComents goodComents) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false, null);
                if (goodComents.getComentsList().size()>0){
                    comentsList.addAll(goodComents.getComentsList());
                    adapter.notifyDataSetChanged();
                    pullScrollView.smoothScrollTo(0, 0);
                }else {
                    toggleShowEmpty(true,null,R.mipmap.comments_no_data,null);
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.weight = 1;
                comtentsPullLayout.setLayoutParams(params);
                //心数
                String num = goodComents.getStarQty();
                comentsStarNumTv.setText(num);
                //几个心
                for (int i = 0; i < Integer.parseInt(num); i++) {
                    LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    imgParams.setMargins(5, 5, 5, 5);
                    ImageView image = new ImageView(mContext);
                    image.setLayoutParams(imgParams);
                    image.setImageResource(R.mipmap.coments_star_normal);
                    comentsStarLayout.addView(image);
                }
                //好评率
                comentsNiceDescTv.setText(goodComents.getNiceDesc());
                //评论数
                comentsNumTv.setText(goodComents.getSumQty() + "个评论");

            }
        });

    }

    @Override
    public void loadComtensListData(final List<ComentsInfo> comentsInfoList) {
        curPage++;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                footerLayout.stopLoad();
                comentsList.addAll(comentsInfoList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void requestError(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleNetworkError(true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.getComentsList(TAG_LOG,goodId,cmtId,curPage,pageSize);
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

    }

    @Override
    public void onLoad(BaseFooterView baseFooterView) {
        presenter.loadComentsList(TAG_LOG,goodId,cmtId,curPage,pageSize);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.coments_goto_write:
                presenter.clickWriteComtensBtn(goodId,goodName,goodImg);
                break;
        }
    }
}
