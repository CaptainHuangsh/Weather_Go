package com.example.owen.weathergo.modules.dao;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseViewHolder;
import com.example.owen.weathergo.component.DLForecast;

import java.util.List;

/**
 * Created by owen on 2017/4/21.
 */

public class DailyWeatherHolder extends BaseViewHolder<List<DLForecast>> {
    private final String TAG = DailyWeatherHolder.class.getSimpleName();
    private Context mContext;
    private List<DLForecast> dlForecastsList;
    private LinearLayout dailyWeather;
    private ImageView img[] = new ImageView[7];
    private TextView dayView[] = new TextView[7];
    private TextView temprView[] = new TextView[7];
    private TextView weamoreView[] = new TextView[7];


    public DailyWeatherHolder(View view, List<DLForecast> dlForecastsList) {
        super(view);
        mContext = view.getContext();
        this.dlForecastsList = dlForecastsList;
        Log.i(TAG + "Constr", "" + dlForecastsList.size());
        dailyWeather = (LinearLayout) itemView.findViewById(R.id.forecast_linear);
        for (int i = 0; i < dlForecastsList.size(); i++) {
            View v = View.inflate(mContext, R.layout.weeklyforecast_items, null);
            img[i] = (ImageView) v.findViewById(R.id.forecast_icon);
            dayView[i] = (TextView) v.findViewById(R.id.forecast_date);
            temprView[i] = (TextView) v.findViewById(R.id.forecast_temp);
            weamoreView[i] = (TextView) v.findViewById(R.id.forecast_txt);
            dailyWeather.addView(v);
        }
    }

    @Override
    public void bind(List<DLForecast> dlForecasts) {
        try {
            dayView[0].setText("今日");
            dayView[1].setText("明日");
            for (int i = 0; i < dlForecasts.size(); i++) {
                if (i > 1) {
                    dayView[i].setText(dlForecasts.get(i).getDay());
                }
                img[i].setImageResource(dlForecasts.get(i).getImageId());
                temprView[i].setText(dlForecasts.get(i).getTempr());
                weamoreView[i].setText(dlForecasts.get(i).getWeamore());
            }
        } catch (Exception e) {

        }

    }


}
