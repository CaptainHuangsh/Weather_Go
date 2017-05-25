package com.example.owen.weathergo.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.owen.weathergo.modules.domain.City;
import com.example.owen.weathergo.modules.domain.Province;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class WeatherDB {

    public WeatherDB() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<Province> loadProvinces(SQLiteDatabase db) {

        List<Province> list = new ArrayList<>();

        //DMBManager类复制数据库操作已再设备上验证成功（coolpad 8297 API19）
        Cursor cursor = db.query("T_Province", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setProSort(cursor.getInt(cursor.getColumnIndex("ProSort")));
                province.setProName(cursor.getString(cursor.getColumnIndex("ProName")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        closeQuietly(cursor);
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<City> loadCities(SQLiteDatabase db, int ProID) {
        List<City> list = new ArrayList<>();
        Cursor cursor = db.query("T_City", null, "ProID = ?", new String[] { String.valueOf(ProID) }, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setCityName(cursor.getString(cursor.getColumnIndex("CityName")));
                city.setProID(ProID);
                city.setCitySort(cursor.getInt(cursor.getColumnIndex("CitySort")));
                list.add(city);
            } while (cursor.moveToNext());
        }
        closeQuietly(cursor);
        return list;
    }


    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}