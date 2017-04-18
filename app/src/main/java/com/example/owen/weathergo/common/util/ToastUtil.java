package com.example.owen.weathergo.common.util;

import android.widget.Toast;

import com.example.owen.weathergo.base.BaseApplication;

/**
 * Created by owen on 2017/4/8.
 */

public class ToastUtil {
    public static void showShort(String msg) {
        Toast.makeText(BaseApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(BaseApplication.getAppContext(), msg, Toast.LENGTH_LONG).show();
    }
}
