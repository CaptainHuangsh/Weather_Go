package com.example.owen.weathergo.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.owen.weathergo.WeatherApplication;

/**
 * Created by owen on 2017/4/8.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData(savedInstanceState);
        initListener();
    }

    protected abstract void initView();

    protected abstract void initListener();

    protected void initData(@Nullable Bundle savedInstanceState){};


    @Override
    protected void onDestroy() {
        super.onDestroy();
        WeatherApplication.getInstance().removeActivity(this);
    }
}
