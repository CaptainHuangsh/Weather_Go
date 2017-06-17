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

    private static final int CITY_NUM_0 = 0;//主城市
    private static final int CITY_NUM_1 = 1;//多城市1
    private static final int CITY_NUM_2 = 2;//多城市2
    private static final int CITY_NUM_3 = 3;//多城市3
    private static final int CITY_NUM_4 = 4;//多城市4
    private static final int CITY_NUM_5 = 5;//多城市5

    private static WeatherAPI mWeatherAPI = new WeatherAPI();
    static List<Weather> mListWeather;
    private static Weather mWeather = new Weather();
    private int cityNum;

    public static JSONUtil getInstance() {
        return JHolder.sInstance;
    }

    private static class JHolder {
        private static JSONUtil sInstance = new JSONUtil();
    }

    public Weather getWeather(final Context context, String sCity) {
        //将MainWeather引导到新方法中实现
        return JSONUtil.getInstance().getWeather(context, sCity, CITY_NUM_0);
    }

    public Weather getWeather(final Context context, String sCity, final int cityNumber) {
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
                    if (context != null) {
                        preferences = context.getSharedPreferences("huang", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        switch (cityNumber) {
                            case CITY_NUM_0:
                                editor.putString("jsonText_city_0", jsonText);
                                break;
                            case CITY_NUM_1:
                                editor.putString("jsonText_city_1", jsonText);
                                break;
                            case CITY_NUM_2:
                                editor.putString("jsonText_city_2", jsonText);
                                break;
                            case CITY_NUM_3:
                                editor.putString("jsonText_city_3", jsonText);
                                break;
                            case CITY_NUM_4:
                                editor.putString("jsonText_city_4", jsonText);
                                break;
                            case CITY_NUM_5:
                                editor.putString("jsonText_city_5", jsonText);
                                break;
                            default:
                                editor.putString("jsonText_city_0", jsonText);
                                break;
                        }
//                    editor.putString("jsonText_city_0", jsonText);
                        editor.apply();
                    }
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
//        SharedPreferences.Editor editor = preferences.edit();
        //缓存json数据
        String jsonTextCity;
        switch (cityNumber) {
            case CITY_NUM_0:
                jsonTextCity = preferences.getString("jsonText_city_0", "");
                break;
            case CITY_NUM_1:
                jsonTextCity = preferences.getString("jsonText_city_1", "");
                break;
            case CITY_NUM_2:
                jsonTextCity = preferences.getString("jsonText_city_2", "");
                break;
            case CITY_NUM_3:
                jsonTextCity = preferences.getString("jsonText_city_3", "");
                break;
            case CITY_NUM_4:
                jsonTextCity = preferences.getString("jsonText_city_4", "");
                break;
            case CITY_NUM_5:
                jsonTextCity = preferences.getString("jsonText_city_5", "");
                break;
            default:
                jsonTextCity = preferences.getString("jsonText_city_0", "");
                break;
        }
//        jsonTextCity = preferences.getString("jsonText_city_0", "");
        parse(jsonTextCity);
        return mWeather;
    }

    private Weather parse(String jsonText) {
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
