package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.MessageInfo;
import com.aero.droid.dutyfree.talent.presenter.impl.MsgPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.TimeFormatUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.MsgCenterView;
import com.aero.droid.dutyfree.talent.widgets.ExpandHeaderView;
import com.ybao.pullrefreshview.layout.BaseHeaderView;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : rongcl
 * Date : 2015/12/15
 * Desc : 消息中心
 */
public class MessageCategoryActivity extends BaseFragmentActivity implements MsgCenterView, ExpandHeaderView.OnRefreshListener {

    @Bind(R.id.msgcenter_header_layout)
    ExpandHeaderView headerLayout;
    @Bind(R.id.msgcenter_lv)
    PullableListView msgcenterLv;
    @Bind(R.id.msgcenter_pull_layout)
    RGPullRefreshLayout msgcenterPullLayout;

    /**************************************/
    private JavaBeanBaseAdapter<MessageInfo> adapter;
    private List<MessageInfo> infoList = new ArrayList<>();
    private MsgPresenterImpl presenter;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_msgcenter;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return msgcenterPullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        initTitle(getResources().getString(R.string.msgcenter_title));
        headerLayout.setOnRefreshListener(this);
        adapter = new JavaBeanBaseAdapter<MessageInfo>(mContext, R.layout.item_msg_center, infoList) {
            @Override
            protected void bindView(int position, ViewHolder holder, final MessageInfo info) {
                //item布局
                LinearLayout layout = holder.getView(R.id.item_msgcenter_msg_layout);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.clickItem(info.getMsgType());
                    }
                });
                //消息标题图片
                ImageView msgTitleImg = holder.getView(R.id.item_msgcenter_title_img);
                //消息分割线
                ImageView msgLine = holder.getView(R.id.item_msgcenter_msg_line);
                if (position == presenter.getshowMsgCount() - 1) {
                    msgLine.setVisibility(View.GONE);
                } else {
                    msgLine.setVisibility(View.VISIBLE);
                }
                //消息数量
                Button msgNum = holder.getView(R.id.item_msgcenter_msg_num);
                //消息标题
                TextView msgTitle = holder.getView(R.id.item_msgcenter_msg_title);
                msgTitle.setText(info.getMsgTitle());
                //消息内容
                TextView msgContent = holder.getView(R.id.item_msgcenter_msg_content);
                msgContent.setText(info.getMsgContent());
                //消息时间
                TextView msgTime = holder.getView(R.id.item_msgcenter_msg_time);
                msgTime.setText(TimeFormatUtil.getTimestamp(mContext, Long.parseLong(info.getMsgTime())));
                if ("1".equals(info.getMsgType())) {
                    msgTitleImg.setImageResource(R.mipmap.msgcenter_app);
                    if (presenter.getAppMsgCount() < 1) {
                        msgNum.setVisibility(View.INVISIBLE);
                    } else if (presenter.getAppMsgCount() > 9) {
                        msgNum.setText("");
                        msgNum.setBackgroundResource(R.mipmap.msgcenter_num_more_bg);
                    } else {
                        msgNum.setBackgroundResource(R.mipmap.msgcenter_num_bg);
                        msgNum.setVisibility(View.VISIBLE);
                        msgNum.setText(presenter.getAppMsgCount() + "");
                    }
                } else if ("2".equals(info.getMsgType())) {
                    msgTitleImg.setImageResource(R.mipmap.msgcenter_order);
                    if (presenter.getOrderMsgCount() < 1) {
                        msgNum.setVisibility(View.INVISIBLE);
                    } else if (presenter.getOrderMsgCount() > 9) {
                        msgNum.setText("");
                        msgNum.setBackgroundResource(R.mipmap.msgcenter_num_more_bg);
                    } else {
                        msgNum.setBackgroundResource(R.mipmap.msgcenter_num_bg);
                        msgNum.setVisibility(View.VISIBLE);
                        msgNum.setText(presenter.getOrderMsgCount() + "");
                    }
                } else if ("3".equals(info.getMsgType())) {
                    msgTitleImg.setImageResource(R.mipmap.msgcenter_shop);
                    if (presenter.getShopMsgCount() < 1) {
                        msgNum.setVisibility(View.INVISIBLE);
                    } else if (presenter.getShopMsgCount() > 9) {
                        msgNum.setText("");
                        msgNum.setBackgroundResource(R.mipmap.msgcenter_num_more_bg);
                    } else {
                        msgNum.setBackgroundResource(R.mipmap.msgcenter_num_bg);
                        msgNum.setVisibility(View.VISIBLE);
                        msgNum.setText(presenter.getShopMsgCount() + "");
                    }
                } else if ("4".equals(info.getMsgType())) {
                    msgTitleImg.setImageResource(R.mipmap.msgcenter_wallet);
                    if (presenter.getWalletMsgCount() < 1) {
                        msgNum.setVisibility(View.INVISIBLE);
                    } else if (presenter.getWalletMsgCount() > 9) {
                        msgNum.setText("");
                        msgNum.setBackgroundResource(R.mipmap.msgcenter_num_more_bg);
                    } else {
                        msgNum.setBackgroundResource(R.mipmap.msgcenter_num_bg);
                        msgNum.setVisibility(View.VISIBLE);
                        msgNum.setText(presenter.getWalletMsgCount() + "");
                    }
                }
            }
        };
        msgcenterLv.setAdapter(adapter);
        presenter = new MsgPresenterImpl(MessageCategoryActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getMsgList(TAG_LOG);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getMsgList(TAG_LOG);
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
        presenter.refreshMsgList(TAG_LOG);
    }

    @Override
    public void showMsgList(final List<MessageInfo> infos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false, null);
                if (infos.size()>0){
                    infoList.addAll(infos);
                    adapter.notifyDataSetChanged();
                }else {
                    toggleShowEmpty(true, "", R.mipmap.message_no_data, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }

            }
        });
    }

    @Override
    public void showRefreshMsgList(final List<MessageInfo> infos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                headerLayout.stopRefresh();
                infoList.clear();
                infoList.addAll(infos);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void requestMsgError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(mContext, msg);
            }
        });
    }

    @Override
    public void refreshMsgError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(mContext, msg);
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
    public void showEmpty(String msg, int imgId) {
    }

    @Override
    public void showException(String msg) {

    }

    @Override
    public void showNetError() {

    }
}
