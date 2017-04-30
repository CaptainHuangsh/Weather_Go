package com.example.owen.weathergo.modules.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.component.DLForecast;
import com.example.owen.weathergo.modules.dao.DailyWeatherHolder;
import com.example.owen.weathergo.modules.dao.HourlyWeatherHolder;
import com.example.owen.weathergo.modules.dao.SuggestionWeatherHolder;
import com.example.owen.weathergo.modules.dao.TodayWeatherHolder;
import com.example.owen.weathergo.modules.dao.WeatherBean;

import java.util.List;

/**
 * Created by owen on 2017/4/21.
 */
//RecycleView参考csdn blog： http://blog.csdn.net/lmj623565791/article/details/45059587
public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    View view;
    List<DLForecast> dlForecastList;
    WeatherBean weatherBean;
    private static final int TYPE_ONE = 0;//今日天气
    private static final int TYPE_TWO = 1;//七日天气
    private static final int TYPE_THREE = 2;//生活建议
    private static final int TYPE_FORE = 3;//分时预报

    public WeatherAdapter(View inflate, List<DLForecast> dlForecastList, WeatherBean weatherBean) {
        this.view = inflate;
        this.dlForecastList = dlForecastList;
        this.weatherBean = weatherBean;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        switch (viewType) {
            case WeatherAdapter.TYPE_ONE:
                return new TodayWeatherHolder(LayoutInflater.from(context).inflate(R.layout.forecast_main, parent, false), weatherBean);
            case WeatherAdapter.TYPE_TWO:
                return new DailyWeatherHolder(LayoutInflater.from(context).inflate(R.layout.forecast_weekly, parent, false), dlForecastList);
            case WeatherAdapter.TYPE_THREE:
                return new SuggestionWeatherHolder(LayoutInflater.from(context).inflate(R.layout.suggestion, parent, false), weatherBean);
            case WeatherAdapter.TYPE_FORE:
                return new HourlyWeatherHolder(LayoutInflater.from(context).inflate(R.layout.forecast_hourly,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case WeatherAdapter.TYPE_ONE:
                ((TodayWeatherHolder) holder).bind(weatherBean);
                break;
            case WeatherAdapter.TYPE_TWO:
                ((DailyWeatherHolder) holder).bind(dlForecastList);
                break;
            case WeatherAdapter.TYPE_THREE:
                ((SuggestionWeatherHolder) holder).bind(weatherBean);
                break;
            case WeatherAdapter.TYPE_FORE:
                ((HourlyWeatherHolder) holder).bind(null);
                break;
        }
    }


    @Override
    public int getItemCount() {
//        return weatherBean != null ? 4 : 0; 暂时隐藏第四栏小时天气，发布1.1.1beta版；下一版本中加入小时天气信息
        //TODO 加入小时天气
        return weatherBean != null ? 3 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 4)
            return position;
        return super.getItemViewType(position);
    }
}
