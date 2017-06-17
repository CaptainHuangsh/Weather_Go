package com.example.owen.weathergo.modules.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.activity.WeatherMain;
import com.example.owen.weathergo.common.base.C;
import com.example.owen.weathergo.modules.adapter.WeatherAdapter;
import com.example.owen.weathergo.modules.domain.Weather;
import com.example.owen.weathergo.util.DBManager;
import com.example.owen.weathergo.util.FileUtil;
import com.example.owen.weathergo.util.JSONUtil;
import com.example.owen.weathergo.util.SharedPreferenceUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by owen on 2017/6/9.
 */

public class MultiCityFragment extends Fragment {

    //TODO FAB的选择城市
    private static final int UPDATE_WEATHER_DATA = 0;
    private static final int SEARCH_CITY = 1;
    private static final int SCREEN_SHOOT = 2;
    private static final int CHANGE_TEXT = 3;

    private static String mThisPage;


    @BindView(R.id.no_city_data)
    TextView mNoCityData;
    @BindView(R.id.main_swipe)//下拉刷新控件
            SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.no_data)//没有查询到城市天气信息或城市不存在时显示
            LinearLayout mNoData;
    @BindView(R.id.weather_info)
    RelativeLayout mWeatherInfo;

    WeatherAdapter mWeatherAdapter;
    private int mCityNum;
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
                    } else {
                        mNoData.setVisibility(View.GONE);
                        refresh();
                    }
                    break;

                case SEARCH_CITY:
                    //Fragment与activity交互http://blog.csdn.net/huangyabin001/article/details/35231753
                    if (!msg.obj.toString().equals("")) {
                        if (mThisPage.equals(msg.getData().getString("which_page"))) {
                            mCityStr = msg.obj.toString();
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
                            String Ccity = cityList.get(mCityNum);
                            ContentValues values = new ContentValues();
                            values.put("city", mCityStr);
                            db.update("MultiCities", values, "city = ?", new String[]{
                                    Ccity
                            });
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    mWeather = JSONUtil.getInstance().getWeather(getActivity(), mCityStr, mCityNum + 1);
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

    //Fragment不直接用构造函数传值 http://blog.csdn.net/anobodykey/article/details/22503413
    public static MultiCityFragment newInstance(int cityNum, String cityStr) {
        MultiCityFragment newFragment = new MultiCityFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("city_num", cityNum);
        bundle.putString("city_str", cityStr);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBManager.getInstance().openDatabase(DBManager.WEATHER_DB_NAME);
        Bundle args = getArguments();
        if (args != null) {
            this.mCityNum = args.getInt("city_num");
            Log.d("MultiCityFragmenthuang", " onCreate mCityNum " + mCityNum);
            this.mCityStr = args.getString("city_str");
            Log.d("MultiCityFragmenthuang", " onCreate mCityStr " + mCityStr);
        }
        switch (mCityNum) {
            //通过当前Fragment决定搜索或选择城市时在哪个位置更改
            case 0:
                mThisPage = C.Tag_CITY_1;
                break;
            case 1:
                mThisPage = C.Tag_CITY_2;
                break;
            case 2:
                mThisPage = C.Tag_CITY_3;
                break;
            case 3:
                mThisPage = C.Tag_CITY_4;
                break;
            case 4:
                mThisPage = C.Tag_CITY_5;
                break;
            default:
        }
    }

    //@Nullable 表示定义的字段可以为空.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_multi_cities, container, false);
            ButterKnife.bind(this, view);
        }
        mIsCreateView = true;
        init();
        initRecycleView();
        if (!mCityStr.equals("") && mCityStr != null) {
            Log.d("MultiCityFragmenthuang", " onCreateView " + mCityStr + "  " + mCityNum);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mWeather = JSONUtil.getInstance().getWeather(getActivity(), mCityStr, mCityNum + 1);
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
        //以节省流量和访问次数（因为每次打开app时用户的位置数据是基本不会改变的）
        super.onStart();
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
        String Ccity = cityList.get(mCityNum);
        if (!"".equals(Ccity) && !Ccity.equals(mCityStr)) {
            mCityStr = Ccity;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mWeather = JSONUtil.getInstance().getWeather(getActivity(), mCityStr, mCityNum + 1);
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
        DBManager.getInstance().closeDatabase();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void init() {
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
        String cCity = cityList.get(mCityNum);
        if (!"".equals(cCity) && cCity != null)//判断SharedPreference中存储的是否为空，即如果第一次执行程序不会变为空值进行初始赋值
        {
            mCityStr = cCity;
        }
        mNoData.setVisibility(View.GONE);

    }

    /**
     * 绑定监听事件
     */
    public void setListener() {
        mNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRecycleView();
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
                String cCity = cityList.get(mCityNum);
                if (!cCity.equals(mCityStr)) {
                    mCityStr = cCity;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mWeather = JSONUtil.getInstance().getWeather(getActivity(), mCityStr, mCityNum + 1);
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
        mNoData.setVisibility(View.GONE);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            mWeather = JSONUtil.getInstance().getWeather(getActivity(), mCityStr, mCityNum + 1);
            int i = 0;
            mRecycleView.setAdapter(mWeatherAdapter = new WeatherAdapter(mWeather));
            mGCityStr = mWeather.getBasic().getCity();
        } catch (Exception e) {
            e.printStackTrace();
            mWeatherInfo.setVisibility(View.GONE);
            mNoData.setVisibility(View.VISIBLE);
        }
    }

    public void safeSetTitle(String title) {
        ActionBar appBarLayout = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (appBarLayout != null) {
            appBarLayout.setTitle(title);
        }
    }
}