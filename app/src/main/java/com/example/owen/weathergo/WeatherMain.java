package com.example.owen.weathergo;

import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class WeatherMain extends AppCompatActivity {

    /**
     * 程序入口，主Activity类
     */

    private Toolbar mToolbar;//自定义的ToolBar
    private String URL1 = "https://api.heweather.com/x3/weather?city=";
    private String APIKEY1 = "&key=b2a628bc1de942dc869fcbe524c65313";
    private String URL0 = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String APIKEY = "&APPID=66f4ecb005a3075769bb414c65a95dd8";
    private EditText mCity;//城市名称输入框，通过城市名称进行查询，大陆地区城市不全且支持拼音
    private TextView mCountry, mTemp_min, mTemp_max, mWind_speed,mTemp;
    private ListView mLv;
    private DrawerLayout mDrawerLayout;
    private String str[] = new String[] { "item1", "item2", "item3"};
    //分别为查询结果国家，最低温度，最高温度，当前温度，风速
    private Button mSearchWeather;
    //查询按钮，触发查询事件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        findView();
        init();
        setListener();
    }

    public void findView() {
        mTemp = (TextView)findViewById(R.id.weather_temp);
        mSearchWeather = (Button)findViewById(R.id.hsh_search_weather);
        mCity = (EditText) findViewById(R.id.hsh_weather_city_editview);
        mCountry = (TextView) findViewById(R.id.weather_country);
        mTemp_min = (TextView) findViewById(R.id.weather_temp_min);
        mTemp_max = (TextView) findViewById(R.id.weather_temp_max);
        mWind_speed = (TextView) findViewById(R.id.weather_wind_speed);
        mToolbar = (Toolbar) findViewById(R.id.weather_toolbar);
        mLv = (ListView) findViewById(R.id.id_lv);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
    }

    public void setListener(){
        /**
         * 绑定监听事件
         */
        mSearchWeather.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                try {
                    Log.e("JSONF","shishi");
                    //URL url = new URL(URL0 + mCity.getText().toString() + APIKEY);
                    URL url = new URL(URL1 + mCity.getText().toString() + APIKEY1);
                    //Toast.makeText(view.getContext(),""+url,Toast.LENGTH_SHORT).show();
                    //生成完整的url
                    WeatherBean weatherBean = JSONUtil.getWeatherBeans(view.getContext(), url);
                    mCountry.setText(getResources().getString(R.string.hsh_country)
                            +weatherBean.getCnty());
                    mTemp_min.setText(getResources().getString(R.string.hsh_temp_min)
                            +weatherBean.getNow_tmp()
                            + getResources().getString(R.string.c));
                    mTemp_max.setText(getResources().getString(R.string.hsh_temp_max)
                            +weatherBean.getNow_tmp()
                            + getResources().getString(R.string.c));
                    mWind_speed.setText(getResources().getString(R.string.hsh_wind_speed)
                            +weatherBean.getNow_dir()+weatherBean.getNow_sc()
                            + getResources().getString(R.string.m_s));
                    mTemp.setText(weatherBean.getNow_tmp()
                            + getResources().getString(R.string.c));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    //@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //自定义toobar Menu
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.weather_toolnar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //为Toolbarmenu各个选项添加点击事件
        switch (item.getItemId()) {
            case R.id.action_edit:
                toSearchDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toSearchDialog(){
        Toast.makeText(this,"ssssssssssss",Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(this).setTitle("请输入")
                .setView(new EditText(this))
                .setPositiveButton("搜索", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //toSearch();
                    }
                })
                .setNegativeButton("取消", null).show();
    }/*
    public void toSearch(){
        try {
            URL url = new URL(URL0 + mCity.getText().toString() + APIKEY);
//                    Toast.makeText(view.getContext(),""+url,Toast.LENGTH_SHORT).show();
            //生成完整的url
            WeatherBean weatherBean = JSONUtil.getWeatherBean(this, url);
            mCountry.setText(getResources().getString(R.string.hsh_country)
                    +weatherBean.getCountry());
            mTemp_min.setText(getResources().getString(R.string.hsh_temp_min)
                    +weatherBean.getTemp_min()
                    + getResources().getString(R.string.c));
            mTemp_max.setText(getResources().getString(R.string.hsh_temp_max)
                    +weatherBean.getTemp_max()
                    + getResources().getString(R.string.c));
            mWind_speed.setText(getResources().getString(R.string.hsh_wind_speed)
                    +weatherBean.getWind_speed()
                    + getResources().getString(R.string.m_s));
            mTemp.setText(weatherBean.getTemp()
                    + getResources().getString(R.string.c));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public void init(){
        /**
         * 初始化各个变量
         */
        mToolbar.setTitle(getResources().getString(R.string.weather_app_name));
        setSupportActionBar(mToolbar);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, str);
        mLv.setAdapter(adapter);
        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        });

    }

}
