package com.example.owen.weathergo.modules.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.owen.weathergo.R;

/**
 * Created by owen on 2017/4/8.
 */

public class About extends AppCompatActivity {

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, About.class));
    }
}
