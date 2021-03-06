package com.example.owen.weathergo.modules.dao;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseViewHolder;
import com.example.owen.weathergo.modules.domain.Weather;

/**
 * Created by owen on 2017/4/24.
 */


public class HourlyWeatherHolder extends BaseViewHolder<Weather> {
    private TextView[] hourlyClock = new TextView[12];//早晨出现fc，问题在初始化数组长度不足
    private TextView[] hourlyTemp = new TextView[12];
    private TextView[] hourlyHumidity = new TextView[12];
    private TextView[] hourlyWind = new TextView[12];
    private Context mContext;
    private LinearLayout hourlyWeather;
    private Weather weather;

    public HourlyWeatherHolder(View itemView, Weather weather) {
        super(itemView);
        this.weather = weather;
        mContext = itemView.getContext();
        hourlyWeather = (LinearLayout) itemView.findViewById(R.id.hourly_forecast_linear);
        for (int i = 0; i < weather.getHourlyForecast().size() + 1; i++) {
            if (weather.getHourlyForecast().size() > 0) {
                View v = View.inflate(mContext, R.layout.items_hour_forecasts, null);
                hourlyClock[i] = (TextView) v.findViewById(R.id.hourly_clock);
                hourlyTemp[i] = (TextView) v.findViewById(R.id.hourly_temp);
                hourlyHumidity[i] = (TextView) v.findViewById(R.id.hourly_humidity);
                hourlyWind[i] = (TextView) v.findViewById(R.id.hourly_wind);
                hourlyWeather.addView(v);
            }
        }
    }

    @Override
    public void bind(Weather weather) {
        try {
            for (int i = 0; i < weather.getHourlyForecast().size() + 1; i++) {
                //添加表头信息后会多出一行信息
                if (i == 0) {
                    if (weather.getHourlyForecast().size() > 0) {
                        hourlyClock[i].setText(mContext.getResources().getString(R.string.TIME));
                        hourlyTemp[i].setText(mContext.getResources().getString(R.string.TEMP));
                        hourlyHumidity[i].setText(mContext.getResources().getString(R.string.HUM));
                        hourlyWind[i].setText(mContext.getResources().getString(R.string.WIND));
                    }
                } else {
                    String mDate = weather.getHourlyForecast().get(i - 1).getDate();
                    hourlyClock[i].setText(mDate.substring(mDate.length() - 5
                            , mDate.length()));//截取时间，不需要日期
                    hourlyTemp[i].setText(String.format("%s℃", weather.getHourlyForecast().get(i - 1).getTmp()));
                    hourlyHumidity[i].setText(String.format("%s%%", weather.getHourlyForecast().get(i - 1).getHum()));
                    hourlyWind[i].setText(String.format("%sKm/h", weather.getHourlyForecast().get(i - 1).getWind().getSpd()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
