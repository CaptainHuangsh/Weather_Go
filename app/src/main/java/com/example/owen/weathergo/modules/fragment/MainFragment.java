package com.example.owen.weathergo.modules.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.activity.WeatherMain;
import com.example.owen.weathergo.modules.adapter.WeatherAdapter;
import com.example.owen.weathergo.modules.domain.Weather;
import com.example.owen.weathergo.service.AutoUpdateService;
import com.example.owen.weathergo.util.FileUtil;
import com.example.owen.weathergo.util.JSONUtil;
import com.example.owen.weathergo.util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by owen on 2017/5/6.
 */

public class MainFragment extends Fragment {

    private static final int UPDATE_WEATHER_DATA = 0;
    private static final int SEARCH_CITY = 1;
    private static final int SCREEN_SHOOT = 2;
    private static final int CHANGE_TEXT = 3;

    @BindView(R.id.no_city_data)
    TextView mNoCityData;
    @BindView(R.id.main_swipe)//下拉刷新控件
            SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.no_data)//没有查询到城市天气信息或城市不存在时显示
            LinearLayout mNoData;
    @BindView(R.id.load_data)
    LinearLayout mLoadData;//第一次加载时等待获取位置信息
    @BindView(R.id.weather_info)
    RelativeLayout mWeatherInfo;

    WeatherAdapter mWeatherAdapter;
    private String mCityStr = "";//设置的CityName
    private String mGCityStr = "";//从和风天气查询到的城市名称CityName，理论上和设置的一样
    private View view;
    private boolean mIsCreateView = false;
    private WeatherMain mActivity;
    private Weather mWeather;
    private int times = 0;

    private Handler mHandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_WEATHER_DATA:
                    initRecycleView();
                    if (SharedPreferenceUtil.getInstance().getCityName().equals("")) {
                        mNoData.setVisibility(View.GONE);
                        mLoadData.setVisibility(View.VISIBLE);
                    } else {
                        mLoadData.setVisibility(View.GONE);
                        mNoData.setVisibility(View.GONE);
                        refresh();
                    }
                    break;
                case SEARCH_CITY:
                    //Fragment与activity交互http://blog.csdn.net/huangyabin001/article/details/35231753
                    if (!msg.obj.toString().equals("")) {
                        mCityStr = msg.obj.toString();
                        SharedPreferenceUtil.getInstance().setCityName(mCityStr);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mWeather = JSONUtil.getInstance().getWeather(getActivity(), mCityStr);
                                Message message = new Message();
                                message.what = UPDATE_WEATHER_DATA;
                                mHandler.sendMessage(message);
                            }
                        }).start();
                    } else {
                        Message message = new Message();
                        message.what = CHANGE_TEXT;
                        message.obj = "no_city_data";
                        mHandler.sendMessage(message);
                        //请手动选择城市
                    }
                    break;
                case SCREEN_SHOOT:
                    /**
                     * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
                     */
                    FileUtil.getInstance().getPermission(getActivity());
                    Bitmap bitmap = FileUtil.getInstance().convertViewBitmap(mRecycleView);
                    String fileName = FileUtil.getInstance().saveMyBitmap(bitmap, "sdcard/");
                    FileUtil.getInstance().shareMsg(getContext(), "分享", "share", "今天天气", fileName, 0);
                    break;
                case CHANGE_TEXT:
                    if (msg.obj.equals("no_city_data")) {
                        mNoCityData.setText("定位失败，请手动选择城市");
                    }
                    if (msg.obj.equals("refresh_wrong")) {
                        mNoCityData.setText("请触图片刷新");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (WeatherMain) activity;
        mActivity.setHandler(mHandler);
    }

    //@Nullable 表示定义的字段可以为空.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, view);
        }
        mIsCreateView = true;
        init();
        if (!mCityStr.equals("")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mWeather = JSONUtil.getInstance().getWeather(getActivity(), mCityStr);
                    Message message = new Message();
                    message.what = UPDATE_WEATHER_DATA;
                    mHandler.sendMessage(message);
                }
            }).start();
        }
        setListener();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //onViewCreated在onCreateView执行完后立即执行。
        //onCreateView返回的就是fragment要显示的view。
        //new RxPermissions()
    }

    @Override
    public void onStart() {
        //TODO onStart中只通过sharedPreference取在service中存储的jsonText
        //以节省流量和访问次数（因为每次打开app时用户的位置数据是基本不会改变的）
        super.onStart();
        String Ccity = SharedPreferenceUtil.getInstance().getCityName();
        if (!Ccity.equals(mCityStr)) {
            mCityStr = Ccity;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mWeather = JSONUtil.getInstance().getWeather(getActivity(), mCityStr);
                    Message message = new Message();
                    message.what = UPDATE_WEATHER_DATA;
                    mHandler.sendMessage(message);
                }
            }).start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getActivity(), AutoUpdateService.class);
        getActivity().stopService(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void init() {
        String cCity = SharedPreferenceUtil.getInstance().getCityName();
        if (!cCity.equals(""))//判断SharedPreference中存储的是否为空，即如果第一次执行程序不会变为空值进行初始赋值
        {
            mCityStr = cCity;
            safeSetTitle(mCityStr);
        }
        mNoData.setVisibility(View.GONE);
        initRecycleView();
    }

    /**
     * 绑定监听事件
     */
    public void setListener() {
        mNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRecycleView();
                String Ccity = SharedPreferenceUtil.getInstance().getCityName();
                if (!Ccity.equals(mCityStr)) {
                    mCityStr = Ccity;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mWeather = JSONUtil.getInstance().getWeather(getActivity(), mCityStr);
                            Message message = new Message();
                            message.what = UPDATE_WEATHER_DATA;
                            mHandler.sendMessage(message);
                        }
                    }).start();
                }
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
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void refresh() {
        {
            // 这里是主线程
            // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override

                public void run() {
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
        mLoadData.setVisibility(View.GONE);
        mNoData.setVisibility(View.GONE);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            mWeather = JSONUtil.getInstance().getWeather(getActivity(), mCityStr);
            int i = 0;
            mRecycleView.setAdapter(mWeatherAdapter = new WeatherAdapter(mWeather));
            mGCityStr = mWeather.getBasic().getCity();
            if (!mGCityStr.equals(""))
                safeSetTitle(mGCityStr);
            Intent intent = new Intent(getActivity(), AutoUpdateService.class);
            getActivity().startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
            mWeatherInfo.setVisibility(View.GONE);
            mLoadData.setVisibility(View.GONE);
            mNoData.setVisibility(View.VISIBLE);
//            Toast.makeText(getActivity(), "    定位失败,请手动输入城市", Toast.LENGTH_LONG).show();
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