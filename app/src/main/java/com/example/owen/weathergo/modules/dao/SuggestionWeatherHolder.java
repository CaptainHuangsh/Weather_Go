package com.example.owen.weathergo.modules.dao;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseViewHolder;
import com.example.owen.weathergo.modules.domain.Weather;

/**
 * Created by owen on 2017/4/24.
 */

public class SuggestionWeatherHolder extends BaseViewHolder<Weather> {
    private Weather weather;
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

    public SuggestionWeatherHolder(View itemView, Weather weather) {
        super(itemView);
        mContext = itemView.getContext();
        this.weather = weather;
//        this.weatherBean = weatherBean;
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
    public void bind(Weather weather) {

        try {
            suggClothesBrief.setText(weather.getSuggestion().getDrsg().getBrf());
            suggClothesTxt.setText(weather.getSuggestion().getDrsg().getTxt());
            suggSportsBrief.setText(weather.getSuggestion().getSport().getBrf());
            suggSportsTxt.setText(weather.getSuggestion().getSport().getTxt());
            suggTravelBrief.setText(weather.getSuggestion().getTrav().getBrf());
            suggTravelTxt.setText(weather.getSuggestion().getTrav().getTxt());
            suggFluBrief.setText(weather.getSuggestion().getFlu().getBrf());
            suggFluTxt.setText(weather.getSuggestion().getFlu().getTxt());
        } catch (Exception e) {

        }

    }
}
