package com.example.owen.weathergo.util;

import android.content.Context;
import android.content.SharedPreferences;
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
 */

public class JSONUtil {

    static WeatherAPI mWeatherAPI = new WeatherAPI();
    static List<Weather> mListWeather;
    static Weather mWeather = new Weather();

    public static JSONUtil getInstance(){
        return JHolder.sInstance;
    }

    public static class JHolder{
        private static JSONUtil sInstance = new JSONUtil();
    }

    public Weather getWeather(final Context context, String sCity) {
        Weather weather = new Weather();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(C.HOST)
                .build();
        //Retrofit创建一个BlogService的代理对象
        WgClient service = retrofit.create(WgClient.class);
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
        parse(jsonTextg);
        return mWeather;
    }

    private static Weather parse(String jsonText) {
        Gson gson = new Gson();
        WeatherAPI weatherAPI = gson.fromJson(jsonText,
                new TypeToken<WeatherAPI>() {
                }.getType());

        mWeatherAPI = weatherAPI;

        if (weatherAPI != null)//防止无城市天气信息时出现的NullPoint异常
            for (Weather lw : mWeatherAPI.getHeWeatherDataService30s()) {
                mWeather = lw;
            }

        return mWeather;
    }

}
