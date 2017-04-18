package com.example.owen.weathergo.modules.main.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owen.weathergo.adapter.DLForecastAdapter;
import com.example.owen.weathergo.common.util.IconGet;
import com.example.owen.weathergo.common.util.JSONUtil;
import com.example.owen.weathergo.component.DLForecast;
import com.example.owen.weathergo.R;
import com.example.owen.weathergo.modules.dao.DailyForecast;
import com.example.owen.weathergo.modules.dao.WeatherBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherMain extends AppCompatActivity {

    /**
     * 程序入口，主Activity类
     */

    //private Toolbar mToolbar;//自定义的ToolBar
//    private String URL1 = "https://api.heweather.com/x3/weather?city=";
//    private String APIKEY1 = "&key=b2a628bc1de942dc869fcbe524c65313";
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
    private ActionBarDrawerToggle mDrawerToggle;
    @BindView(R.id.lv_left_menu)
    ListView lvLeftMenu;
    private String[] lvs = {"设置", "选择城市", "关于", "建议"};
    private ArrayAdapter arrayAdapter;
    private String mCityStr = "kaifeng";
    private String mGCityStr = "";
    //private ListView mLv;
    //private DrawerLayout mDrawerLayout;
    //private String str[] = new String[] { "item1", "item2", "item3"};
    @BindView(R.id.weather_forecast)
    ListView mForecastList;
    private ArrayList<DailyForecast> mDFList = new ArrayList<>();
    @BindView(R.id.weather_touxiang)
    ImageView mLogImg;
    private List<DLForecast> dlForecastList = new ArrayList<DLForecast>();
    @BindView(R.id.weather_img)
    ImageView ToImg;
    @BindView(R.id.viewPager)
    ViewPager viewPager;  //对应的viewPager
    private View view1, view2;
    private List<View> viewList;//view数组
    private boolean isSugg = false;
    //分别为查询结果国家，最低温度，最高温度，当前温度，风速
    @BindView(R.id.hsh_search_weather)
    Button mSearchWeather;
    //查询按钮，触发查询事件

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        findView();
        init();

        setListener();
        getWeather();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.daily_7_forecast, null);
        view2 = inflater.inflate(R.layout.suggestion, null);
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));


                return viewList.get(position);
            }
        };


//        viewPager.setAdapter(pagerAdapter);


    }

    public void findView() {
        mTemp = (TextView) findViewById(R.id.weather_temp);
        mSearchWeather = (Button) findViewById(R.id.hsh_search_weather);
        mCity = (EditText) findViewById(R.id.hsh_weather_city_editview);
        mCountry = (TextView) findViewById(R.id.weather_country);
        mTemp_min = (TextView) findViewById(R.id.weather_temp_min);
        mTemp_max = (TextView) findViewById(R.id.weather_temp_max);
        mWind_speed = (TextView) findViewById(R.id.weather_wind_speed);
        mForecastList = (ListView) findViewById(R.id.weather_forecast);
        mSugg = (TextView) findViewById(R.id.weather_suggesstions);
        mToolBar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        lvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);
        mLogImg = (ImageView) findViewById(R.id.weather_touxiang);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    public void setListener() {
        /**
         * 绑定监听事件
         */
        mSearchWeather.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                //Toast.makeText(WeatherMain.this,mLocationClient.getLastKnownLocation()+"",Toast.LENGTH_LONG).show();
                //mLocationClient.stop();
                mCityStr = mCity.getText().toString();
                getWeather();


            }
        });
        /*mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
*/
        mLogImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherMain.this, Stud.class);
//            Intent intent  = new Intent(WeatherMain.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        lvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (lvs[position]) {

                    case "设置":
                        break;
                    case "选择城市":
                        break;
                    case "关于":
                        break;
                    case "建议":
                        showSugg(isSugg);
                        break;

                }
