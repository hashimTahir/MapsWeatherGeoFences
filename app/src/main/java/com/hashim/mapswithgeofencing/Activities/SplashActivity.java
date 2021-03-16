package com.hashim.mapswithgeofencing.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.databinding.ActivitySplashBinding;


public class SplashActivity extends AppCompatActivity {


    private ActivitySplashBinding hActivitySplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hActivitySplashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(hActivitySplashBinding.getRoot());
        UIHelper.hOreoOrientationCheck(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        UIHelper.hOreoOrientationCheck(this);

    }

    private void hLetsGo() {
        Intent hMainIntent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(hMainIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }


    @Override
    public void onPause() {

        super.onPause();
    }


    public void hSetListeners() {
        hActivitySplashBinding.hLetsGoIv.setOnClickListener(v -> {
            hLetsGo();
        });
    }
}
