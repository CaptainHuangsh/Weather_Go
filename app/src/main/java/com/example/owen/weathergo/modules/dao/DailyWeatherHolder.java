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

/**
 * Created by owen on 2017/4/21.
 */

public class DailyWeatherHolder extends BaseViewHolder<ArrayList<DailyForecast>> {
    private final String TAG = DailyWeatherHolder.class.getSimpleName();
    private Context mContext;
    ArrayList<DailyForecast> mDFList;
    private LinearLayout dailyWeather;
    private ImageView img[] = new ImageView[7];
    private TextView dayView[] = new TextView[7];
    private TextView tempView[] = new TextView[7];
    private TextView moreInfoView[] = new TextView[7];


    public DailyWeatherHolder(View view, ArrayList<DailyForecast> mDFList) {
        super(view);
        mContext = view.getContext();
        this.mDFList = mDFList;
        dailyWeather = (LinearLayout) itemView.findViewById(R.id.forecast_linear);
        for (int i = 0; i < mDFList.size(); i++) {
            View v = View.inflate(mContext, R.layout.items_weeklyforecast, null);
            img[i] = (ImageView) v.findViewById(R.id.forecast_icon);
            dayView[i] = (TextView) v.findViewById(R.id.forecast_date);
            tempView[i] = (TextView) v.findViewById(R.id.forecast_temp);
            moreInfoView[i] = (TextView) v.findViewById(R.id.forecast_txt);
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
                tempView[i].setText(mContext.getResources().getString(R.string.hsh_temp_min)
                        + mDFList.get(i).getMin()
                        + mContext.getResources().getString(R.string.c)
                        + mContext.getResources().getString(R.string.hsh_temp_max)
                        + mDFList.get(i).getMax()
                        + mContext.getResources().getString(R.string.c));
                moreInfoView[i].setText(mDFList.get(i).getDir() + mDFList.get(i).getSc()
                        + mContext.getResources().getString(R.string.m_s)
                        + (mDFList.get(i).getTxt_d().equals(mDFList.get(i).getTxt_n())
                        ? mDFList.get(i).getTxt_d() : mDFList.get(i).getTxt_d()
                        + mContext.getResources().getString(R.string.to) + mDFList.get(i).getTxt_n()));
                //判断getTxt_d()和getTxt_n()的值是否相等，即上下午天气是否不变，如果不变则显示一个，否则在两天气信息中加入过渡词“转”
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
