package com.example.owen.weathergo.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.owen.weathergo.R;
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
    private List<String> mDatas;
    public WeatherAdapter(View inflate) {
        view = inflate;
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
        holder.dayView.setText("huang"+mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }
}
