package com.example.owen.weathergo.modules.dao;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owen.weathergo.R;

import butterknife.BindView;

/**
 * Created by owen on 2017/4/21.
 */

public class WeatherHolder extends RecyclerView.ViewHolder {

    public ImageView img;
    public TextView dayView;
    public TextView temprView;
    public TextView weamoreView;

    public WeatherHolder(View view) {
        super(view);
        img = (ImageView) view.findViewById(R.id.forecast_icon);
        dayView = (TextView) view.findViewById(R.id.forecast_date);
        temprView = (TextView) view.findViewById(R.id.forecast_temp);
        weamoreView = (TextView) view.findViewById(R.id.forecast_txt);
    }

}
