package com.example.owen.weathergo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.DoubleClickExit;
import com.example.owen.weathergo.service.AutoUpdateService;
import com.example.owen.weathergo.util.IconGet;
import com.example.owen.weathergo.util.JSONUtil;
import com.example.owen.weathergo.util.ToastUtil;
import com.example.owen.weathergo.modules.dao.DLForecast;
import com.example.owen.weathergo.modules.dao.DailyForecast;
import com.example.owen.weathergo.modules.dao.WeatherBean;
import com.example.owen.weathergo.modules.adapter.WeatherAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * 程序入口，主Activity类
     */

    private static final String TAG = WeatherMain.class.getSimpleName();

    //ButterKnife参考http://jakewharton.github.io/butterknife/
    @BindView(R.id.tl_custom)
    Toolbar mToolBar;
    @BindView(R.id.dl_left)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.main_swipe)//下拉刷新控件
            SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    WeatherAdapter mWeatherAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mCityStr = "开封市";//设置的CityName
    private String mGCityStr = "";//从和风天气查询到的城市名称CityName，理论上和设置的一样
    private List<DLForecast> dlForecastList = new ArrayList<DLForecast>();
    private View view1, view2;
    private List<View> viewList;//view数组
    private SharedPreferences preferences;
    //分别为查询结果国家，最低温度，最高温度，当前温度，风速
    private static final int UPDATE_WEATHER_DATA = 0;

    private Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_WEATHER_DATA:
                    Log.i("huangshaohua3.5", "" + mCityStr);
                    getWeather();
                    break;
                default:
                    break;
            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        setListener();
        getWeather();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("huangshaohua1", mCityStr);
        preferences = getApplicationContext().getSharedPreferences("huang", MODE_PRIVATE);
        String Ccity = preferences.getString("city", "");
        if (!Ccity.equals(mCityStr)) {
            mCityStr = Ccity;
            Log.i("huangshaohua2", "onstart" + Ccity + mCityStr);

            initRecycleView();
            refresh();
        }
    }

    public void setListener() {
        /**
         * 绑定监听事件
         */

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "fab等待绑定的活动", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        });


    }

    //的给 Android 开发者的 RxJava 详解 https://gank.io/post/560e15be2dca930e00da1083
    //大头鬼Bruce的译文 深入浅出RxJava系列 http://blog.csdn.net/lzyzsd/article/category/2767743
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getWeather() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        try {
            Log.i("huangshaohua4", "" + mCityStr);
            WeatherBean weatherBean = null;
//            Log.i("huangshaohua5", weatherBean.getCity());
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONUtil.getWeatherBeans(WeatherMain.this, mCityStr);
                }
            }).start();*/
            weatherBean = JSONUtil.getWeatherBeans(this, mCityStr);
            //问题在这里，新更改的mCityStr但weatherBean仍然返回前一个值
            //TODO 解决实时刷新天气
            Log.i("huangshaohua6", weatherBean.getCity());
            Log.i("huangshaohua7", mCityStr);
            ArrayList<DailyForecast> mDFList = JSONUtil.getDForecast();
            Log.i("wtfs", mDFList.toString());
            int i = 0;
            for (DailyForecast df : mDFList
                    ) {
                DailyForecast dfs = mDFList.get(i);
                Log.e("wtf", dfs.getDate());

                DLForecast dls = new DLForecast(dfs.getDate() + "", getResources().getString(R.string.hsh_temp_min)
                        + dfs.getMin()
                        + getResources().getString(R.string.c) + getResources().getString(R.string.hsh_temp_max)
                        + dfs.getMax()
                        + getResources().getString(R.string.c), dfs.getDir() + dfs.getSc() + dfs.getTxt_d() + dfs.getTxt_n(), IconGet.getWeaIcon(dfs.getTxt_d()));
                dlForecastList.add(dls);
                i++;
            }
            Log.i("huangshaohua8", mCityStr);
            mRecycleView.setAdapter(mWeatherAdapter = new WeatherAdapter(getWindow().getDecorView(), dlForecastList, weatherBean));
            mGCityStr = weatherBean.getCity();
            Log.i("huangshaohua9", mGCityStr);
            Log.i("huangshaohua10", "" + weatherBean.getCity());
//            Log.i("WeatherMains","ReadyToStartService");
            mToolBar.setTitle("" + mGCityStr);
            Log.i("WeatherMains","ReadyToStartService");
            Intent intent = new Intent(WeatherMain.this, AutoUpdateService.class);
//            Bundle bundle = new Bundle();
            intent.putExtra("weather", weatherBean);
            startService(intent);
            Log.i("WeatherMains","startService");

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "    定位失败,请手动输入城市", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, "加载完毕，✺◟(∗❛ัᴗ❛ั∗)◞✺,", Toast.LENGTH_SHORT).show();

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 初始化各个变量
     */
    public void init() {
        preferences = getApplicationContext().getSharedPreferences("huang", MODE_PRIVATE);
        String Ccity = preferences.getString("city", "");
        if (!Ccity.equals(""))//判断SharedPreference中存储的是否为空，即如果第一次执行程序不会变为空值
            mCityStr = Ccity;
        Log.i("huangshaohua", "init" + Ccity);
        mToolBar.setTitle(getResources().getString(R.string.weather_app_name));
        setSupportActionBar(mToolBar);

        initDrawer();
        initRecycleView();
        initNavigationView();


    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }

    //初始化抽屉
    public void initDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.i(TAG, "opened");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //mAnimationDrawable.start();
                Log.i(TAG, "closed");
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //设置菜单列表
    }

    //初始化navigationView
    public void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("onSelected", "id=" + item.getItemId());
                switch (item.getItemId()) {
                    case R.id.nav_city:
                        ChoiceCityActivity.launch(WeatherMain.this);
                        Log.i(TAG + "navigation", "nav_city");
                        break;
                    case R.id.nav_multi_cities:
                        Log.i(TAG + "navigation", "nav_multi_cities");
                        break;
                    case R.id.nav_setting:
                        SettingsActivity.launch(WeatherMain.this);
                        Log.i(TAG + "navigation", "nav_setting");
                        break;
                    case R.id.nav_about:
                        About.launch(WeatherMain.this);
                        Log.i(TAG + "navigation", "nav_about");
                        break;
                }
                return false;
            }
        });
    }

    //初始化下拉刷新控件
    public void initRecycleView() {
        //下拉刷新 http://www.jianshu.com/p/d23b42b6360b
        mRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        mRecycleView.removeAllViews();
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        //获取当前Activity的View
//        mRecycleView.setAdapter(mWeatherAdapter = new WeatherAdapter(getWindow().getDecorView(), dlForecastList,weatherBean));


    }

    public void refresh() {
        {

            // 开始刷新，设置当前为刷新状态
            //swipeRefreshLayout.setRefreshing(true);

            // 这里是主线程
            // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override

                public void run() {
                    Log.i("huangshaohua3", mCityStr);
                    dlForecastList.clear();
                    mRecycleView.removeAllViews();
                    getWeather();
                    mWeatherAdapter.notifyDataSetChanged();
                    Log.i("huangshaohua11", mCityStr);

                    // 加载完数据设置为不刷新状态，将下拉进度收起来
                    mRefreshLayout.setRefreshing(false);
                }
            }, 1200);

            // 这个不能写在外边，不然会直接收起来
        }
    }

    //设置双击推出
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (!DoubleClickExit.check()) {
                ToastUtil.showShort(getString(R.string.double_exit));
            } else {
                finish();
            }
        }
    }


}
