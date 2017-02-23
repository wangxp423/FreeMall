package com.aero.droid.dutyfree.talent.widgets;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.GoodsDetailActivity;
import com.aero.droid.dutyfree.talent.activity.LoginActivity;
import com.aero.droid.dutyfree.talent.activity.OrderConfrimActivity;
import com.aero.droid.dutyfree.talent.activity.PosterGoodsActivity;
import com.aero.droid.dutyfree.talent.activity.SecondCategoryActivity;
import com.aero.droid.dutyfree.talent.activity.SpecialGoodsActivity;
import com.aero.droid.dutyfree.talent.activity.TopicGoodsActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.Discover;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.bean.SubFind;
import com.aero.droid.dutyfree.talent.util.DownTimeUtil;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.StringUtils;
import com.aero.droid.dutyfree.talent.util.TimeFormatUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Author : wangxp
 * Date : 2015/12/7
 * Desc :  首页 精选 布局页面
 */
public class HandpickLayout extends LinearLayout {
    private Context mContext;
    private int width;

    public HandpickLayout(Context context, int mWidth) {
        super(context);
        this.width = mWidth;
        init(context);
    }

    public HandpickLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HandpickLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public HandpickLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setOrientation(VERTICAL);
    }

    public void setData(List<HandpickType> handpickTypes) {
        for (HandpickType handpickType : handpickTypes) {
            if ("1".equals(handpickType.getType())) {
//                showType1View(handpickType);
            } else if ("2".equals(handpickType.getType())) {
                showType2View(handpickType);
            } else if ("3".equals(handpickType.getType())) {
                if ("1".equals(handpickType.getOuterLayout())) {
                    showSingleImageView(handpickType);
                } else if ("2".equals(handpickType.getOuterLayout())) {
                    showType3View(handpickType);
                }
            } else if ("4".equals(handpickType.getType())) {
                if ("1".equals(handpickType.getDiscover().getOuterLayout())) {
                    showSingleImageView(handpickType);
                } else if ("2".equals(handpickType.getDiscover().getOuterLayout())) {
                    showType4View(handpickType);
                }
            }
        }
    }

    /**
     * type = 1 显示 横向滚动 特惠精选 商品
     *
     * @param handpickType
     */
    private void showType1View(final HandpickType handpickType) {
        List<GoodsInfo> goodsInfos = handpickType.getInfoList();
        View cheapHandpickView = View.inflate(mContext, R.layout.view_cheap_hanpick, null);
        LinearLayout.LayoutParams cheapViewParmas = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        cheapViewParmas.topMargin = UiUtil.dip2px(getContext(), 10);
        cheapHandpickView.setLayoutParams(cheapViewParmas);
        LinearLayout horLayout = (LinearLayout) cheapHandpickView.findViewById(R.id.cheap_handpick_layout);
        for (final GoodsInfo info : goodsInfos) {
            View view = View.inflate(mContext, R.layout.item_cheap_handpick, null);
            LinearLayout.LayoutParams viewParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            viewParams.leftMargin = UiUtil.dip2px(getContext(), 10);
            viewParams.rightMargin = UiUtil.dip2px(getContext(), 5);
            view.setLayoutParams(viewParams);
            //商品图片
            ImageView goodImg = (ImageView) view.findViewById(R.id.item_cheap_handpick_good_img);
            int mwidth = width - UiUtil.dip2px(mContext, 60);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mwidth, mwidth / 13 * 8);
            int margin = UiUtil.dip2px(mContext, 5);
            params.setMargins(margin, margin, margin, margin);
            goodImg.setLayoutParams(params);
            Glide.with(mContext).load(info.getGoodsImg()).centerCrop().into(goodImg);
            goodImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoGoodDetail(info.getId(), "2", info.getDiscSpecId());
                }
            });
            //商品 品牌
            TextView brandName = (TextView) view.findViewById(R.id.item_cheap_handpick_good_brand_name);
            brandName.setText(info.getMarkName());
            //商品 名称
            TextView goodName = (TextView) view.findViewById(R.id.item_cheap_handpick_good_name);
            goodName.setText(info.getGoodsDes());
            //商品 折扣
            TextView discount = (TextView) view.findViewById(R.id.item_cheap_handpick_good_discount);
            discount.setText("低至" + info.getDiscount() + "折");
            //商品 价格
            TextView goodPrice = (TextView) view.findViewById(R.id.item_cheap_handpick_good_price);
            goodPrice.setText("＄" + info.getGoodsPrice());
            //立即购买
            int activeStatus = -1;
            TextView buyNow = (TextView) view.findViewById(R.id.item_cheap_handpick_buy);
            //活动开始or结束 title
            TextView downTimeTitle = (TextView) view.findViewById(R.id.item_cheap_handpick_downtime_title);

            //标签 活动开始还是 未开始
            ImageView lable = (ImageView) view.findViewById(R.id.item_cheap_handpick_left_lable);
            if ("0".equals(info.getStatus())) {
                buyNow.setBackgroundResource(R.drawable.radius_solid_purple);
                buyNow.setText(mContext.getResources().getString(R.string.buy_first));
                buyNow.setOnClickListener(new GotoOrderConfrimClickListener(info));
                lable.setImageResource(R.mipmap.active_time_begining);
            } else if ("1".equals(info.getStatus())) {
                buyNow.setBackgroundResource(R.drawable.radius_solid_red);
                buyNow.setText(mContext.getResources().getString(R.string.good_i_want));
                buyNow.setOnClickListener(new AddFavoClickListener(info));
                lable.setImageResource(R.mipmap.active_time_unbegin);
            }
            //日历时间
            TextView date = (TextView) view.findViewById(R.id.item_cheap_handpick_good_date);
            try {
                date.setText(TimeFormatUtil.translateDate1(Long.parseLong(info.getBeginTime()) * 1000));
            } catch (Exception e) {
                e.printStackTrace();
                date.setText("0000/00/00");
            }
            //分类标题 分类特惠
            TextView title = (TextView) view.findViewById(R.id.item_cheap_handpick_good_title);
            title.setText(info.getDiscSepcName());
            //购买人数
            TextView shopPerple = (TextView) view.findViewById(R.id.item_cheap_handpick_shop_peoples);
            shopPerple.setText("已有" + info.getSaleCount() + "人购买");
            //剩余数量
            TextView remains = (TextView) view.findViewById(R.id.item_cheap_handpick_remains);
            remains.setText("剩余" + info.getStoke() + "件");
            //倒计时
            DownTimerView downTime = (DownTimerView) view.findViewById(R.id.item_cheap_handpick_good_downtime);
            long curTime = System.currentTimeMillis();
            try {
                if (Long.parseLong(info.getBeginTime()) * 1000 > curTime) {
                    buyNow.setBackgroundResource(R.drawable.radius_solid_red);
                    buyNow.setText(mContext.getResources().getString(R.string.good_i_want));
                    buyNow.setOnClickListener(new AddFavoClickListener(info));
                    lable.setImageResource(R.mipmap.active_time_unbegin);
                    downTimeTitle.setText(mContext.getResources().getString(R.string.active_downtime_start));
                    DownTimeUtil timeUtil = new DownTimeUtil(Long.parseLong(info.getBeginTime()));
                    downTime.setTime(timeUtil.getDay(), timeUtil.getHour(), timeUtil.getMin(), timeUtil.getSec());
                } else if (Long.parseLong(info.getBeginTime()) * 1000 < curTime && curTime < Long.parseLong(info.getEndTime()) * 1000) {
                    buyNow.setBackgroundResource(R.drawable.radius_solid_purple);
                    buyNow.setText(mContext.getResources().getString(R.string.buy_first));
                    buyNow.setOnClickListener(new GotoOrderConfrimClickListener(info));
                    lable.setImageResource(R.mipmap.active_time_begining);
                    downTimeTitle.setText(mContext.getResources().getString(R.string.active_downtime_text));
                    DownTimeUtil timeUtil = new DownTimeUtil(Long.parseLong(info.getEndTime()));
                    downTime.setTime(timeUtil.getDay(), timeUtil.getHour(), timeUtil.getMin(), timeUtil.getSec());
                } else {
                    buyNow.setBackgroundResource(R.drawable.radius_solid_red);
                    buyNow.setText(mContext.getResources().getString(R.string.good_i_want));
                    buyNow.setOnClickListener(new AddFavoClickListener(info));
                    lable.setImageResource(R.mipmap.active_time_unbegin);
                    downTimeTitle.setText(mContext.getResources().getString(R.string.active_downtime_end));
                    downTime.setTime(0, 0, 0, 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                downTime.setTime(0, 0, 0, 1);
            }

            downTime.start();
            horLayout.addView(view);
        }
        addView(cheapHandpickView);
    }

    /**
     * type = 2 显示 top10 页面
     *
     * @param handpickType
     */
    private void showType2View(final HandpickType handpickType) {
        List<GoodsInfo> goodsInfos = handpickType.getInfoList();
        if (goodsInfos.size() < 1)
            return;
        View topView = View.inflate(mContext, R.layout.view_top_ten, null);
        LinearLayout.LayoutParams topViewParmas = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        topViewParmas.topMargin = UiUtil.dip2px(getContext(), 10);
        topView.setLayoutParams(topViewParmas);
        //左侧标题
        TextView title = (TextView) topView.findViewById(R.id.view_topten_title);
        title.setText(handpickType.getTitle());
        //查看更多
        TextView seeMore = (TextView) topView.findViewById(R.id.view_topten_see_more);
        if ("0".equals(handpickType.getIsMore())) {
            seeMore.setVisibility(VISIBLE);
        } else if ("1".equals(handpickType.getIsMore())) {
            seeMore.setVisibility(GONE);
        } else {
            seeMore.setVisibility(GONE);
        }
        seeMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMoreGoods(handpickType);
            }
        });
        //获取第一个商品 展示
        final GoodsInfo goodFirst = goodsInfos.get(0);
        //第一个商品layout 大小
        RelativeLayout firstLayout = (RelativeLayout) topView.findViewById(R.id.view_topten_first_layout);
        LayoutParams params = new LayoutParams(width, width / 2);
        firstLayout.setLayoutParams(params);
        //第一个商品图片
        ImageView firstImg = (ImageView) topView.findViewById(R.id.view_topten_first_img);
        Glide.with(mContext).load(goodFirst.getGoodsImg()).centerCrop().into(firstImg);
        firstImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoGoodDetail(goodFirst.getId(), "0", "0");
            }
        });
        //商品名称 价格背景
        View viewBg = topView.findViewById(R.id.view_topten_text_bg);
        viewBg.setAlpha(0.6f);
        //第一个商品name
        TextView firstName = (TextView) topView.findViewById(R.id.view_topten_first_name);
        firstName.setText(goodFirst.getGoodsName());
        //第一个商品price
        TextView firstPrice = (TextView) topView.findViewById(R.id.view_topten_first_price);
        firstPrice.setText(goodFirst.getPrice_app_dollar());
        //第一个商品 likenum
        TextView firstFirstLike = (TextView) topView.findViewById(R.id.view_topten_first_like_num);
        firstFirstLike.setText(goodFirst.getFavoQty() + "喜欢");
        //下面九个商品gridview
        GridViewForScrollView topGv = (GridViewForScrollView) topView.findViewById(R.id.view_topten_gv);
