package com.hashim.mapswithgeofencing.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.hLetsGoIv)
    ImageView hLetsGoIv;
    private H_InterstetialAdd h_interstetialAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        UIHelper.hOreoOrientationCheck(this);

        h_interstetialAdd = new H_InterstetialAdd(this);
        h_interstetialAdd.hLoadAd();

//        hHandler.postDelayed(hRunnable, Constants.H_2Secs_Timer);

    }

    @Override
    protected void onResume() {
        super.onResume();
        UIHelper.hOreoOrientationCheck(this);

    }

    private void hLetsGo() {
        Intent hMainIntent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(hMainIntent);
        h_interstetialAdd.hShowInterstitial();
        finish();
    }

    @Override
    public void onBackPressed() {

//        if (!(((ApplicationClass) getApplication()).hIsPurchased)) {
        h_interstetialAdd.hLoadAd();
//        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }


    @Override
    public void onPause() {
//        if (!(((ApplicationClass) getApplication()).hIsPurchased)) {

        h_interstetialAdd.hPauseInterstitialAds();

//        }

        super.onPause();
    }

    @OnClick(R.id.hLetsGoIv)
    public void onViewClicked() {
        hLetsGo();
    }
}
