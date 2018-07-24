package com.example.owen.weathergo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owen on 2017/4/8.
 */

public class WeatherApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;
    private static WeatherApplication instance;
    private List<Activity> activityStack = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        //不能直接new一个，那样此变量将只是一个java对象布具备context能力
        return context;
    }

    public static WeatherApplication getInstance() {
        if (instance == null) {
            instance = new WeatherApplication();
        }
        return instance;
    }

    public List<Activity> getActivityStack() {
        return activityStack;
    }

    public void init(Application app) {

    }

    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack != null) {
            if (activityStack.size() > 0 && !activityStack.contains(activity)) {
                activityStack.add(activity);
            } else {
                activityStack.add(activity);
            }
        }
    }

    /**
     * 获取栈顶activity
     *
     * @return
     */
    public Activity getTopActivity() {
        if (activityStack == null || activityStack.size() == 0)
            return null;
        return activityStack.get(activityStack.size() - 1);
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activityStack.contains(activity))
            activityStack.remove(activity);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null && !activity.isFinishing())
                activityStack.remove(activity);
        }
        activityStack.clear();
    }

    public Boolean isWeatherRunning() {
        boolean isRunning = false;
        for (Activity activity : activityStack) {
            if (activity != null && !activity.isFinishing()) {
                isRunning = true;
            }
        }
        return isRunning;
    }
}