//        List<GoodsInfo> otherList = (List<GoodsInfo>) goodsInfos.remove(0);
        goodsInfos.remove(0);
        JavaBeanBaseAdapter<GoodsInfo> adapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_top_ten, goodsInfos) {
            @Override
            protected void bindView(int position, ViewHolder holder, final GoodsInfo info) {
                //商品图片
                ImageView goodImg = holder.getView(R.id.item_top_img);
                int mWidth = width - UiUtil.dip2px(mContext, 20);
                LinearLayout.LayoutParams params1 = new LayoutParams(mWidth / 3, mWidth / 3);
                goodImg.setLayoutParams(params1);
                Glide.with(mContext).load(info.getGoodsImg()).centerCrop().into(goodImg);
                goodImg.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoGoodDetail(info.getId(), "0", "0");
                    }
                });
                //商品name
                TextView goodName = holder.getView(R.id.item_top_good_name);
                goodName.setText(info.getGoodsName());
                //商品price
                TextView goodPrice = holder.getView(R.id.item_top_good_price);
                goodPrice.setText(info.getPrice_app_dollar());
                //商品 likenum
                TextView goodLike = holder.getView(R.id.item_top_like_num);
                goodLike.setText(info.getFavoQty() + "喜欢");
                //商品排序号
                TextView goodSort = holder.getView(R.id.item_top_sort);
                goodSort.setText(position + 2 + "");
            }
        };
        topGv.setAdapter(adapter);
        addView(topView);
    }

    /**
     * 显示 list 列表(只有图片) 商品集合(目前只有一个图片)
     * type = 3 或者 type = 4 且 banner = 1  这个时候只有一个图片
     *
     * @param handpickType
     */
    private void showSingleImageView(final HandpickType handpickType) {
//        View listView = View.inflate(mContext, R.layout.view_active_list, null);
//        ListViewForScrollView list = (ListViewForScrollView) listView.findViewById(R.id.view_active_listview);
//        JavaBeanBaseAdapter<GoodsInfo> adapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_brand, goodsInfos) {
//            @Override
//            protected void bindView(int position, ViewHolder holder, GoodsInfo info) {
//                ImageView goodImg = holder.getView(R.id.brand_logo);
//                int mWidth = width - UiUtil.dip2px(mContext, 20);
//                LinearLayout.LayoutParams params = new LayoutParams(mWidth, mWidth / 2);
//                goodImg.setLayoutParams(params);
//                Glide.with(mContext).load(info.getGoodsImg()).into(goodImg);
//                goodImg.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ToastUtil.showShortToast(mContext, "点击图片");
//                    }
//                });
//            }
//        };
        String url = null;
        if ("3".equals(handpickType.getType())) {
            url = handpickType.getActiveImg();
        } else if ("4".equals(handpickType.getType())) {
            url = handpickType.getDiscover().getImgUrl();
        }
        ImageView goodImg = new ImageView(mContext);
        LinearLayout.LayoutParams params = new LayoutParams(width, width / 2);
        params.topMargin = UiUtil.dip2px(getContext(), 10);
//        goodImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        goodImg.setLayoutParams(params);
        Glide.with(mContext).load(url).centerCrop().into(goodImg);
        goodImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("3".equals(handpickType.getType())) {
                    gotoActiveActivity(handpickType);
                } else if ("4".equals(handpickType.getType())) {
                    gotoSecondDiscoveActivity(handpickType);
                }
            }
        });
        addView(goodImg);
    }

    /**
     * type = 3 精品推荐  (跟top10展示稍微不同  通过代码控制)
     *
     * @param handpickType
     */
    private void showType3View(final HandpickType handpickType) {
        View type3View = View.inflate(mContext, R.layout.view_top_ten, null);
        LinearLayout.LayoutParams type3Parmas = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        type3Parmas.topMargin = UiUtil.dip2px(getContext(), 10);
        type3View.setLayoutParams(type3Parmas);
        //左侧标签
        TextView title = (TextView) type3View.findViewById(R.id.view_topten_title);
        Drawable drawable = getResources().getDrawable(R.mipmap.active_recommend);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        title.setCompoundDrawables(drawable, null, null, null);
        title.setText(handpickType.getTitle());
        //查看更多
        TextView seeMore = (TextView) type3View.findViewById(R.id.view_topten_see_more);
        if ("0".equals(handpickType.getIsMore())) {
            seeMore.setVisibility(VISIBLE);
        } else if ("1".equals(handpickType.getIsMore())) {
            seeMore.setVisibility(GONE);
        } else {
            seeMore.setVisibility(GONE);
        }
        seeMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMoreGoods(handpickType);
            }
        });
        //获取第一个商品 展示
        //第一个商品layout 大小
        RelativeLayout firstLayout = (RelativeLayout) type3View.findViewById(R.id.view_topten_first_layout);
        LayoutParams params = new LayoutParams(width, width / 2);
        firstLayout.setLayoutParams(params);
        //第一个商品图片
        ImageView firstImg = (ImageView) type3View.findViewById(R.id.view_topten_first_img);
        Glide.with(mContext).load(handpickType.getActiveImg()).into(firstImg);
        firstImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActiveActivity(handpickType);
            }
        });
        //隐藏价格 什么的
        LinearLayout layout = (LinearLayout) type3View.findViewById(R.id.view_topten_text_layout);
        layout.setVisibility(GONE);
        View viewBg = type3View.findViewById(R.id.view_topten_text_bg);
        viewBg.setVisibility(GONE);
        //第一个商品 标签
        ImageView lableImg = (ImageView) type3View.findViewById(R.id.view_topten_lable);
        RelativeLayout.LayoutParams lableParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lableParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lableParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lableParams.topMargin = UiUtil.dip2px(mContext, 20);
        lableParams.rightMargin = UiUtil.dip2px(mContext, 20);
        lableImg.setLayoutParams(lableParams);
        lableImg.setImageResource(R.mipmap.good_recommend);
        //下面显示其他商品gridview
        GridViewForScrollView topGv = (GridViewForScrollView) type3View.findViewById(R.id.view_topten_gv);
        List<GoodsInfo> goodsInfos = handpickType.getInfoList();
        if (goodsInfos.size() < 1)
            return;
        if (null == goodsInfos)
            goodsInfos = new ArrayList<>();
        JavaBeanBaseAdapter<GoodsInfo> adapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_top_ten, goodsInfos) {
            @Override
            protected void bindView(int position, ViewHolder holder, final GoodsInfo info) {
                //商品图片
                ImageView goodImg = holder.getView(R.id.item_top_img);
                int mWidth = width - UiUtil.dip2px(mContext, 20);
                LinearLayout.LayoutParams params1 = new LayoutParams(mWidth / 3, mWidth / 3);
                goodImg.setLayoutParams(params1);
                Glide.with(mContext).load(info.getGoodsImg()).into(goodImg);
                goodImg.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoGoodDetail(info.getId(), "0", handpickType.getActiveId());
                    }
                });
                //商品name 隐藏
                TextView goodName = holder.getView(R.id.item_top_good_name);
