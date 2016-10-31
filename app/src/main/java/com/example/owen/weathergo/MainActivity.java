package com.example.owen.weathergo;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends Activity {


    /**
     * 程序入口，主Activity类
     */
    private Toolbar mToolbar;//自定义的ToolBar
    private String URL0 = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String APIKEY = "&APPID=66f4ecb005a3075769bb414c65a95dd8";
    private EditText mCity;//城市名称输入框，通过城市名称进行查询，大陆地区城市不全且支持拼音
    private TextView mCountry, mTemp_min, mTemp_max, mWind_speed,mTemp;
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
        mTemp = (TextView)findViewById(R.id.hsh_weather_temp);
        mSearchWeather = (Button)findViewById(R.id.hsh_search_weather);
        mCity = (EditText) findViewById(R.id.hsh_weather_city_editview);
        mCountry = (TextView) findViewById(R.id.hsh_weather_country);
        mTemp_min = (TextView) findViewById(R.id.hsh_weather_temp_min);
        mTemp_max = (TextView) findViewById(R.id.hsh_weather_temp_max);
        mWind_speed = (TextView) findViewById(R.id.hsh_weather_wind_speed);
        mToolbar = (Toolbar) findViewById(R.id.hsh_weather_toolbar);
    }

    public void setListener(){
        /**
         * 绑定监听事件
         */
        mSearchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    URL url = new URL(URL0 + mCity.getText().toString() + APIKEY);
//                    Toast.makeText(view.getContext(),""+url,Toast.LENGTH_SHORT).show();
                    //生成完整的url
                    Hsh_WeatherBean weatherBean = Hsh_JSONUtil.getWeatherBean(view.getContext(), url);
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
            }
        });
    }

    //@Override
   /* public boolean onCreateOptionsMenu(Menu menu) {
        //自定义toobar Menu
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.weather_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //为Toolbarmenu各个选项添加点击事件
        switch (item.getItemId()) {
            case R.id.hsh_action_search:
                toSearchDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    public void toSearchDialog(){
        Toast.makeText(this,"ssssssssssss",Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(this).setTitle("请输入")
                .setView(new EditText(this))
                .setPositiveButton("搜索", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        toSearch();
                    }
                })
                .setNegativeButton("取消", null).show();
    }
    public void toSearch(){
        try {
            URL url = new URL(URL0 + mCity.getText().toString() + APIKEY);
//                    Toast.makeText(view.getContext(),""+url,Toast.LENGTH_SHORT).show();
            //生成完整的url
            Hsh_WeatherBean weatherBean = Hsh_JSONUtil.getWeatherBean(this, url);
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
    }

    public void init(){
        /**
         * 初始化各个变量
         */
        mToolbar.setTitle(getResources().getString(R.string.hsh_weather_app_name));
        //setSupportActionBar(mToolbar);

    }

}
