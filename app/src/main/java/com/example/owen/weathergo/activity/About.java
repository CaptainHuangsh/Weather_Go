package com.example.owen.weathergo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.util.FileUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by owen on 2017/4/8.
 */

public class About extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.about_version)
    TextView mVersion;
    @BindView(R.id.feed_back)
    TextView mFeedBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mVersion.setText(getVersion());
        mFeedBack.setOnClickListener(v->FileUtil.getInstance().shareMsg(About.this
                ,getApplicationContext().getString(R.string.send_email),"WeatherGo Feedback",FileUtil.getInstance().getAppInfo(About.this).toString(),null,1));

    }


    public static void launch(Context context) {
        context.startActivity(new Intent(context, About.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    /**
     * 获取版本号
     * http://www.cnblogs.com/yeahui/archive/2012/10/20/2732429.html
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return this.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }

}
