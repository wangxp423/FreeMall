package com.aero.droid.dutyfree.talent.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.ComentsActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.ComentsInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.TimeFormatUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.glidetrans.GlideCircleTransform;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author : wangxp
 * Date : 2015/12/11
 * Desc : 商品评论(商品详情中间部分 包括后面的评论列表)
 */
public class ComentsLayout extends LinearLayout {
    private Context mContext;

    public ComentsLayout(Context context) {
        super(context);
        init(context);
    }

    public ComentsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ComentsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ComentsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setOrientation(VERTICAL);
    }

    public void setConments(final GoodsDetail detail) {
        View comentsView = View.inflate(mContext, R.layout.view_coments, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params.topMargin = UiUtil.dip2px(mContext,10);
        comentsView.setLayoutParams(params);
        final GoodComents coments = detail.getComents();
        //心数
        TextView goodDesc = (TextView) comentsView.findViewById(R.id.coments_star_num);
        String num = coments.getStarQty();
        goodDesc.setText(num);
        //几个心
        LinearLayout goodStandard = (LinearLayout) comentsView.findViewById(R.id.coments_star);
        for (int i = 0; i < Integer.parseInt(num); i++) {
            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            imgParams.setMargins(5,5,5,5);
            ImageView image = new ImageView(mContext);
            image.setLayoutParams(imgParams);
            image.setImageResource(R.mipmap.coments_star_normal);
            goodStandard.addView(image);
        }
        //好评率
        TextView niceDesc = (TextView) comentsView.findViewById(R.id.coments_nice_desc);
        niceDesc.setText(coments.getNiceDesc());
        //评论数
        TextView comensNum = (TextView) comentsView.findViewById(R.id.coments_num);
        comensNum.setText(coments.getSumQty()+"个评论");
        comensNum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("goodId",detail.getId());
                bundle.putString("goodName",detail.getGoodsName());
                bundle.putString("goodImg",detail.getGoodsImg());
                intent.setClass(mContext, ComentsActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        //横线
        ImageView lineImg = (ImageView) comentsView.findViewById(R.id.coments_line);
        //评论列表
        ListViewForScrollView listView = (ListViewForScrollView) comentsView.findViewById(R.id.coments_listview);
        JavaBeanBaseAdapter<ComentsInfo> adapter = new JavaBeanBaseAdapter<ComentsInfo>(mContext,R.layout.item_coments,coments.getComentsList()) {
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
                if (coments.getComentsList().size()>0 && position == coments.getComentsList().size()-1){
                    bottomLine.setVisibility(GONE);
                }else {
                    bottomLine.setVisibility(VISIBLE);
                }
            }
        };
        listView.setAdapter(adapter);
        addView(comentsView);
    }
}
