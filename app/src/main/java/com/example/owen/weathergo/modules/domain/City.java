package com.example.owen.weathergo.modules.domain;

import java.io.Serializable;

/**
 * Created by owen on 2017/4/25.
 */

public class City implements Serializable {
    private String CityName;
    private int ProID;

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public int getProID() {
        return ProID;
    }

    public void setProID(int proID) {
        ProID = proID;
    }

    public int getCitySort() {
        return CitySort;
    }

    public void setCitySort(int citySort) {
        CitySort = citySort;
    }

    private int CitySort;
}
