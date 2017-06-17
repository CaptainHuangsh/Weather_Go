package com.example.owen.weathergo.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by owen on 2017/4/25.
 */

public class DBManager {

    private static String TAG = DBManager.class.getSimpleName();
    private static final String DB_NAME = "china_city.db"; //城市数据库名字
    public static final String WEATHER_DB_NAME = "weathergo_city.db";//"其他"数据库名字
    private static final String PACKAGE_NAME = "com.example.owen.weathergo";
    private static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" +
            PACKAGE_NAME + "/databases/";  //在手机里存放数据库的位置(/data/data/com.example.owen.weathergo/databases/" + china_city.db)
    private SQLiteDatabase database;
    private Context context;

    private DBManager() {

    }

    public static DBManager getInstance() {
        return DBManagerHolder.sInstance;
    }

    private static final class DBManagerHolder {
        static final DBManager sInstance = new DBManager();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }


    public void openDatabase() {
        //Log.e(TAG, DB_PATH + "/" + DB_NAME);
        this.database = this.openDatabase(DB_NAME);
    }

    @Nullable
    public SQLiteDatabase openDatabase(String dbfile) {

        try {

            if (!(new File(DB_PATH + "/" + dbfile).exists())) {
                if (dbfile.equals(DB_NAME)) {

                    File file = new File(DB_PATH);
                    //判断文件夹是否存在,如果不存在则创建文件夹
                    if (!file.exists()) {
                        file.mkdir();
                    }

                    //如果是city.db的话，从raw中导入；否则按正常方法
                    //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                    InputStream is = BaseApplication.getAppContext().getResources().openRawResource(R.raw.china_city); //欲导入的数据库
                    FileOutputStream fos = new FileOutputStream(DB_PATH + "/" + dbfile);
                    int BUFFER_SIZE = 400000;
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int count;
                    while ((count = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);
                    }
                    fos.close();
                    is.close();
                } else {
                    MyDatabaseHelper dpHelper = new MyDatabaseHelper(BaseApplication.getAppContext(), "create table MultiCities(" +
                            "id integer primary key autoincrement," +
                            "city text)"
                            , dbfile, null, 1);
                    dpHelper.getWritableDatabase();
                }
            }
            this.database = SQLiteDatabase.openOrCreateDatabase(DB_PATH + "/" + dbfile, null);
            ;
            //非CITY_DB情况下赋值database
            return SQLiteDatabase.openOrCreateDatabase(DB_PATH + "/" + dbfile, null);
        } catch (FileNotFoundException e) {
            Log.e("File not found", "");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("IO exception", "");
            e.printStackTrace();
        }

        return null;
    }

    public void closeDatabase() {
        if (this.database != null) {
            this.database.close();
        }
    }

    /**
     * 查询数据库中的总条数.
     *
     * @return
     */
    public long allCaseNum(String table) {
        String sql = "select count(*) from " + table;
        Cursor cursor = this.database.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }

    private class MyDatabaseHelper extends SQLiteOpenHelper {
        private String CREATE_DB;

        MyDatabaseHelper(Context context, String sqlStr, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            this.CREATE_DB = sqlStr;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d("huangshaohua", "sqlStr : " + CREATE_DB);
            sqLiteDatabase.execSQL(CREATE_DB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

}
