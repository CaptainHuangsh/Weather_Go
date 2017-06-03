package com.example.owen.weathergo.modules.dao;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseViewHolder;
import com.example.owen.weathergo.modules.domain.Weather;
import com.example.owen.weathergo.util.IconGet;

/**
 * Created by owen on 2017/4/21.
 */

public class DailyWeatherHolder extends BaseViewHolder<Weather> {
    private final String TAG = DailyWeatherHolder.class.getSimpleName();
    private Context mContext;
    private LinearLayout dailyWeather;
    private ImageView img[] = new ImageView[7];
    private ImageView imgN[] = new ImageView[7];
    private TextView dayView[] = new TextView[7];
    private TextView tempView[] = new TextView[7];
    private TextView moreInfoView[] = new TextView[7];
    private Weather weather;


    public DailyWeatherHolder(View view, Weather weather) {
        super(view);
        this.weather = weather;
        mContext = view.getContext();
        dailyWeather = (LinearLayout) itemView.findViewById(R.id.forecast_linear);
        for (int i = 0; i < weather.getDailyForecast().size(); i++) {
            View v = View.inflate(mContext, R.layout.items_weeklyforecast, null);
            img[i] = (ImageView) v.findViewById(R.id.forecast_icon);
            imgN[i] = (ImageView) v.findViewById(R.id.forecast_icon_n);
            dayView[i] = (TextView) v.findViewById(R.id.forecast_date);
            tempView[i] = (TextView) v.findViewById(R.id.forecast_temp);
            moreInfoView[i] = (TextView) v.findViewById(R.id.forecast_txt);
            dailyWeather.addView(v);
        }
    }

    @Override
    public void bind(Weather weather) {
        try {
            dayView[0].setText(mContext.getResources().getString(R.string.TODAY));
            dayView[1].setText(mContext.getResources().getString(R.string.TOMORROW));

            for (int i = 0; i < weather.getDailyForecast().size(); i++) {
                if (i > 1) {
                    dayView[i].setText(weather.getDailyForecast().get(i).getDate() + "");
                }
                img[i].setImageResource(IconGet.getWeaIcon(weather.getDailyForecast().get(i)
                        .getCond().getTxtD()));//TODO 图标为上午图标，需要改成全天适用
                if (weather.getDailyForecast().get(i).getCond().getTxtD().equals(weather.getDailyForecast().get(i).getCond().getTxtN())) {
                    imgN[i].setVisibility(View.INVISIBLE);
                } else {
                    imgN[i].setImageResource(IconGet.getWeaIcon(weather.getDailyForecast().get(i)
                            .getCond().getTxtN()));
                }
                tempView[i].setText(mContext.getResources().getString(R.string.temp_min)
                        + weather.getDailyForecast().get(i).getTmp().getMin()
                        + mContext.getResources().getString(R.string.c)
                        + mContext.getResources().getString(R.string.temp_max)
                        + weather.getDailyForecast().get(i).getTmp().getMin()
                        + mContext.getResources().getString(R.string.c));
                moreInfoView[i].setText(weather.getDailyForecast().get(i).getWind().getDir() + (weather.getDailyForecast().get(i).getWind().getSc().equals("微风")
                        ? weather.getDailyForecast().get(i).getWind().getSc() : weather.getDailyForecast().get(i).getWind().getSc() + mContext.getResources().getString(R.string.m_s))
                        //判断风速大小若无风或微风不显示风力
                        + (weather.getDailyForecast().get(i).getCond().getTxtD().equals(weather.getDailyForecast().get(i).getCond().getTxtN())
                        ? weather.getDailyForecast().get(i).getCond().getTxtD() : weather.getDailyForecast().get(i).getCond().getTxtD()
                        + mContext.getResources().getString(R.string.to) + weather.getDailyForecast().get(i).getCond().getTxtN()));
                //判断getTxt_d()和getTxt_n()的值是否相等，即上下午天气是否不变，如果不变则显示一个，否则在两天气信息中加入过渡词“转”
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
