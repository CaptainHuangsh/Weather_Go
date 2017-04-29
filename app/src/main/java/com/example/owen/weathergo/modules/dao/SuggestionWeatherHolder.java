package com.example.owen.weathergo.modules.dao;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseViewHolder;

/**
 * Created by owen on 2017/4/24.
 */

public class SuggestionWeatherHolder extends BaseViewHolder<WeatherBean> {
    private WeatherBean weatherBean;
    private Context mContext;
    private TextView sugg;

    public SuggestionWeatherHolder(View itemView, WeatherBean weatherBean) {
        super(itemView);
        mContext = itemView.getContext();
        this.weatherBean = weatherBean;
        sugg = (TextView) itemView.findViewById(R.id.weather_suggesstions);
    }

    @Override
    public void bind(WeatherBean weatherBean) {

        try {
            sugg.setText(weatherBean.getComf_brf() + weatherBean.getComf_txt());
        } catch (Exception e) {

        }

    }
}
