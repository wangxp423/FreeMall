package com.aero.droid.dutyfree.talent.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.MyCouponsActivity;
import com.aero.droid.dutyfree.talent.base.BaseFragment;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.TimeFormatUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenu;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenuCreator;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenuItem;
import com.aero.droid.dutyfree.talent.widgets.menulistview.SwipeMenuListView;

import java.util.List;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/12/5
 * Desc : 显示 优惠券(已使用，未使用，已过期)页面
 */
public class CouponsFragment extends BaseFragment {
    @Bind(R.id.fm_my_coupons_lv)
    SwipeMenuListView couponsLv;
    @Bind(R.id.fm_my_coupons_no_data)
    ImageView noData;


    /*******************************/
    private List<CouponsInfo> infoList;
    private JavaBeanBaseAdapter<CouponsInfo> adapter;
    private MyCouponsActivity mActivity;
    private int deletePosition = -1;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (MyCouponsActivity) activity;
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
        infoList = getArguments().getParcelableArrayList("couponsList");
        adapter = new JavaBeanBaseAdapter<CouponsInfo>(getActivity(), R.layout.item_coupons, infoList) {
            @Override
            protected void bindView(int position, ViewHolder holder, CouponsInfo info) {
                //优惠券 金额
                TextView couponPrice = holder.getView(R.id.coupons_price_img);
                couponPrice.setText(info.getCouponValue());
                //优惠券 条件
                TextView condition = holder.getView(R.id.coupons_conditions);
                condition.setText(info.getCouponName());
                //状态描述
                TextView typeTv = holder.getView(R.id.coupons_type_tv);
                //类型 图片
                ImageView lable = holder.getView(R.id.coupons_lable);
                //状态图片
                ImageView typeImg = holder.getView(R.id.coupons_type_img);
                //时间
                TextView time = holder.getView(R.id.coupons_time);
                time.setText(info.getBeginTime()+"至"+info.getEndTime());
                //使用范围
                TextView scope = holder.getView(R.id.coupons_scope);
                if ("0".equals(info.getStatus())) {
                    //已使用
                    typeTv.setText(getResources().getString(R.string.coupons_used));
                    typeImg.setImageResource(R.mipmap.used_coupon);
                } else if ("1".equals(info.getStatus())) {
                    //未使用
                    String intervalTime = TimeFormatUtil.getTwoDay(TimeFormatUtil.getStringDate1(),info.getEndTime());
                    typeTv.setText("还有"+intervalTime+"天过期");
                    typeImg.setVisibility(View.GONE);
                } else if ("2".equals(info.getStatus())) {
                    //已失效
                    typeTv.setText(getResources().getString(R.string.coupons_outdate));
                    typeImg.setImageResource(R.mipmap.out_date_coupon);
                }
                if ("1".equals(info.getType())){
                    //满减
                    lable.setImageResource(R.mipmap.man_jian_coupon);
                }else if ("2".equals(info.getType())){
                    //打折
                    lable.setImageResource(R.mipmap.dai_jin_coupon);
                }
            }
        };

        couponsLv.setAdapter(adapter);
        if (null != infoList && infoList.size()<1)
            couponsLv.setEmptyView(noData);

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
            }
        };
        // set creator
        couponsLv.setMenuCreator(creator);

        // step 2. listener item click event
        couponsLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        deletePosition = position;
                        mActivity.presenter.clickDeleteCoupons(TAG_LOG,infoList.get(position).getCouponId());
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fm_my_coupons;
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

    public void deleteCouponSuccess(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != infoList && infoList.size()>0 && deletePosition >=0){
                    infoList.remove(deletePosition);
                    adapter.notifyDataSetChanged();
                    if (infoList.size()<1)
                        couponsLv.setEmptyView(noData);
                }else {
                    couponsLv.setEmptyView(noData);
                }
            }
        });

    }


}
