package com.example.owen.weathergo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.modules.fragment.SettingFragment;

/**
 * Created by owen on 2017/5/3.
 * 简单的设置页面
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.xml.pref_settings);
        setContentView(R.layout.activity_setting);
        this.getFragmentManager().beginTransaction()
                .replace(R.id.main_setting,new SettingFragment())
                .commit();
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }
}
