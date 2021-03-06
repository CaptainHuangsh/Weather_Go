package com.example.owen.weathergo.util;

import android.widget.Toast;

import com.example.owen.weathergo.WeatherApplication;

/**
 * Created by owen on 2017/4/8.
 * commit at least 10 times per day from 4.23
 */

public class ToastUtil {
    public static void showShort(String msg) {
        Toast.makeText(WeatherApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(WeatherApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
