package com.example.owen.weathergo.modules.dao;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseViewHolder;

/**
 * Created by owen on 2017/4/24.
 */


public class HourlyWeatherHolder extends BaseViewHolder {
    private TextView[] hourlytxt = new TextView[3];
    private Context mContext;
    private LinearLayout hourlyWeather;

    public HourlyWeatherHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        hourlyWeather = (LinearLayout) itemView.findViewById(R.id.hourly_forecast_linear);
        for (int i = 0; i < 3; i++) {
            View v = View.inflate(mContext, R.layout.items_hourly_forecast, null);
            Log.i("HourlyWeatherHolderConstr", "" + i);
            hourlytxt[i] = (TextView) v.findViewById(R.id.hourly_forecast_i1);
            hourlyWeather.addView(v);
        }
    }

    @Override
    public void bind(Object o) {
        try {
            for (int i = 0; i < 3; i++) {

                hourlytxt[i].setText("Hourly Forecast Here Later");
            }
        } catch (Exception e) {

        }

    }

}
