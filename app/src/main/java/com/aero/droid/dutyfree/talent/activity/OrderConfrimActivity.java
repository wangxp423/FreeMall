package com.aero.droid.dutyfree.talent.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.AirportCompany;
import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.bean.FlightInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.User;
import com.aero.droid.dutyfree.talent.listener.ChoiseAirportDateListener;
import com.aero.droid.dutyfree.talent.presenter.impl.OrderConfrimPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.PopWindowUtil;
import com.aero.droid.dutyfree.talent.util.StringUtils;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.OrderConfrimView;
import com.aero.droid.dutyfree.talent.widgets.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/12/15
 * Desc : 订单确认页面
 */
public class OrderConfrimActivity extends BaseFragmentActivity implements OrderConfrimView, View.OnClickListener {
    @Bind(R.id.order_confrim_agreement_check)
    CheckBox orderAgreementCb;
    @Bind(R.id.order_confrim_agreement_tv)
    TextView orderAgreementTv;
    @Bind(R.id.order_confrim_edit_name)
    EditText orderNameEd;
    @Bind(R.id.order_confrim_edit_phone)
    EditText orderPhoneEd;
    @Bind(R.id.order_confrim_check_airport)
    TextView orderCheckAirportTv;
    @Bind(R.id.order_confrim_choise_airport)
    TextView orderChoiseAirportTv;
    @Bind(R.id.order_confrim_edit_airport)
    EditText orderAirportEd;
    @Bind(R.id.order_confrim_edit_date)
    TextView orderDateEd;
    @Bind(R.id.order_confrim_check_airport_result_tv)
    TextView orderCheckAirportResultTv;
    @Bind(R.id.order_confrim_check_airport_result_layout)
    LinearLayout orderCheckAirportResultLayout;
    @Bind(R.id.order_confrim_goods_listview)
    ListViewForScrollView orderConfrimGoodsListview;
    @Bind(R.id.order_confrim_use_coupons)
    TextView orderUseCoupons;
    @Bind(R.id.choice_coupon_layout)
    RelativeLayout choiceCouponLayout;
    @Bind(R.id.order_confrim_total_price_tv)
    TextView orderTotalPriceTv;
    @Bind(R.id.order_confrim_is_coupon)
    TextView orderIsCoupon;
    @Bind(R.id.order_confrim_scrllview_layout)
    PullableScrollView orderScrllviewLayout;
    @Bind(R.id.order_confrim_commit)
    ImageView orderCommit;
    @Bind(R.id.order_confrim_pull_layout)
    RGPullRefreshLayout orderPullLayout;

    /******************************************/
    private OrderConfrimPresenterImpl presenter;
    private List<GoodsInfo> orderGoodsList;
    private List<AirportCompany> companyList;
    private List<CouponsInfo> couponList;
    private JavaBeanBaseAdapter<GoodsInfo> goodsAdapter;
    private String airportTwoCode = "CA";//航班二字码
    private int totalPrice, noCouponPrice;
    private String couponId;

    @Override
    protected void getBundleExtras(Bundle extras) {
        orderGoodsList = extras.getParcelableArrayList("orderGoodsList");
        totalPrice = extras.getInt("totalPrice");
        noCouponPrice = totalPrice;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_confrim;
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
        initTitle(getResources().getString(R.string.order_confirm));
        orderAgreementTv.setOnClickListener(this);
        orderCheckAirportTv.setOnClickListener(this);
        orderChoiseAirportTv.setOnClickListener(this);
        orderDateEd.setOnClickListener(this);
        orderCommit.setOnClickListener(this);
        choiceCouponLayout.setOnClickListener(this);
        goodsAdapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_order_confirm, orderGoodsList) {
            @Override
            protected void bindView(int position, ViewHolder holder, GoodsInfo info) {
                //商品图片
                ImageView goodImg = holder.getView(R.id.iv_order_confirm_logo);
                Glide.with(mContext).load(info.getGoodsImg()).into(goodImg);
                //商品名称
                TextView goodName = holder.getView(R.id.tv_order_confirm_product);
                goodName.setText(info.getGoodsName());
                //商品价格
                TextView goodPrice = holder.getView(R.id.tv_order_confirm_money);
                goodPrice.setText(info.getPrice_app_dollar());
                //商品数量
                TextView goodNum = holder.getView(R.id.tv_order_confirm_num);
                goodNum.setText("×" + (TextUtils.isEmpty(info.getQuantity()) ? "1" : info.getQuantity()));
            }
        };
        orderConfrimGoodsListview.setAdapter(goodsAdapter);
        presenter = new OrderConfrimPresenterImpl(OrderConfrimActivity.this, this);
        presenter.getAirportList(TAG_LOG);
        presenter.getCouponsList(TAG_LOG, orderGoodsList);

