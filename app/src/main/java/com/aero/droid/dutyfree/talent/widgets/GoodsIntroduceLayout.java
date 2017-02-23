package com.aero.droid.dutyfree.talent.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.util.StringUtils;
import com.aero.droid.dutyfree.talent.util.UiUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author : wangxp
 * Date : 2015/12/11
 * Desc : 商品介绍(商品详情中间部分)
 */
public class GoodsIntroduceLayout extends LinearLayout {
    private Context mContext;
    private int mWidth;

    public GoodsIntroduceLayout(Context context) {
        super(context);
        init(context);
    }

    public GoodsIntroduceLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GoodsIntroduceLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public GoodsIntroduceLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setOrientation(VERTICAL);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWidth = manager.getDefaultDisplay().getWidth();
    }

    public void setGoodDetail(GoodsDetail detail) {
        View goodIntroduce = View.inflate(mContext, R.layout.view_goods_introduct, null);
        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params.topMargin = UiUtil.dip2px(mContext,10);
        goodIntroduce.setLayoutParams(params);
        //价格风向标
        LinearLayout priceLayout = (LinearLayout) goodIntroduce.findViewById(R.id.goods_good_price_desc);
        initNewPriceLayout(detail,priceLayout);
        //商品介绍
        TextView goodDesc = (TextView) goodIntroduce.findViewById(R.id.goods_good_desc);
        goodDesc.setText(detail.getGoodsDes());
        //商品规格
        LinearLayout goodStandard = (LinearLayout) goodIntroduce.findViewById(R.id.goods_good_standard);
        try {
            JSONArray propArray = new JSONArray(detail.getProp());
            for (int i = 0; i < propArray.length(); i++) {
                JSONObject prop = propArray.getJSONObject(i);
                View itemView = View.inflate(mContext, R.layout.item_goods_standard, null);
                TextView keyTv = (TextView) itemView.findViewById(R.id.tv_standard_key);
                keyTv.setText(prop.optString("key"));
                TextView valueTv = (TextView) itemView.findViewById(R.id.tv_standard_value);
                valueTv.setText(prop.optString("value"));
                goodStandard.addView(itemView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //品牌介绍
        TextView brandDesc = (TextView) goodIntroduce.findViewById(R.id.goods_brand_desc);
        brandDesc.setText(detail.getMarkDes());
        //商品状态
        TextView goodStatus = (TextView) goodIntroduce.findViewById(R.id.goods_good_tatus);
        if ("0".equals(detail.getStatus())){
            goodStatus.setText(getResources().getString(R.string.goods_status_0));
        }else if ("1".equals(detail.getStatus())){
            goodStatus.setText(getResources().getString(R.string.goods_status_1));
        }
        addView(goodIntroduce);

    }

    //初始化新 价格风向标界面
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initNewPriceLayout(GoodsDetail goodsDetail,LinearLayout priceLayout) {
        //
        View appview = View.inflate(mContext, R.layout.item_new_price, null);
        TextView appShopName = (TextView) appview.findViewById(R.id.detail_new_price_shop);
        TextView appPrice = (TextView) appview.findViewById(R.id.detail_new_price_price);
        ImageView appLine = (ImageView) appview.findViewById(R.id.detail_new_price_line);
        ViewGroup.LayoutParams appParams = appLine.getLayoutParams();
        appParams.width = (int) mWidth - UiUtil.dip2px(mContext, 150);
        appParams.height = UiUtil.dip2px(mContext, 10);
        appLine.setLayoutParams(appParams);
        appShopName.setText(R.string.schedule_now);
        appPrice.setText("＄" + goodsDetail.getPrice_app_dollar());
        priceLayout.addView(appview);
        if (!TextUtils.isEmpty(goodsDetail.getPriceIndicator())) {
            try {
                JSONArray priceArray = new JSONArray(goodsDetail.getPriceIndicator());
                //如果有其他商家数据，以最高商家价格算价格条百分比显示
                int maxPrice = StringUtils.parseInt(priceArray.getJSONObject(priceArray.length() - 1).optString("maxMoney"));
                appParams.width = ((int) mWidth - UiUtil.dip2px(mContext, 150)) * StringUtils.parseInt(goodsDetail.getPrice_app_dollar()) / maxPrice;
                appLine.setLayoutParams(appParams);
                for (int i = 0; i < priceArray.length(); i++) {
                    JSONObject priceItem = priceArray.getJSONObject(i);
                    View view = View.inflate(mContext, R.layout.item_new_price, null);
                    TextView shopName = (TextView) view.findViewById(R.id.detail_new_price_shop);
                    TextView price = (TextView) view.findViewById(R.id.detail_new_price_price);
                    ImageView line = (ImageView) view.findViewById(R.id.detail_new_price_line);
                    ImageView sort = (ImageView) view.findViewById(R.id.detail_new_price_sort);

                    shopName.setText(priceItem.optString("eBusiness"));
                    price.setText("＄" + priceItem.optString("maxMoney"));
                    line.setBackground(getResources().getDrawable(R.drawable.radius_gradient_gray_bg));
                    ViewGroup.LayoutParams params = line.getLayoutParams();
                    params.width = ((int) mWidth - UiUtil.dip2px(mContext, 150)) * StringUtils.parseInt(priceItem.optString("maxMoney")) / maxPrice;
                    params.height = UiUtil.dip2px(mContext, 10);
                    line.setLayoutParams(params);
                    sort.setVisibility(View.GONE);
                    priceLayout.addView(view);
                }
                if (!TextUtils.isEmpty(goodsDetail.getShowPriceDesc()) && "1".equals(goodsDetail.getShowPriceDesc())) {
                    LinearLayout.LayoutParams descParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    descParams.leftMargin = UiUtil.dip2px(mContext, 15);
                    descParams.rightMargin = UiUtil.dip2px(mContext, 15);
                    descParams.topMargin = UiUtil.dip2px(mContext, 10);
                    WebView webView = new WebView(mContext);
                    webView.setLayoutParams(descParams);
                    webView.loadDataWithBaseURL(null, goodsDetail.getPriceDesc(), "text/html", "utf-8", null);
                    priceLayout.addView(webView);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
