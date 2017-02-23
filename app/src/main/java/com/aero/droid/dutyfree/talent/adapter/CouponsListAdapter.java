package com.aero.droid.dutyfree.talent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.bean.CouponInfo;
import com.aero.droid.dutyfree.talent.view.SliderView;

import java.util.ArrayList;

/**
 * Author : rongcl
 * Date : 2015/12/16
 * Desc : 优惠券列表Adapter
 */
public class CouponsListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<CouponInfo> mList;

    public CouponsListAdapter(Context mContext, ArrayList<CouponInfo> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (mList != null && (mList.size() > position)) ? -1 : position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        SliderView slideView;

        slideView = new SliderView(mContext);
        View itemView = LayoutInflater.from(mContext).inflate(
                R.layout.coupons_list_item_layout, null);
        slideView.setContentView(itemView);
        holder = new ViewHolder(slideView);

//        if (convertView == null) {
//            slideView = new SliderView(mContext);
//            View itemView = LayoutInflater.from(mContext).inflate(
//                    R.layout.coupons_list_item_layout, null);
//            slideView.setContentView(itemView);
//            holder = new ViewHolder(slideView);
//            slideView.setTag(holder);
//        } else {
//            slideView = (SliderView) convertView;
//            holder = (ViewHolder) slideView.getTag();
//        }
        CouponInfo info = mList.get(position);

        holder.priceBtn.setText("￥" + info.getPrice());
        holder.nameTv.setText(info.getName());
        holder.dateOutTv.setText("还有" + info.getDateOut() + "天过期");
        holder.validityDateTv.setText(info.getValidityDays());
        holder.useAreaTv.setText(info.getUseArea());
        if ("0".equalsIgnoreCase(info.getType())) {
            holder.typeIv.setImageResource(R.mipmap.dai_jin_coupon);
        } else if ("1".equalsIgnoreCase(info.getType())) {
            holder.typeIv.setImageResource(R.mipmap.man_jian_coupon);
        }
        if ("0".equalsIgnoreCase(info.getUseStatus())) {
            holder.statusIv.setVisibility(View.GONE);
        } else if ("1".equalsIgnoreCase(info.getUseStatus())) {
            holder.statusIv.setVisibility(View.VISIBLE);
            holder.statusIv.setImageResource(R.mipmap.used_coupon);
        } else if ("2".equalsIgnoreCase(info.getUseStatus())) {
            holder.statusIv.setVisibility(View.VISIBLE);
            holder.statusIv.setImageResource(R.mipmap.out_date_coupon);
        }
        holder.deleteVg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });

        return slideView;
    }

    class ViewHolder {

        Button priceBtn;
        TextView nameTv;
        TextView dateOutTv;
        ImageView typeIv;
        ImageView statusIv;
        TextView validityDateTv;
        TextView useAreaTv;
        ViewGroup deleteVg;

        public ViewHolder(View v) {
            priceBtn = (Button) v
                    .findViewById(R.id.price_bt_cli);
            nameTv = (TextView) v
                    .findViewById(R.id.name_coupon_tv_cli);
            dateOutTv = (TextView) v
                    .findViewById(R.id.out_date_tv_cli);
            typeIv = (ImageView) v
                    .findViewById(R.id.type_iv_cli);
            statusIv = (ImageView) v
                    .findViewById(R.id.use_status_iv_cli);
            validityDateTv = (TextView) v
                    .findViewById(R.id.use_date_both_tv_cli);
            useAreaTv = (TextView) v
                    .findViewById(R.id.area_use_tv_cli);
            deleteVg = (ViewGroup) v.findViewById(R.id.holder);
        }

    }
}
