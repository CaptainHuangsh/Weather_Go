package com.example.owen.weathergo.modules.dao;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * Created by owen on 2017/4/24.
 */


public class HourlyWeatherHolder extends BaseViewHolder<ArrayList<HourlyForecast>> {
    private TextView[] hourlyClock = new TextView[10];//早晨出现fc，问题在初始化数组长度不足
    private TextView[] hourlyTemp = new TextView[10];
    private TextView[] hourlyHumidity = new TextView[10];
    private TextView[] hourlyWind = new TextView[10];
    private Context mContext;
    private LinearLayout hourlyWeather;

    public HourlyWeatherHolder(View itemView, ArrayList<HourlyForecast> hourlyForecastList) {
        super(itemView);
        mContext = itemView.getContext();
        hourlyWeather = (LinearLayout) itemView.findViewById(R.id.hourly_forecast_linear);
        for (int i = 0; i < hourlyForecastList.size(); i++) {
            View v = View.inflate(mContext, R.layout.items_hour_forecasts, null);
            hourlyClock[i] = (TextView) v.findViewById(R.id.hourly_clock);
            hourlyTemp[i] = (TextView) v.findViewById(R.id.hourly_temp);
            hourlyHumidity[i] = (TextView) v.findViewById(R.id.hourly_humidity);
            hourlyWind[i] = (TextView) v.findViewById(R.id.hourly_wind);
            hourlyWeather.addView(v);
        }
    }

    @Override
    public void bind(ArrayList<HourlyForecast> hourlyForecastList) {
        try {
            for (int i = 0; i < hourlyForecastList.size(); i++) {
                if (i == 0) {
                    hourlyClock[i].setText("时间");
                    hourlyTemp[i].setText("温度");
                    hourlyHumidity[i].setText("湿度");
                    hourlyWind[i].setText("风力");
                } else {
                    String mDate = hourlyForecastList.get(i).getDate();
                    hourlyClock[i].setText(mDate.substring(mDate.length() - 5
                            , mDate.length()));//截取时间，不需要日期
                    hourlyTemp[i].setText(String.format("%s℃", hourlyForecastList.get(i).getTmp()));
                    hourlyHumidity[i].setText(String.format("%s%%", hourlyForecastList.get(i).getHum()));
                    hourlyWind[i].setText(String.format("%sKm/h", hourlyForecastList.get(i).getSpd()));
                }
            }
        } catch (Exception e) {

        }

    }


}
