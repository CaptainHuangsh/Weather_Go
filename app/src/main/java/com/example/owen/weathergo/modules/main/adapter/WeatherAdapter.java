package com.example.owen.weathergo.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.component.DLForecast;
import com.example.owen.weathergo.modules.dao.DailyForecast;
import com.example.owen.weathergo.modules.dao.WeatherHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owen on 2017/4/21.
 */
//RecycleView参考csdn blog： http://blog.csdn.net/lmj623565791/article/details/45059587
public class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder> {
    Context context;
    View view;
    List<DLForecast> dlForecastList;
    private List<String> mDatas;
    private DLForecast dls;

    public WeatherAdapter(View inflate,List<DLForecast> dlForecastList) {
        this.view = inflate;
        this.dlForecastList = dlForecastList;
//        this.dls = dls;
        initData();
    }



    @Override
    public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        WeatherHolder holder = new WeatherHolder(LayoutInflater.from(context).inflate(R.layout.item_forecast_line, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(WeatherHolder holder, int position) {
//        holder.img.setImageResource();

//        DailyForecast dfs = mDFList.get(position);
        holder.dayView.setText("huang" + mDatas.get(position));
        holder.img.setImageResource(dlForecastList.get(position).getImageId());
        holder.dayView.setText(dlForecastList.get(position).getDay());
        holder.temprView.setText(dlForecastList.get(position).getTempr());
        holder.weamoreView.setText(dlForecastList.get(position).getWeamore());
    }

    @Override
    public int getItemCount() {
        return dlForecastList.size();
    }


    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }
}
