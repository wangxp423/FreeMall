package com.aero.droid.dutyfree.talent.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.OrderDetail;
import com.aero.droid.dutyfree.talent.presenter.OrderDetailPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.BrandGoodsPresenterImpl;
import com.aero.droid.dutyfree.talent.presenter.impl.OrderDetailPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.PopWindowUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.OrderDetailView;
import com.aero.droid.dutyfree.talent.widgets.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableScrollView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/12/16
 * Desc : 订单详情
 */
public class OrderDetailActivity extends BaseFragmentActivity implements OrderDetailView, View.OnClickListener {
    @Bind(R.id.order_detail_order_num)
    TextView orderNumTv;
    @Bind(R.id.order_detail_order_total_price)
    TextView orderTotalPriceTv;
    @Bind(R.id.order_detail_line_left)
    View lineLeftIv;
    @Bind(R.id.order_detail_line_right)
    View lineRightIv;
    @Bind(R.id.order_detail_line_layout)
    LinearLayout orderDetailLineLayout;
    @Bind(R.id.order_detail_step1)
    TextView orderStep1Tv;
    @Bind(R.id.order_detail_step2)
    TextView orderStep2Tv;
    @Bind(R.id.order_detail_step3)
    TextView orderStep3Tv;
    @Bind(R.id.order_detail_airport_info)
    TextView orderAirportInfoTv;
    @Bind(R.id.order_detail_goods_listview)
    ListViewForScrollView orderGoodsListview;
    @Bind(R.id.order_detail_coments_notify)
    TextView orderComentsNotifyTv;
    @Bind(R.id.order_detail_airport)
    TextView orderDetailAirport;
    @Bind(R.id.order_detail_airport_date_tv)
    TextView orderDetailAirportDateTv;
    @Bind(R.id.order_detail_airport_date)
    TextView orderAirportDateTv;
    @Bind(R.id.order_detail_airport_startcity)
    TextView orderStartcityTv;
    @Bind(R.id.order_detail_airport_endcity)
    TextView orderEndcityTv;
    @Bind(R.id.order_detail_airport_layout)
    LinearLayout orderAirportLayout;
    @Bind(R.id.order_detail_airport_user)
    TextView orderDetailAirportUser;
    @Bind(R.id.order_detail_airport_name_tv)
    TextView orderNameTv;
    @Bind(R.id.order_detail_airport_phone_tv)
    TextView orderPhoneTv;
    @Bind(R.id.order_detail_scrollview)
    PullableScrollView orderDetailScrollview;
    @Bind(R.id.order_detail_pull_layout)
    RGPullRefreshLayout orderDetailPullLayout;

    /***********************************************/
    private OrderDetailPresenter presenter;
    private JavaBeanBaseAdapter<GoodsInfo> adapter;
    private String orderId;//订单Id
    private String orderNo, arrive, depart, departDate, flightNo, twoCode;
    private OrderDetail orderDetail;

