package com.example.owen.weathergo.modules.dao;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.base.BaseViewHolder;
import com.example.owen.weathergo.modules.main.domain.Weather;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by owen on 2017/4/24.
 */

public class WeatherHolder extends BaseViewHolder<WeatherBean> {

    private Context mContext;
    @BindView(R.id.weather_temp_min)
    TextView mTemp_min;
    @BindView(R.id.weather_temp_max)
    TextView mTemp_max;
    @BindView(R.id.weather_country)
    TextView mCountry;
    @BindView(R.id.weather_wind_speed)
    TextView mWind_speed;
    @BindView(R.id.weather_temp)
    TextView mTemp;
    /*mTemp_min.setText(getResources().getString(R.string.hsh_temp_min)
                            + dfs.getMin()
                                    + getResources().getString(R.string.c));
                    mTemp_max.setText(getResources().getString(R.string.hsh_temp_max)
                            + dfs.getMax()
                                    + getResources().getString(R.string.c));
                    mCountry.setText(dfs.getTxt_d() + "转" + dfs.getTxt_n());*/
    private WeatherBean weatherBean;

    public WeatherHolder(View view, WeatherBean weatherBean) {
        super(view);
        this.weatherBean = weatherBean;
    }

    @Override
    public void bind(WeatherBean weatherBean) {
        try {
            mTemp_min.setText(mContext.getResources().getString(R.string.hsh_temp_min)
                    + weatherBean.getNow_tmp()
                    + mContext.getResources().getString(R.string.c));
            mTemp_max.setText(mContext.getResources().getString(R.string.hsh_temp_max)
                    + weatherBean.getNow_tmp()
                    + mContext.getResources().getString(R.string.c));
            mWind_speed.setText(mContext.getResources().getString(R.string.hsh_wind_speed)
                    + weatherBean.getNow_dir() + weatherBean.getNow_sc()
                    + mContext.getResources().getString(R.string.m_s));
            mTemp.setText(weatherBean.getNow_tmp()
                    + mContext.getResources().getString(R.string.c));
            mCountry.setText(mContext.getResources().getString(R.string.hsh_country)
                    + weatherBean.getCnty());
        }catch (Exception e){

        }
    }

}
