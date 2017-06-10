package com.example.owen.weathergo.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    }

    private void initRecycleView() {
        mCityRecycle.setLayoutManager(new LinearLayoutManager(this));
        mCityRecycle.setHasFixedSize(true);
        mAdapter = new MultiCityAdapter(this, cityList);
        mCityRecycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiCityAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                if (pos == mCityCount) {
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
                            ToastUtil.showShort("inputCity");
                        }
                    });
                    dialog.show();
                } else
                    ToastUtil.showShort("dianjil" + cityList.get(pos));
            }
        });
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
