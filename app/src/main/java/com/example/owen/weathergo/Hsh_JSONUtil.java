package com.example.owen.weathergo;

/**
 * Created by owenh on 2016/5/17.
 * 处理从openweathermap网站上获取的json代码，进行解析赋值操作
 */

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class Hsh_JSONUtil {
    /**
     * @param context
     * @param url
     * @return
     */
    public static Hsh_WeatherBean getWeatherBean(Context context, URL url){
        /**
         * 处理从openweathermap网站上获取的json代码，进行解析赋值操作的静态方法
         */
        String jsonText = new Hsh_JSONFetcher().getJSONText(url);
        System.out.println(jsonText);
        Hsh_WeatherBean weather = new Hsh_WeatherBean();
//        Toast.makeText(context,"jsonText",Toast.LENGTH_LONG).show();
        try {
            JSONObject weatherJSONObject = new JSONObject(jsonText);
            JSONObject sysJSONObject = weatherJSONObject.getJSONObject("sys");

            String country = sysJSONObject.getString("country");
            JSONObject mainJSONObject = weatherJSONObject.getJSONObject("main");
            double temp_min = mainJSONObject.getDouble("temp_min");
            temp_min = (double) Math.round((temp_min-273.15)*100)/100;
            double temp_max = mainJSONObject.getDouble("temp_max");
            temp_max = (double) Math.round((temp_max- 273.15)*100)/100;
            double temp = mainJSONObject.getDouble("temp");
            temp = (double) Math.round((temp-273.15)*100)/100;
            JSONObject windJSONObject = weatherJSONObject.getJSONObject("wind");
            int wind_speed = windJSONObject.getInt("speed");
            //解析操作
            weather.setCountry(country);
            weather.setTemp_min(temp_min);
            weather.setTemp_max(temp_max);
            weather.setWind_speed(wind_speed);
            weather.setTemp(temp);
            //赋值操作
        } catch (JSONException e) {
            System.out.println("test");
            e.printStackTrace();
        }

        return weather;//返回weatherBean类型对象
    }
}

