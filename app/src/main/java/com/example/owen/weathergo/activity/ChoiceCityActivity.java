package com.example.owen.weathergo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.util.DBManager;
import com.example.owen.weathergo.util.WeatherDB;
import com.example.owen.weathergo.modules.dao.City;
import com.example.owen.weathergo.modules.dao.Province;
import com.example.owen.weathergo.modules.adapter.CityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoiceCityActivity extends AppCompatActivity {

    @BindView(R.id.city_recycle)
    RecyclerView mCityRecy;

    private ArrayList<String> dataList = new ArrayList<>();
    private Province selectedProvince;
    private int proNum;
    private City selectedCity;
    private List<Province> provincesList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();

    private CityAdapter mAdapter;

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    private int currentLevel;
    SharedPreferences preferences;
    private boolean isChecked = false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise_city);
        ButterKnife.bind(this);
        currentLevel = 0;
        DBManager.getInstance().openDatabase();
        initRecycleView();
        queryProvince();

    }

    private void initRecycleView() {
        mCityRecy.setLayoutManager(new LinearLayoutManager(this));
        mCityRecy.setHasFixedSize(true);
        mAdapter = new CityAdapter(this, dataList);
        mCityRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CityAdapter.OnRecyclerViewItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(View view, int pos) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provincesList.get(pos);
                    queryCities(selectedProvince.getProSort());
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(pos);
                    preferences = getApplicationContext().getSharedPreferences("huang", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("city");
                    editor.putString("city", selectedCity.getCityName());
                    editor.commit();
                    finish();
                }

            }
        });

    }

    private void quit() {
        ChoiceCityActivity.this.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void queryCities(int ProId) {
        cityList.clear();
        DBManager.getInstance().openDatabase();
        cityList.addAll(WeatherDB.loadCities(DBManager.getInstance().getDatabase(), ProId));
        DBManager.getInstance().closeDatabase();
        dataList.clear();
        for (City city : cityList) {
            dataList.add(city.getCityName());
        }
        currentLevel = LEVEL_CITY;//城市级
        mAdapter.notifyDataSetChanged();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void queryProvince() {
        provincesList.clear();
        DBManager.getInstance().openDatabase();
        provincesList.addAll(WeatherDB.loadProvinces(DBManager.getInstance().getDatabase()));
        DBManager.getInstance().closeDatabase();

        dataList.clear();

        for (Province province : provincesList) {
            dataList.add(province.getProName());
        }
        mAdapter.notifyDataSetChanged();
        currentLevel = LEVEL_PROVINCE;//省级
    }

    /*
    * 捕获Back键，根据当前的级别来判断，此时应该返回省列表还是直接退出
    *
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        //重写onBackPressed()方法需要将super.onBackPressed()注释掉，不然无论执行什么都会默认执行这一句而退出
        if (currentLevel == LEVEL_PROVINCE) {
            finish();
        } else if (currentLevel == LEVEL_CITY) {
            queryProvince();
            selectedProvince = null;
        }
    }


    //启动Activity方法
    public static void launch(Context context) {
        context.startActivity(new Intent(context, ChoiceCityActivity.class));
    }

}
