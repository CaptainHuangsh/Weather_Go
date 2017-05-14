package com.example.owen.weathergo.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.activity.WeatherMain;
import com.example.owen.weathergo.modules.dao.WeatherBean;
import com.example.owen.weathergo.util.IconGet;
import com.example.owen.weathergo.util.SharedPreferenceUtil;

/**
 * Created by owen on 2017/5/1.
 * Info auto update and add a widget
 */

public class AutoUpdateService extends Service {
    //http://www.jianshu.com/p/67c1d82b50b7
    private final String TAG = AutoUpdateService.class.getSimpleName();
    private SharedPreferences preferences;
    private boolean mNotificationMode; //通知栏常驻
    private boolean mVibrate; //天气推送震动
    SharedPreferenceUtil mSharedPreferenceUtil;

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
        PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mNotificationMode = preferences.getBoolean("notification_mode", false);
        mVibrate = preferences.getBoolean("vibrate", false);
        Log.i("autoUpdateService", "onStartCommand()");
        WeatherBean weatherBean = (WeatherBean) intent.getSerializableExtra("weather");
        //TODO 查找崩溃原因
        Log.i("autoUpdateService", "onStartCommand()" + weatherBean.getCity());
        createNotification(weatherBean);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void createNotification(WeatherBean weatherBean) {
        Log.i("autoUpdateService", "createNotification()" + weatherBean.getCity());
        Intent autoServiceIntent
                = new Intent(AutoUpdateService.this, WeatherMain.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                AutoUpdateService.this, 0, autoServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(AutoUpdateService.this);
        Notification notification = builder.setContentIntent(pendingIntent)
                .setContentTitle(weatherBean.getCity() + "   " + weatherBean.getNow_tmp() + getApplicationContext().getResources().getString(R.string.c))
                .setContentText("" + weatherBean.getNow_dir() +(weatherBean.getNow_sc().equals("微风")?weatherBean.getNow_sc():weatherBean.getNow_sc()
                        + getApplicationContext().getResources().getString(R.string.m_s))+" "+weatherBean.getTxt())
                .setSmallIcon(IconGet.getWeaIcon(weatherBean.getMain_weather_img())).build();
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                IconGet.getWeaIcon(weatherBean.getMain_weather_img())));
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(
                        NOTIFICATION_SERVICE);
        if (mNotificationMode) {
            //通知栏常驻
            notification.flags = Notification.FLAG_ONGOING_EVENT;
        } else {
            notification.flags = Notification.FLAG_AUTO_CANCEL;
        }
        notificationManager.notify(1, notification);

    }
}
