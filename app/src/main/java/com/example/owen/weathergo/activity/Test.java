package com.example.owen.weathergo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.modules.domain.Weather;
import com.example.owen.weathergo.util.JSONUtil;

/**
 * Created by owen on 2017/5/24.
 */

public class Test extends Activity {
    Weather mWeather;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        findViewById(R.id.test).setOnClickListener( v-> mWeather = JSONUtil.getInstance()
                .getWeather(getApplicationContext(), "luoyang"));
    }
}
