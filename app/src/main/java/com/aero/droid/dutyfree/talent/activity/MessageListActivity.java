package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.bean.MessageInfo;
import com.aero.droid.dutyfree.talent.util.ActivityJumpUtil;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.TimeFormatUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableListView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : rongcl
 * Date : 2015/12/15
 * Desc : 消息列表
 */
public class MessageListActivity extends BaseFragmentActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    @Bind(R.id.message_list_lv)
    PullableListView messageListLv;
    @Bind(R.id.message_list_pull_layout)
    RGPullRefreshLayout messageListPullLayout;
    /***********************************************/
    private String msgTitle;
    private List<MessageInfo> msgList;
    private JavaBeanBaseAdapter<MessageInfo> adapter;

    @Override
    protected void getBundleExtras(Bundle extras) {
        msgList = extras.getParcelableArrayList("msgList");
        msgTitle = msgList.get(0).getMsgTitle();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_message_list;
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
        initTitle(msgTitle);
        messageListLv.setOnItemClickListener(this);
        adapter = new JavaBeanBaseAdapter<MessageInfo>(mContext, R.layout.item_message, msgList) {
            @Override
            protected void bindView(int position, ViewHolder holder, MessageInfo info) {
                //消息时间
                TextView msgTime = holder.getView(R.id.item_msg_time);
                msgTime.setText(TimeFormatUtil.getTimestamp(mContext, Long.parseLong(info.getMsgTime())));
                //消息标题
                TextView msgTitle = holder.getView(R.id.item_msg_title);
                msgTitle.setText(info.getMsgTitle());
                //消息内容
                TextView msgContent = holder.getView(R.id.item_msg_content);
                msgContent.setText(info.getMsgContent());

            }
        };
        messageListLv.setAdapter(adapter);
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
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.showShortToast(mContext,"根据全局跳转来跳转页面");
        MessageInfo msg = msgList.get(position);
        //全局跳转 实体类
        HandpickBanner banner = new HandpickBanner();
        banner.setAcParams(msg.getJumpParams());
        banner.setAcType(msg.getJumpType());
        ActivityJumpUtil.activityJump(mContext,banner);
        setMsgReaded(msg.getMsgId());
    }

    private void setMsgReaded(String msgId){
        HashMap<String,String> params = new HashMap<>();
        String memberId = UserUtil.getUserId(mContext);
        params.put("memberId", TextUtils.isEmpty(memberId)?"0":memberId);
        params.put("msgId",msgId);
        OkHttpRequest.okHttpGet(mContext, Url.MSGREADED, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON","设置消息已读成功 = " + data.toString());
            }

            @Override
            public void onRespError(String code, String msg) {

            }
        });
    }
}
