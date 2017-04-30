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
    private TextView suggClothesBrief;
    private TextView suggClothesTxt;
    private TextView suggSportsBrief;
    private TextView suggSportsTxt;
    private TextView suggTravelBrief;
    private TextView suggTravelTxt;
    private TextView suggFluBrief;
    private TextView suggFluTxt;

    private TextView sugg;

    public SuggestionWeatherHolder(View itemView, WeatherBean weatherBean) {
        super(itemView);
        mContext = itemView.getContext();
        this.weatherBean = weatherBean;
        suggClothesBrief = (TextView) itemView.findViewById(R.id.clothes_brief);
        suggClothesTxt = (TextView) itemView.findViewById(R.id.clothes_txt);
        suggSportsBrief = (TextView) itemView.findViewById(R.id.sports_brief);
        suggSportsTxt = (TextView) itemView.findViewById(R.id.sports_txt);
        suggTravelBrief = (TextView) itemView.findViewById(R.id.travel_brief);
        suggTravelTxt = (TextView) itemView.findViewById(R.id.travel_txt);
        suggFluBrief = (TextView) itemView.findViewById(R.id.flu_brief);
        suggFluTxt = (TextView) itemView.findViewById(R.id.flu_txt);
    }

    @Override
    public void bind(WeatherBean weatherBean) {

        try {
            suggClothesBrief.setText(weatherBean.getDrsg_brf());
            suggClothesTxt.setText(weatherBean.getDrsg_txt());
            suggSportsBrief.setText(weatherBean.getSport_brf());
            suggSportsTxt.setText(weatherBean.getSport_txt());
            suggTravelBrief.setText(weatherBean.getTrav_brf());
            suggTravelTxt.setText(weatherBean.getTrav_txt());
            suggFluBrief.setText(weatherBean.getFlu_brf());
            suggFluTxt.setText(weatherBean.getFlu_txt());
        } catch (Exception e) {

        }

    }
}