    @Override
    protected void getBundleExtras(Bundle extras) {
        orderId = extras.getString("orderId");
        orderNo = extras.getString("orderNo");
        arrive = extras.getString("arrive");
        depart = extras.getString("depart");
        departDate = extras.getString("departDate");
        flightNo = extras.getString("flightNo");
        twoCode = flightNo.substring(0, 2);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return orderDetailPullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        initTitle(getResources().getString(R.string.order_detail));
        if (!TextUtils.isEmpty(twoCode) && !twoCode.equals("CA")) {
            orderAirportLayout.setVisibility(View.GONE);
            orderStep1Tv.setText(getResources().getString(R.string.order_detail_other_step1));
            orderStep2Tv.setText(getResources().getString(R.string.order_detail_other_step2));
            orderStep3Tv.setText(getResources().getString(R.string.order_detail_other_step3));
        }
        orderAirportDateTv.setText(departDate);
        orderStartcityTv.setText(depart);
        orderEndcityTv.setText(arrive);
        orderTotalPriceTv.setOnClickListener(this);
        orderStep1Tv.setOnClickListener(this);
        orderStep2Tv.setOnClickListener(this);
        orderStep3Tv.setOnClickListener(this);
        orderAirportInfoTv.setOnClickListener(this);
        orderComentsNotifyTv.setOnClickListener(this);
        Drawable drawable = orderStep1Tv.getCompoundDrawables()[1];
        int height = drawable.getMinimumHeight();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, UiUtil.dip2px(mContext, 1));
        params.topMargin = height / 2;
        params.weight = 1;
        params.leftMargin = height/2;
        params.rightMargin = height/2;
        lineLeftIv.setLayoutParams(params);
        lineRightIv.setLayoutParams(params);
        SpannableString ss = new SpannableString(getResources().getString(R.string.order_detail_coments_notify));
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#8D69B9")), 31, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new UnderlineSpan(), 31, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        orderComentsNotifyTv.setText(ss);
        adapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_order_detail_good) {
            @Override
            protected void bindView(int position, ViewHolder holder, final GoodsInfo info) {
                //商品图片
                ImageView goodImg = holder.getView(R.id.order_detail_good_img);
                Glide.with(mContext).load(info.getGoodsImg()).centerCrop().into(goodImg);
                //商品名称
                TextView goodName = holder.getView(R.id.order_detail_good_name);
                goodName.setText(info.getGoodsName());
                //商品数量
                TextView goodNum = holder.getView(R.id.order_detail_good_num);
                goodNum.setText(info.getQuantity());
                //状态 是否评价过
                TextView goodComents = holder.getView(R.id.order_detail_good_coments);
                if ("0".equals("")) {
                    Drawable drawable1 = getResources().getDrawable(R.drawable.order_detail_coments_selector);
                    drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                    goodComents.setCompoundDrawables(drawable1, null, null, null);
                    goodComents.setText(getResources().getString(R.string.order_detail_to_coments));
                } else if ("1".equals("")) {
                    goodComents.setText(getResources().getString(R.string.order_detail_again_coments));
                }
                goodComents.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("goodId", info.getId());
                        bundle.putString("goodName", info.getGoodsName());
                        bundle.putString("goodImg", info.getGoodsImg());
                        readyGo(WriteComentsActivity.class, bundle);
                    }
                });

            }
        };
        orderGoodsListview.setAdapter(adapter);

        presenter = new OrderDetailPresenterImpl(OrderDetailActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getOrderDetail(TAG_LOG, orderId);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getOrderDetail(TAG_LOG, orderId);
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
    public void showGoodsListData(final OrderDetail detail) {
        orderDetail = detail;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false, null);
                orderNumTv.setText("订单号" + orderNo);
                orderPhoneTv.setText(detail.getPhone());
                orderNameTv.setText(detail.getMemberName());
                orderAirportDateTv.setText(detail.getTakeoffDate());
                orderStartcityTv.setText(detail.getDepart());
                orderEndcityTv.setText(detail.getArrive());
                setOrderStatus(detail.getOrderStatus());
                for (int i = 0; i < detail.getStatusInfos().size(); i++) {
                    if (detail.getOrderStatus().equals(detail.getStatusInfos().get(i).getStatus()))
                        orderAirportInfoTv.setText(detail.getStatusInfos().get(i).getStatusMsg());
                }
                orderTotalPriceTv.setText("＄" + detail.getOrderMoney());
                adapter.addAll(detail.getGoodsInfoList());
            }
        });

    }

    @Override
    public void requestError(String msg) {

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_detail_order_total_price:
                PopWindowUtil.showOrderDetailPricePop(mContext, orderDetail);
                break;
            case R.id.order_detail_step1:
            case R.id.order_detail_step2:
            case R.id.order_detail_step3:
            case R.id.order_detail_airport_info:
                PopWindowUtil.showOrderDetailStatusPop(mContext, orderDetail, mScreenWidth);
                break;
            case R.id.order_detail_coments_notify:
//                readyGo(DiscountAeraActivity.class);
                break;
        }
    }

    /**
     * 订单状态 对应的显示
     * @param status
     */
    private void setOrderStatus(String status) {
        Drawable step2Drawable, step3Drawable;
        if ("0".equals(status)) {
            lineLeftIv.setBackgroundResource(R.color.gray_line);
            lineRightIv.setBackgroundResource(R.color.gray_line);

        } else if ("1".equals(status)) {
            step2Drawable = getResources().getDrawable(R.drawable.order_detail_step2_selector);
            step2Drawable.setBounds(0, 0, step2Drawable.getMinimumWidth(), step2Drawable.getMinimumHeight());
            orderStep2Tv.setCompoundDrawables(null, step2Drawable, null, null);
            lineLeftIv.setBackgroundResource(R.color.purple);
            lineRightIv.setBackgroundResource(R.color.gray_line);
        } else if ("2".equals(status)) {
            step2Drawable = getResources().getDrawable(R.drawable.order_detail_step2_selector);
            step2Drawable.setBounds(0, 0, step2Drawable.getMinimumWidth(), step2Drawable.getMinimumHeight());
            orderStep2Tv.setCompoundDrawables(null, step2Drawable, null, null);

            step3Drawable = getResources().getDrawable(R.drawable.order_detail_step3_selector);
            step3Drawable.setBounds(0, 0, step3Drawable.getMinimumWidth(), step3Drawable.getMinimumHeight());
            orderStep3Tv.setCompoundDrawables(null, step3Drawable, null, null);

            lineLeftIv.setBackgroundResource(R.color.purple);
            lineRightIv.setBackgroundResource(R.color.purple);
        }
    }
}
