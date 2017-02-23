package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.bean.FlightInfo;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/12/16
 * Desc : 查询航班详情
 */
public class AirportDetailActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.airport_detail_airport)
    TextView airportTv;
    @Bind(R.id.airport_detail_airport_no)
    TextView airportNoTv;
    @Bind(R.id.airport_detail_start_city)
    TextView startCityTv;
    @Bind(R.id.airport_detail_start_time)
    TextView startTimeTv;
    @Bind(R.id.airport_detail_start_date)
    TextView startDateTv;
    @Bind(R.id.airport_detail_end_city)
    TextView endCityTv;
    @Bind(R.id.airport_detail_end_time)
    TextView endTimeTv;
    @Bind(R.id.airport_detail_end_date)
    TextView endDateTv;
    @Bind(R.id.airport_detail_layout)
    LinearLayout airportDetailLayout;
    @Bind(R.id.confrim_tv)
    TextView confrimTv;
    @Bind(R.id.confrim_layout)
    LinearLayout confrimLayout;
    /******************************************/
    private FlightInfo flightInfo;

    @Override
    protected void getBundleExtras(Bundle extras) {
        flightInfo = extras.getParcelable("flightInfo");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_airport_detail;
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
        initTitle(getResources().getString(R.string.order_confrim_check_airport));
        confrimLayout.setOnClickListener(this);

        airportTv.setText(flightInfo.getAirline());
        airportNoTv.setText(flightInfo.getFlightNo());
        startCityTv.setText(flightInfo.getDepart());
        startDateTv.setText(flightInfo.getDepartDate());
        startTimeTv.setText(flightInfo.getDepartTime());
        endCityTv.setText(flightInfo.getArrive());
        endDateTv.setText(flightInfo.getArriveDate());
        endTimeTv.setText(flightInfo.getArriveTime());
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
            case R.id.confrim_layout:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
