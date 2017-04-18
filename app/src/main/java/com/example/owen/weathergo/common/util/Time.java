package com.example.owen.weathergo.common.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by owen on 2017/4/8.
 * Info:获取不同格式时间的工具方法
 */

public class Time {

    /**
    * yyyy-MM-dd HH:mm:ss
    */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYNDHMSTime(){
        SimpleDateFormat mDataFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss"
        );
        return mDataFormat.format(new Date());
    }
    /**
     * MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowMDHMSTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "MM-dd HH:mm:ss");
        return mDateFormat.format(new Date());
    }
    /**
     * MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMD() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        return mDateFormat.format(new Date());
    }

    /**
     * yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getYMD(Date date) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        return mDateFormat.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMD(Date date) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "MM-dd");
        return mDateFormat.format(date);
    }


}
