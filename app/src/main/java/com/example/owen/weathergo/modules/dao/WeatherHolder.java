package com.example.owen.weathergo.modules.dao;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by owen on 2017/4/21.
 */

public class WeatherHolder extends BaseViewHolder {

    @BindView(R.id.forecast_icon)
    public ImageView img;
    @BindView(R.id.forecast_date)
    public TextView dayView;
    @BindView(R.id.forecast_temp)
    public TextView temprView;
    @BindView(R.id.forecast_txt)
    public TextView weamoreView;


    public WeatherHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
//        /*img = (ImageView) view.findViewById(R.id.forecast_icon);
//        dayView = (TextView) view.findViewById(R.id.forecast_date);
//        temprView = (TextView) view.findViewById(R.id.forecast_temp);
//        weamoreView = (TextView) view.findViewById(R.id.forecast_txt);*/
    }

    @Override
    protected void bind(Object o) {

    }

}
