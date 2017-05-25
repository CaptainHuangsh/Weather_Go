package com.example.owen.weathergo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.modules.domain.Weather;
import com.example.owen.weathergo.util.JSONUtil2;

/**
 * Created by owen on 2017/5/24.
 */

public class Test extends Activity {
    Weather mWeather;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ((Button) findViewById(R.id.test)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        JSONUtil2 jsonUtil2 = new JSONUtil2(getApplicationContext(), "luoyang");
                        mWeather = JSONUtil2.getsomeThing(getApplicationContext(), "luoyang");
                        Log.d("huangshaohuaTest", " : " + mWeather.getBasic().getCity());
                Log.d("huangshaohuaTest", " hour : " + mWeather.getHourlyForecast().get(0).getDate());
            }
        });
    }
}
