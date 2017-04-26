package com.example.owen.weathergo.common.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.owen.weathergo.modules.dao.City;
import com.example.owen.weathergo.modules.dao.Province;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

public class WeatherDB {

    public WeatherDB() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<Province> loadProvinces(SQLiteDatabase db) {

        List<Province> list = new ArrayList<>();

        Cursor cursor = db.query("T_Province", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.ProSort = cursor.getInt(cursor.getColumnIndex("ProSort"));
                province.ProName = cursor.getString(cursor.getColumnIndex("ProName"));
                list.add(province);
            } while (cursor.moveToNext());
        }
        Util.closeQuietly(cursor);
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<City> loadCities(SQLiteDatabase db, int ProID) {
        List<City> list = new ArrayList<>();
        Cursor cursor = db.query("T_City", null, "ProID = ?", new String[] { String.valueOf(ProID) }, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.CityName = cursor.getString(cursor.getColumnIndex("CityName"));
                city.ProID = ProID;
                city.CitySort = cursor.getInt(cursor.getColumnIndex("CitySort"));
                list.add(city);
            } while (cursor.moveToNext());
        }
        Util.closeQuietly(cursor);
        return list;
    }
}