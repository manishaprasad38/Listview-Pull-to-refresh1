package com.demo.app.assignment.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import com.demo.app.assignment.R;
import com.demo.app.assignment.databinding.ActivitySplashBinding;


// Introduction of App
public class SplashActivity extends Activity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // for full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        showSystemUI();
       // setContentView(R.layout.activity_splash);
        //data binding concept
        ActivitySplashBinding activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        activitySplashBinding.getRoot();
        // managing threads for 2 sec
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
    // for full screen
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        if(Build.VERSION.PREVIEW_SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

}
