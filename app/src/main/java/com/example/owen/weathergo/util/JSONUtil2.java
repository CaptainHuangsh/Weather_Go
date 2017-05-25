package com.example.owen.weathergo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.owen.weathergo.common.base.C;
import com.example.owen.weathergo.component.WgClient;
import com.example.owen.weathergo.modules.domain.Weather;
import com.example.owen.weathergo.modules.domain.WeatherAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by owen on 2017/5/24.
 * 原先是用JsonObject解析，效率低，不易维护，此类是用于替代之前方法的工具类
 * 原Created by owenh on 2016/5/17.
 * 处理从openweathermap网站上获取的json代码，进行解析赋值操作
 *
 */

public class JSONUtil2 {

//    private static Context mContext;
//    private static String mCity;
    static WeatherAPI mWeatherAPI = new WeatherAPI();
    static List<Weather> mListWeather;
    static Weather mWeather = new Weather();

    public JSONUtil2(final Context context, String sCity) {
//        this.mCity = sCity;
//        this.mContext = context;
        Log.d("huangshaohuaUtil2", " -1: " + sCity);
    }

    public static Weather getsomeThing(final Context context, String sCity) {
        Log.d("huangshaohuaUtil2", " 0: " + sCity);
        Weather weather = new Weather();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(C.HOST)
                .build();
        //Retrofit创建一个BlogService的代理对象
        WgClient service = retrofit.create(WgClient.class);
        Log.d("huangshaohuaUtil2", " 0.5: " + sCity);
        Call<ResponseBody> call = service.mWeatherAPI(sCity, C.HE_WEATHER_KEY);
        String jss = "";
        call.enqueue(new Callback<ResponseBody>() {
            String jsonText = "";

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    jsonText = "" + response.body().string();
                    SharedPreferences preferences;
                    preferences = context.getSharedPreferences("huang", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("jsonTextg", jsonText);
                    editor.apply();
                    Log.d("huangshaohuaUtil2", " 1: " + jsonText);
//                    parse(jsonText);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        SharedPreferences preferences = context.getSharedPreferences("huang", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //缓存json数据
        String jsonTextg = preferences.getString("jsonTextg", "");
        Log.d("huangshaohuaUtil2", " 1.5: " + jsonTextg);
        parse(jsonTextg);
        return mWeather;
    }

    public static Weather parse(String jsonText) {
        Log.d("huangshaohuaUtil2", " 2: " + jsonText);
        Gson gson = new Gson();
        WeatherAPI weatherAPI = gson.fromJson(jsonText,
                new TypeToken<WeatherAPI>() {
                }.getType());

        mWeatherAPI = weatherAPI;

        if (weatherAPI!=null)
        for (Weather lw : mWeatherAPI.getHeWeatherDataService30s()) {
            mWeather = lw;
        }

//        Log.d("huangshaohuaUtil2", " : " + mWeather.getSuggestion().getTrav().getBrf());
        return mWeather;
    }

}
