package com.aero.droid.dutyfree.talent.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.widgets.textcounter.CounterView;

public class PriceView extends FrameLayout {
    private String TAG = PriceView.class.getSimpleName();
    // 价格变动View
    private int marketPrice = 6000; // 市场价
    private int airportPrice = 4589; // 机上价
    private int onlinePrice = 3878; // 线上价
    private int dollarMarketPrice = 1000; // 市场价
    private int dollarAirportPrice = 749; // 机上价
    private int dollarOnlinePrice = 550; // 线上价
    private int curOnlinePrice, curDollarOnlinePrice; // 当前显示的线上价/线上美元价
    private float curAirportPercent, curOnlinePercent; // 当前机场价百分比/线上价百分比
    private boolean priceFirst = true;
    private boolean bgFirst = true;
    private CounterView price, dollarPrice;
    private TextView airportPriceTv, onlinePriceTv, marketPriceTv, background1,
            background2, background3;
    private int appColor, airportColor;
    private int defaultColor = Color.parseColor("#FF7900");
    private Context mContext;
    protected float mWidth = 0;

    private boolean isAnim = false;//

    public PriceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public PriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public PriceView(Context context) {
        super(context);
    }


    private void init(Context context,AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.PriceViewColor);
        appColor = typedArray.getColor(R.styleable.PriceViewColor_app_color,
                defaultColor);
        airportColor = typedArray.getColor(
                R.styleable.PriceViewColor_airport_color, defaultColor);
        mContext = context;
        mWidth = UiUtil.getWindowWidth(mContext);
        View view = View.inflate(mContext, R.layout.price_animation_layout,
                null);
        initPriceViews(view);
        this.addView(view);
    }

    protected void initPriceViews(View view) {
        // 价格变动View
        price = (CounterView) view.findViewById(R.id.animation_price);
        dollarPrice = (CounterView) view
                .findViewById(R.id.animation_dollar_price);
        airportPriceTv = (TextView) view
                .findViewById(R.id.animation_airport_price);
        marketPriceTv = (TextView) view
                .findViewById(R.id.animation_market_price);
        onlinePriceTv = (TextView) view.findViewById(R.id.animation_app_price);
        background1 = (TextView) view.findViewById(R.id.background1);
        background2 = (TextView) view.findViewById(R.id.background2);
        background3 = (TextView) view.findViewById(R.id.background3);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                UiUtil.dip2px(mContext, 50), LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int) (mWidth - UiUtil.dip2px(mContext, 50));
        marketPriceTv.setLayoutParams(params);
    }

    public void changViews() {
        Drawable airportDrawable = getResources().getDrawable(
                R.mipmap.airport_normal);
        airportDrawable.setBounds(0, 0, airportDrawable.getMinimumWidth(),
                airportDrawable.getMinimumHeight());
        airportPriceTv.setCompoundDrawables(null, null, null, airportDrawable);
        airportPriceTv.setTextColor(Color.WHITE);
        Drawable onlineDrawable = getResources().getDrawable(
                R.mipmap.phone_normal);
        onlineDrawable.setBounds(0, 0, onlineDrawable.getMinimumWidth(),
                onlineDrawable.getMinimumHeight());
        onlinePriceTv.setCompoundDrawables(null, onlineDrawable, null, null);
        onlinePriceTv.setTextColor(Color.WHITE);
        Drawable marketDrawable = getResources().getDrawable(
                R.mipmap.market_normal);
        marketDrawable.setBounds(0, 0, marketDrawable.getMinimumWidth(),
                marketDrawable.getMinimumHeight());
        marketPriceTv.setCompoundDrawables(null, marketDrawable, null, null);
        marketPriceTv.setTextColor(Color.WHITE);
    }

    /**
     * @param marketPrice  市场价
     * @param airportPrice 机上价
     * @param onlinePrice  线上价
     * @param airportMs    机上动画时间
     * @param onlineMs     线上动画时间
     */
    public void setBackgroundAnimation(int marketPrice, int airportPrice,
                                       int onlinePrice, int airportMs, int onlineMs) {
        float airportPercent = marketPrice == 0 ? 0 : (float) airportPrice / (float) marketPrice;
        float onlinePercent = marketPrice == 0 ? 0 : (airportPercent - (1 - (float) onlinePrice / (float) airportPrice));
        if (bgFirst) {
            LogUtil.v(TAG, "setBackgroundAnimation = first");
            airportPriceTv.setVisibility(View.INVISIBLE);
            onlinePriceTv.setVisibility(View.INVISIBLE);
            Animation animation2 = new ScaleAnimation(1.0f, airportPercent,
                    1.0f, 1.0f);
            animation2.setDuration(airportMs);
            animation2.setFillAfter(true);
            background2.startAnimation(animation2);
            Animation animation3 = new ScaleAnimation(1.0f, onlinePercent,
                    1.0f, 1.0f);
            animation3.setDuration(onlineMs);
            animation3.setFillAfter(true);
            // background3.
            background3.startAnimation(animation3);
            curAirportPercent = airportPercent;
            curOnlinePercent = onlinePercent;
            bgFirst = false;
        } else {
            Animation animation2 = new ScaleAnimation(curAirportPercent,
                    airportPercent, 1.0f, 1.0f);
            animation2.setDuration(airportMs);
            animation2.setFillAfter(true);
            background2.startAnimation(animation2);
            Animation animation3 = new ScaleAnimation(curOnlinePercent,
                    onlinePercent, 1.0f, 1.0f);
            animation3.setDuration(onlineMs);
            animation3.setFillAfter(true);
            background3.startAnimation(animation3);
            curAirportPercent = airportPercent;
            curOnlinePercent = onlinePercent;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                UiUtil.dip2px(mContext, 50), LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int) ((mWidth - UiUtil.dip2px(mContext, 50))
                * curAirportPercent - UiUtil.dip2px(mContext, 40 / 2 - 20));
        LogUtil.v(TAG, "leftMargin = " + params.leftMargin
                + "   curAirportPercent = " + curAirportPercent);
        airportPriceTv.setLayoutParams(params);
        airportPriceTv.postDelayed(new Runnable() {

            @Override
            public void run() {
                // LinearLayout.LayoutParams params = new
                // LinearLayout.LayoutParams(CommonUtil.dip2px(mContext,
                // 40), LayoutParams.WRAP_CONTENT);
                // params.leftMargin = (int) ((mWidth -
                // CommonUtil.dip2px(mContext, 40)) * curAirportPercent -
                // CommonUtil.dip2px(mContext, 40 / 2 - 20));
                // LogUtil.v(TAG, "leftMargin = " + params.leftMargin +
                // "   curAirportPercent = " + curAirportPercent);
                // airportPriceTv.setLayoutParams(params);
                airportPriceTv.setVisibility(View.VISIBLE);
                LogUtil.t("airportPriceTv.VISIBLE");
            }
        }, airportMs);
        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(
                UiUtil.dip2px(mContext, 50), LayoutParams.WRAP_CONTENT);
        params1.leftMargin = (int) ((mWidth - UiUtil.dip2px(mContext, 50))
                * curOnlinePercent - UiUtil.dip2px(mContext, 40 / 2 - 20));
        LogUtil.v(TAG, "leftMargin = " + params1.leftMargin
                + "   curOnlinePercent = " + curOnlinePercent);
        onlinePriceTv.setLayoutParams(params1);
        onlinePriceTv.postDelayed(new Runnable() {

            @Override
            public void run() {
                // FrameLayout.LayoutParams params = new
                // FrameLayout.LayoutParams(CommonUtil.dip2px(mContext,
                // 40), LayoutParams.WRAP_CONTENT);
                // params.leftMargin = (int) ((mWidth -
                // CommonUtil.dip2px(mContext, 40)) * curOnlinePercent -
                // CommonUtil.dip2px(
                // mContext, 40 / 2 - 20));
                // LogUtil.v(TAG,"leftMargin = " + params.leftMargin +
                // "   curOnlinePercent = " + curOnlinePercent);
                // onlinePriceTv.setLayoutParams(params);
                onlinePriceTv.setVisibility(View.VISIBLE);
                LogUtil.t("onlinePriceTv.VISIBLE");
            }
        }, onlineMs);
    }

    /**
     *
     * @param airportPrice
     *            机上价
     * @param onlinePrice
     *            线上价
     * @param dollarAirportPrice
     *            美元 机上价
     * @param dollarOnlinePrice
     *            美元 线上价
     */
