package com.aero.droid.dutyfree.talent.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.GoodsDetailActivity;
import com.aero.droid.dutyfree.talent.activity.PosterGoodsActivity;
import com.aero.droid.dutyfree.talent.base.BaseFragment;
import com.aero.droid.dutyfree.talent.bean.ActiveInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.util.DownTimeUtil;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.widgets.DownTimerView;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/12/15
 * Desc : 海报页 fragment
 */
public class PosterFragment extends BaseFragment {
    @Bind(R.id.poster_good_img)
    ImageView posterGoodImgIv;
    @Bind(R.id.poster_good_brand_logo)
    ImageView posterGoodLogoIv;
    @Bind(R.id.poster_good_name)
    TextView posterGoodNameTv;
    @Bind(R.id.poster_good_price)
    TextView posterGoodPriceTv;
    @Bind(R.id.poster_good_active_name)
    TextView posterGoodActiveNameTv;
    @Bind(R.id.poster_good_coments_num)
    TextView posterGoodComentsNumTv;
    @Bind(R.id.poster_good_favo_num)
    TextView posterGoodFavoNumTv;
    @Bind(R.id.poster_good_active_time)
    TextView posterGoodActiveTimeTv;
    @Bind(R.id.poster_good_active_downtime)
    DownTimerView posterGoodDowntimeTv;
    @Bind(R.id.poster_good_active_downtime_layout)
    RelativeLayout posterGoodDowntimeLayout;
    @Bind(R.id.poster_good_desc)
    TextView posterGoodDescTv;

    /**************************************/
    private GoodsDetail goodDetail;
    private ActiveInfo activeInfo;
    private PosterGoodsActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (PosterGoodsActivity) activity;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

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
        goodDetail = getArguments().getParcelable("goodDetail");
        activeInfo = mActivity.getAcitiveInfo();
        setCurShow(goodDetail);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fm_poster;
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

    /**
     * 设置 当前显示的商品
     *
     * @param info
     */
    private void setCurShow(final GoodsDetail info) {
        //商品图片
        Glide.with(getActivity()).load(info.getGoodsImg()).centerCrop().into(posterGoodImgIv);
        posterGoodImgIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("goodId",info.getId());
                bundle.putString("srcType", TextUtils.isEmpty(info.getSrcType()) ? "0" : info.getSrcType());
                bundle.putString("srcId", TextUtils.isEmpty(info.getSrcId()) ? "0" : info.getSrcId());
                readyGo(GoodsDetailActivity.class, bundle);
            }
        });
        //商品logo
        Glide.with(getActivity()).load(info.getMarkLogo()).into(posterGoodLogoIv);
        //商品名称
        posterGoodNameTv.setText(info.getGoodsName());
        //商品价格
        posterGoodPriceTv.setText(info.getPrice_app_dollar());
        //收藏数
        posterGoodFavoNumTv.setText(info.getFavoQty());
        //评论数
        posterGoodComentsNumTv.setText(info.getFavoQty());
        //活动名字
        posterGoodActiveNameTv.setText(activeInfo.getActiveName());
        //活动倒计时
        if (!TextUtils.isEmpty(activeInfo.getStartTime()) && !TextUtils.isEmpty(activeInfo.getEndTime())) {
            long startTime = 0;
            long endTime = 0;
            //如果时间格式是  2015-12-28 19:57:56 转义一下
            if (activeInfo.getStartTime().contains("-")||activeInfo.getEndTime().contains("-")){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    startTime = format.parse(activeInfo.getStartTime()).getTime();
                    endTime = format.parse(activeInfo.getEndTime()).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else {
                startTime = Long.parseLong(activeInfo.getStartTime())*1000;
                endTime = Long.parseLong(activeInfo.getEndTime())*1000;
            }
            long curTime = System.currentTimeMillis();

            if (startTime > curTime) {
                posterGoodActiveTimeTv.setText(getResources().getString(R.string.active_downtime_start));
                DownTimeUtil timeUtil = new DownTimeUtil(startTime/1000);
                posterGoodDowntimeTv.setTime(timeUtil.getDay(), timeUtil.getHour(), timeUtil.getMin(), timeUtil.getSec());
            } else if (startTime < curTime && curTime < endTime) {
                posterGoodActiveTimeTv.setText(getResources().getString(R.string.active_downtime_text));
                DownTimeUtil timeUtil = new DownTimeUtil(endTime/1000);
                posterGoodDowntimeTv.setTime(timeUtil.getDay(), timeUtil.getHour(), timeUtil.getMin(), timeUtil.getSec());
            } else {
                posterGoodActiveTimeTv.setText(getResources().getString(R.string.active_downtime_end));
                posterGoodDowntimeTv.setTime(0, 0, 0, 1);
            }
            posterGoodDowntimeTv.start();
        } else {
            posterGoodDowntimeLayout.setVisibility(View.GONE);
        }
        //商品描述
        posterGoodDescTv.setText(info.getGoodsDes());
    }
}
