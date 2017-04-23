package com.example.owen.weathergo.modules.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.owen.weathergo.R;

/**
 * Created by owen on 2017/4/8.
 */

public class About extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, About.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
