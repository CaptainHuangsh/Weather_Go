package com.example.owen.weathergo.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.activity.WeatherMain;
import com.example.owen.weathergo.modules.dao.WeatherBean;
import com.example.owen.weathergo.util.IconGet;

/**
 * Created by owen on 2017/5/1.
 * Info auto update and add a widget
 */

public class AutoUpdateService extends Service {
    private final String TAG = AutoUpdateService.class.getSimpleName();
    private SharedPreferences preferences;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("autoUpdateService","onStartCommand()");
        WeatherBean weatherBean = (WeatherBean) intent.getSerializableExtra("weather");
        Log.i("autoUpdateService","onStartCommand()"+weatherBean.getCity());
        createNotification(weatherBean);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void createNotification(WeatherBean weatherBean) {
        Log.i("autoUpdateService","createNotification()"+weatherBean.getCity());
        Intent autoServiceIntent
                = new Intent(AutoUpdateService.this, WeatherMain.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                AutoUpdateService.this, 0, autoServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(AutoUpdateService.this);
        Notification notification = builder.setContentIntent(pendingIntent)
                .setContentTitle(weatherBean.getCity()+"   "+weatherBean.getNow_tmp()+getApplicationContext().getResources().getString(R.string.c))
                .setContentText("" + weatherBean.getNow_dir() + weatherBean.getNow_sc()
                        + getApplicationContext().getResources().getString(R.string.m_s))
                .setSmallIcon(IconGet.getWeaIcon(weatherBean.getMain_weather_img()))
                .build();
        startForeground(1, notification);
    }
}
