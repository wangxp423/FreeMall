package com.aero.droid.dutyfree.talent.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.BrandGoodsActivity;
import com.aero.droid.dutyfree.talent.activity.GoodsDetailActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2015/12/12
 * Desc : 商品推荐(商品详情里面，购物车里面)
 */
public class RecommendGoodsLayout extends LinearLayout {
    private Context mContext;
    private int mWidth;

    public RecommendGoodsLayout(Context context) {
        super(context);
        init(context);
    }

    public RecommendGoodsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecommendGoodsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RecommendGoodsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWidth = manager.getDefaultDisplay().getWidth();
        setOrientation(VERTICAL);
    }

    /**
     * 给View填充数据  商品详情中
     *
     * @param goodList
     * @param type     1 同品牌商品 2 推荐商品
     * @param id       分类ID 或者品牌Id
     */
    public void setGoods(List<GoodsInfo> goodList, int type, final String id) {
        View recommendView = View.inflate(mContext, R.layout.view_recomend_good, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.topMargin = UiUtil.dip2px(mContext, 10);
        recommendView.setLayoutParams(params);
        //推荐商品标题
        TextView title = (TextView) recommendView.findViewById(R.id.view_recommend_title);
        if (1 == type) {
            title.setText(mContext.getResources().getString(R.string.goods_same_brand));
        } else if (2 == type) {
            title.setText(mContext.getResources().getString(R.string.goods_recommend));
        }

        //查看更多
        TextView seeMore = (TextView) recommendView.findViewById(R.id.view_recomend_see_more);
        if (1 == type) {
            seeMore.setText(mContext.getResources().getString(R.string.goods_see_more));
            seeMore.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("markId", id);
                    intent.setClass(mContext, BrandGoodsActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        } else if (2 == type) {
            seeMore.setVisibility(GONE);
        }
        //商品列表
        GridViewForScrollView goodGv = (GridViewForScrollView) recommendView.findViewById(R.id.view_recommend_gv);
        JavaBeanBaseAdapter<GoodsInfo> adapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_recommend_good, goodList) {
            @Override
            protected void bindView(int position, ViewHolder holder, final GoodsInfo info) {
                //商品图片
                ImageView goodImg = holder.getView(R.id.item_recommend_good_img);
                int width = mWidth - UiUtil.dip2px(mContext, 30);
                LinearLayout.LayoutParams goodImgParams = new LayoutParams(width / 2, width / 2);
                goodImg.setLayoutParams(goodImgParams);
                Glide.with(mContext).load(info.getGoodsImg()).centerCrop().into(goodImg);
                goodImg.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("goodId", info.getId());
                        bundle.putString("srcType", TextUtils.isEmpty(info.getSrcType()) ? "0" : info.getSrcType());
                        bundle.putString("srcId", TextUtils.isEmpty(info.getSrcId()) ? "0" : info.getSrcId());
                        intent.setClass(mContext, GoodsDetailActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
                //商品名称
                TextView goodName = holder.getView(R.id.item_recommend_good_name);
                goodName.setText(info.getGoodsName());
                //商品描述
                TextView goodDesc = holder.getView(R.id.item_recommend_good_desc);
                goodDesc.setVisibility(GONE);
                goodDesc.setText(info.getGoodsDes());
                //app价格
                TextView appPirce = holder.getView(R.id.item_recommend_good_price1);
                appPirce.setText(info.getPrice_app_dollar());
                //市场价格
                TextView refPrice = holder.getView(R.id.item_recommend_good_price2);
                refPrice.setText(info.getPrice_ref_dollar());
            }
        };
        goodGv.setAdapter(adapter);
        addView(recommendView);

    }

    /**
     * 给View填充数据  购物车中
     *
     * @param goodList
     */
    public void setTopGoods(List<GoodsInfo> goodList) {
        View recommendView = View.inflate(mContext, R.layout.view_top_good, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.topMargin = UiUtil.dip2px(mContext, 10);
        recommendView.setLayoutParams(params);
        //推荐商品标题
        TextView title = (TextView) recommendView.findViewById(R.id.view_recommend_title);

        //查看更多
        TextView seeMore = (TextView) recommendView.findViewById(R.id.view_recomend_see_more);
        seeMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("goodId", id);
//                intent.setClass(mContext, BrandGoodsActivity.class);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
                ToastUtil.showShortToast(mContext, "跳转更多");
            }
        });
        //商品列表
        GridViewForScrollView goodGv = (GridViewForScrollView) recommendView.findViewById(R.id.view_recommend_gv);
        JavaBeanBaseAdapter<GoodsInfo> adapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_recommend_good, goodList) {
            @Override
            protected void bindView(int position, ViewHolder holder, GoodsInfo info) {
                //商品图片
                ImageView goodImg = holder.getView(R.id.item_recommend_good_img);
                int width = mWidth - UiUtil.dip2px(mContext, 30);
                LinearLayout.LayoutParams goodImgParams = new LayoutParams(width / 3, width / 3);
                goodImg.setLayoutParams(goodImgParams);
                Glide.with(mContext).load(info.getGoodsImg()).centerCrop().into(goodImg);
                //商品名称
                TextView goodName = holder.getView(R.id.item_recommend_good_name);
                goodName.setText(info.getGoodsName());
                //商品描述
                TextView goodDesc = holder.getView(R.id.item_recommend_good_desc);
                goodDesc.setText(info.getGoodsDes());
                goodDesc.setVisibility(GONE);
                //app价格
                TextView appPirce = holder.getView(R.id.item_recommend_good_price1);
                appPirce.setText(info.getPrice_app_dollar());
                //市场价格
                TextView refPrice = holder.getView(R.id.item_recommend_good_price2);
                refPrice.setText(info.getPrice_ref_dollar());
                //排行标签
                TextView label = holder.getView(R.id.item_top_sort);
                label.setVisibility(VISIBLE);
                label.setText(String.valueOf(position + 1));
            }
        };
        goodGv.setAdapter(adapter);
        addView(recommendView);

    }

    /**
     * 给View填充数据  购物车中
     *
     * @param goodList
     */
    public void setTopGoods1(List<GoodsInfo> goodList) {
        View recommendView = View.inflate(mContext, R.layout.view_top_good1, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        recommendView.setLayoutParams(params);
        //推荐商品标题
        TextView title = (TextView) recommendView.findViewById(R.id.view_recommend_title);

        //查看更多
        TextView seeMore = (TextView) recommendView.findViewById(R.id.view_recomend_see_more);
        seeMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("goodId", id);
//                intent.setClass(mContext, BrandGoodsActivity.class);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
                ToastUtil.showShortToast(mContext, "跳转更多");
            }
        });
        LinearLayout goodsLayout = (LinearLayout) recommendView.findViewById(R.id.view_recomend_goods_layout);
        for (int i = 0; i < goodList.size(); i++) {
            int width = mWidth - UiUtil.dip2px(mContext, 30);
            final GoodsInfo info = goodList.get(i);
            View goodView = View.inflate(mContext, R.layout.item_recommend_good, null);
            LinearLayout.LayoutParams viewParams = new LayoutParams(width / 3, LayoutParams.WRAP_CONTENT);
            viewParams.leftMargin = UiUtil.dip2px(getContext(), 10);
            viewParams.rightMargin = UiUtil.dip2px(getContext(), 5);
            goodView.setLayoutParams(viewParams);
            //商品图片
            ImageView goodImg = (ImageView) goodView.findViewById(R.id.item_recommend_good_img);
            goodImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("goodId", info.getId());
                    bundle.putString("srcType", TextUtils.isEmpty(info.getSrcType()) ? "0" : info.getSrcType());
                    bundle.putString("srcId", TextUtils.isEmpty(info.getSrcId()) ? "0" : info.getSrcId());
                    intent.setClass(mContext, GoodsDetailActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            LinearLayout.LayoutParams goodImgParams = new LayoutParams(width / 3, width / 3);
            goodImg.setLayoutParams(goodImgParams);
            Glide.with(mContext).load(info.getGoodsImg()).centerCrop().into(goodImg);
            //商品名称
            TextView goodName = (TextView) goodView.findViewById(R.id.item_recommend_good_name);
            goodName.setText(info.getGoodsName());
            //商品描述
            TextView goodDesc = (TextView) goodView.findViewById(R.id.item_recommend_good_desc);
            goodDesc.setText(info.getGoodsDes());
            goodDesc.setVisibility(GONE);
            //app价格
            TextView appPirce = (TextView) goodView.findViewById(R.id.item_recommend_good_price1);
            appPirce.setText(info.getPrice_app_dollar());
            //市场价格
            TextView refPrice = (TextView) goodView.findViewById(R.id.item_recommend_good_price2);
            refPrice.setText(info.getPrice_ref_dollar());
            //排行标签
            TextView label = (TextView) goodView.findViewById(R.id.item_top_sort);
            label.setVisibility(VISIBLE);
            label.setText(String.valueOf(i + 1));
            goodsLayout.addView(goodView);
        }
        addView(recommendView);

    }
}
