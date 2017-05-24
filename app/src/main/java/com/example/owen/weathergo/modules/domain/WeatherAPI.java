package com.example.owen.weathergo.modules.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owen on 2017/4/8.
 */

public class WeatherAPI {

    @SerializedName("HeWeather data service 3.0")
    @Expose
    private List<Weather> heWeatherDataService30s
            = new ArrayList<>();

    public List<Weather> getHeWeatherDataService30s() {
        return heWeatherDataService30s;
    }

    public void setHeWeatherDataService30s(List<Weather> heWeatherDataService30s) {
        this.heWeatherDataService30s = heWeatherDataService30s;
    }
}
