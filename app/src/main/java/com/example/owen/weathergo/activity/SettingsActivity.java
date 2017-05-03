package com.example.owen.weathergo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by owen on 2017/5/3.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }
}