//                goodName.setVisibility(GONE);
                goodName.setText(info.getGoodsName());
                //商品price
                TextView goodPrice = holder.getView(R.id.item_top_good_price);
                goodPrice.setText(info.getPrice_app_dollar());
                //商品 likenum(这里控制为市场价格)
                TextView goodLike = holder.getView(R.id.item_top_like_num);
                goodLike.setBackgroundResource(R.drawable.custom_black_line);
                goodLike.setText(info.getPrice_airport_dollar());
                //商品排序号
                TextView goodSort = holder.getView(R.id.item_top_sort);
                goodSort.setVisibility(GONE);
                goodSort.setText(position + 2 + "");
            }
        };
        topGv.setAdapter(adapter);
        addView(type3View);
    }


    /**
     * type = 4 发现活动  (跟top10展示稍微不同(只显示图片)  通过代码控制)
     *
     * @param handpickType
     */
    private void showType4View(final HandpickType handpickType) {
        View type4View = View.inflate(mContext, R.layout.view_top_ten, null);
        LinearLayout.LayoutParams type4Parmas = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        type4Parmas.topMargin = UiUtil.dip2px(getContext(), 10);
        type4View.setLayoutParams(type4Parmas);
        //左侧标签
        TextView title = (TextView) type4View.findViewById(R.id.view_topten_title);
        Drawable drawable = getResources().getDrawable(R.mipmap.active_brand);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        title.setCompoundDrawables(drawable, null, null, null);
        title.setText(handpickType.getTitle());
        //查看更多
        TextView seeMore = (TextView) type4View.findViewById(R.id.view_topten_see_more);
        if ("0".equals(handpickType.getIsMore())) {
            seeMore.setVisibility(VISIBLE);
        } else if ("1".equals(handpickType.getIsMore())) {
            seeMore.setVisibility(GONE);
        } else {
            seeMore.setVisibility(GONE);
        }
        seeMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSecondDiscoveActivity(handpickType);
            }
        });
        //获取第一个商品 展示 第一级发现
        Discover discover = handpickType.getDiscover();
        //第一个商品layout 大小
        RelativeLayout firstLayout = (RelativeLayout) type4View.findViewById(R.id.view_topten_first_layout);
        LayoutParams params = new LayoutParams(width, width / 2);
        firstLayout.setLayoutParams(params);
        //第一个商品图片
        ImageView firstImg = (ImageView) type4View.findViewById(R.id.view_topten_first_img);
        Glide.with(mContext).load(discover.getImgUrl()).centerCrop().into(firstImg);
        firstImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSecondDiscoveActivity(handpickType);
            }
        });
        //隐藏价格 什么的
        LinearLayout layout = (LinearLayout) type4View.findViewById(R.id.view_topten_text_layout);
        layout.setVisibility(GONE);
        //第一个商品 标签
        ImageView lableImg = (ImageView) type4View.findViewById(R.id.view_topten_lable);
        RelativeLayout.LayoutParams lableParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lableParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lableParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lableParams.topMargin = UiUtil.dip2px(mContext, 10);
        lableParams.rightMargin = UiUtil.dip2px(mContext, 20);
        lableImg.setLayoutParams(lableParams);
        lableImg.setImageResource(R.mipmap.good_recommend);
        //下面显示二级发现
        GridViewForScrollView topGv = (GridViewForScrollView) type4View.findViewById(R.id.view_topten_gv);
        List<SubFind> subFinds = discover.getSubFinds();
        if (subFinds.size() < 1)
            return;
        JavaBeanBaseAdapter<SubFind> adapter = new JavaBeanBaseAdapter<SubFind>(mContext, R.layout.item_top_ten, subFinds) {
            @Override
            protected void bindView(int position, ViewHolder holder, SubFind info) {
                //商品图片
                ImageView goodImg = holder.getView(R.id.item_top_img);
                int mWidth = width - UiUtil.dip2px(mContext, 20);
                LinearLayout.LayoutParams params1 = new LayoutParams(mWidth / 3, mWidth / 3);
                goodImg.setLayoutParams(params1);
                Glide.with(mContext).load(info.getSubSquareImg()).centerCrop().into(goodImg);
                goodImg.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoSecondDiscoveActivity(handpickType);
                    }
                });
                //商品name 隐藏
                TextView goodName = holder.getView(R.id.item_top_good_name);
                goodName.setText("");
                //商品 价格什么的 隐藏
                LinearLayout layout = holder.getView(R.id.item_top_good_text_layout);
                layout.setVisibility(GONE);
                //商品排序号 lable 隐藏
                TextView goodSort = holder.getView(R.id.item_top_sort);
                goodSort.setVisibility(GONE);
            }
        };
        topGv.setAdapter(adapter);
        addView(type4View);
    }

    /**
     * 跳转到商品详情
     *
     * @param goodId
     * @param srcType 来源类型
     * @param srcId   来源id
     */
    private void gotoGoodDetail(String goodId, String srcType, String srcId) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setClass(mContext, GoodsDetailActivity.class);
        bundle.putString("goodId", goodId);
        bundle.putString("srcType", srcType);
        bundle.putString("srcId", srcId);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 跳转到活动
     *
     * @param handpickType
     */
    private void gotoActiveActivity(HandpickType handpickType) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if ("1".equals(handpickType.getInsideLayout())) {
            intent.setClass(mContext, SpecialGoodsActivity.class);
            bundle.putString("activeId", handpickType.getActiveId());
        } else if ("2".equals(handpickType.getInsideLayout())) {
            intent.setClass(mContext, PosterGoodsActivity.class);
            bundle.putString("activeId", handpickType.getActiveId());
        } else if ("3".equals(handpickType.getInsideLayout())) {
            intent.setClass(mContext, TopicGoodsActivity.class);
            bundle.putString("activeId", handpickType.getActiveId());
        }
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 跳转到二级发现
     *
     * @param handpickType
     */
    private void gotoSecondDiscoveActivity(HandpickType handpickType) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setClass(mContext, SecondCategoryActivity.class);
        bundle.putString("discoverId", handpickType.getDiscover().getFindId());
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 查看更多(其实是专场页)
     *
     * @param handpickType
     */
    private void gotoMoreGoods(HandpickType handpickType) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setClass(mContext, SpecialGoodsActivity.class);
        bundle.putString("activeid", handpickType.getActiveId());
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 添加我喜欢 点击事件
     */
    public class AddFavoClickListener implements OnClickListener {
        private GoodsInfo info;

        public AddFavoClickListener(GoodsInfo info) {
            this.info = info;
        }

        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(UserUtil.getUserId(mContext))) {
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
            } else {
                EventCenter<String> eventCenter = new EventCenter<String>(Constants.EVENT_ADD_FAVO, info.getId());
                EventBus.getDefault().post(eventCenter);
            }

        }
    }

    /**
     * 立即抢购 点击事件
     */
    public class GotoOrderConfrimClickListener implements OnClickListener {
        private GoodsInfo info;

        public GotoOrderConfrimClickListener(GoodsInfo info) {
            this.info = info;
        }

        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(UserUtil.getUserId(mContext))) {
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
            } else {
                info.setQuantity("1");
                List<GoodsInfo> orderGoods = new ArrayList<GoodsInfo>();
                orderGoods.add(info);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("orderGoodsList", (ArrayList<? extends Parcelable>) orderGoods);
                bundle.putInt("totalPrice", StringUtils.parseInt(info.getGoodsPrice()));
                intent.setClass(mContext, OrderConfrimActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }

        }
    }

}
