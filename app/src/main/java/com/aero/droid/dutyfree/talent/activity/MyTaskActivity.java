package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.TaskInfo;
import com.aero.droid.dutyfree.talent.bean.TaskListInfo;
import com.aero.droid.dutyfree.talent.presenter.TaskPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.TaskPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.TaskView;
import com.aero.droid.dutyfree.talent.widgets.ListViewForScrollView;
import com.aero.droid.dutyfree.talent.widgets.TaskItemView;
import com.bumptech.glide.Glide;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/12/12
 * Desc : 我的特权任务页面
 */
public class MyTaskActivity extends BaseFragmentActivity implements TaskView, View.OnClickListener {
    @Bind(R.id.title_left_layout)
    RelativeLayout titleLeftLayout;
    @Bind(R.id.title_right_layout)
    RelativeLayout titleRightLayout;
    @Bind(R.id.my_task_introduce)
    RelativeLayout taskIntroduceLv;
    @Bind(R.id.my_task_listview)
    PullableListView taskListview;
    @Bind(R.id.my_task_pull_layout)
    RGPullRefreshLayout taskPullLayout;
    @Bind(R.id.my_task_desc)
    TextView taskDescTv;

    /***********************************/
    private TaskPresenter presenter;
    private JavaBeanBaseAdapter<TaskListInfo> adapter;
    private int curPosition = -1;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_my_task;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return taskPullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        titleLeftLayout.setOnClickListener(this);
        titleRightLayout.setOnClickListener(this);
        taskIntroduceLv.setOnClickListener(this);
        adapter = new JavaBeanBaseAdapter<TaskListInfo>(mContext, R.layout.item_my_task) {
            @Override
            protected void bindView(final int position, ViewHolder holder, final TaskListInfo info) {
                //等级图标
                ImageView levelImg = holder.getView(R.id.item_task_level_img);
                int id = getResources().getIdentifier("task_" + (position + 1), "mipmap", mContext.getPackageName());
                Glide.with(mContext).load(id).into(levelImg);
                //特权名称
                TextView levelName = holder.getView(R.id.item_task_name);
                levelName.setText(info.getSpName());
                //等级状态(是否领取)
                final TextView statusTv = holder.getView(R.id.item_task_has);
                if ("0".equals(info.getStatus())) {//拥有特权
                    statusTv.setBackgroundResource(R.drawable.radius_solid_gray3);
                    statusTv.setText(getResources().getString(R.string.task_privilege_has));
                } else if ("1".equals(info.getStatus())) {//未拥有
                    statusTv.setBackgroundResource(R.drawable.radius_solid_purple);
                    statusTv.setText(getResources().getString(R.string.task_privilege_unhas));
                }
                statusTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("0".equals(info.getStatus())) {//已拥有
                            ToastUtil.showShortToast(mContext, getResources().getString(R.string.task_privilege_has));
                        } else if ("1".equals(info.getStatus())) {//未领取
                            if (isConditionFinish(info)){
                                curPosition = position;
                                presenter.getPrivilege(TAG_LOG, info.getId());
                            }else {
                                ToastUtil.showShortToast(mContext, getResources().getString(R.string.task_privilege_notify));
                            }
                        }
                    }
                });
                //等级标题1
                TextView title1 = holder.getView(R.id.item_task_title1);
                title1.setText(info.getSpDesc());
                //等级标题2
//                TextView title2 = holder.getView(R.id.item_task_title2);
//                title1.setText(info.getTitle());
                //任务 listView
                LinearLayout taskLaout = holder.getView(R.id.item_task_task);
                TaskItemView itemView = new TaskItemView(mContext);
                itemView.setLayoutData(info);
                taskLaout.removeAllViews();
                taskLaout.addView(itemView);
                /**
                 * 下面是当时因为数据不全造成我以为是listView复用没弄好的另一种解决方案，也是可行的
                 */
//                ListViewForScrollView taskListView = holder.getView(R.id.item_my_task_lv);
//                List<TaskInfo> taskInfos = info.getTaskInfoList();
//                JavaBeanBaseAdapter<TaskInfo> adapter = new JavaBeanBaseAdapter<TaskInfo>(mContext, R.layout.item_task_status, taskInfos) {
//                    @Override
//                    protected void bindView(int position, ViewHolder holder, TaskInfo taskInfo) {
//                        //任务完成标题 status 0 1
//                        TextView doneTitle = holder.getView(R.id.task_done_name);
//                        doneTitle.setText(taskInfo.getCondName());
//
//                        //任务未完成 status 2
//                        RelativeLayout doingLayout = holder.getView(R.id.item_task_doing_layout);
//                        TextView doingTitle = holder.getView(R.id.task_doing_name);
//                        doingTitle.setText(taskInfo.getCondName());
//                        //当前进度
//                        TextView curProgress = holder.getView(R.id.task_doing_cur_progress);
//                        curProgress.setText(taskInfo.getFinishValue());
//                        //当前进度
//                        TextView totalProgress = holder.getView(R.id.task_doing_total_progress);
//                        totalProgress.setText("/"+taskInfo.getCondValue());
//                        //去做任务
//                        TextView todo = holder.getView(R.id.task_doing_todo);
//                        todo.setText(taskInfo.getAcName());
//                        if ("0".equals(info.getStatus()) || "1".equals(info.getStatus())) {
//                            doneTitle.setVisibility(View.VISIBLE);
//                            doingLayout.setVisibility(View.GONE);
//                        } else if ("2".equals(info.getStatus())) {
//                            doneTitle.setVisibility(View.GONE);
//                            doingLayout.setVisibility(View.VISIBLE);
//                        }
//                    }
//                };
//                taskListView.setAdapter(adapter);
            }
        };
        taskListview.setAdapter(adapter);
        presenter = new TaskPresenterImpl(MyTaskActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getTaskList(TAG_LOG);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getTaskList(TAG_LOG);
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
    public void showTaskListData(final List<TaskListInfo> infoList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false, null);
                adapter.addAll(infoList);
            }
        });
    }

    @Override
    public void getPrivilegeSuccess(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.getItem(curPosition).setStatus("0");
                adapter.notifyDataSetChanged();
//                ToastUtil.showShortToast(mContext,msg);
            }
        });
    }

    @Override
    public void showTaskDesc(String desc) {
    }

    @Override
    public void requestError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(mContext, msg);
                toggleShowLoading(false, null);
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
                toggleShowLoading(true, null);
                presenter.getTaskList(TAG_LOG);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_layout:
                finish();
                break;
            case R.id.title_right_layout:
                Bundle bundle = new Bundle();
                bundle.putString("url", "http://www.aerotq.com/coogo/spmemo.html");
                readyGo(WebViewActivity.class, bundle);
                break;
            case R.id.my_task_introduce:
                readyGo(DiscountAeraActivity.class);
                break;
        }
    }

    private boolean isConditionFinish(TaskListInfo info){
        int done = 0; //完成 +0 没完成 +1
        for (TaskInfo taskInfo :info.getTaskInfoList()){
            if (taskInfo.getCondValue().equals(taskInfo.getFinishValue())){
                done = done + 0;
            }else {
                done = done + 1;
            }
        }
        if (0 == done){
            return true;
        }else {
            return false;
        }
    }
}
