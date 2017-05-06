package com.example.owen.weathergo.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.modules.adapter.WeatherAdapter;
import com.example.owen.weathergo.modules.dao.DLForecast;
import com.example.owen.weathergo.modules.dao.DailyForecast;
import com.example.owen.weathergo.modules.dao.WeatherBean;
import com.example.owen.weathergo.service.AutoUpdateService;
import com.example.owen.weathergo.util.IconGet;
import com.example.owen.weathergo.util.JSONUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by owen on 2017/5/6.
 */

public class MainFragment extends Fragment {

    private static final int UPDATE_WEATHER_DATA = 0;

    @BindView(R.id.main_swipe)//下拉刷新控件
            SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.no_data)//没有查询到城市天气信息或城市不存在时显示
            LinearLayout mNoData;
    @BindView(R.id.weather_info)
    RelativeLayout mWeatherInfo;

    WeatherAdapter mWeatherAdapter;
    private String mCityStr = "开封市";//设置的CityName
    private String mGCityStr = "";//从和风天气查询到的城市名称CityName，理论上和设置的一样
    private List<DLForecast> dlForecastList = new ArrayList<DLForecast>();

    private Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_WEATHER_DATA:
//                    mDrawerLayout.closeDrawers();
                    initRecycleView();
                    refresh();
                    break;
                default:
                    break;
            }
        }
    };


    private View view;
    private boolean mIsCreateView = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //@Nullable 表示定义的字段可以为空.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, view);
        }
        mIsCreateView = true;
        Log.d("MainFragment", "oncreateView");
        return view;
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

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                    dlForecastList.clear();
                    mRecycleView.removeAllViews();
                    getWeather();
                    mWeatherAdapter.notifyDataSetChanged();

                    // 加载完数据设置为不刷新状态，将下拉进度收起来
                    mRefreshLayout.setRefreshing(false);
                }
            }, 1200);

            // 这个不能写在外边，不然会直接收起来
        }
    }

    //的给 Android 开发者的 RxJava 详解 https://gank.io/post/560e15be2dca930e00da1083
    //大头鬼Bruce的译文 深入浅出RxJava系列 http://blog.csdn.net/lzyzsd/article/category/2767743
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getWeather() {
        mWeatherInfo.setVisibility(View.VISIBLE);
        mNoData.setVisibility(View.GONE);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            WeatherBean weatherBean = null;
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONUtil.getWeatherBeans(WeatherMain.this, mCityStr);
                }
            }).start();*/
            weatherBean = JSONUtil.getWeatherBeans(getActivity(), mCityStr);
            //问题在这里，新更改的mCityStr但weatherBean仍然返回前一个值
            //TODO 解决实时刷新天气
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
            mRecycleView.setAdapter(mWeatherAdapter = new WeatherAdapter(dlForecastList, weatherBean));
            mGCityStr = weatherBean.getCity();
//            Log.i("WeatherMains","ReadyToStartService");
//            mToolBar.setTitle("" + mGCityStr);
            safeSetTitle(mGCityStr);
            Log.i("WeatherMains", "ReadyToStartService");
            Intent intent = new Intent(getActivity(), AutoUpdateService.class);
            intent.putExtra("weather", weatherBean);
            getActivity().startService(intent);
            Log.i("WeatherMains", "startService");

        } catch (Exception e) {
            e.printStackTrace();
            mWeatherInfo.setVisibility(View.GONE);
            mNoData.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "    定位失败,请手动输入城市", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getActivity(), "加载完毕，✺◟(∗❛ัᴗ❛ั∗)◞✺,", Toast.LENGTH_SHORT).show();

    }

    public void safeSetTitle(String title) {
        ActionBar appBarLayout = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (appBarLayout != null) {
            appBarLayout.setTitle(title);
        }
    }
}