        orderTotalPriceTv.setText("＄" + String.valueOf(totalPrice));

        //获取用户信息  默认 登陆以后 姓名和手机是填写状态
        User user = UserUtil.getUserInfo(mContext);
        if (null != user){
            orderNameEd.setText(user.getName());
            orderPhoneEd.setText(user.getPhone());
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
    public void showAirportListData(List<AirportCompany> companyList) {
        this.companyList = companyList;
    }

    @Override
    public void showCouponsListData(final List<CouponsInfo> couponsInfoList) {
        couponList = couponsInfoList;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (couponsInfoList.size() < 1)
                    choiceCouponLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void confrimOrderSuccess(String msg) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("orderGoodsList", (ArrayList<? extends Parcelable>) orderGoodsList);
        bundle.putInt("totalPrice", totalPrice);
        bundle.putInt("noCouponPrice", noCouponPrice);
        readyGo(OrderResultActivity.class, bundle);
        finish();
    }

    @Override
    public void checkCAAirportSuccess(FlightInfo info) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("airportInfo", info);
        readyGoForResult(AirportInfoActivity.class, Constants.EVENT_ADD_DATA, bundle);
    }

    @Override
    public void checkHUAirportSuccess(FlightInfo info) {
        orderCheckAirportResultLayout.setVisibility(View.VISIBLE);
        orderCheckAirportResultTv.setText(getResources().getString(R.string.order_confirm_airport_check_success));
    }

    @Override
    public void checkHUAirportFail(String msg) {
        orderCheckAirportResultLayout.setVisibility(View.VISIBLE);
        orderCheckAirportResultTv.setText(getResources().getString(R.string.order_confirm_airport_check_fail));
    }

    @Override
    public void requestError(final String msg) {
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
    public void showEmpty(String msg,int imgId) {

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
            case R.id.order_confrim_agreement_tv:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("url", "http://www.aerotq.com/coogo/purinfo.html");
                intent.setClass(mContext, WebViewActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                break;
            case R.id.order_confrim_choise_airport:
                if (companyList.size() > 0) {
                    showPopWin();
                } else {
                    presenter.getAirportList(TAG_LOG);
                    ToastUtil.showShortToast(mContext, getResources().getString(R.string.order_confrim_get_airportlist_error));
                }

                break;
            case R.id.order_confrim_commit:
                if (presenter.checkUserData(orderNameEd.getText().toString(), orderPhoneEd.getText().toString()) && presenter.checkAirportData(airportTwoCode, orderDateEd.getText().toString(), orderAirportEd.getText().toString()))
                    presenter.commitOrder(TAG_LOG, orderPhoneEd.getText().toString(), orderDateEd.getText().toString(), airportTwoCode+orderAirportEd.getText().toString(), String.valueOf(totalPrice), orderNameEd.getText().toString(), couponId, orderGoodsList);
                break;
            case R.id.choice_coupon_layout:
                showCouponsPopWin();
                break;
            case R.id.order_confrim_edit_date:
                PopWindowUtil.showAirportDatePop(mContext, new ChoiseAirportDateListener() {
                    @Override
                    public void date(String date) {
                        orderDateEd.setText(date);
                    }
                });
                break;
            case R.id.order_confrim_check_airport:
                if (presenter.checkAirportData(airportTwoCode, orderDateEd.getText().toString(), orderAirportEd.getText().toString())) {
                    if (airportTwoCode.equals("CA")) {
                        presenter.checkCAAirport(TAG_LOG, airportTwoCode, orderDateEd.getText().toString(), orderAirportEd.getText().toString());
                    } else if (airportTwoCode.equals("HU")) {
                        presenter.checkHUAirport(TAG_LOG, airportTwoCode, orderDateEd.getText().toString(), orderAirportEd.getText().toString());
                    }else {
                        orderCheckAirportResultLayout.setVisibility(View.VISIBLE);
                        orderCheckAirportResultTv.setText(getResources().getString(R.string.order_confirm_airport_check_success));
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.EVENT_ADD_DATA:
                if (resultCode == RESULT_OK) {
                    orderCheckAirportResultLayout.setVisibility(View.VISIBLE);
                    orderCheckAirportResultTv.setText(getResources().getString(R.string.order_confirm_airport_check_success));
                }
                break;
        }

    }

    private PopupWindow airportCompaynWindow;
    private ListView airportCompaynLv;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void showPopWin() {
        if (airportCompaynWindow == null) {
            View layout = LayoutInflater.from(mContext).inflate(
                    R.layout.pop_airport_company_layout, null);
            airportCompaynWindow = new PopupWindow(layout, orderChoiseAirportTv.getWidth(), ViewPager.LayoutParams.WRAP_CONTENT);
            // 给popupWindow加进入动画
            airportCompaynWindow.setAnimationStyle(R.style.PopCategory);
            airportCompaynWindow.update();
            airportCompaynWindow.setOutsideTouchable(true);
            airportCompaynWindow.setFocusable(true);
            airportCompaynWindow.setBackgroundDrawable(new BitmapDrawable());
            airportCompaynLv = (ListView) layout.findViewById(R.id.pop_airport_company_lv);
            JavaBeanBaseAdapter<AirportCompany> adapter = new JavaBeanBaseAdapter<AirportCompany>(mContext, R.layout.item_airport_company, companyList) {
                @Override
                protected void bindView(int position, ViewHolder holder, AirportCompany object) {
                    TextView textView = holder.getView(R.id.airport_company_name);
                    textView.setText(object.getName());
                }
            };
            airportCompaynLv.setAdapter(adapter);
            airportCompaynLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    orderChoiseAirportTv.setText(companyList.get(position).getName());
                    airportTwoCode = companyList.get(position).getTwocode();
                    airportCompaynWindow.dismiss();
                }
            });
        }
        airportCompaynWindow.showAsDropDown(orderChoiseAirportTv);
//        categoryWindow.showAsDropDown(airportCompany, 0, 0, Gravity.CENTER);
    }


