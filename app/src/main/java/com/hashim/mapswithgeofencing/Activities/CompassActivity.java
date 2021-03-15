package com.hashim.mapswithgeofencing.Activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


import com.hashim.mapswithgeofencing.Helper.ToolBarHelper;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    @BindView(R.id.tvHeading)
    TextView hTvHeading;
    @BindView(R.id.imageViewCompass)
    ImageView hImageViewCompass;

    private SensorManager hSensorManager;
    @BindView(R.id.toolbar_title)
    TextView hToolbarTitle;
    @BindView(R.id.hACappBar)
    Toolbar hToolbar;
    private Sensor hAccelerometerSensor;
    private Sensor hMagnetometerSensor;

    private float[] hLastAccelerometerReading = new float[3];
    private float[] hLastMagnetometerReading = new float[3];
    private boolean hIsLastAccelerometerSet = false;
    private boolean hIsLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    private float mCurrentDegree = 0f;
    private long hLastUpdate;

    @BindView(R.id.adView)
    AdView mAdView;
    private HAdmob hAdmob;
    private H_InterstetialAdd h_interstetialAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        ButterKnife.bind(this);

        UIHelper.hOreoOrientationCheck(this);

        h_interstetialAdd = new H_InterstetialAdd(this);

        ToolBarHelper hToolBarHelper = new ToolBarHelper(this);
        hToolBarHelper.hSetToolbar(hToolbar);
        hToolBarHelper.hSetToolbarTitle(hToolbarTitle, "Compass");


        hSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        hAccelerometerSensor = hSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        hMagnetometerSensor = hSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        hAdmob = new HAdmob(this, mAdView);
        hAdmob.hLoadAd();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        UIHelper.hOreoOrientationCheck(this);

        // for the system's orientation sensor registered listeners
        hAdmob.hResumeAdd();
        h_interstetialAdd.hLoadAd();
        hSensorManager.registerListener(this, hAccelerometerSensor, SensorManager.SENSOR_STATUS_ACCURACY_LOW);
        hSensorManager.registerListener(this, hMagnetometerSensor, SensorManager.SENSOR_STATUS_ACCURACY_LOW);
    }

    protected void onPause() {
        super.onPause();
        hSensorManager.unregisterListener(this, hAccelerometerSensor);
        hSensorManager.unregisterListener(this, hMagnetometerSensor);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == hAccelerometerSensor) {
            System.arraycopy(event.values, 0, hLastAccelerometerReading, 0, event.values.length);
            hIsLastAccelerometerSet = true;
        } else if (event.sensor == hMagnetometerSensor) {
            System.arraycopy(event.values, 0, hLastMagnetometerReading, 0, event.values.length);
            hIsLastMagnetometerSet = true;
        }
        if (hIsLastAccelerometerSet && hIsLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, hLastAccelerometerReading, hLastMagnetometerReading);
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthInRadians = mOrientation[0];
            float azimuthInDegress = (float) (Math.toDegrees(azimuthInRadians) + 360) % 360;
            RotateAnimation ra = new RotateAnimation(
                    mCurrentDegree,
                    -azimuthInDegress,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            ra.setDuration(1000);

            ra.setFillAfter(true);

            hImageViewCompass.startAnimation(ra);
            mCurrentDegree = -azimuthInDegress;


            long hCurrentTime = System.currentTimeMillis();
            if ((hCurrentTime - hLastUpdate) > 1000) {
                hLastUpdate = hCurrentTime;
                UIHelper.hSetTextToTextView(hTvHeading, "Value : " + String.valueOf((int) mCurrentDegree));

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        h_interstetialAdd.hShowInterstitial();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
