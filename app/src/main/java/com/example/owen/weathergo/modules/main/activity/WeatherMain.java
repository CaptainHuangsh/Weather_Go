package com.example.owen.weathergo.modules.main.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.DoubleClickExit;
import com.example.owen.weathergo.common.util.IconGet;
import com.example.owen.weathergo.common.util.JSONUtil;
import com.example.owen.weathergo.common.util.ToastUtil;
import com.example.owen.weathergo.component.DLForecast;
import com.example.owen.weathergo.modules.about.About;
import com.example.owen.weathergo.modules.dao.DailyForecast;
import com.example.owen.weathergo.modules.dao.WeatherBean;
import com.example.owen.weathergo.modules.main.adapter.WeatherAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * 程序入口，主Activity类
     */

    private static final String TAG = "WeatherMain.c";

    //http://jakewharton.github.io/butterknife/
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
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.hsh_search_weather)
    Button mSearchWeather; //查询按钮，触发查询事件
    @BindView(R.id.main_swipe)//下拉刷新控件
            SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    WeatherAdapter mWeatherAdapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<DailyForecast> mDFList = new ArrayList<>();
    private String mCityStr = "kaifeng";
    private String mGCityStr = "";
    private List<DLForecast> dlForecastList = new ArrayList<DLForecast>();
    private View view1, view2;
    private List<View> viewList;//view数组
    private boolean isSugg = false;
    //分别为查询结果国家，最低温度，最高温度，当前温度，风速

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


    public void setListener() {
        /**
         * 绑定监听事件
         */
        mSearchWeather.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                mCityStr = mCity.getText().toString();
                getWeather();


            }
        });


    }

    //的给 Android 开发者的 RxJava 详解 https://gank.io/post/560e15be2dca930e00da1083
    //大头鬼Bruce的译文 深入浅出RxJava系列 http://blog.csdn.net/lzyzsd/article/category/2767743
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getWeather() {
        try {
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


    public void init() {
        /**
         * 初始化各个变量
         */
        mToolBar.setTitle(getResources().getString(R.string.weather_app_name));
        setSupportActionBar(mToolBar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听

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
                Log.i(TAG,"opened");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //mAnimationDrawable.start();
                Log.i(TAG,"closed");
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
    public void initNavigationView(){
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("onSelected", "id=" + item.getItemId());
                switch (item.getItemId()){
                    case R.id.nav_city:
                        Log.i(TAG+"navigation","nav_city");
                        break;
                    case R.id.nav_multi_cities:
                        Log.i(TAG+"navigation","nav_multi_cities");
                        break;
                    case R.id.nav_setting:
                        SettingsActivity.launch(WeatherMain.this);
                        Log.i(TAG+"navigation","nav_setting");
                        break;
                    case R.id.nav_about:
                        About.launch(WeatherMain.this);
                        Log.i(TAG+"navigation","nav_about");
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

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // 开始刷新，设置当前为刷新状态
                //swipeRefreshLayout.setRefreshing(true);

                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // TODO 获取数据
//                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {

//                        mWeatherAdapter.;
                        dlForecastList.clear();
                        mRecycleView.removeAllViews();

                        getWeather();
                        mWeatherAdapter.notifyDataSetChanged();

                        Toast.makeText(WeatherMain.this, "刷新了数据", Toast.LENGTH_SHORT).show();

                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 1200);

                // System.out.println(Thread.currentThread().getName());

                // 这个不能写在外边，不然会直接收起来
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        //获取当前Activity的View
        mRecycleView.setAdapter(mWeatherAdapter = new WeatherAdapter(getWindow().getDecorView(), dlForecastList));


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