    private PopupWindow couponWindow;
    private ListView couponLv;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void showCouponsPopWin() {  //可使用优惠券list
        if (couponWindow == null) {
            View layout = LayoutInflater.from(mContext).inflate(
                    R.layout.pop_choice_coupon, null);
            couponWindow = new PopupWindow(layout,
                    ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
            couponWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams params = ((Activity) mContext).getWindow().getAttributes();
                    params.alpha = 1.0f;
                    ((Activity) mContext).getWindow().setAttributes(params);
                }
            });
            // 给popupWindow加进入动画
            couponWindow.setAnimationStyle(R.style.share_pop_style);
            couponWindow.update();
            couponWindow.setOutsideTouchable(true);
            couponWindow.setFocusable(true);
            couponWindow.setBackgroundDrawable(new BitmapDrawable());
            couponLv = (ListView) layout.findViewById(R.id.pop_conpon_lv);
            layout.findViewById(R.id.choice_title_left).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    couponWindow.dismiss();
                }
            });
            JavaBeanBaseAdapter<CouponsInfo> adapter = new JavaBeanBaseAdapter<CouponsInfo>(mContext, R.layout.item_airport_company, couponList) {
                @Override
                protected void bindView(int position, ViewHolder holder, CouponsInfo object) {
                    TextView textView = holder.getView(R.id.airport_company_name);
                    textView.setText(object.getCouponName());
                }
            };
            couponLv.setAdapter(adapter);
            couponLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    CouponsInfo info = couponList.get(position);
                    if ("1".equals(info.getType())) {
                        totalPrice = noCouponPrice - StringUtils.parseInt(info.getCouponValue());
                    } else if ("2".equals(info.getType())) {
                        if (TextUtils.isEmpty(info.getMaxMoney())) {
                            totalPrice = (int)(noCouponPrice * Float.parseFloat(info.getCouponValue()));
                        } else {
                            if (totalPrice > StringUtils.parseInt(info.getMaxMoney())) {
                                totalPrice = (noCouponPrice - StringUtils.parseInt(info.getMaxMoney())) + (int)(StringUtils.parseInt(info.getMaxMoney()) * Float.parseFloat(info.getCouponValue()));
                            } else {
                                totalPrice = (int)(noCouponPrice * Float.parseFloat(info.getCouponValue()));
                            }
                        }
                    } else {
                        totalPrice = noCouponPrice;
                    }
                    orderUseCoupons.setText(info.getSubTitle());
                    orderTotalPriceTv.setText("＄"+totalPrice);
                    couponId = info.getCouponId();
                    orderIsCoupon.setVisibility(View.VISIBLE);
                    orderIsCoupon.setText(getResources().getString(R.string.order_confrim_price_coupon)+(noCouponPrice - totalPrice));
                    if (couponWindow != null)
                        couponWindow.dismiss();
                }
            });
        }
        couponWindow.showAtLocation(choiceCouponLayout, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = 0.7f;
        this.getWindow().setAttributes(params);
    }
}
