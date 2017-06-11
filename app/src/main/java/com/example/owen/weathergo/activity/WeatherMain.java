package com.example.owen.weathergo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.DoubleClickExit;
import com.example.owen.weathergo.dialog.CityDialog;
import com.example.owen.weathergo.modules.adapter.HomePagerAdapter;
import com.example.owen.weathergo.modules.fragment.MainFragment;
import com.example.owen.weathergo.modules.fragment.MultiCityFragment;
import com.example.owen.weathergo.util.DBManager;
import com.example.owen.weathergo.util.SharedPreferenceUtil;
import com.example.owen.weathergo.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * 程序入口，主Activity类
     */

    //TODO 设置推送并震动
    private static final String TAG = WeatherMain.class.getSimpleName();
    private static final int UPDATE_WEATHER_DATA = 0;
    private static final int SEARCH_CITY = 1;
    private static final int SCREEN_SHOOT = 2;

    private static String Tag_CITY_0 = "main_fragment";
    private static String Tag_CITY_1 = "city_1_fragment";
    private static String Tag_CITY_2 = "city_2_fragment  ";

    public LocationClient mLocationClient;
    ArrayList<String> cityList = new ArrayList<>();

    //ButterKnife参考http://jakewharton.github.io/butterknife/
    @BindView(R.id.tl_custom)
    Toolbar mToolBar;
    @BindView(R.id.dl_left)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.weather_viewpager)
    ViewPager mViewPager;

    private ActionBarDrawerToggle mDrawerToggle;
    private Handler mHandler;
    private HomePagerAdapter mHomePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        init();
        setListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        mDrawerLayout.closeDrawers();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //从多城市管理界面跳转过来，并接收选中的城市编号
//        Intent intent = this.getIntent();
//        if (intent.getExtras().getString("city_num", "-1") != null) {
        int cityNum = getIntent().getIntExtra("city_num", -1);
        Log.d("WeatherMainhuang", "onStart getIntExtra " + cityNum);
        mViewPager.setCurrentItem(cityNum + 1);
//        }
    }

    /**
     * 由于在打开MultiCitiesManagerActivity时WeatherMain并未destroyed
     * 所以在MultiCitiesManagerActivity中启动也就没有重新调用onCreate方法；
     * WeatherMain中的intent因此也就没有刷新为MultiCitiesManagerActivity
     * 传来的新intent；
     * 调用下面的onNewIntent方法可以解决这一问题
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDrawerLayout.closeDrawers();
    }

    /**
     * 绑定监听事件
     */
    public void setListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchDialog();//发布版本暂时设为选择地址
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                String titleStr;
                if (position == 0)
                    titleStr = SharedPreferenceUtil.getInstance().getCityName();
                else
                    titleStr = cityList.get(position - 1);
                safeSetTitle(titleStr);
//                ToastUtil.showShort(""+position);
//                super.onPageSelected(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.weather_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Message msg = new Message();
                msg.what = SCREEN_SHOOT;
                mHandler.sendMessage(msg);
