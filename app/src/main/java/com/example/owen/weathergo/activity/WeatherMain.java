package com.example.owen.weathergo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.example.owen.weathergo.dao.DailyForecast;
import com.example.owen.weathergo.util.JSONUtil;
import com.example.owen.weathergo.R;
import com.example.owen.weathergo.dao.WeatherBean;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherMain extends AppCompatActivity {

    /**
     * 程序入口，主Activity类
     */

    //private Toolbar mToolbar;//自定义的ToolBar
    private String URL1 = "https://api.heweather.com/x3/weather?city=";
    private String APIKEY1 = "&key=b2a628bc1de942dc869fcbe524c65313";
    private EditText mCity;//城市名称输入框，通过城市名称进行查询，大陆地区城市不全且支持拼音
    private TextView mCountry, mTemp_min, mTemp_max, mWind_speed,mTemp;
    private TextView mSugg;
    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvLeftMenu;
    private String[] lvs = {"List Item 01", "List Item 02", "List Item 03", "List Item 04"};
    private ArrayAdapter arrayAdapter;
    private String mCityStr = "kaifeng";
    private String mGCityStr = "";
    //private ListView mLv;
    //private DrawerLayout mDrawerLayout;
    //private String str[] = new String[] { "item1", "item2", "item3"};
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private ListView mListView;
    private ArrayList<DailyForecast> mDFList = new ArrayList<>();


    //分别为查询结果国家，最低温度，最高温度，当前温度，风速
    private Button mSearchWeather;
    //查询按钮，触发查询事件
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        findView();
        init();
        setListener();
        getWeather();

    }

    public void findView() {
        mTemp = (TextView)findViewById(R.id.weather_temp);
        mSearchWeather = (Button)findViewById(R.id.hsh_search_weather);
        mCity = (EditText) findViewById(R.id.hsh_weather_city_editview);
        mCountry = (TextView) findViewById(R.id.weather_country);
        mTemp_min = (TextView) findViewById(R.id.weather_temp_min);
        mTemp_max = (TextView) findViewById(R.id.weather_temp_max);
        mWind_speed = (TextView) findViewById(R.id.weather_wind_speed);
        //mToolbar = (Toolbar) findViewById(R.id.weather_toolbar);
        //mLv = (ListView) findViewById(R.id.id_lv);
        //mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        mListView = (ListView)findViewById(R.id.weather_suggesstion);
        mSugg = (TextView)findViewById(R.id.weather_suggesstions);
        mToolBar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        lvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);
    }

    public void setListener(){
        /**
         * 绑定监听事件
         */
        mSearchWeather.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (mLocationClient != null && mLocationClient.isStarted())
                {mLocationClient.requestLocation();
                }
                else{
                    Log.d("LocSDK_2.0_Demo1", "locClient is null or not started");}
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
*/    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getWeather(){
        try {
            Log.e("JSONF","shishi");
            //URL url = new URL(URL0 + mCity.getText().toString() + APIKEY);
            URL url = new URL(URL1 + mCityStr + APIKEY1);
            //Toast.makeText(view.getContext(),""+url,Toast.LENGTH_SHORT).show();
            //生成完整的url
            WeatherBean weatherBean = JSONUtil.getWeatherBeans(this, url);
            mGCityStr = weatherBean.getCity();
            mToolBar.setTitle(""+mGCityStr);
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
            mSugg.setText(weatherBean.getComf_brf()+"\n"+weatherBean.getComf_txt()+"\n"
                    +weatherBean.getCw_brf()+"\n"+weatherBean.getCw_txt()+"\n"
                    +weatherBean.getDrsg_brf()+"\n"+weatherBean.getDrsg_txt()+"\n"
                    +weatherBean.getComf_brf()+"\n"+weatherBean.getComf_txt()+"\n"
                    +weatherBean.getFlu_brf()+"\n"+weatherBean.getFlu_txt()+"\n"
                    +weatherBean.getSport_brf()+"\n"+weatherBean.getSport_txt()+"\n"
                    +weatherBean.getTrav_brf()+"\n"+weatherBean.getTrav_txt()+"\n"
                    +weatherBean.getUv_brf()+"\n"+weatherBean.getUv_txt()+"\n");
            mDFList = JSONUtil.getDForecast();
            int i = 0;
            for (DailyForecast df:mDFList
                 ) {
                DailyForecast dfs = mDFList.get(i);
                Log.e("wtf","hh"+i);
                if(i == 0)
                {
                    mTemp_min.setText(getResources().getString(R.string.hsh_temp_min)
                            +dfs.getMin()
                            + getResources().getString(R.string.c));
                    mTemp_max.setText(getResources().getString(R.string.hsh_temp_max)
                            +dfs.getMax()
                            + getResources().getString(R.string.c));
                }
                i ++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                Intent intent  = new Intent(WeatherMain.this, LoginActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toSearchDialog(){
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
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            mLocationClient = null;
        }
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=100000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


    public void init(){
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
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        initLocation();
        //mLocationClient.start();
//        mBDll = mLocationClient.getLastKnownLocation();
        //Toast.makeText(this,mLocationClient.getLastKnownLocation()+"",Toast.LENGTH_LONG).show();
        if (mLocationClient != null && mLocationClient.isStarted())
        {mLocationClient.requestLocation();
        }
        else{
            Log.d("LocSDK_2.0_Demo1", "locClient is null or not started");}
        //Toast.makeText(this,mLocationClient.getLastKnownLocation()+"",Toast.LENGTH_LONG).show();




    }
    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.e("BaiduLocationApiDem", sb.toString());
            Toast.makeText(WeatherMain.this,sb.toString(),Toast.LENGTH_LONG).show();
        }

    }

}
