package com.example.owen.weathergo.modules.dao;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseViewHolder;
import com.example.owen.weathergo.util.IconGet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owen on 2017/4/21.
 */

public class DailyWeatherHolder extends BaseViewHolder<ArrayList<DailyForecast>> {
    private final String TAG = DailyWeatherHolder.class.getSimpleName();
    private Context mContext;
    private List<DLForecast> dlForecastsList;
    ArrayList<DailyForecast> mDFList;
    private LinearLayout dailyWeather;
    private ImageView img[] = new ImageView[7];
    private TextView dayView[] = new TextView[7];
    private TextView temprView[] = new TextView[7];
    private TextView weamoreView[] = new TextView[7];


    public DailyWeatherHolder(View view, ArrayList<DailyForecast> mDFList) {
        super(view);
        mContext = view.getContext();
        this.mDFList = mDFList;
        this.dlForecastsList = dlForecastsList;
        dailyWeather = (LinearLayout) itemView.findViewById(R.id.forecast_linear);
        for (int i = 0; i < mDFList.size(); i++) {
            View v = View.inflate(mContext, R.layout.items_weeklyforecast, null);
            img[i] = (ImageView) v.findViewById(R.id.forecast_icon);
            dayView[i] = (TextView) v.findViewById(R.id.forecast_date);
            temprView[i] = (TextView) v.findViewById(R.id.forecast_temp);
            weamoreView[i] = (TextView) v.findViewById(R.id.forecast_txt);
            dailyWeather.addView(v);
        }
    }

    @Override
    public void bind(ArrayList<DailyForecast> mDFList) {
        try {
            dayView[0].setText("今日");
            dayView[1].setText("明日");

            for (int i = 0; i < mDFList.size(); i++) {
                if (i > 1) {
                    dayView[i].setText(mDFList.get(i).getDate() + "");
                }
                img[i].setImageResource(IconGet.getWeaIcon(mDFList.get(i)
                        .getTxt_d()));
                temprView[i].setText(mContext.getResources().getString(R.string.hsh_temp_min)
                        + mDFList.get(i).getMin()
                        + mContext.getResources().getString(R.string.c)
                        + mContext.getResources().getString(R.string.hsh_temp_max)
                        + mDFList.get(i).getMax()
                        + mContext.getResources().getString(R.string.c));
                weamoreView[i].setText(mDFList.get(i).getDir() + mDFList.get(i).getSc()
                        + mDFList.get(i).getTxt_d() + mDFList.get(i).getTxt_n());
            }

            /*for (int i = 0; i < dlForecasts.size(); i++) {
                if (i > 1) {
                    dayView[i].setText(dlForecasts.get(i).getDay());
                }
                img[i].setImageResource(dlForecasts.get(i).getImageId());
                temprView[i].setText(dlForecasts.get(i).getTempr());
                weamoreView[i].setText(dlForecasts.get(i).getWeamore());*//*
            }*/
        } catch (Exception e) {

        }

    }


}