//                startActivity(new Intent(WeatherMain.this,Test.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 手动输入城市
     */
    public void toSearchDialog() {
        final CityDialog dialog = new CityDialog(WeatherMain.this);
        dialog.setYesOnclickListener("确定", new CityDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                Message msg = new Message();
                msg.obj = dialog.mCityEdit.getText().toString();
                msg.what = SEARCH_CITY;
                mHandler.sendMessage(msg);
                dialog.dismiss();
            }
        });
        dialog.setNoOnclickListener("取消", new CityDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
        mDrawerLayout.closeDrawers();
    }


    /**
     * 添加城市
     * 1：判断城市是否已经超过3个
     * （1）如果没有超过则添加一个Pager平行于MainWeather
     * 暂时通过手动输入选择城市；下一阶段可以在新的Pager中通过Fb选择选择城市方式
     * （城市选择是否要去重？）
     * （2）如果已经3个则弹出框提示已经选择超限制
     */
    private void toAddDialog() {
        /*MultiCityFragment tf = new MultiCityFragment();
        mHomePagerAdapter.addTab(tf, "");
        mHomePagerAdapter.notifyDataSetChanged();
        mDrawerLayout.closeDrawers();
        mViewPager.setCurrentItem(1);*/
//        ToastUtil.showShort("Multi Cities");

        MultiCitiesManagerActivity.launch(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null)
            mLocationClient.stop();
        //先判空，否则可能fc

        DBManager.getInstance().closeDatabase();
    }

    /**
     * 初始化各个变量
     */
    public void init() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            //使状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //使导航栏透明getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setSupportActionBar(mToolBar);
        initDrawer();
        initNavigationView();
        mHomePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        MainFragment mf = new MainFragment();
        mHomePagerAdapter.addTab(mf, "aaa");

        DBManager.getInstance().openDatabase(DBManager.WEATHER_DB_NAME);
        final SQLiteDatabase db = DBManager.getInstance().getDatabase();
        Cursor cursor = db.rawQuery("select city from MultiCities", null);
        if (cursor.moveToFirst()) {
            do {
                //遍历cursor
                String city = cursor.getString(cursor.getColumnIndex("city"));
                cityList.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        int mCityCount = (int) DBManager.getInstance().allCaseNum("MultiCities");
        if (mCityCount != 0) {
            for (int i = 0; i < mCityCount; i++) {
//            for (int i = 0; i < 2; i++) {
                MultiCityFragment mtf = MultiCityFragment.newInstance(i, cityList.get(i));
                Log.d("WeatherMainhuang", " init " + cityList.get(i));
                mHomePagerAdapter.addTab(mtf, cityList.get(i));
//                mHomePagerAdapter.notifyDataSetChanged();
            }
        }
        /*if (!"".equals(SharedPreferenceUtil.getInstance().getString("city_1", ""))) {
            MultiCityFragment tf = new MultiCityFragment();
            mHomePagerAdapter.addTab(tf, "");
        }*/
        mViewPager.setAdapter(mHomePagerAdapter);

        String cCity = SharedPreferenceUtil.getInstance().getCityName();
        Log.d("WeatherMainhuang", " init " + cCity);
        if ("".equals(cCity) || cCity == null)//判断SharedPreference中存储的是否为空，即如果第一次执行程序不会变为空值进行初始赋值
        {
            Log.d("WeatherMainhuang", " initLocation " + cCity);
            initLocation();
        }
    }

    private void initLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        /*RxPermissions rxPermissions = new RxPermissions(WeatherMain.this);
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                    } else {
                        // Oups permission denied
                    }
                });*/
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(WeatherMain.this, android.Manifest
                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(WeatherMain.this, android.Manifest
                .permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(WeatherMain.this, android.Manifest
                .permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(WeatherMain.this, permissions, 1);
            //Android6.0之后动态申请权限
        } else {
            requestLocation();
        }

    }

    private void requestLocation() {
        refreshLocation();
        mLocationClient.start();
    }

    public void refreshLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(WeatherMain.this, "必须获得以上权限才能正常使用", Toast.LENGTH_SHORT)
                                    .show();
                            finish();//不同意就退出
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(WeatherMain.this, "发生未知错误", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    //初始化抽屉
    public void initDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        //mDrawerLayout.setDrawerListener(mDrawerToggle);
        //旧版本可能会空指针
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    //初始化navigationView
    public void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_city:
                        ChoiceCityActivity.launch(WeatherMain.this);
                        break;
                    case R.id.nav_edit_city:
                        toSearchDialog();
                        break;
                    case R.id.nav_multi_city:
                        toAddDialog();
                        break;
                    case R.id.nav_setting:
                        SettingsActivity.launch(WeatherMain.this);
                        break;
                    case R.id.nav_about:
                        About.launch(WeatherMain.this);
                        break;
                }
                return false;
            }
        });
    }


    //设置双击推出
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            //如果抽屉没有合上先合上抽屉
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (!DoubleClickExit.check()) {
                ToastUtil.showShort(getString(R.string.double_exit));
            } else {
                finish();
            }
        }
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, WeatherMain.class));
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }


    private class MyLocationListener implements BDLocationListener {
        //用于定位
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getCity() != null) {
                SharedPreferenceUtil.getInstance().setCityName(bdLocation.getCity());
                Message msg = new Message();
                msg.obj = bdLocation.getCity() + "";
                msg.what = SEARCH_CITY;
                Log.d("search_weather_data", "" + bdLocation.getCity());
                mHandler.sendMessage(msg);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }

    public void safeSetTitle(String title) {
        ActionBar appBarLayout = (this).getSupportActionBar();
        if (appBarLayout != null) {
            appBarLayout.setTitle(title);
        }
    }
}
