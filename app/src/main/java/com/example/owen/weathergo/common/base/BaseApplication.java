package com.example.owen.weathergo.common.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by owen on 2017/4/8.
 */

public class BaseApplication extends Application {

    public static Context sAppContext;
    private static String sCacheDir;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public static String getAppCacheDir() {
        return sCacheDir;
    }
}
