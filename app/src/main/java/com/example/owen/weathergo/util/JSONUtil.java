package com.example.owen.weathergo.util;

/**
 * Created by owenh on 2016/5/17.
 * 处理从openweathermap网站上获取的json代码，进行解析赋值操作
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.owen.weathergo.WeatherBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class JSONUtil {
    /**
     * @param context
     * @param url
     * @return
     */



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static WeatherBean getWeatherBeans(Context context, URL url){
        /**
         * 处理从heweather网站上获取的json代码，进行解析赋值操作的静态方法
         */
        String jsonText = new JSONFetcher().getJSONText(url);
        //System.out.println(jsonText);
        WeatherBean weather = new WeatherBean();
        //Toast.makeText(context,jsonText,Toast.LENGTH_LONG).show();
        try {
            JSONObject weatherJSONObject = new JSONObject(jsonText);//first grade
            JSONArray allJSONObject = weatherJSONObject.getJSONArray("HeWeather data service 3.0");//second:HeWeather data service 3.0
            JSONObject OJSONObject = allJSONObject.getJSONObject(0);//third:"0"


            JSONObject aqiJSONObject = OJSONObject.getJSONObject("aqi");//fouth grade
            JSONObject cityJSONObject = aqiJSONObject.getJSONObject("city");//fifth grade
                int aqi = cityJSONObject.getInt("aqi");
                weather.setAqi(aqi);
            Log.e("JSONF aqi",""+weather.getAqi());
                int pm10 = cityJSONObject.getInt("pm10");
                weather.setPm10(pm10);
            Log.e("JSONF pm10",""+weather.getPm10());
                int pm25 = cityJSONObject.getInt("pm25");
                weather.setPm25(pm25);
            Log.e("JSONF pm25",""+weather.getPm25());
                String qlty = cityJSONObject.getString("qlty");
                weather.setQlty(qlty);
            Log.e("JSONF qlty",""+weather.getQlty());
            //Toast.makeText(context,jsonText,Toast.LENGTH_LONG).show();

            JSONObject basicJSONObject = OJSONObject.getJSONObject("basic");//fouth grade
                String city = basicJSONObject.getString("city");
                weather.setCity(city);
            Log.e("JSONF city",""+weather.getCity());
                String cnty = basicJSONObject.getString("cnty");
                weather.setCnty(cnty);
            Log.e("JSONF cnty",""+weather.getCnty());
                String id = basicJSONObject.getString("id");
                weather.setId(id);
            Log.e("JSONF id",""+weather.getId());
                Double lat = basicJSONObject.getDouble("lat");
                weather.setLat(lat);
            Log.e("JSONF lat",""+weather.getLat());
                Double lon = basicJSONObject.getDouble("lon");
                weather.setLon(lon);
            Log.e("JSONF lon",""+weather.getLon());
            JSONObject updateJSONObject = basicJSONObject.getJSONObject("update");//fifth grade
                String loc = updateJSONObject.getString("loc");
                weather.setLoc(loc);
            Log.e("JSONF loc",""+weather.getLoc());
                String utc = updateJSONObject.getString("utc");
                weather.setUtc(utc);
            Log.e("JSONF utc",""+weather.getUtc());

            //JSONArray daily_forecastJSONObject = weatherJSONObject.getJSONArray("daily_forecast");//fouth:daily_forecast
            //shuzu


            //"now"
            JSONObject nowJSONObject = OJSONObject.getJSONObject("now");//fouth grade;
            JSONObject condSONObject = nowJSONObject.getJSONObject("cond");//fifth grade
                weather.setCode(condSONObject.getInt("code"));
            Log.e("JSONF code",""+weather.getCode());
                weather.setTxt(condSONObject.getString("txt"));
            Log.e("JSONF txt",""+weather.getTxt());
            weather.setNow_fl(nowJSONObject.getInt("fl"));
            Log.e("JSONF now_fl",""+weather.getNow_fl());
            weather.setNow_hum(nowJSONObject.getInt("hum"));
            Log.e("JSONF now_hum",""+weather.getNow_hum());
            weather.setNow_pcpn(nowJSONObject.getInt("pcpn"));
            Log.e("JSONF now_pcpn",""+weather.getNow_pcpn());
            weather.setNow_pres(nowJSONObject.getInt("pres"));
            Log.e("JSONF now_pres",""+weather.getNow_pres());
            weather.setNow_tmp(nowJSONObject.getInt("tmp"));
            Log.e("JSONF now_tmp",""+weather.getNow_tmp());
            weather.setNow_vis(nowJSONObject.getInt("vis"));
            Log.e("JSONF now_vis",""+weather.getNow_vis());
            //"wind"
            JSONObject windJSONObject = nowJSONObject.getJSONObject("wind");//fifth grade
                weather.setNow_deg(windJSONObject.getInt("deg"));
            Log.e("JSONF now_deg",""+weather.getNow_deg());
                weather.setNow_dir(windJSONObject.getString("dir"));
            Log.e("JSONF now_dir",""+weather.getNow_dir());
                weather.setNow_sc(windJSONObject.getString("sc"));
            Log.e("JSONF now_sc",""+weather.getNow_sc());
                weather.setNow_spd(windJSONObject.getString("spd"));
            Log.e("JSONF now_spd",""+weather.getNow_spd());
            weather.setStatus(OJSONObject.getString("status"));
            Log.e("JSONF status",""+weather.getStatus());

            //"suggestion"
            JSONObject suggJSONObject = OJSONObject.getJSONObject("suggestion");//fouth grade;
            //"comf"
            JSONObject comfJSONObject = suggJSONObject.getJSONObject("comf");//fifth grade;
                weather.setComf_brf(comfJSONObject.getString("brf"));
            Log.e("JSONF comf_brf",""+weather.getComf_brf());
                weather.setComf_txt(comfJSONObject.getString("txt"));
            Log.e("JSONF comf_txt",""+weather.getComf_txt());
            //"cw"
            JSONObject cwJSONObject = suggJSONObject.getJSONObject("cw");//fifth grade;
            weather.setCw_brf(cwJSONObject.getString("brf"));
            Log.e("JSONF cw_brf",""+weather.getCw_brf());
            weather.setCw_txt(cwJSONObject.getString("txt"));
            Log.e("JSONF cw_txt",""+weather.getCw_txt());
            //"drsg"
            JSONObject drsgJSONObject = suggJSONObject.getJSONObject("drsg");//fifth grade;
            weather.setDrsg_brf(drsgJSONObject.getString("brf"));
            Log.e("JSONF drsg_brf",""+weather.getDrsg_brf());
            weather.setDrsg_txt(drsgJSONObject.getString("txt"));
            Log.e("JSONF drsg_txt",""+weather.getDrsg_txt());
            //"flu"
            JSONObject fluJSONObject = suggJSONObject.getJSONObject("flu");//fifth grade;
            weather.setFlu_brf(fluJSONObject.getString("brf"));
            Log.e("JSONF flu_brf",""+weather.getFlu_brf());
            weather.setFlu_txt(fluJSONObject.getString("txt"));
            Log.e("JSONF flu_txt",""+weather.getFlu_txt());
            //"sport"
            JSONObject sportJSONObject = suggJSONObject.getJSONObject("sport");//fifth grade;
            weather.setSport_brf(sportJSONObject.getString("brf"));
            Log.e("JSONF sport_brf",""+weather.getSport_brf());
            weather.setSport_txt(sportJSONObject.getString("txt"));
            Log.e("JSONF sport_txt",""+weather.getSport_txt());
            //"trav"
            JSONObject travJSONObject = suggJSONObject.getJSONObject("trav");//fifth grade;
            weather.setTrav_brf(travJSONObject.getString("brf"));
            Log.e("JSONF trav_brf",""+weather.getTrav_brf());
            weather.setTrav_txt(travJSONObject.getString("txt"));
            Log.e("JSONF trav_txt",""+weather.getTrav_txt());
            //"uv"
            JSONObject uvJSONObject = suggJSONObject.getJSONObject("uv");//fifth grade;
            weather.setUv_brf(uvJSONObject.getString("brf"));
            Log.e("JSONF uv_brf",""+weather.getUv_brf());
            weather.setUv_txt(uvJSONObject.getString("txt"));
            Log.e("JSONF uv_txt",""+weather.getUv_txt());













            //赋值操作
        } catch (JSONException e) {
            System.out.println("test");
            e.printStackTrace();
        }

        return weather;//返回weatherBean类型对象
    }
}

