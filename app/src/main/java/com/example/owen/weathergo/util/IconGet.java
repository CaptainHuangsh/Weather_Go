package com.example.owen.weathergo.util;

import com.example.owen.weathergo.R;

/**
 * Created by owen on 2016/11/15.
 */

public class IconGet {

    public static int getWeaIcon(String weatherType){

        int ic = 0;
        switch (weatherType){
            case "未知":
                ic = R.mipmap.none;
                break;
            case "晴":
                ic = R.mipmap.type_one_sunny;
                break;
            case "阴":
                ic = R.mipmap.type_one_cloudy;
                break;
            case "多云":
                ic = R.mipmap.type_one_cloudy;
                break;
            case "少云":
                ic = R.mipmap.type_one_cloudy;
                break;
            case "晴间多云":
                ic = R.mipmap.type_one_cloudytosunny;
                break;
            case "小雨":
                ic = R.mipmap.type_one_light_rain;
                break;
            case "中雨":
                ic = R.mipmap.type_one_light_rain;
                break;
            case "大雨":
                ic = R.mipmap.type_one_heavy_rain;
                break;
            case "阵雨":
                ic = R.mipmap.type_one_thunderstorm;
                break;
            case "雷阵雨":
                ic = R.mipmap.type_one_thunder_rain;
                break;
            case "霾":
                ic = R.mipmap.type_one_fog;
                break;
            case "雾":
                ic = R.mipmap.type_one_fog;
                break;
            default:
                ic = R.mipmap.type_one_sunny;
                break;
        }

        return ic;
    }

}
