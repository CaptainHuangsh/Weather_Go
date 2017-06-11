package com.example.owen.weathergo.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.dialog.CityDialog;
import com.example.owen.weathergo.dialog.MultiCityAddDialog;
import com.example.owen.weathergo.modules.adapter.MultiCityAdapter;
import com.example.owen.weathergo.util.DBManager;
import com.example.owen.weathergo.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by owen on 2017/6/9.
 */

public class MultiCitiesManagerActivity extends AppCompatActivity {

    //TODO 修改数据库中id不自增,随总数改变id
    @BindView(R.id.city_recycle)
    RecyclerView mCityRecycle;

    private ArrayList<String> cityList = new ArrayList<>();
    private MultiCityAdapter mAdapter;
    private int mCityCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_cities_manager);
        ButterKnife.bind(this);
        init();
        initRecycleView();
    }

    private void init() {
        setTitle("多城市管理");
        DBManager.getInstance().openDatabase(DBManager.WEATHER_DB_NAME);
        final SQLiteDatabase db = DBManager.getInstance().getDatabase();
//        ToastUtil.showShort("" + db);
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
//        DBManager.getInstance().closeDatabase();
        /*cityList.add("洛阳");
        cityList.add("开封");*/
        cityList.add("添加城市");
//        initRecycleView();
    }

    private void initRecycleView() {
        mCityRecycle.setLayoutManager(new LinearLayoutManager(this));
        mCityRecycle.setHasFixedSize(true);
        mAdapter = new MultiCityAdapter(this, cityList);
        mCityRecycle.setAdapter(mAdapter);
        final ContentValues values = new ContentValues();
        mAdapter.setOnItemClickListener(new MultiCityAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                if (pos == mCityCount) {
                    if (mCityCount < 5) {
                        final MultiCityAddDialog dialog = new MultiCityAddDialog(MultiCitiesManagerActivity.this);
                        dialog.setSelectOnclickListener(new MultiCityAddDialog.onSelectOnclickListener() {
                            @Override
                            public void onSelectClick() {
                                ToastUtil.showShort("selectCity");
                            }
                        });
                        dialog.setInputOnclickListener(new MultiCityAddDialog.onInputOnclickListener() {
                            @Override
                            public void onInputClick() {
                                final CityDialog dialog2 = new CityDialog(MultiCitiesManagerActivity.this);
                                dialog2.setYesOnclickListener("确定", new CityDialog.onYesOnclickListener() {
                                    @Override
                                    public void onYesClick() {
                                        if (!"".equals(dialog2.mCityEdit.getText().toString())) {
                                            values.put("city", dialog2.mCityEdit.getText().toString());
                                            final SQLiteDatabase db = DBManager.getInstance().getDatabase();
                                            db.insert("MultiCities", null, values);
                                            values.clear();
                                            cityList.clear();
                                            init();
                                            mAdapter.notifyDataSetChanged();
                                        }
                                        dialog2.dismiss();
                                    }
                                });
                                dialog2.setNoOnclickListener("取消", new CityDialog.onNoOnclickListener() {
                                    @Override
                                    public void onNoClick() {
                                        dialog2.dismiss();
                                    }
                                });
                                dialog2.show();
                                dialog.dismiss();
                            }
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
                    Log.d("MultiCitiesManagerActivityhuang startActivity "," pos "+pos);
                    startActivity(intent);
//                    quit();
//                    ToastUtil.showShort("dianjil" + cityList.get(pos));
                }

            }
        });
        mAdapter.setOnItemLongClickListener(new MultiCityAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int pos) {
                if (pos < mCityCount) {
//                    ToastUtil.showShort("shanchu" + cityList.get(pos));
                    toDeleteCity(cityList.get(pos));
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBManager.getInstance().closeDatabase();
    }

    private void toDeleteCity(final String cityStr) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(
                MultiCitiesManagerActivity.this);
        dialog.setTitle("提示信息")
                .setMessage("确定删除城市" + cityStr + "?")
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final SQLiteDatabase db = DBManager.getInstance().getDatabase();
                        db.delete("MultiCities", "city = ?", new String[]{
                                cityStr
                        });
                        cityList.clear();
                        init();
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
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
