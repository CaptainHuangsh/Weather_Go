package com.example.owen.weathergo.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseActivity;
import com.example.owen.weathergo.common.base.C;
import com.example.owen.weathergo.modules.adapter.CityAdapter;
import com.example.owen.weathergo.modules.domain.City;
import com.example.owen.weathergo.modules.domain.Province;
import com.example.owen.weathergo.util.DBManager;
import com.example.owen.weathergo.util.SharedPreferenceUtil;
import com.example.owen.weathergo.util.WeatherDB;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoiceCityActivity extends BaseActivity {

    @BindView(R.id.city_recycle)
    RecyclerView mCityRecycle;

    private ArrayList<String> dataList = new ArrayList<>();
    private Province selectedProvince;
    private City selectedCity;
    private List<Province> provincesList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();

    private CityAdapter mAdapter;

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    private String what_to_do;//从那个页面过来，多城市管理、主天气页面
    private String which_city;
    private int currentLevel;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choice_city);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        initRecycleView();
        queryProvince();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        setTitle("选择城市");
        currentLevel = 0;
        DBManager.getInstance().openDatabase();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = this.getIntent();
        what_to_do = intent.getStringExtra("what_to_do");
        which_city = ("".equals(intent.getStringExtra("which_city"))
                || intent.getStringExtra("which_city") == null)
                ? C.Tag_CITY_0 : intent.getStringExtra("which_city");
    }

    private void initRecycleView() {
        mCityRecycle.setLayoutManager(new LinearLayoutManager(this));
        mCityRecycle.setHasFixedSize(true);
        mAdapter = new CityAdapter(this, dataList);
        mCityRecycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, pos) -> {
            if (currentLevel == LEVEL_PROVINCE) {
                selectedProvince = provincesList.get(pos);
                queryCities(selectedProvince.getProSort());
            } else if (currentLevel == LEVEL_CITY) {
                selectedCity = cityList.get(pos);
                if ("select_multi_city".equals(what_to_do)) {
                    Intent intent = new Intent();
                    intent.putExtra("select_multi_city", selectedCity.getCityName());
                    setResult(RESULT_OK, intent);
                } else if (!"".equals(which_city)) {
                    updateCity();
                }
                quit();
            }

        });

    }

    private void quit() {
        ChoiceCityActivity.this.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void updateCity() {
        Intent intent = new Intent();
        intent.setClass(ChoiceCityActivity.this, WeatherMain.class);
        DBManager.getInstance().openDatabase(DBManager.WEATHER_DB_NAME);
        final SQLiteDatabase db = DBManager.getInstance().getDatabase();
        Cursor cursor = db.rawQuery("select city from MultiCities", null);
        ArrayList<String> cityList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                //遍历cursor
                String city = cursor.getString(cursor.getColumnIndex("city"));
                cityList.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put("city", selectedCity.getCityName());
        if (which_city != null)
            switch (which_city) {
                case C.Tag_CITY_0:
                    SharedPreferenceUtil.getInstance()
                            .setCityName(selectedCity.getCityName());
                    //城市0 主城市
                    intent.putExtra("which_page", 0);
                    break;
                case C.Tag_CITY_1:
                    db.update("MultiCities", values, "city = ?", new String[]{
                            cityList.get(0)
                    });
                    intent.putExtra("which_page", 1);
                    break;
                case C.Tag_CITY_2:
                    db.update("MultiCities", values, "city = ?", new String[]{
                            cityList.get(1)
                    });
                    intent.putExtra("which_page", 2);
                    break;
                case C.Tag_CITY_3:
                    db.update("MultiCities", values, "city = ?", new String[]{
                            cityList.get(2)
                    });
                    intent.putExtra("which_page", 3);
                    break;
                case C.Tag_CITY_4:
                    db.update("MultiCities", values, "city = ?", new String[]{
                            cityList.get(3)
                    });
                    intent.putExtra("which_page", 4);
                    break;
                case C.Tag_CITY_5:
                    db.update("MultiCities", values, "city = ?", new String[]{
                            cityList.get(4)
                    });
                    intent.putExtra("which_page", 5);
                    break;
                default:
            }
        DBManager.getInstance().closeDatabase();
        startActivity(intent);
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
