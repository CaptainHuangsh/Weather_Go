package com.example.owen.weathergo.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseActivity;
import com.example.owen.weathergo.dialog.CityDialog;
import com.example.owen.weathergo.dialog.MultiCityAddDialog;
import com.example.owen.weathergo.modules.adapter.MultiCityAdapter;
import com.example.owen.weathergo.util.DBManager;
import com.example.owen.weathergo.util.SharedPreferenceUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by owen on 2017/6/9.
 */

public class MultiCitiesManagerActivity extends BaseActivity {

    //TODO 验证输入的城市是否可用

    @BindView(R.id.city_recycle)
    RecyclerView mCityRecycle;

    private ArrayList<String> cityList = new ArrayList<>();
    private MultiCityAdapter mAdapter;
    private int mCityCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_multi_cities_manager);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
//        init();
//        initRecycleView();
    }

    @Override
    protected void initView() {
        mCityRecycle.setLayoutManager(new LinearLayoutManager(this));
        mCityRecycle.setHasFixedSize(true);
        mAdapter = new MultiCityAdapter(this, cityList);
        mCityRecycle.setAdapter(mAdapter);
        final ContentValues values = new ContentValues();
        mAdapter.setOnItemClickListener((v, pos) -> {
            if (pos == mCityCount) {
                if (mCityCount < 5) {
                    final MultiCityAddDialog dialog = new MultiCityAddDialog(MultiCitiesManagerActivity.this);
                    dialog.setSelectOnclickListener(() -> {
                        Intent intent = new Intent();
                        intent.setClass(MultiCitiesManagerActivity.this, ChoiceCityActivity.class);
                        intent.putExtra("what_to_do", "select_multi_city");
                        startActivityForResult(intent, 1);
                        dialog.dismiss();

                    });
                    dialog.setInputOnclickListener(() -> {
                        final CityDialog dialog2 = new CityDialog(MultiCitiesManagerActivity.this);
                        dialog2.setYesOnclickListener("确定", () -> {
                            Boolean addData = true;
                            if (!"".equals(dialog2.mCityEdit.getText().toString())) {
                                for (String city : cityList) {
                                    if (city.equals(dialog2.mCityEdit.getText().toString())) {
                                        AlertDialog.Builder dialog3 = new AlertDialog.Builder(
                                                MultiCitiesManagerActivity.this);
                                        dialog3.setMessage("城市已存在😁")
                                                .show();
                                        addData = false;
                                        break;
                                    }
                                }
                                if (SharedPreferenceUtil.getInstance().getCityName()
                                        .equals(dialog2.mCityEdit.getText().toString())) {//是否和主城市冲突
                                    AlertDialog.Builder dialog3 = new AlertDialog.Builder(
                                            MultiCitiesManagerActivity.this);
                                    dialog3.setMessage("城市已存在😁")
                                            .show();
                                    addData = false;
                                }
                                if (addData) {
                                    DBManager.getInstance().openDatabase(DBManager.WEATHER_DB_NAME);
                                    values.put("city", dialog2.mCityEdit.getText().toString());
                                    final SQLiteDatabase db = DBManager.getInstance().getDatabase();
                                    db.insert("MultiCities", null, values);
                                    values.clear();
                                    cityList.clear();
                                    init();
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                            dialog2.dismiss();
                            DBManager.getInstance().closeDatabase();

                        });
                        dialog2.setNoOnclickListener("取消", dialog2::dismiss);
                        dialog2.show();
                        dialog.dismiss();
                    });
                    dialog.show();
                } else {
                    AlertDialog.Builder dialog3 = new AlertDialog.Builder(
                            MultiCitiesManagerActivity.this);
                    dialog3.setMessage("最多选择5个城市哦😭")
                            .show();
                }
            } else {
                Intent intent = new Intent(MultiCitiesManagerActivity.this, WeatherMain.class);
                intent.putExtra("city_num", pos);
                startActivity(intent);
            }


        });
        mAdapter.setOnItemLongClickListener((v, pos) -> {
                    if (pos < mCityCount) {
                        toDeleteCity(cityList.get(pos));
                    }
                }
        );
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        setTitle("多城市管理");
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
        mCityCount = (int) DBManager.getInstance().allCaseNum("MultiCities");
        cityList.add("添加城市");
        DBManager.getInstance().closeDatabase();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.putExtra("what_activity_from", "MultiCitiesManagerActivity");
        setResult(RESULT_OK, intent);
    }

    private void init() {

    }

    private void initRecycleView() {
        mCityRecycle.setLayoutManager(new LinearLayoutManager(this));
        mCityRecycle.setHasFixedSize(true);
        mAdapter = new MultiCityAdapter(this, cityList);
        mCityRecycle.setAdapter(mAdapter);
        final ContentValues values = new ContentValues();
        mAdapter.setOnItemClickListener((v, pos) -> {
            if (pos == mCityCount) {
                if (mCityCount < 5) {
                    final MultiCityAddDialog dialog = new MultiCityAddDialog(MultiCitiesManagerActivity.this);
                    dialog.setSelectOnclickListener(() -> {
                        Intent intent = new Intent();
                        intent.setClass(MultiCitiesManagerActivity.this, ChoiceCityActivity.class);
                        intent.putExtra("what_to_do", "select_multi_city");
                        startActivityForResult(intent, 1);
                        dialog.dismiss();

                    });
                    dialog.setInputOnclickListener(() -> {
                        final CityDialog dialog2 = new CityDialog(MultiCitiesManagerActivity.this);
                        dialog2.setYesOnclickListener("确定", () -> {
                            Boolean addData = true;
                            if (!"".equals(dialog2.mCityEdit.getText().toString())) {
                                for (String city : cityList) {
                                    if (city.equals(dialog2.mCityEdit.getText().toString())) {
                                        AlertDialog.Builder dialog3 = new AlertDialog.Builder(
                                                MultiCitiesManagerActivity.this);
                                        dialog3.setMessage("城市已存在😁")
                                                .show();
                                        addData = false;
                                        break;
                                    }
                                }
                                if (SharedPreferenceUtil.getInstance().getCityName()
                                        .equals(dialog2.mCityEdit.getText().toString())) {//是否和主城市冲突
                                    AlertDialog.Builder dialog3 = new AlertDialog.Builder(
                                            MultiCitiesManagerActivity.this);
                                    dialog3.setMessage("城市已存在😁")
                                            .show();
                                    addData = false;
                                }
                                if (addData) {
                                    DBManager.getInstance().openDatabase(DBManager.WEATHER_DB_NAME);
                                    values.put("city", dialog2.mCityEdit.getText().toString());
                                    final SQLiteDatabase db = DBManager.getInstance().getDatabase();
                                    db.insert("MultiCities", null, values);
                                    values.clear();
                                    cityList.clear();
                                    init();
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                            dialog2.dismiss();
                            DBManager.getInstance().closeDatabase();

                        });
                        dialog2.setNoOnclickListener("取消", dialog2::dismiss);
                        dialog2.show();
                        dialog.dismiss();
                    });
                    dialog.show();
                } else {
                    AlertDialog.Builder dialog3 = new AlertDialog.Builder(
                            MultiCitiesManagerActivity.this);
                    dialog3.setMessage("最多选择5个城市哦😭")
                            .show();
                }
            } else {
                Intent intent = new Intent(MultiCitiesManagerActivity.this, WeatherMain.class);
                intent.putExtra("city_num", pos);
                startActivity(intent);
            }


        });
        mAdapter.setOnItemLongClickListener((v, pos) -> {
                    if (pos < mCityCount) {
                        toDeleteCity(cityList.get(pos));
                    }
                }
        );
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Boolean addData = true;
                    String mResultCity = data.getStringExtra("select_multi_city");
                    if (!"".equals(mResultCity) && mResultCity != null) {
                        DBManager.getInstance().openDatabase(DBManager.WEATHER_DB_NAME);
                        final ContentValues values = new ContentValues();
                        final SQLiteDatabase db = DBManager.getInstance().getDatabase();
                        for (String city : cityList) {
                            if (city.equals(mResultCity)) {
                                AlertDialog.Builder dialog3 = new AlertDialog.Builder(
                                        MultiCitiesManagerActivity.this);
                                dialog3.setMessage("城市已存在😁")
                                        .show();
                                addData = false;
                                break;
                            }
                        }
                        if (SharedPreferenceUtil.getInstance().getCityName()
                                .equals(mResultCity)) {//是否和主城市冲突
                            AlertDialog.Builder dialog3 = new AlertDialog.Builder(
                                    MultiCitiesManagerActivity.this);
                            dialog3.setMessage("城市已存在😁")
                                    .show();
                            addData = false;
                        }
                        if (addData) {
                            values.put("city", mResultCity);
                            db.insert("MultiCities", null, values);
                        }
                        DBManager.getInstance().closeDatabase();
                        values.clear();
                    }
                    cityList.clear();
                    init();
                    mAdapter.notifyDataSetChanged();
                }
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBManager.getInstance().closeDatabase();
    }

    private void toDeleteCity(final String cityStr) {
        DBManager.getInstance().openDatabase(DBManager.WEATHER_DB_NAME);
        AlertDialog.Builder dialog = new AlertDialog.Builder(
                MultiCitiesManagerActivity.this);
        dialog.setTitle("提示信息")
                .setMessage("确定删除城市" + cityStr + "?")
                .setCancelable(true)
                .setPositiveButton("确定", (d, which) -> {
                    final SQLiteDatabase db = DBManager.getInstance().getDatabase();
                    db.delete("MultiCities", "city = ?", new String[]{
                            cityStr
                    });
                    cityList.clear();
                    init();
                    mAdapter.notifyDataSetChanged();

                })
                .setNegativeButton("取消", (d, which) -> d.dismiss()).show();
    }

    private void quit() {
        this.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    //启动Activity方法
    public static void launch(Context context) {
        context.startActivity(new Intent(context, MultiCitiesManagerActivity.class));
    }

}
