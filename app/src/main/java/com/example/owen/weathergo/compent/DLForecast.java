package com.example.owen.weathergo.compent;

import android.media.Image;

/**
 * Created by owen on 2016/11/15.
 */

public class DLForecast {

    private String day,tempr,weamore;
    private int imageId;
    public DLForecast(String day,String tempr, String weamore,int imageId){

        this.day = day;
        this.tempr = tempr;
        this.weamore=  weamore;
        this.imageId = imageId;

    }

    public String getDay(){
        return day;
    }

    public String getTempr(){
        return tempr;
    }

    public String getWeamore(){
        return weamore;
    }

    public int getImageId(){
        return imageId;
    }

}