//	public void setPrice(final int airportPrice, final int onlinePrice,
//			final int dollarAirportPrice, final int dollarOnlinePrice) {
//		if (priceFirst) {
//			price.setStartValue(airportPrice);
//			price.setEndValue(onlinePrice);
//			dollarPrice.setStartValue(dollarAirportPrice);
//			dollarPrice.setEndValue(dollarOnlinePrice);
//			price.setIncrement((onlinePrice - airportPrice) / 30);
//			dollarPrice
//					.setIncrement((dollarOnlinePrice - dollarAirportPrice) / 30);
//			curOnlinePrice = onlinePrice;
//			curDollarOnlinePrice = dollarOnlinePrice;
//			priceFirst = false;
//		} else {
//			price.setStartValue(curOnlinePrice);
//			price.setEndValue(onlinePrice);
//			dollarPrice.setStartValue(curDollarOnlinePrice);
//			dollarPrice.setEndValue(dollarOnlinePrice);
//			price.setIncrement((onlinePrice - curOnlinePrice) / 30);
//			dollarPrice
//					.setIncrement((dollarOnlinePrice - curDollarOnlinePrice) / 30);
//			curOnlinePrice = onlinePrice;
//			curDollarOnlinePrice = dollarOnlinePrice;
//		}
//
//		price.setAutoStart(false);
//		price.setTimeInterval(50);
//		price.setPrefix("＄");
//		price.setSuffix(".00");
//		price.start();
//
//		dollarPrice.setAutoStart(false);
//		dollarPrice.setTimeInterval(50);
//		dollarPrice.setPrefix("＄");
//		dollarPrice.setSuffix(".00");
//		dollarPrice.start();
//
//	}

    /**
     * @param dollarAirportPrice 美元机上价
     * @param dollarOnlinePrice  美元线上价
     * @param marketDollarPrice  美元市场价
     */
    public void setPrice(final int dollarOnlinePrice, final int dollarAirportPrice, int marketDollarPrice) {
        if (priceFirst) {
//			price.setStartValue(marketDollarPrice);
//			price.setEndValue(dollarOnlinePrice);
//			price.setIncrement((dollarOnlinePrice - marketDollarPrice) / 30);
//			
//			dollarPrice.setStartValue(marketDollarPrice);
//			dollarPrice.setEndValue(dollarAirportPrice);
//			dollarPrice.setIncrement((dollarAirportPrice - marketDollarPrice) / 30);
            price.setStartValue(dollarOnlinePrice + 150);
            price.setEndValue(dollarOnlinePrice);
            price.setIncrement(-5);

            dollarPrice.setStartValue(dollarAirportPrice + 150);
            dollarPrice.setEndValue(dollarAirportPrice);
            dollarPrice.setIncrement(-5);

            curOnlinePrice = dollarOnlinePrice;
            curDollarOnlinePrice = dollarAirportPrice;
            priceFirst = false;
        } else {
            price.setStartValue(curOnlinePrice);
            price.setEndValue(dollarOnlinePrice);
            dollarPrice.setStartValue(curDollarOnlinePrice);
            dollarPrice.setEndValue(dollarAirportPrice);
            price.setIncrement((dollarOnlinePrice - curOnlinePrice) / 30);
            dollarPrice
                    .setIncrement((dollarAirportPrice - curDollarOnlinePrice) / 30);
            curOnlinePrice = dollarOnlinePrice;
            curDollarOnlinePrice = dollarAirportPrice;
        }
        price.setTextColor(appColor);
        price.setAutoStart(false);
        price.setTimeInterval(45);
        price.setPrefix("＄");
        price.setSuffix(".00");
        price.start();

        dollarPrice.setTextColor(airportColor);
        dollarPrice.setAutoStart(false);
        dollarPrice.setTimeInterval(45);
        dollarPrice.setPrefix("＄");
        dollarPrice.setSuffix(".00");
        dollarPrice.start();

    }

    /**
     * 开始播放动画
     */
    public void start() {
        setBackgroundAnimation(marketPrice, airportPrice, onlinePrice, 1000,
                1500);
    }

    public void setBackgroundAnimation(int marketPrice, int airportPrice,
                                       int onlinePrice) {
        setBackgroundAnimation(marketPrice, airportPrice, onlinePrice, 1000,
                1500);
    }

    public void setBackgroundAnimation2(int marketPrice, int airportPrice,
                                        int onlinePrice, boolean priceFirst) {
        this.priceFirst = priceFirst;
        setBackgroundAnimation(marketPrice, airportPrice, onlinePrice, 1000,
                1500);
    }

