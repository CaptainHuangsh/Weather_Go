package com.example.owen.weathergo.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.activity.WeatherMain;
import com.example.owen.weathergo.modules.domain.Weather;
import com.example.owen.weathergo.util.IconGet;
import com.example.owen.weathergo.util.JSONUtil;
import com.example.owen.weathergo.util.SharedPreferenceUtil;

/**
 * Created by owen on 2017/5/1.
 * Info auto update and add a widget
 */

public class AutoUpdateService extends Service {
    //http://www.jianshu.com/p/67c1d82b50b7
    private final String TAG = AutoUpdateService.class.getSimpleName();
    //    private SharedPreferences preferences;
    private boolean mNotificationMode; //通知栏常驻
    private Weather mWeather;

    private Handler mHandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    final String Ccity = SharedPreferenceUtil.getInstance().getCityName();
                    Weather weather = JSONUtil.getInstance().getWeather(getApplicationContext(), Ccity);
                    if (weather != null) {
                        createNotification(weather);
                    }
                    break;
            }
        }
    };

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
        /*Message msg = new Message();
        msg.what = 0;
        mHandler.sendMessage(msg);*/
        new Thread(this::updateWeather).start();//不要忘了start

//        createNotification(mWeather);
        PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mNotificationMode = preferences.getBoolean("notification_mode", false);
//        mVibrate = preferences.getBoolean("vibrate", false);
        int mUpdateTime = Integer.valueOf(preferences.getString("update_time", "3"));
        /*if (mWeather != null) {
            createNotification(mWeather);
        }*/

        if (mUpdateTime > 0) {
            //几种定时刷新的方式 http://blog.csdn.net/wanglixin1999/article/details/7874316
            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            int aTime = mUpdateTime * 60 * 60 * 1000;//测试10分钟
            // 用户划掉天气后，一小时/2小时/8小时候会重新出现notification
            long triggerAtTime = SystemClock.elapsedRealtime() + aTime;
            Intent i = new Intent(this, AutoUpdateService.class);
            PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
            assert manager != null;
            manager.cancel(pi);
            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void createNotification(Weather weather) {
        Intent autoServiceIntent
                = new Intent(AutoUpdateService.this, WeatherMain.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                AutoUpdateService.this, 0, autoServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(AutoUpdateService.this);
        Notification notification = builder.setContentIntent(pendingIntent)
                .setContentTitle(weather.getBasic().getCity() + "   " + weather.getNow().getTmp() + getApplicationContext().getResources().getString(R.string.c))
                .setContentText("" + weather.getNow().getWind().getDir() + (weather.getNow().getWind().getSc().equals("微风") ? weather.getNow().getWind().getSc() : weather.getNow().getWind().getSc()
                        + getApplicationContext().getResources().getString(R.string.m_s)) + " " + weather.getNow().getCond().getTxt())
                .setSmallIcon(IconGet.getWeaIcon(weather.getNow().getCond().getTxt())).build();
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                IconGet.getWeaIcon(weather.getNow().getCond().getTxt())));
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(
                        NOTIFICATION_SERVICE);
        if (mNotificationMode) {
            //通知栏常驻
            notification.flags = Notification.FLAG_ONGOING_EVENT;
        } else {
            notification.flags = Notification.FLAG_AUTO_CANCEL;
        }
        assert notificationManager != null;
        notificationManager.notify(1, notification);

    }

    private void updateWeather() {
        final String Ccity = SharedPreferenceUtil.getInstance().getCityName();
        mWeather = JSONUtil.getInstance().getWeather(getApplicationContext(), Ccity);
        Message msg = new Message();
        msg.what = 0;
        mHandler.sendMessage(msg);
    }
}
