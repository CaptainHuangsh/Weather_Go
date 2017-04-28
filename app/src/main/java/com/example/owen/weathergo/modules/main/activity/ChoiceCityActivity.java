package com.example.owen.weathergo.modules.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.util.DBManager;
import com.example.owen.weathergo.common.util.WeatherDB;
import com.example.owen.weathergo.modules.dao.City;
import com.example.owen.weathergo.modules.dao.Province;
import com.example.owen.weathergo.modules.main.adapter.CityAdapter;

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
    private List<City> cityList;
    private CityAdapter mAdapter;

    public static final int LEVEL_PROVINCE = 1;
    public static final int LEVEL_CITY = 2;
    private int currentLevel;

    private boolean isChecked = false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise_city);
        ButterKnife.bind(this);
        /*dataList.add("洛阳");
        dataList.add("开封");
        dataList.add("北京");*/

        queryProvince();
        /*Observable.defer(() -> {
            //mDBManager = new DBManager(ChoiceCityActivity.this);
            DBManager.getInstance().openDatabase();
            return Observable.just(1);
        }).compose(RxUtils.rxSchedulerHelper())
                .compose(this.bindToLifecycle())
                .subscribe(integer -> {
                    initRecyclerView();
                    queryProvinces();
                });*/
        DBManager.getInstance().openDatabase();
        initRecycleView();

    }

    private void initRecycleView() {
        mCityRecy.setLayoutManager(new LinearLayoutManager(this));
        mCityRecy.setHasFixedSize(true);
        mAdapter = new CityAdapter(this, dataList);
        mCityRecy.setAdapter(mAdapter);
        Log.i("ChoiceCityActivityOnItc","ready to bind");
        mAdapter.setOnItemClickListener(new CityAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                    Log.i("ChoiceCityActivityOnItc",""+dataList.get(pos));
            }
        });
        Log.i("ChoiceCityActivityOnItc","has binded");

        /*mAdapter.setOnItemClickListener(new CityAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provincesList.get(pos);
                    mCityRecy.smoothScrollToPosition(0);
                    queryCities();
                }
                else if (currentLevel == LEVEL_CITY) {
                    String city = Util.replaceCity(cityList.get(pos).CityName);
                    if (isChecked) {

                    } else {

                    }
                    quit();
                }

            }
        })*/;
    }

    private void quit() {
        ChoiceCityActivity.this.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void queryCities() {
        if(cityList.isEmpty()){
            DBManager.getInstance().openDatabase();
            Log.i("ChoiceCityActivityQP",""+DBManager.getInstance().getDatabase());
            cityList.addAll(WeatherDB.loadCities(DBManager.getInstance().getDatabase(),1));
            DBManager.getInstance().closeDatabase();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void queryProvince(){
        if (provincesList.isEmpty()) {
            DBManager.getInstance().openDatabase();
            Log.i("ChoiceCityActivityQP",""+DBManager.getInstance().getDatabase());
            provincesList.addAll(WeatherDB.loadProvinces(DBManager.getInstance().getDatabase()));
            DBManager.getInstance().closeDatabase();
        }
        dataList.clear();

        for(Province province : provincesList){
            dataList.add(province.ProName);
        }
//        dataList.addAll(provincesList);
    }

    //启动Activity方法
    public static void launch(Context context) {
        context.startActivity(new Intent(context, ChoiceCityActivity.class));
    }
}
