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

    private boolean isChecked = false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise_city);
        ButterKnife.bind(this);
        currentLevel = 0;
        /*dataList.add("洛阳");
        dataList.add("开封");
        dataList.add("北京");*/
        DBManager.getInstance().openDatabase();
        initRecycleView();
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


    }

    private void initRecycleView() {
        mCityRecy.setLayoutManager(new LinearLayoutManager(this));
        mCityRecy.setHasFixedSize(true);
        mAdapter = new CityAdapter(this, dataList);
        mCityRecy.setAdapter(mAdapter);
        Log.i("ChoiceCityActivityOnItc", "ready to bind");
        mAdapter.setOnItemClickListener(new CityAdapter.OnRecyclerViewItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(View view, int pos) {
                Log.i("ChoiceCityActivityOnItc", "" + dataList.get(pos) + pos);
                if (currentLevel == LEVEL_PROVINCE) {
//                    selectedProvince.setProName(dataList.get(pos));
//                    selectedProvince.setProSort(pos + 1);
                    selectedProvince = provincesList.get(pos);
                    Log.i("ChoiceCityActivityOnItcP", "" + dataList.get(pos) + pos);
                    queryCities(selectedProvince.getProSort());
                } else if (currentLevel == LEVEL_CITY) {
                    //TODO 替代WeatherMain的主查询城市，并直接跳转到WeatherMain界面，传递值为城市名

                    //http://www.cnblogs.com/shaocm/archive/2013/01/08/2851248.html
//                    selectedCity.setCityName(dataList.get(pos) + "");
//                    selectedCity.setCitySort(0);//暂时没有用到所以没有去查询
//                    selectedCity.setProID(selectedProvince.getProSort());
//                    Intent intent = new Intent();
//                    intent.setClass(ChoiceCityActivity.this, WeatherMain.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("city", selectedCity);
//                    intent.putExtras(bundle);
//                    startActivity(intent);

                    SharedPreferences preferences;
                    preferences = getApplicationContext().getSharedPreferences("huang", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("city");
                    editor.putString("city", dataList.get(pos));
                    editor.commit();
                    finish();

//                    Intent intent = new Intent();
//                    intent.setClass(ChoiceCityActivity.this, WeatherMain.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("city", dataList.get(pos));
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                }
                //暂时没有从数据库中读取ProId而是从排列顺序中+1而得，有点走钢丝，后面deltWeek测试再考虑
            }
        });
        Log.i("ChoiceCityActivityOnItc", "has binded");

    }

    private void quit() {
        ChoiceCityActivity.this.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void queryCities(int ProId) {
        cityList.clear();
            DBManager.getInstance().openDatabase();
            Log.i("ChoiceCityActivityQP", "" + DBManager.getInstance().getDatabase());
            cityList.addAll(WeatherDB.loadCities(DBManager.getInstance().getDatabase(), ProId));
            DBManager.getInstance().closeDatabase();
        dataList.clear();
        for (City city : cityList) {
            dataList.add(city.getCityName());
        }
        currentLevel = LEVEL_CITY;//城市级
        mAdapter.notifyDataSetChanged();
//        dataList.clear();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void queryProvince() {
        provincesList.clear();
        DBManager.getInstance().openDatabase();
        Log.i("ChoiceCityActivityQP", "" + DBManager.getInstance().getDatabase());
        provincesList.addAll(WeatherDB.loadProvinces(DBManager.getInstance().getDatabase()));
        DBManager.getInstance().closeDatabase();

        dataList.clear();

        for (Province province : provincesList) {
            dataList.add(province.getProName());
        }
        mAdapter.notifyDataSetChanged();
//        dataList.addAll(provincesList);
        currentLevel = LEVEL_PROVINCE;//省级
    }

    /*
    * 捕获Back键，根据当前的级别来判断，此时应该返回省列表还是直接退出
    *
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        Log.i("Choiceonbackpress", "" + currentLevel);
//        super.onBackPressed();
        //重写onBackPressed()方法需要将super.onBackPressed()注释掉，不然无论执行什么都会默认执行这一句而退出
        if (currentLevel == LEVEL_PROVINCE) {
            Log.i("Choiceonbackpress2", "" + currentLevel);
            finish();
            Log.i("Choiceonbackpress3", "" + currentLevel);
        } else if (currentLevel == LEVEL_CITY) {
            Log.i("Choiceonbackpress4", "" + currentLevel);
            queryProvince();
            selectedProvince = null;
            Log.i("Choiceonbackpress5", "" + currentLevel);
        }
    }

    //启动Activity方法
    public static void launch(Context context) {
        context.startActivity(new Intent(context, ChoiceCityActivity.class));
    }

}
