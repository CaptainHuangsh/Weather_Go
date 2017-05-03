package com.example.owen.weathergo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.owen.weathergo.R;

/**
 * Created by owen on 2017/5/3.
 * 简单的设置页面
 */

public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }
}
