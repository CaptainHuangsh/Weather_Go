package com.example.owen.weathergo.util;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.owen.weathergo.WeatherApplication;

/**
 * Created by owen on 2017/4/8.
 * Info: sp写入工具类
 */

public class SharedPreferenceUtil {

    private static final String CITY_NAME = "city";//选择城市
    private static final String CITY_NAME_1 = "city_1";//城市2
    private static final String CITY_NAME_2 = "city_2";//城市3
    private static final String HOUR = "current_hour";//当前小时
    private static final String CHANG_ICONS = "change_icons";//切换图标
    private static final String AUTO_UPDATE = "auto_update_time";//自动更新时长
    //    public static final String CLEAR_CACHE = "clear_cache";//清空缓存
    private static final String NOTIFICATION_MODE = "notification_mode";//notification常开与否

    private SharedPreferences mSharedPreference;

    public static SharedPreferenceUtil getInstance() {
        return SPHolder.sInstance;
    }

    private static class SPHolder {
        private static SharedPreferenceUtil sInstance = new SharedPreferenceUtil();
    }

    private SharedPreferenceUtil() {
        mSharedPreference = WeatherApplication.getContext().getSharedPreferences("huang", Context.MODE_PRIVATE);

    }

    public SharedPreferenceUtil putInt(String key, int value) {
        mSharedPreference.edit().putInt(key, value).apply();
        return this;
    }

    public int getInt(String key, int defValue) {
        return mSharedPreference.getInt(key, defValue);
    }

    public SharedPreferenceUtil putString(String key, String value) {
        mSharedPreference.edit().putString(key, value).apply();
        return this;
    }

    public String getString(String key, String defValue) {
        return mSharedPreference.getString(key, defValue);
    }

    public SharedPreferenceUtil putBoolean(String key, Boolean value) {
        mSharedPreference.edit().putBoolean(key, value).apply();
        return this;
    }

    public boolean getBoolean(String key, Boolean defValue) {
        return mSharedPreference.getBoolean(key, defValue);
    }

    //设置当前小时
    public void setCurrentHour(int hour) {
        mSharedPreference.edit().putInt(HOUR, hour).apply();
    }

    public int getCurrentHour() {
        return mSharedPreference.getInt(HOUR, 0);
    }

    //自动更新时间 hours
    public void setAutoUpdate(int t) {
        mSharedPreference.edit().putInt(AUTO_UPDATE, t).apply();
    }

    public int getAutoUpdate() {
        return mSharedPreference.getInt(AUTO_UPDATE, 3);
    }

    //当前城市
    public void setCityName(String cityName) {
        mSharedPreference.edit().putString(CITY_NAME, cityName).apply();
    }

    public String getCityName() {
        return mSharedPreference.getString(CITY_NAME, "");
    }

    //通知栏默认常驻

    public void setNotificationModel(int t) {
        mSharedPreference.edit().putInt(NOTIFICATION_MODE, t).apply();
    }

    public int getNotificationModel() {
        return mSharedPreference.getInt(NOTIFICATION_MODE, Notification.FLAG_ONGOING_EVENT);
    }
}
