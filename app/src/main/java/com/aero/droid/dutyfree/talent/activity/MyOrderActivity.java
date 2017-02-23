package com.aero.droid.dutyfree.talent.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.FlightInfo;
import com.aero.droid.dutyfree.talent.bean.OrderInfo;
import com.aero.droid.dutyfree.talent.bean.OrderListInfo;
import com.aero.droid.dutyfree.talent.presenter.MyOrderPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.MyOrderPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.MyOrderView;
import com.aero.droid.dutyfree.talent.widgets.ListViewForScrollView;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenu;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenuCreator;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenuItem;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenuListView;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 我的订单页面
 */
public class MyOrderActivity extends BaseFragmentActivity implements MyOrderView{
    @Bind(R.id.my_order_listview)
    ListViewForScrollView myOrderLv;
    @Bind(R.id.my_order_layout)
    RGPullRefreshLayout myOrderLayout;

    /***********************************/
    private MyOrderPresenter presenter;
    private JavaBeanBaseAdapter<OrderListInfo> adapter;
    private List<OrderListInfo> orderListInfos = new ArrayList<>();
    private int orderListPositon, orderPositon;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_myorder;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return myOrderLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        initTitle(getResources().getString(R.string.my_order));
        adapter = new JavaBeanBaseAdapter<OrderListInfo>(mContext, R.layout.item_my_order, orderListInfos) {
            @Override
            protected void bindView(final int position, ViewHolder holder, OrderListInfo info) {
                final FlightInfo flightInfo = info.getFlightInfo();
                final List<OrderInfo> infoList = info.getOrderInfoList();
                //上面竖线
                ImageView topLine = holder.getView(R.id.item_order_list_top_line);
                //下面竖线
                ImageView bottomLine = holder.getView(R.id.item_order_list_bottom_line);
                //钟表图片
                ImageView timeIv = holder.getView(R.id.item_order_list_time_iv);
                if (position == 0) {
                    topLine.setVisibility(View.INVISIBLE);
                } else {
                    topLine.setVisibility(View.VISIBLE);
                }
                //起飞时间
                TextView flightStartTime = holder.getView(R.id.item_order_list_time);
                flightStartTime.setText(flightInfo.getDepartDate());
                //航班号
                TextView flightNo = holder.getView(R.id.item_order_list_flight_no);
                flightNo.setText(flightInfo.getFlightNo());
                //起飞 到达布局
                LinearLayout layout = holder.getView(R.id.order_list_airport_layout);
                String twoCode = flightInfo.getFlightNo().substring(0, 2);
                if (!TextUtils.isEmpty(twoCode) && !twoCode.equals("CA")){
                    layout.setVisibility(View.INVISIBLE);
                }else {
                    layout.setVisibility(View.VISIBLE);
                }
                //起飞机场
                TextView startAirport = holder.getView(R.id.order_list_airport_startcity);
                startAirport.setText(flightInfo.getDepart());
                //到达机场
                TextView arriveAirport = holder.getView(R.id.order_list_airport_endcity);
                arriveAirport.setText(flightInfo.getArrive());
                //订单列表
                SwipeMenuListView listView = holder.getView(R.id.order_list_order_listview);
                JavaBeanBaseAdapter<OrderInfo> orderAdapter = new JavaBeanBaseAdapter<OrderInfo>(mContext, R.layout.item_order, infoList) {
                    @Override
                    protected void bindView(int orderPosition, ViewHolder holder, OrderInfo order) {
                        //订单号
                        TextView orderNo = holder.getView(R.id.item_order_no);
                        orderNo.setText(order.getOrderNo());
                        //订单金额
                        TextView orderPrice = holder.getView(R.id.item_order_price);
                        orderPrice.setText("＄" + order.getOrderPrice_dollar());
                        //订单状态
                        TextView orderStatus = holder.getView(R.id.item_order_status);
                        if ("0".equals(order.getStatus())) {
                            orderStatus.setText(getResources().getString(R.string.order_status_done));
                            orderStatus.setTextColor(getResources().getColor(R.color.black_text));
                        } else if ("1".equals(order.getStatus())) {
                            orderStatus.setText(getResources().getString(R.string.order_status_ing));
                            orderStatus.setTextColor(getResources().getColor(R.color.purple));
                        }
                        orderStatus.setText(order.getStatusMsg());
                    }
                };
                listView.setAdapter(orderAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("orderId", infoList.get(position).getOrderId());
                        bundle.putString("orderNo", infoList.get(position).getOrderNo());
                        bundle.putString("arrive", flightInfo.getArrive());
                        bundle.putString("depart", flightInfo.getDepart());
                        bundle.putString("departDate", flightInfo.getDepartDate());
                        bundle.putString("flightNo", flightInfo.getFlightNo());
                        readyGo(OrderDetailActivity.class,bundle);
                    }
                });

                SwipeMenuCreator creator = new SwipeMenuCreator() {

                    @Override
                    public void create(SwipeMenu menu) {
                        // create "delete" item
                        SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
                        // set item background
                        deleteItem.setBackground(new ColorDrawable(Color.rgb(255, 51, 52)));
                        // set item width
                        deleteItem.setWidth(UiUtil.dip2px(mContext, 90));
                        // set a icon
                        deleteItem.setIcon(R.mipmap.text_delete);
                        // add to menu
                        menu.addMenuItem(deleteItem);
                        // create "cancel" item
//                        SwipeMenuItem cancelItem = new SwipeMenuItem(mContext);
//                        // set item background
//                        cancelItem.setBackground(new ColorDrawable(Color.rgb(104, 59, 164)));
//                        // set item width
//                        cancelItem.setWidth(UiUtil.dip2px(mContext, 90));
//                        // set a icon
//                        cancelItem.setIcon(R.mipmap.edit_clear);
//                        // add to menu
//                        menu.addMenuItem(cancelItem);
                    }
                };
                // set creator
                listView.setMenuCreator(creator);

                // step 2. listener item click event
                listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(int menuPosition, SwipeMenu menu, int index) {
                        switch (index) {
                            case 0:
                                orderListPositon = position;
                                orderPositon = menuPosition;
                                presenter.clickDeleteOrder(TAG_LOG, infoList.get(menuPosition).getOrderId());
                                break;
                            case 1:
//                                ToastUtil.showShortToast(mContext, "cancel");
//                        presenter.clickDeleteOrder(TAG_LOG,"id");
                                break;
                        }
                        return false;
                    }
                });
            }
        };
        myOrderLv.setAdapter(adapter);
        presenter = new MyOrderPresenterImpl(MyOrderActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getOrderList(TAG_LOG);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getOrderList(TAG_LOG);
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
    public void showOrderList(final List<OrderListInfo> infoList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false, null);
                orderListInfos.addAll(infoList);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void deleteOrderSuccess(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                orderListInfos.get(orderListPositon).getOrderInfoList().remove(orderPositon);
                if (orderListInfos.get(orderListPositon).getOrderInfoList().size()<1)
                    orderListInfos.remove(orderListPositon);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void cancelOrderSuccess(String msg) {

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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowEmpty(true, "", R.mipmap.shopbag_no_data, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        Bundle bundle = new Bundle();
                        bundle.putInt("jumpType",0);
                        readyGo(MainActivity.class,bundle);
                    }
                });
            }
        });

    }

    @Override
    public void showException(String msg) {

    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getOrderList(TAG_LOG);
            }
        });
    }

}
