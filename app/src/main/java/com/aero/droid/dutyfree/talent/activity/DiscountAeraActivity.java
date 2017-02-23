package com.aero.droid.dutyfree.talent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.DiscountInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.presenter.DiscountPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.DiscountPresenterImpl;
import com.aero.droid.dutyfree.talent.util.DownTimeUtil;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.StringUtils;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.DiscountView;
import com.aero.droid.dutyfree.talent.widgets.DownTimerView;
import com.aero.droid.dutyfree.talent.widgets.ExpandHeaderView;
import com.aero.droid.dutyfree.talent.widgets.GridViewForScrollView;
import com.bumptech.glide.Glide;
import com.ybao.pullrefreshview.layout.BaseHeaderView;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/12/10
 * Desc : 折扣专区页面
 */
public class DiscountAeraActivity extends BaseFragmentActivity implements DiscountView, BaseHeaderView.OnRefreshListener {
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.discount_pull_header)
    ExpandHeaderView discountPullHeader;
    @Bind(R.id.discount_list_view)
    PullableListView discountListView;
    @Bind(R.id.discount_pull_layout)
    RGPullRefreshLayout discountPullLayout;
    /***********************************/
    private DiscountPresenter presenter;
    private JavaBeanBaseAdapter<DiscountInfo> adapter;
    private List<DiscountInfo> discountList = new ArrayList<>();

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_discount_aera;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return discountPullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        titleLayout.setBackgroundResource(0);
        initTitleRightTv(getResources().getString(R.string.discount_area), getResources().getString(R.string.discount_instruction), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url","http://www.aerotq.com/coogo/spmemo.html");
                readyGo(WebViewActivity.class, bundle);
            }
        });
        discountPullHeader.setOnRefreshListener(this);
        adapter = new JavaBeanBaseAdapter<DiscountInfo>(mContext, R.layout.item_discount_aera, discountList) {
            @Override
            protected void bindView(int position, ViewHolder holder, final DiscountInfo info) {
                //折扣标题
                TextView title = holder.getView(R.id.item_discount_title);
                title.setText(info.getName()+" "+info.getDiscount() + "折");
                //活动倒计时
                DownTimerView timerView = holder.getView(R.id.item_discount_downtime);
                long curTime = System.currentTimeMillis();
                if (Long.parseLong(info.getBeginTime()) * 1000 > curTime) {
                    DownTimeUtil timeUtil = new DownTimeUtil(Long.parseLong(info.getBeginTime()));
                    timerView.setTime(timeUtil.getDay(), timeUtil.getHour(), timeUtil.getMin(), timeUtil.getSec());
                } else if (Long.parseLong(info.getBeginTime()) * 1000 < curTime && curTime < Long.parseLong(info.getEndTime()) * 1000) {
                    DownTimeUtil timeUtil = new DownTimeUtil(Long.parseLong(info.getEndTime()));
                    timerView.setTime(timeUtil.getDay(), timeUtil.getHour(), timeUtil.getMin(), timeUtil.getSec());
                } else {
                    timerView.setTime(0, 0, 0, 1);
                }
                timerView.start();
                //vip 等级以上购买
                TextView vip = holder.getView(R.id.item_discount_vip);
                vip.setText(info.getPrivDesc());
                //是否满足等级限制
                final TextView isCheck = holder.getView(R.id.item_discount_ischeck);
                if ("0".equals(info.getStatus())) {
                    isCheck.setText(getResources().getString(R.string.discount_check));
                } else if ("1".equals(info.getStatus())) {
                    isCheck.setText(getResources().getString(R.string.discount_uncheck));
                    isCheck.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            readyGo(MyTaskActivity.class);
                        }
                    });

                }
                //折扣商品
                GridViewForScrollView gridView = holder.getView(R.id.item_discount_gv);
                JavaBeanBaseAdapter<GoodsInfo> goodsAdapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_discount_good) {
                    @Override
                    protected void bindView(int position, ViewHolder holder, final GoodsInfo good) {
                        //商品图片
                        ImageView goodImg = holder.getView(R.id.item_discount_good_img);
                        int mwidth = mScreenWidth - UiUtil.dip2px(mContext, 40);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mwidth / 2, mwidth / 2);
                        goodImg.setLayoutParams(params);
                        Glide.with(mContext).load(good.getGoodsImg()).centerCrop().into(goodImg);
                        goodImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Bundle bundle = new Bundle();
//                                bundle.putString("goodId",good.getId());
//                                readyGo(GoodsDetailActivity.class,bundle);
                            }
                        });
                        //商品名称
                        TextView goodName = holder.getView(R.id.item_discount_good_name);
                        goodName.setText(info.getPrivName() + "  " + good.getGoodsName());
                        //商品app价格
                        TextView appPrice = holder.getView(R.id.item_discount_good_price1);
                        appPrice.setText(good.getPrice_app_dollar());
                        //商品市场价格
                        TextView refPrice = holder.getView(R.id.item_discount_good_price2);
                        refPrice.setText(good.getPrice_airport_dollar());
                        //是否够资格购买
                        RelativeLayout layoutBg = holder.getView(R.id.item_discount_buy_bg);
                        TextView buy = holder.getView(R.id.item_discount_buy);
                        if ("0".equals(info.getStatus())) {
                            layoutBg.setBackgroundResource(R.drawable.radius_stroke_purple_solid_white);
                            buy.setText(getResources().getString(R.string.buy_now));
                            buy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    List<GoodsInfo> orderGoods = new ArrayList<GoodsInfo>();
                                    good.setQuantity("1");
                                    orderGoods.add(good);
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelableArrayList("orderGoodsList", (ArrayList<? extends Parcelable>) orderGoods);
                                    bundle.putInt("totalPrice", StringUtils.parseInt(good.getPrice_app_dollar()));
                                    intent.setClass(mContext, OrderConfrimActivity.class);
                                    intent.putExtras(bundle);
                                    mContext.startActivity(intent);
                                }
                            });
                        } else if ("1".equals(info.getStatus())) {
                            layoutBg.setBackgroundResource(R.drawable.radius_stroke_gray_solid_white);
                            buy.setBackgroundResource(R.mipmap.discount_permisstion);
                            buy.setText("");
                        }
                        //库存
                        TextView surplus = holder.getView(R.id.item_discount_good_surplus);
                        surplus.setText("/" + good.getStoke());
                        //剩余
                        TextView shengyu = holder.getView(R.id.item_discount_good_sale);
                        shengyu.setText(good.getSurStock());
                        //左侧标签
                        ImageView lable = holder.getView(R.id.item_discount_good_lable);
                    }
                };
                gridView.setAdapter(goodsAdapter);
                goodsAdapter.addAll(info.getGoodsList());
            }
        };
        discountListView.setAdapter(adapter);
        presenter = new DiscountPresenterImpl(DiscountAeraActivity.this, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            toggleShowLoading(true, null);
            presenter.getDiscountList(TAG_LOG);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getDiscountList(TAG_LOG);
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
    public void showDiscountList(final List<DiscountInfo> infoList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false,null);
                discountList.addAll(infoList);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void refreshDiscountList(final List<DiscountInfo> infoList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                discountPullHeader.stopRefresh();
                if (discountList.size() > 0)
                    discountList.clear();
                discountList.addAll(infoList);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void requestError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleShowLoading(false,null);
                discountPullHeader.stopRefresh();
                ToastUtil.showLongToast(mContext, msg);
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
                presenter.getDiscountList(TAG_LOG);
            }
        });
    }


    @Override
    public void onRefresh(BaseHeaderView baseHeaderView) {
        presenter.refreshDiscountList(TAG_LOG);

    }
}
