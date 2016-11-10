package com.example.owen.weathergo.activity;

/**
 * Created by root on 16-11-10.
 */


        import android.app.Activity;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.SharedPreferences.Editor;
        import android.opengl.ETC1;
        import android.os.Bundle;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.Window;
        import android.view.animation.AlphaAnimation;
        import android.view.animation.Animation;
        import android.view.animation.Animation.AnimationListener;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.ProgressBar;

        import com.example.owen.weathergo.R;

public class LogingActivity extends Activity {
    private ProgressBar progressBar;
    private Button backButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loging_activity);

        progressBar = (ProgressBar) findViewById(R.id.pgBar);
        backButton = (Button) findViewById(R.id.btn_back);

        Intent intent = new Intent(this, WeatherMain.class);
        LogingActivity.this.startActivity(intent);

        backButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

}

