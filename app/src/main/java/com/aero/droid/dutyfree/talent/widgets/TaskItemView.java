package com.aero.droid.dutyfree.talent.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.bean.TaskInfo;
import com.aero.droid.dutyfree.talent.bean.TaskListInfo;
import com.aero.droid.dutyfree.talent.util.ActivityJumpUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;

import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/18
 * Desc : 特权任务页，任务显示
 */
public class TaskItemView extends LinearLayout {
    private Context mContext;

    public TaskItemView(Context context) {
        super(context);
        init(context);
    }

    public TaskItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TaskItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public TaskItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setOrientation(VERTICAL);
    }

    public void setLayoutData(TaskListInfo taskListInfo) {
        List<TaskInfo> taskInfoList = taskListInfo.getTaskInfoList();
        for (final TaskInfo info : taskInfoList) {
            if (info.getCondValue().equals(info.getFinishValue())) {
                View doneView = View.inflate(mContext, R.layout.view_task_done, null);
                //任务完成标题
                TextView doneTitle = (TextView) doneView.findViewById(R.id.task_done_name);
                doneTitle.setText(info.getCondName());
                addView(doneView);
            } else {
                View doingView = View.inflate(mContext, R.layout.view_task_doing, null);
                //进行任务标题
                TextView doingTitle = (TextView) doingView.findViewById(R.id.task_doing_name);
                doingTitle.setText(info.getCondName());
                //完成进度
                TextView curProgress = (TextView) doingView.findViewById(R.id.task_doing_cur_progress);
                curProgress.setText(info.getFinishValue());
                //总进度
                TextView totalProgress = (TextView) doingView.findViewById(R.id.task_doing_total_progress);
                totalProgress.setText("/"+info.getCondValue());
                //去做任务
                TextView todo = (TextView) doingView.findViewById(R.id.task_doing_todo);
                todo.setText(info.getAcName());
                todo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HandpickBanner banner = new HandpickBanner();
                        banner.setAcType(info.getAcType());
                        banner.setAcParams(info.getAcParams());
                        ActivityJumpUtil.activityJump(mContext,banner);
                    }
                });
                addView(doingView);
            }
        }
    }
}