//	public void setPrice(final int airportPrice, final int onlinePrice,
//			final int dollarAirportPrice, final int dollarOnlinePrice,
//			boolean bgFirst) {
//		this.bgFirst = bgFirst;
//		setPrice(airportPrice, onlinePrice, dollarAirportPrice,
//				dollarOnlinePrice);
//	}

    public void setPrice(int dollarOnlinePrice, int dollarAirportPrice, int marketDollarPrice, boolean bgFirst) {
        this.bgFirst = bgFirst;
        setPrice(dollarOnlinePrice, dollarAirportPrice, marketDollarPrice);
    }

    public int getOnlinePrice() {
        return onlinePrice;
    }

    public void setOnlinePrice(int onlinePrice) {
        this.onlinePrice = onlinePrice;
    }

    public CounterView getDollarPrice() {
        return dollarPrice;
    }

    public void setDollarPrice(CounterView dollarPrice) {
        this.dollarPrice = dollarPrice;
    }

    public TextView getOnlinePriceTv() {
        return onlinePriceTv;
    }

    public void setOnlinePriceTv(TextView onlinePriceTv) {
        this.onlinePriceTv = onlinePriceTv;
    }

    public int getDollarMarketPrice() {
        return dollarMarketPrice;
    }

    public void setDollarMarketPrice(int dollarMarketPrice) {
        this.dollarMarketPrice = dollarMarketPrice;
    }

    public int getDollarAirportPrice() {
        return dollarAirportPrice;
    }

    public void setDollarAirportPrice(int dollarAirportPrice) {
        this.dollarAirportPrice = dollarAirportPrice;
    }

    public int getDollarOnlinePrice() {
        return dollarOnlinePrice;
    }

    public void setDollarOnlinePrice(int dollarOnlinePrice) {
        this.dollarOnlinePrice = dollarOnlinePrice;
    }

}
