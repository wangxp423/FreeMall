package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/12/16
 * Desc : 提交订单结果页面
 */
public class OrderResultActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.order_result_goods)
    LinearLayout resultGoodsLayout;
    @Bind(R.id.order_reuslt_total)
    TextView reusltTotalTv;
    @Bind(R.id.order_reuslt_coupons)
    TextView reusltCouponsTv;
    @Bind(R.id.order_reuslt_all)
    TextView reusltAllTv;
    @Bind(R.id.order_result_to_home)
    TextView resultToHomeTv;
    @Bind(R.id.order_result_to_orderdetail)
    TextView resultToOrderdetailTv;

    /******************************************/
    private List<GoodsInfo> goodsInfoList;
    private int totalPirce;
    private int couponsPrice;

    @Override
    protected void getBundleExtras(Bundle extras) {
        goodsInfoList = extras.getParcelableArrayList("orderGoodsList");
        totalPirce = extras.getInt("totalPrice");
        couponsPrice = extras.getInt("noCouponPrice") - totalPirce;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_result;
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
        initTitle(getResources().getString(R.string.order_result));
        resultToHomeTv.setOnClickListener(this);
        resultToOrderdetailTv.setOnClickListener(this);
        if (null != goodsInfoList && goodsInfoList.size() > 0) {
            for (int i = 0; i < goodsInfoList.size(); i++) {
                GoodsInfo info = goodsInfoList.get(i);
                View view = View.inflate(mContext, R.layout.item_order_result, null);
                //商品名称
                TextView goodName = (TextView) view.findViewById(R.id.item_order_good_name);
                goodName.setText(info.getGoodsName());
                //商品数量
                TextView goodNum = (TextView) view.findViewById(R.id.item_order_good_num);
                goodNum.setText("×" + info.getQuantity());
                resultGoodsLayout.addView(view);
            }
        }
        reusltTotalTv.setText("＄" + totalPirce);
        reusltCouponsTv.setText("-" + couponsPrice);
        reusltAllTv.setText("＄" + totalPirce);

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
            case R.id.order_result_to_home:
                Bundle bundle = new Bundle();
                bundle.putInt("jumpType",0);
                readyGo(MainActivity.class,bundle);
                break;
            case R.id.order_result_to_orderdetail:
                readyGo(MyOrderActivity.class);
                break;
        }
    }
}
