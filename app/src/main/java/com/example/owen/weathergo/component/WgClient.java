package com.example.owen.weathergo.component;

import com.example.owen.weathergo.modules.main.domain.WeatherAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by owen on 2017/4/8.
 */

public interface WgClient {



    @GET("weather")
    Call<ResponseBody> mWeatherAPI(@Query("city") String city, @Query("key") String key);

}
