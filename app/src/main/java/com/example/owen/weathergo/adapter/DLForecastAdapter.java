package com.example.owen.weathergo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.component.DLForecast;

import java.util.List;

/**
 * Created by owen on 2016/11/15.
 */

public class DLForecastAdapter  extends ArrayAdapter<DLForecast> {
    private int resourceId;

    public DLForecastAdapter(Context context, int resource,
                             List<DLForecast> objects) {
        super(context, resource,objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DLForecast dlForecast = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView img= (ImageView) view.findViewById(R.id.forecast_icon);
        TextView dayView = (TextView) view.findViewById(R.id.forecast_date);
        TextView temprView = (TextView) view.findViewById(R.id.forecast_temp);
        TextView weamoreView = (TextView) view.findViewById(R.id.forecast_txt);
        img.setImageResource(dlForecast.getImageId());
        dayView.setText(dlForecast.getDay());
        temprView.setText(dlForecast.getTempr());
        weamoreView.setText(dlForecast.getWeamore());
        return view;
    }
}