//                String sp = lvs[position];
//                Toast.makeText(WeatherMain.this,sp+"",Toast.LENGTH_LONG).show();
            }
        });

    }

    //的给 Android 开发者的 RxJava 详解 https://gank.io/post/560e15be2dca930e00da1083
    //大头鬼Bruce的译文 深入浅出RxJava系列 http://blog.csdn.net/lzyzsd/article/category/2767743
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getWeather() {
        try {
//            Log.e("JSONF", "shishi");
            //URL url = new URL(URL0 + mCity.getText().toString() + APIKEY);
//            URL url = new URL(URL1 + mCityStr + APIKEY1);
            //Toast.makeText(view.getContext(),""+url,Toast.LENGTH_SHORT).show();
            //生成完整的url
            WeatherBean weatherBean = JSONUtil.getWeatherBeans(this, mCityStr);
            mGCityStr = weatherBean.getCity();
            mToolBar.setTitle("" + mGCityStr);
            mCountry.setText(getResources().getString(R.string.hsh_country)
                    + weatherBean.getCnty());
            mTemp_min.setText(getResources().getString(R.string.hsh_temp_min)
                    + weatherBean.getNow_tmp()
                    + getResources().getString(R.string.c));
            mTemp_max.setText(getResources().getString(R.string.hsh_temp_max)
                    + weatherBean.getNow_tmp()
                    + getResources().getString(R.string.c));
            mWind_speed.setText(getResources().getString(R.string.hsh_wind_speed)
                    + weatherBean.getNow_dir() + weatherBean.getNow_sc()
                    + getResources().getString(R.string.m_s));
            mTemp.setText(weatherBean.getNow_tmp()
                    + getResources().getString(R.string.c));
            mSugg.setText(weatherBean.getComf_brf() + "\n" + weatherBean.getComf_txt() + "\n"
                    + weatherBean.getCw_brf() + "\n" + weatherBean.getCw_txt() + "\n"
                    + weatherBean.getDrsg_brf() + "\n" + weatherBean.getDrsg_txt() + "\n"
                    + weatherBean.getComf_brf() + "\n" + weatherBean.getComf_txt() + "\n"
                    + weatherBean.getFlu_brf() + "\n" + weatherBean.getFlu_txt() + "\n"
                    + weatherBean.getSport_brf() + "\n" + weatherBean.getSport_txt() + "\n"
                    + weatherBean.getTrav_brf() + "\n" + weatherBean.getTrav_txt() + "\n"
                    + weatherBean.getUv_brf() + "\n" + weatherBean.getUv_txt() + "\n");
            mDFList = JSONUtil.getDForecast();
            Log.i("wtfs", mDFList.toString());
            int i = 0;
            for (DailyForecast df : mDFList
                    ) {
                DailyForecast dfs = mDFList.get(i);
                Log.e("wtf", dfs.getDate());
                if (i == 0) {
                    mTemp_min.setText(getResources().getString(R.string.hsh_temp_min)
                            + dfs.getMin()
                            + getResources().getString(R.string.c));
                    mTemp_max.setText(getResources().getString(R.string.hsh_temp_max)
                            + dfs.getMax()
                            + getResources().getString(R.string.c));
//                    ToImg.setImageResource(IconGet.getWeaIcon(dfs.getTxt_d()));
                    mCountry.setText(dfs.getTxt_d() + "转" + dfs.getTxt_n());
                }
                DLForecast dls = new DLForecast(dfs.getDate() + "", getResources().getString(R.string.hsh_temp_min)
                        + dfs.getMin()
                        + getResources().getString(R.string.c) + getResources().getString(R.string.hsh_temp_max)
                        + dfs.getMax()
                        + getResources().getString(R.string.c), dfs.getDir() + dfs.getSc() + dfs.getTxt_d() + dfs.getTxt_n(), IconGet.getWeaIcon(dfs.getTxt_d()));
                dlForecastList.add(dls);
                i++;
            }

            DLForecastAdapter adapter = new DLForecastAdapter(WeatherMain.this, R.layout.item_forecast_line, dlForecastList);
            mForecastList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "    定位失败,请手动输入城市", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, "加载完毕，✺◟(∗❛ัᴗ❛ั∗)◞✺,", Toast.LENGTH_LONG).show();
    }

    //@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //自定义toobar Menu
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.weather_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //为Toolbarmenu各个选项添加点击事件
        switch (item.getItemId()) {
            case R.id.action_edit:
                toSearchDialog();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(WeatherMain.this, LoginActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toSearchDialog() {
        final EditText et = new EditText(this);
        //Toast.makeText(this,"ssssssssssss",Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(this).setTitle("请输入")
                .setView(et)
                .setPositiveButton("搜索", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mCityStr = et.getText().toString();
                        getWeather();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void showSugg(boolean isSugg) {
        if (!isSugg) {
            mSugg.setVisibility(View.VISIBLE);
            mForecastList.setVisibility(View.GONE);

        } else {
            mSugg.setVisibility(View.GONE);
            mForecastList.setVisibility(View.VISIBLE);
        }
        isSugg = !isSugg;

    }

    public void init() {
        /**
         * 初始化各个变量
         */
        mToolBar.setTitle(getResources().getString(R.string.weather_app_name));
        //mToolBar.setTitle(""+mGCityStr);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //mAnimationDrawable.stop();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //mAnimationDrawable.start();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //设置菜单列表
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lvs);
        lvLeftMenu.setAdapter(arrayAdapter);


        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, str);*/
        //mLv.setAdapter(adapter);
/*
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
*/
        //mLocationClient.start();
//        mBDll = mLocationClient.getLastKnownLocation();
        //Toast.makeText(this,mLocationClient.getLastKnownLocation()+"",Toast.LENGTH_LONG).show();


        //测试
        /*Stud st = new Stud();
        st.getR();*/

    }


}
