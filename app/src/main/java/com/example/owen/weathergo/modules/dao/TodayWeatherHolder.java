package com.example.owen.weathergo.modules.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseViewHolder;
import com.example.owen.weathergo.modules.domain.Weather;
import com.example.owen.weathergo.util.IconGet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by owen on 2017/4/24.
 */

public class TodayWeatherHolder extends BaseViewHolder<Weather> {

    private final String TAG = TodayWeatherHolder.class.getSimpleName();
    private Context mContext;
    private TextView mTemp_min;
    private TextView mTemp_max;
    private TextView mAir;
    private TextView mWind_speed;
    private TextView mTemp;
    private TextView mCity;
    private ImageView mImg;
    private ImageView mBingPic;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                    String bingPic = prefs.getString("bing_pic", null);
                    Glide.with(mContext).load(bingPic).into(mBingPic);
                    break;
            }
        }
    };

    public TodayWeatherHolder(View view, Weather weather) {
        super(view);
        mContext = view.getContext();
        mTemp_min = (TextView) view.findViewById(R.id.weather_temp_min);
        mTemp_max = (TextView) view.findViewById(R.id.weather_temp_max);
        mAir = (TextView) view.findViewById(R.id.weather_air);
        mWind_speed = (TextView) view.findViewById(R.id.weather_wind_speed);
        mTemp = (TextView) view.findViewById(R.id.weather_temp);
        mImg = (ImageView) view.findViewById(R.id.weather_img);
        mBingPic = (ImageView) view.findViewById(R.id.bg_pic);
        mCity = (TextView) view.findViewById(R.id.weather_city);
    }

    @Override
    public void bind(Weather weather) {

        try {
            mTemp_min.setText(mContext.getResources().getString(R.string.temp_min)
                    + weather.getDailyForecast().get(0).getTmp().getMin()
                    + mContext.getResources().getString(R.string.c));
            mTemp_max.setText(mContext.getResources().getString(R.string.temp_max)
                    + weather.getDailyForecast().get(0).getTmp().getMax()
                    + mContext.getResources().getString(R.string.c));
            mWind_speed.setText(mContext.getResources().getString(R.string.wind_speed)
                    + weather.getNow().getWind().getDir()
                    + (weather.getNow().getWind().getSc().equals(mContext.getResources().getString(R.string.light_wind))
                    ? weather.getNow().getWind().getSc() : weather.getNow().getWind().getSc()
                    + mContext.getResources().getString(R.string.m_s)));
            mTemp.setText(
                    weather.getNow().getTmp()
                            + mContext.getResources().getString(R.string.c));
            mAir.setText(
                    weather.getAqi().getCity().getQlty().length() < 2 ? mContext.getResources().getString(R.string.air)
                            + weather.getAqi().getCity().getQlty() : weather.getAqi().getCity().getQlty());
            mCity.setText(weather.getBasic().getCity());
            mImg.setImageResource(IconGet.getWeaIcon(weather.getNow().getCond().getTxt()));
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String today = sf.format(c.getTime());
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
            String bingPic = prefs.getString("bing_pic", null);
            String date = prefs.getString("date", null);
            if (bingPic != null && today.equals(date)) {
                Glide.with(mContext).load(bingPic).into(mBingPic);
            } else {
                loadPic();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPic() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        final String today = sf.format(c.getTime());
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestBingPic)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.
                        getDefaultSharedPreferences(mContext).edit();
                editor.putString("bing_pic", bingPic);
                editor.putString("date", today);
                editor.apply();
                new Thread(() -> {
                    Message message = new Message();
                    message.what = 0;
                    mHandler.sendMessage(message);
                }).start();//又忘记start了
//                Glide.with(mContext).load(bingPic).into(mBingPic);
            }
        });
    }

}
