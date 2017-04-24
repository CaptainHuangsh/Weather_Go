package com.example.owen.weathergo.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.component.DLForecast;
import com.example.owen.weathergo.modules.dao.DailyWeatherHolder;
import com.example.owen.weathergo.modules.dao.WeatherBean;
import com.example.owen.weathergo.modules.dao.WeatherHolder;

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
    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;
    private static final int TYPE_THREE = 2;
    private static final int TYPE_FORE = 3;

    public WeatherAdapter(View inflate, List<DLForecast> dlForecastList, WeatherBean weatherBean) {
        this.view = inflate;
        this.dlForecastList = dlForecastList;
        this.weatherBean = weatherBean;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
//        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case WeatherAdapter.TYPE_ONE:
//                dHolder = new DailyWeatherHolder(LayoutInflater.from(context).inflate(R.layout.item_forecast, parent, false));
                Log.i("WeatherAdapteroncreateView",""+WeatherAdapter.TYPE_ONE);
                return new WeatherHolder(LayoutInflater.from(context).inflate(R.layout.item_forecast,parent,false),weatherBean);
            case WeatherAdapter.TYPE_TWO:
                Log.i("WeatherAdapteroncreateView",""+WeatherAdapter.TYPE_TWO);
                return new DailyWeatherHolder(LayoutInflater.from(context).inflate(R.layout.item_forecast_line, parent, false), dlForecastList);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case WeatherAdapter.TYPE_ONE:
                ((WeatherHolder)holder).bind(weatherBean);
                break;
            case WeatherAdapter.TYPE_TWO:
                ((DailyWeatherHolder)holder).bind(dlForecastList);
                break;
        }
    }

/*
    @Override
    public void onBindViewHolder(DailyWeatherHolder holder, int position) {
        holder.img.setImageResource(dlForecastList.get(position).getImageId());
        holder.dayView.setText(dlForecastList.get(position).getDay());
        holder.temprView.setText(dlForecastList.get(position).getTempr());
        holder.weamoreView.setText(dlForecastList.get(position).getWeamore());
    }
*/

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 4)
            return position;
        return super.getItemViewType(position);
    }
}
