package com.example.owen.weathergo.modules.dao;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseViewHolder;
import com.example.owen.weathergo.modules.domain.Weather;
import com.example.owen.weathergo.util.IconGet;

/**
 * Created by owen on 2017/4/24.
 */

public class TodayWeatherHolder extends BaseViewHolder<Weather> {

    private final String TAG = TodayWeatherHolder.class.getSimpleName();
    private Context mContext;
    TextView mTemp_min;
    TextView mTemp_max;
    TextView mCountry;
    TextView mWind_speed;
    TextView mTemp;
    ImageView mImg;
    private Weather weather;

    public TodayWeatherHolder(View view, Weather weather) {
        super(view);
        this.weather = weather;
        mContext = view.getContext();
        mTemp_min = (TextView) view.findViewById(R.id.weather_temp_min);
        mTemp_max = (TextView) view.findViewById(R.id.weather_temp_max);
        mCountry = (TextView) view.findViewById(R.id.weather_air);
        mWind_speed = (TextView) view.findViewById(R.id.weather_wind_speed);
        mTemp = (TextView) view.findViewById(R.id.weather_temp);
        mImg = (ImageView) view.findViewById(R.id.weather_img);
    }

    @Override
    public void bind(Weather weather) {

        try {
            mTemp_min.setText(mContext.getResources().getString(R.string.temp_min)
//                    + weatherBean.getNow_min()
                    + weather.getDailyForecast().get(0).getTmp().getMin()
                    + mContext.getResources().getString(R.string.c));
            mTemp_max.setText(mContext.getResources().getString(R.string.temp_max)
//                    + weatherBean.getNow_max()
                    + weather.getDailyForecast().get(0).getTmp().getMax()
                    + mContext.getResources().getString(R.string.c));
            mWind_speed.setText(mContext.getResources().getString(R.string.wind_speed)
//                    + weatherBean.getNow_dir() + (weatherBean.getNow_sc().equals("微风")?weatherBean.getNow_sc():weatherBean.getNow_sc()
                    + weather.getNow().getWind().getDir()
                    + (weather.getNow().getWind().getSc().equals("微风") ? weather.getNow().getWind().getSc() : weather.getNow().getWind().getSc()
                    + mContext.getResources().getString(R.string.m_s)));
            mTemp.setText(
//                    weatherBean.getNow_tmp()
                    weather.getNow().getTmp()
                            + mContext.getResources().getString(R.string.c));
            mCountry.setText(
//                    weatherBean.getQlty().length() < 2 ? mContext.getResources().getString(R.string.air)
                    weather.getAqi().getCity().getQlty().length() < 2 ? mContext.getResources().getString(R.string.air)
//                    + weatherBean.getQlty() : weatherBean.getQlty());
                            + weather.getAqi().getCity().getQlty() : weather.getAqi().getCity().getQlty());
            mImg.setImageResource(IconGet.getWeaIcon(weather.getNow().getCond().getTxt()));
        } catch (Exception e) {

        }
    }

}
