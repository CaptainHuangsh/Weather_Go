package com.example.owen.weathergo.modules.dao;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseViewHolder;
import com.example.owen.weathergo.component.DLForecast;

import java.util.List;

/**
 * Created by owen on 2017/4/24.
 */

public class TodayWeatherHolder extends BaseViewHolder<WeatherBean> {

    private final String TAG = TodayWeatherHolder.class.getSimpleName();
    private Context mContext;
    private List<DLForecast> dlForecasts;
    TextView mTemp_min;
    TextView mTemp_max;
    TextView mCountry;
    TextView mWind_speed;
    TextView mTemp;
    private WeatherBean weatherBean;

    public TodayWeatherHolder(View view, WeatherBean weatherBean) {
        super(view);
        this.weatherBean = weatherBean;
        mContext = view.getContext();
        mTemp_min = (TextView) view.findViewById(R.id.weather_temp_min);
        mTemp_max = (TextView) view.findViewById(R.id.weather_temp_max);
        mCountry = (TextView) view.findViewById(R.id.weather_country);
        mWind_speed = (TextView) view.findViewById(R.id.weather_wind_speed);
        mTemp = (TextView) view.findViewById(R.id.weather_temp);
    }

    @Override
    public void bind(WeatherBean weatherBean) {

        try {
            mTemp_min.setText(mContext.getResources().getString(R.string.hsh_temp_min)
                    + weatherBean.getNow_min()
                    + mContext.getResources().getString(R.string.c));
            mTemp_max.setText(mContext.getResources().getString(R.string.hsh_temp_max)
                    + weatherBean.getNow_max()
                    + mContext.getResources().getString(R.string.c));
            mWind_speed.setText(mContext.getResources().getString(R.string.hsh_wind_speed)
                    + weatherBean.getNow_dir() + weatherBean.getNow_sc()
                    + mContext.getResources().getString(R.string.m_s));
            mTemp.setText(weatherBean.getNow_tmp()
                    + mContext.getResources().getString(R.string.c));
            mCountry.setText(mContext.getResources().getString(R.string.hsh_country)
                    + weatherBean.getCnty());
        } catch (Exception e) {

        }
    }

}
