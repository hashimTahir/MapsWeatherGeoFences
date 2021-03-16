package com.hashim.mapswithgeofencing.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.hLetsGoIv)
    ImageView hLetsGoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
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

    @OnClick(R.id.hLetsGoIv)
    public void onViewClicked() {
        hLetsGo();
    }
}
