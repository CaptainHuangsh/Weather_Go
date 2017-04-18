package com.example.owen.weathergo.common.util;

/**
 * Created by owenh on 2016/5/17.
 * 处理从openweathermap网站上获取的json代码，进行解析赋值操作
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.owen.weathergo.component.WgClient;
import com.example.owen.weathergo.modules.dao.DailyForecast;
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

    public static WeatherBean getWeatherBeans(final Context context, String sCity) {
        /**
         * 处理从heweather网站上获取的json代码，进行解析赋值操作的静态方法
         */
//        String jsonText = "";
        WeatherBean weather = new WeatherBean();
        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.0.110:4567/")
                .baseUrl("https://api.heweather.com/x3/")
                .build();
        //Retrofit创建一个BlogService的代理对象
        WgClient service = retrofit.create(WgClient.class);
        Call<ResponseBody> call = service.mWeatherAPI("kaifeng", "b2a628bc1de942dc869fcbe524c65313");
        String jss = "";
        call.enqueue(new Callback<ResponseBody>() {
            String jsonText = "";

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                WeatherBean weather = new WeatherBean();
                try {
//                    weather.setApd(null);
                    jsonText = "" + response.body().string();
                    Log.i("huangr", "" + jsonText);

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

        String jsonText = preferences.getString("jsonText", "");
//        String jsonText = new JSONFetcher().getJSONText(url);

        ArrayList<DailyForecast> dfList = new ArrayList<DailyForecast>();
//        DailyForecast mDailyForecast = new DailyForecast();
        //Toast.makeText(context,jsonText,Toast.LENGTH_LONG).show();
        try {
            JSONObject weatherJSONObject = new JSONObject(jsonText);//first grade
            Log.i("chuanru", "" + jsonText);
            JSONArray allJSONObject = weatherJSONObject.getJSONArray("HeWeather data service 3.0");//second:HeWeather data service 3.0
            JSONObject OJSONObject = allJSONObject.getJSONObject(0);//third:"0"


            JSONObject aqiJSONObject = OJSONObject.getJSONObject("aqi");//fouth grade
            JSONObject cityJSONObject = aqiJSONObject.getJSONObject("city");//fifth grade
            int aqi = cityJSONObject.getInt("aqi");
            weather.setAqi(aqi);
            Log.e("JSONF aqi", "" + weather.getAqi());
            int pm10 = cityJSONObject.getInt("pm10");
            weather.setPm10(pm10);
            Log.e("JSONF pm10", "" + weather.getPm10());
            int pm25 = cityJSONObject.getInt("pm25");
            weather.setPm25(pm25);
            Log.e("JSONF pm25", "" + weather.getPm25());
            String qlty = cityJSONObject.getString("qlty");
            weather.setQlty(qlty);
            Log.e("JSONF qlty", "" + weather.getQlty());
            //Toast.makeText(context,jsonText,Toast.LENGTH_LONG).show();

            JSONObject basicJSONObject = OJSONObject.getJSONObject("basic");//fouth grade
            String city = basicJSONObject.getString("city");
            weather.setCity(city);
            Log.e("JSONF city", "" + weather.getCity());
            String cnty = basicJSONObject.getString("cnty");
            weather.setCnty(cnty);
            Log.e("JSONF cnty", "" + weather.getCnty());
            String id = basicJSONObject.getString("id");
            weather.setId(id);
            Log.e("JSONF id", "" + weather.getId());
            Double lat = basicJSONObject.getDouble("lat");
            weather.setLat(lat);
            Log.e("JSONF lat", "" + weather.getLat());
            Double lon = basicJSONObject.getDouble("lon");
            weather.setLon(lon);
            Log.e("JSONF lon", "" + weather.getLon());
            JSONObject updateJSONObject = basicJSONObject.getJSONObject("update");//fifth grade
            String loc = updateJSONObject.getString("loc");
            weather.setLoc(loc);
            Log.e("JSONF loc", "" + weather.getLoc());
            String utc = updateJSONObject.getString("utc");
            weather.setUtc(utc);
            Log.e("JSONF utc", "" + weather.getUtc());

            //JSONArray daily_forecastJSONObject = weatherJSONObject.getJSONArray("daily_forecast");//fouth:daily_forecast
            //shuzu


            //"now"
            JSONObject nowJSONObject = OJSONObject.getJSONObject("now");//fouth grade;
            JSONObject condSONObject = nowJSONObject.getJSONObject("cond");//fifth grade
            weather.setCode(condSONObject.getInt("code"));
            Log.e("JSONF code", "" + weather.getCode());
            weather.setTxt(condSONObject.getString("txt"));
            Log.e("JSONF txt", "" + weather.getTxt());
            weather.setNow_fl(nowJSONObject.getInt("fl"));
            Log.e("JSONF now_fl", "" + weather.getNow_fl());
            weather.setNow_hum(nowJSONObject.getInt("hum"));
            Log.e("JSONF now_hum", "" + weather.getNow_hum());
            weather.setNow_pcpn(nowJSONObject.getInt("pcpn"));
            Log.e("JSONF now_pcpn", "" + weather.getNow_pcpn());
            weather.setNow_pres(nowJSONObject.getInt("pres"));
            Log.e("JSONF now_pres", "" + weather.getNow_pres());
            weather.setNow_tmp(nowJSONObject.getInt("tmp"));
            Log.e("JSONF now_tmp", "" + weather.getNow_tmp());
            weather.setNow_vis(nowJSONObject.getInt("vis"));
            Log.e("JSONF now_vis", "" + weather.getNow_vis());
            //"wind"
            JSONObject windJSONObject = nowJSONObject.getJSONObject("wind");//fifth grade
            weather.setNow_deg(windJSONObject.getInt("deg"));
            Log.e("JSONF now_deg", "" + weather.getNow_deg());
            weather.setNow_dir(windJSONObject.getString("dir"));
            Log.e("JSONF now_dir", "" + weather.getNow_dir());
            weather.setNow_sc(windJSONObject.getString("sc"));
            Log.e("JSONF now_sc", "" + weather.getNow_sc());
            weather.setNow_spd(windJSONObject.getString("spd"));
            Log.e("JSONF now_spd", "" + weather.getNow_spd());
            weather.setStatus(OJSONObject.getString("status"));
            Log.e("JSONF status", "" + weather.getStatus());

            //"suggestion"
            JSONObject suggJSONObject = OJSONObject.getJSONObject("suggestion");//fouth grade;
            //"comf"
            JSONObject comfJSONObject = suggJSONObject.getJSONObject("comf");//fifth grade;
            weather.setComf_brf(comfJSONObject.getString("brf"));
            Log.e("JSONF comf_brf", "" + weather.getComf_brf());
            weather.setComf_txt(comfJSONObject.getString("txt"));
            Log.e("JSONF comf_txt", "" + weather.getComf_txt());
            //"cw"
            JSONObject cwJSONObject = suggJSONObject.getJSONObject("cw");//fifth grade;
            weather.setCw_brf(cwJSONObject.getString("brf"));
            Log.e("JSONF cw_brf", "" + weather.getCw_brf());
            weather.setCw_txt(cwJSONObject.getString("txt"));
            Log.e("JSONF cw_txt", "" + weather.getCw_txt());
            //"drsg"
            JSONObject drsgJSONObject = suggJSONObject.getJSONObject("drsg");//fifth grade;
            weather.setDrsg_brf(drsgJSONObject.getString("brf"));
            Log.e("JSONF drsg_brf", "" + weather.getDrsg_brf());
            weather.setDrsg_txt(drsgJSONObject.getString("txt"));
            Log.e("JSONF drsg_txt", "" + weather.getDrsg_txt());
            //"flu"
            JSONObject fluJSONObject = suggJSONObject.getJSONObject("flu");//fifth grade;
            weather.setFlu_brf(fluJSONObject.getString("brf"));
            Log.e("JSONF flu_brf", "" + weather.getFlu_brf());
            weather.setFlu_txt(fluJSONObject.getString("txt"));
            Log.e("JSONF flu_txt", "" + weather.getFlu_txt());
            //"sport"
            JSONObject sportJSONObject = suggJSONObject.getJSONObject("sport");//fifth grade;
            weather.setSport_brf(sportJSONObject.getString("brf"));
            Log.e("JSONF sport_brf", "" + weather.getSport_brf());
            weather.setSport_txt(sportJSONObject.getString("txt"));
            Log.e("JSONF sport_txt", "" + weather.getSport_txt());
            //"trav"
            JSONObject travJSONObject = suggJSONObject.getJSONObject("trav");//fifth grade;
            weather.setTrav_brf(travJSONObject.getString("brf"));
            Log.e("JSONF trav_brf", "" + weather.getTrav_brf());
            weather.setTrav_txt(travJSONObject.getString("txt"));
            Log.e("JSONF trav_txt", "" + weather.getTrav_txt());
            //"uv"
            JSONObject uvJSONObject = suggJSONObject.getJSONObject("uv");//fifth grade;
            weather.setUv_brf(uvJSONObject.getString("brf"));
            Log.e("JSONF uv_brf", "" + weather.getUv_brf());
            weather.setUv_txt(uvJSONObject.getString("txt"));
            Log.e("JSONF uv_txt", "" + weather.getUv_txt());


            //dailyforecast
            JSONArray dfJSONObject = OJSONObject.getJSONArray("daily_forecast");//fouth grade;

            for (int i = 0; i < dfJSONObject.length(); i++) {
                DailyForecast mDailyForecast = new DailyForecast();
                Log.e("JSON df_sr", "" + i);
                JSONObject df0JSONObject = dfJSONObject.getJSONObject(i);//fifth:"0"
//                mDailyForecast.setUv(df0JSONObject.getInt("uv"));
//                Log.e("JSONF df_uv"+i,""+mDailyForecast.getUv());
                mDailyForecast.setVis(df0JSONObject.getInt("vis"));
                Log.e("JSONF df_vis" + i, "" + mDailyForecast.getVis());
                mDailyForecast.setDate(df0JSONObject.getString("date"));
                Log.e("JSONF df_date" + i, "" + mDailyForecast.getDate());
                mDailyForecast.setHum(df0JSONObject.getInt("hum"));
                Log.e("JSONF df_hum" + i, "" + mDailyForecast.getHum());
                mDailyForecast.setPcpn(df0JSONObject.getString("pcpn"));
                Log.e("JSONF df_pcpn" + i, "" + mDailyForecast.getPcpn());
                mDailyForecast.setPop(df0JSONObject.getInt("pop"));
                Log.e("JSONF df_pop" + i, "" + mDailyForecast.getPop());
                mDailyForecast.setPres(df0JSONObject.getInt("pres"));
                Log.e("JSONF df_pres" + i, "" + mDailyForecast.getPres());
                //"astro"
                JSONObject dfAstroJSONObject = df0JSONObject.getJSONObject("astro");//sixth
                mDailyForecast.setSr(dfAstroJSONObject.getString("sr"));
                Log.e("JSONF df_sr" + i, "" + mDailyForecast.getSr());
                mDailyForecast.setSs(dfAstroJSONObject.getString("ss"));
                Log.e("JSONF df_ss" + i, "" + mDailyForecast.getSs());
                //"cond"
                JSONObject dfCondJSONObject = df0JSONObject.getJSONObject("cond");//sixth
                mDailyForecast.setCode_d(dfCondJSONObject.getInt("code_d"));
                Log.e("JSONF df_code_d" + i, "" + mDailyForecast.getCode_d());
                mDailyForecast.setCode_n(dfCondJSONObject.getInt("code_n"));
                Log.e("JSONF df_code_n" + i, "" + mDailyForecast.getCode_n());
                mDailyForecast.setTxt_d(dfCondJSONObject.getString("txt_d"));
                Log.e("JSONF df_txt_d" + i, "" + mDailyForecast.getTxt_d());
                mDailyForecast.setTxt_n(dfCondJSONObject.getString("txt_n"));
                Log.e("JSONF df_txt_n" + i, "" + mDailyForecast.getTxt_n());
                //"tmp"
                JSONObject dfTmpJSONObject = df0JSONObject.getJSONObject("tmp");//sixth
                mDailyForecast.setMax(dfTmpJSONObject.getInt("max"));
                Log.e("JSONF df_max" + i, "" + mDailyForecast.getMax());
                mDailyForecast.setMin(dfTmpJSONObject.getInt("min"));
                Log.e("JSONF df_min" + i, "" + mDailyForecast.getMin());

                //"wind"
                JSONObject dfWindJSONObject = df0JSONObject.getJSONObject("wind");//sixth
                mDailyForecast.setDeg(dfWindJSONObject.getInt("deg"));
                Log.e("JSONF df_deg" + i, "" + mDailyForecast.getDeg());
                mDailyForecast.setDir(dfWindJSONObject.getString("dir"));
                Log.e("JSONF df_dir" + i, "" + mDailyForecast.getDir());
                mDailyForecast.setSc(dfWindJSONObject.getString("sc"));
                Log.e("JSONF df_sc" + i, "" + mDailyForecast.getSc());
                mDailyForecast.setSpd(dfWindJSONObject.getInt("spd"));
                Log.e("JSONF df_spd" + i, "" + mDailyForecast.getSpd());
                /*
                //dfList.add()
                JSONObject df0JSONObject = dfJSONObject.getJSONObject(i);//fifth:"0"
                //"astro"
                JSONObject dfAstroJSONObject = df0JSONObject.getJSONObject("astro");//sixth:"0"
                dailyForecastss[i].setSr(dfAstroJSONObject.getString("sr"));
                Log.e("JSON df_sr",""+dailyForecastss[i].getSr());
                dailyForecastss[i].setSs(dfAstroJSONObject.getString("ss"));
                Log.e("JSON df_sr",""+dailyForecastss[i].getSr());
                //dailyForecastss[i].setSr();
*/

                dfList.add(mDailyForecast);
            }

            dfLists = dfList;
            for (DailyForecast md : dfList
                    ) {
                Log.e("kakan", "" + md.getDate());

            }


            /*//"0"
            JSONObject df0JSONObject = dfJSONObject.getJSONObject(0);//third:"0"
                //"astro"
                JSONObject dfAstroJSONObject = df0JSONObject.getJSONObject("astro");//third:"0"
                    mDailyForecast.setSr(dfAstroJSONObject.getString("sr"));
                    Log.e("JSON df_sr","");
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
}

