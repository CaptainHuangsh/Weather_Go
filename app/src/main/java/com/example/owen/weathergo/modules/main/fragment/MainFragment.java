package com.example.owen.weathergo.modules.main.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.base.BaseFragment;
import com.example.owen.weathergo.component.DLForecast;
import com.example.owen.weathergo.modules.dao.DailyForecast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by owen on 2017/4/20.
 */

public class MainFragment extends BaseFragment {
    @BindView(R.id.hsh_weather_city_editview)
    EditText mCity;//城市名称输入框，通过城市名称进行查询，大陆地区城市不全且支持拼音
    @BindView(R.id.weather_country)
    TextView mCountry;
    @BindView(R.id.weather_temp_min)
    TextView mTemp_min;
    @BindView(R.id.weather_temp_max)
    TextView mTemp_max;
    @BindView(R.id.weather_wind_speed)
    TextView mWind_speed;
    @BindView(R.id.weather_temp)
    TextView mTemp;
    @BindView(R.id.weather_suggesstions)
    TextView mSugg;
    @BindView(R.id.tl_custom)
    Toolbar mToolBar;
    @BindView(R.id.dl_left)
    DrawerLayout mDrawerLayout;
    /*@BindView(R.id.lv_left_menu)
    ListView lvLeftMenu;*/
    /*@BindView(R.id.weather_forecast)
    ListView mForecastList;*/
    /*@BindView(R.id.weather_touxiang)
    ImageView mLogImg;*/
    @BindView(R.id.weather_img)
    ImageView ToImg;


    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<DailyForecast> mDFList = new ArrayList<>();
    private ArrayAdapter arrayAdapter;
    private String mCityStr = "kaifeng";
    private String mGCityStr = "";
    private List<DLForecast> dlForecastList = new ArrayList<DLForecast>();
    private View view1, view2;
    private List<View> viewList;//view数组
    private boolean isSugg = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MAINFRAGMENT", "onCreate");
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
