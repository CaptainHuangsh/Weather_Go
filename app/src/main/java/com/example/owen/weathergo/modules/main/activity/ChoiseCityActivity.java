package com.example.owen.weathergo.modules.main.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.owen.weathergo.R;

public class ChoiseCityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise_city);
    }
    //启动Activity方法
    public static void launch(Context context) {
        context.startActivity(new Intent(context, ChoiseCityActivity.class));
    }
}
