package com.example.owen.weathergo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.DoubleClickExit;
import com.example.owen.weathergo.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * 程序入口，主Activity类
     */

    //TODO 设置推送并震动

    private static final String TAG = WeatherMain.class.getSimpleName();
    private static final int SEARCH_CITY = 1;
    private static final int SCREEN_SHOOT = 2;

    //ButterKnife参考http://jakewharton.github.io/butterknife/
    @BindView(R.id.tl_custom)
    Toolbar mToolBar;
    @BindView(R.id.dl_left)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ActionBarDrawerToggle mDrawerToggle;
    private Handler mHandler;

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
    }

    //@Override
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
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toSearchDialog() {
        final EditText et = new EditText(this);
        new AlertDialog.Builder(this).setTitle("请输入城市名称")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Message msg = new Message();
                        msg.obj = et.getText().toString();
                        msg.what = SEARCH_CITY;
                        mHandler.sendMessage(msg);
                        //TODO 美化搜索框界面
                    }
                })
                .setNegativeButton("取消", null).show();
        mDrawerLayout.closeDrawers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }

    //初始化抽屉
    public void initDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
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
}
