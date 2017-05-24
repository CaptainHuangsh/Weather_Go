package com.example.owen.weathergo.util;

/**
 * Created by owenh on 2016/5/17.
 * 处理从openweathermap网站上获取的json代码，进行解析赋值操作
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.owen.weathergo.common.base.C;
import com.example.owen.weathergo.component.WgClient;
import com.example.owen.weathergo.modules.dao.DailyForecast;
import com.example.owen.weathergo.modules.dao.HourlyForecast;
import com.example.owen.weathergo.modules.dao.WeatherBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class JSONUtil {
    /**
     * @param context
     * @param url
     * @return
     */

    static ArrayList<DailyForecast> dfLists = new ArrayList<DailyForecast>();
    static ArrayList<HourlyForecast> hfLists = new ArrayList<HourlyForecast>();
    public static WeatherBean getWeatherBeans(final Context context, String sCity) {
        dfLists = null;
        /**
         * 处理从heweather网站上获取的json代码，进行解析赋值操作的静态方法
         */
        WeatherBean weather = new WeatherBean();
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
                    editor.putString("jsonText", jsonText);
                    editor.commit();

                } catch (IOException e) {

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
        String jsonText = preferences.getString("jsonText", "");

        ArrayList<DailyForecast> dfList = new ArrayList<DailyForecast>();
        try {
            JSONObject weatherJSONObject = new JSONObject(jsonText);//first grade
            JSONArray allJSONObject = weatherJSONObject.getJSONArray("HeWeather data service 3.0");//second:HeWeather data service 3.0
            JSONObject OJSONObject = allJSONObject.getJSONObject(0);//third:"0"


            JSONObject aqiJSONObject = OJSONObject.getJSONObject("aqi");//fouth grade
            JSONObject cityJSONObject = aqiJSONObject.getJSONObject("city");//fifth grade
            int aqi = cityJSONObject.getInt("aqi");
            weather.setAqi(aqi);
            Log.d("JSONF aqi", "" + weather.getAqi());
            int pm10 = cityJSONObject.getInt("pm10");
            weather.setPm10(pm10);
            Log.d("JSONF pm10", "" + weather.getPm10());
            int pm25 = cityJSONObject.getInt("pm25");
            weather.setPm25(pm25);
            Log.d("JSONF pm25", "" + weather.getPm25());
            String qlty = cityJSONObject.getString("qlty");
            weather.setQlty(qlty);
            Log.d("JSONF qlty", "" + weather.getQlty());
            //Toast.makeText(context,jsonText,Toast.LENGTH_LONG).show();

            JSONObject basicJSONObject = OJSONObject.getJSONObject("basic");//fouth grade
            String city = basicJSONObject.getString("city");
            weather.setCity(city);
            Log.d("JSONF city", "" + weather.getCity());
            String cnty = basicJSONObject.getString("cnty");
            weather.setCnty(cnty);
            Log.d("JSONF cnty", "" + weather.getCnty());
            String id = basicJSONObject.getString("id");
            weather.setId(id);
            Log.d("JSONF id", "" + weather.getId());
            Double lat = basicJSONObject.getDouble("lat");
            weather.setLat(lat);
            Log.d("JSONF lat", "" + weather.getLat());
            Double lon = basicJSONObject.getDouble("lon");
            weather.setLon(lon);
            Log.d("JSONF lon", "" + weather.getLon());
            JSONObject updateJSONObject = basicJSONObject.getJSONObject("update");//fifth grade
            String loc = updateJSONObject.getString("loc");
            weather.setLoc(loc);
            Log.d("JSONF loc", "" + weather.getLoc());
            String utc = updateJSONObject.getString("utc");
            weather.setUtc(utc);
            Log.d("JSONF utc", "" + weather.getUtc());

            //JSONArray daily_forecastJSONObject = weatherJSONObject.getJSONArray("daily_forecast");//fouth:daily_forecast
            //shuzu


            //"now"
            JSONObject nowJSONObject = OJSONObject.getJSONObject("now");//fouth grade;
            JSONObject condSONObject = nowJSONObject.getJSONObject("cond");//fifth grade
            weather.setCode(condSONObject.getInt("code"));
            Log.d("JSONF code", "" + weather.getCode());
            weather.setTxt(condSONObject.getString("txt"));
            Log.d("JSONF txt", "" + weather.getTxt());
            weather.setNow_fl(nowJSONObject.getInt("fl"));
            Log.d("JSONF now_fl", "" + weather.getNow_fl());
            weather.setNow_hum(nowJSONObject.getInt("hum"));
            Log.d("JSONF now_hum", "" + weather.getNow_hum());
            weather.setNow_pcpn(nowJSONObject.getInt("pcpn"));
            Log.d("JSONF now_pcpn", "" + weather.getNow_pcpn());
            weather.setNow_pres(nowJSONObject.getInt("pres"));
            Log.d("JSONF now_pres", "" + weather.getNow_pres());
            weather.setNow_tmp(nowJSONObject.getInt("tmp"));
            Log.d("JSONF now_tmp", "" + weather.getNow_tmp());
            weather.setNow_vis(nowJSONObject.getInt("vis"));
            Log.d("JSONF now_vis", "" + weather.getNow_vis());
            //"wind"
            JSONObject windJSONObject = nowJSONObject.getJSONObject("wind");//fifth grade
            weather.setNow_deg(windJSONObject.getInt("deg"));
            Log.d("JSONF now_deg", "" + weather.getNow_deg());
            weather.setNow_dir(windJSONObject.getString("dir"));
            Log.d("JSONF now_dir", "" + weather.getNow_dir());
            weather.setNow_sc(windJSONObject.getString("sc"));
            Log.d("JSONF now_sc", "" + weather.getNow_sc());
            weather.setNow_spd(windJSONObject.getString("spd"));
            Log.d("JSONF now_spd", "" + weather.getNow_spd());
            weather.setStatus(OJSONObject.getString("status"));
            Log.d("JSONF status", "" + weather.getStatus());

            //"suggestion"
            JSONObject suggJSONObject = OJSONObject.getJSONObject("suggestion");//fouth grade;
            //"comf"
            JSONObject comfJSONObject = suggJSONObject.getJSONObject("comf");//fifth grade;
            weather.setComf_brf(comfJSONObject.getString("brf"));
            Log.d("JSONF comf_brf", "" + weather.getComf_brf());
            weather.setComf_txt(comfJSONObject.getString("txt"));
            Log.d("JSONF comf_txt", "" + weather.getComf_txt());
            //"cw"
            JSONObject cwJSONObject = suggJSONObject.getJSONObject("cw");//fifth grade;
            weather.setCw_brf(cwJSONObject.getString("brf"));
            Log.d("JSONF cw_brf", "" + weather.getCw_brf());
            weather.setCw_txt(cwJSONObject.getString("txt"));
            Log.d("JSONF cw_txt", "" + weather.getCw_txt());
            //"drsg"
            JSONObject drsgJSONObject = suggJSONObject.getJSONObject("drsg");//fifth grade;
            weather.setDrsg_brf(drsgJSONObject.getString("brf"));
            Log.d("JSONF drsg_brf", "" + weather.getDrsg_brf());
            weather.setDrsg_txt(drsgJSONObject.getString("txt"));
            Log.d("JSONF drsg_txt", "" + weather.getDrsg_txt());
            //"flu"
            JSONObject fluJSONObject = suggJSONObject.getJSONObject("flu");//fifth grade;
            weather.setFlu_brf(fluJSONObject.getString("brf"));
            Log.d("JSONF flu_brf", "" + weather.getFlu_brf());
            weather.setFlu_txt(fluJSONObject.getString("txt"));
            Log.d("JSONF flu_txt", "" + weather.getFlu_txt());
            //"sport"
            JSONObject sportJSONObject = suggJSONObject.getJSONObject("sport");//fifth grade;
            weather.setSport_brf(sportJSONObject.getString("brf"));
            Log.d("JSONF sport_brf", "" + weather.getSport_brf());
            weather.setSport_txt(sportJSONObject.getString("txt"));
            Log.d("JSONF sport_txt", "" + weather.getSport_txt());
            //"trav"
            JSONObject travJSONObject = suggJSONObject.getJSONObject("trav");//fifth grade;
            weather.setTrav_brf(travJSONObject.getString("brf"));
            Log.d("JSONF trav_brf", "" + weather.getTrav_brf());
            weather.setTrav_txt(travJSONObject.getString("txt"));
            Log.d("JSONF trav_txt", "" + weather.getTrav_txt());
            //"uv"
            JSONObject uvJSONObject = suggJSONObject.getJSONObject("uv");//fifth grade;
            weather.setUv_brf(uvJSONObject.getString("brf"));
            Log.d("JSONF uv_brf", "" + weather.getUv_brf());
            weather.setUv_txt(uvJSONObject.getString("txt"));
            Log.d("JSONF uv_txt", "" + weather.getUv_txt());


            //dailyforecast
            JSONArray dfJSONObject = OJSONObject.getJSONArray("daily_forecast");//fouth grade;

            for (int i = 0; i < dfJSONObject.length(); i++) {

                DailyForecast mDailyForecast = new DailyForecast();
                Log.d("JSON df_sr", "" + i);
                JSONObject df0JSONObject = dfJSONObject.getJSONObject(i);//fifth:"0"
//                mDailyForecast.setUv(df0JSONObject.getInt("uv"));
//                Log.d()("JSONF df_uv"+i,""+mDailyForecast.getUv());
                mDailyForecast.setVis(df0JSONObject.getInt("vis"));
                Log.d("JSONF df_vis" + i, "" + mDailyForecast.getVis());
                mDailyForecast.setDate(df0JSONObject.getString("date"));
                Log.d("JSONF df_date" + i, "" + mDailyForecast.getDate());
                mDailyForecast.setHum(df0JSONObject.getInt("hum"));
                Log.d("JSONF df_hum" + i, "" + mDailyForecast.getHum());
                mDailyForecast.setPcpn(df0JSONObject.getString("pcpn"));
                Log.d("JSONF df_pcpn" + i, "" + mDailyForecast.getPcpn());
                mDailyForecast.setPop(df0JSONObject.getInt("pop"));
                Log.d("JSONF df_pop" + i, "" + mDailyForecast.getPop());
                mDailyForecast.setPres(df0JSONObject.getInt("pres"));
                Log.d("JSONF df_pres" + i, "" + mDailyForecast.getPres());
                //"astro"
                JSONObject dfAstroJSONObject = df0JSONObject.getJSONObject("astro");//sixth
                mDailyForecast.setSr(dfAstroJSONObject.getString("sr"));
                Log.d("JSONF df_sr" + i, "" + mDailyForecast.getSr());
                mDailyForecast.setSs(dfAstroJSONObject.getString("ss"));
                Log.d("JSONF df_ss" + i, "" + mDailyForecast.getSs());
                //"cond"
                JSONObject dfCondJSONObject = df0JSONObject.getJSONObject("cond");//sixth
                mDailyForecast.setCode_d(dfCondJSONObject.getInt("code_d"));
                Log.d("JSONF df_code_d" + i, "" + mDailyForecast.getCode_d());
                mDailyForecast.setCode_n(dfCondJSONObject.getInt("code_n"));
                Log.d("JSONF df_code_n" + i, "" + mDailyForecast.getCode_n());
                mDailyForecast.setTxt_d(dfCondJSONObject.getString("txt_d"));
                Log.d("JSONF df_txt_d" + i, "" + mDailyForecast.getTxt_d());
                mDailyForecast.setTxt_n(dfCondJSONObject.getString("txt_n"));
                Log.d("JSONF df_txt_n" + i, "" + mDailyForecast.getTxt_n());
                //"tmp"
                JSONObject dfTmpJSONObject = df0JSONObject.getJSONObject("tmp");//sixth
                if (i == 0) {
                    weather.setNow_max(dfTmpJSONObject.getInt("max"));
                    weather.setNow_min(dfTmpJSONObject.getInt("min"));
                    weather.setMain_weather_img(mDailyForecast.getTxt_d());
                }
                mDailyForecast.setMax(dfTmpJSONObject.getInt("max"));
                Log.d("JSONF df_max" + i, "" + mDailyForecast.getMax());
                mDailyForecast.setMin(dfTmpJSONObject.getInt("min"));
                Log.d("JSONF df_min" + i, "" + mDailyForecast.getMin());

                //"wind"
                JSONObject dfWindJSONObject = df0JSONObject.getJSONObject("wind");//sixth
                mDailyForecast.setDeg(dfWindJSONObject.getInt("deg"));
                Log.d("JSONF df_deg" + i, "" + mDailyForecast.getDeg());
                mDailyForecast.setDir(dfWindJSONObject.getString("dir"));
                Log.d("JSONF df_dir" + i, "" + mDailyForecast.getDir());
                mDailyForecast.setSc(dfWindJSONObject.getString("sc"));
                Log.d("JSONF df_sc" + i, "" + mDailyForecast.getSc());
                mDailyForecast.setSpd(dfWindJSONObject.getInt("spd"));
                Log.d("JSONF df_spd" + i, "" + mDailyForecast.getSpd());
                /*
                //dfList.add()
                JSONObject df0JSONObject = dfJSONObject.getJSONObject(i);//fifth:"0"
                //"astro"
                JSONObject dfAstroJSONObject = df0JSONObject.getJSONObject("astro");//sixth:"0"
                dailyForecastss[i].setSr(dfAstroJSONObject.getString("sr"));
                Log.d()("JSON df_sr",""+dailyForecastss[i].getSr());
                dailyForecastss[i].setSs(dfAstroJSONObject.getString("ss"));
                Log.d()("JSON df_sr",""+dailyForecastss[i].getSr());
                //dailyForecastss[i].setSr();
*/

                dfList.add(mDailyForecast);
            }

            dfLists = dfList;
            for (DailyForecast md : dfList
                    ) {
                Log.d("kakan", "" + md.getDate());

            }

            ArrayList<HourlyForecast> hfList = new ArrayList<HourlyForecast>();
            JSONArray hfJSONObject = OJSONObject.getJSONArray("hourly_forecast");//fouth grade;

            for (int i = 0; i < hfJSONObject.length(); i++) {
                HourlyForecast hourlyForecast = new HourlyForecast();
                JSONObject hf0JSONObject = hfJSONObject.getJSONObject(i);
                hourlyForecast.setDate(hf0JSONObject.getString("date"));
                Log.d("hourlyforecast", "date" + hourlyForecast.getDate());
                hourlyForecast.setHum(hf0JSONObject.getInt("hum"));
                Log.d("hourlyforecast", "hum" + hourlyForecast.getHum());
                hourlyForecast.setPop(hf0JSONObject.getInt("pop"));
                Log.d("hourlyforecast", "pop" + hourlyForecast.getPop());
                hourlyForecast.setPres(hf0JSONObject.getInt("pres"));
                Log.d("hourlyforecast", "pres" + hourlyForecast.getPres());
                hourlyForecast.setTmp(hf0JSONObject.getInt("tmp"));
                Log.d("hourlyforecast", "tmp" + hourlyForecast.getTmp());

                JSONObject hf0WindJSONObject = hf0JSONObject.getJSONObject("wind");
                hourlyForecast.setDeg(hf0WindJSONObject.getInt("deg"));
                Log.d("hourlyforecast", "deg" + hourlyForecast.getDeg());
                hourlyForecast.setDir(hf0WindJSONObject.getString("dir"));
                Log.d("hourlyforecast", "dir" + hourlyForecast.getDir());
                hourlyForecast.setSc(hf0WindJSONObject.getString("sc"));
                Log.d("hourlyforecast", "sc" + hourlyForecast.getSc());
                hourlyForecast.setSpd(hf0WindJSONObject.getInt("spd"));
                Log.d("hourlyforecast", "spd" + hourlyForecast.getSpd());
                hfList.add(hourlyForecast);
            }
                hfLists = hfList;

            /*//"0"
            JSONObject df0JSONObject = dfJSONObject.getJSONObject(0);//third:"0"
                //"astro"
                JSONObject dfAstroJSONObject = df0JSONObject.getJSONObject("astro");//third:"0"
                    mDailyForecast.setSr(dfAstroJSONObject.getString("sr"));
                    Log.d()("JSON df_sr","");
*/


            //赋值操作
        } catch (JSONException e) {
            System.out.println("test");
            e.printStackTrace();
        }

        Log.i("util1", "" + weather.getCity());
        return weather;//返回weatherBean类型对象
    }

    public static ArrayList<DailyForecast> getDForecast() {
        ArrayList<DailyForecast> rdfList = dfLists;
        return rdfList;
    }

    public static ArrayList<HourlyForecast> getHForecast() {
        ArrayList<HourlyForecast> rhfList = hfLists;
        return rhfList;
    }
}

