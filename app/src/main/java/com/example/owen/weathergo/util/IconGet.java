package com.example.owen.weathergo.util;

import com.example.owen.weathergo.R;

/**
 * Created by owen on 2016/11/15.
 * 根据天气概况信息给出对应的天气概况图标
 * http://www.iconfont.cn/collections/detail?cid=2385
 */

public class IconGet {
    //TODO 修改阵雨图标

    public static int getWeaIcon(String weatherType) {

        int ic = 0;
        switch (weatherType) {
            case "未知":
                ic = R.mipmap.none;
                break;
            case "晴":
                ic = R.mipmap.icon_weather_sunny;
                break;
            case "阴":
                ic = R.mipmap.icon_weather_cloudy;
                break;
            case "多云":
                ic = R.mipmap.icon_weather_cloud;
                break;
            case "少云":
                ic = R.mipmap.icon_weather_cloud;
                break;
            case "晴间多云":
                ic = R.mipmap.icon_weather_cloud;
                break;
            case "小雨":
                ic = R.mipmap.icon_weather_rain_light;
                break;
            case "中雨":
                ic = R.mipmap.icon_weather_rain_middle;
                break;
            case "大雨":
                ic = R.mipmap.icon_weather_rain_heavy;
                break;
            case "阵雨":
                ic = R.mipmap.icon_weather_rain_light;
                break;
            case "雷阵雨":
                ic = R.mipmap.icon_weather_rain_thunder;
                break;
            case "霾":
                ic = R.mipmap.icon_weather_fog;
                break;
            case "雾":
                ic = R.mipmap.icon_weather_fogy;
                break;
            case "小雪":
                ic = R.mipmap.icon_weather_snow_light;
                break;
            case "中雪":
                ic = R.mipmap.icon_weather_snow_middle;
                break;
            case "大雪":
                ic = R.mipmap.icon_weather_snow_heavy;
                break;
            case "雨夹雪":
                ic = R.mipmap.icon_weather_rain_snow;
                break;
            default:
                ic = R.mipmap.icon_weather_sunny;
                break;
        }

        return ic;
    }

}
