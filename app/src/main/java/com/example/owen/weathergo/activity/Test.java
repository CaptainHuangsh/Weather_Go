package com.example.owen.weathergo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.util.JSONUtil2;

/**
 * Created by owen on 2017/5/24.
 */

public class Test extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ((Button)findViewById(R.id.test)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONUtil2 jsonUtil2 = new JSONUtil2(getApplicationContext(),"luoyang");
                jsonUtil2.getsomeThing();
            }
        });
    }
}
