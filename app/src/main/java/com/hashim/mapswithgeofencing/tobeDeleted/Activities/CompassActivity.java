/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.Activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.appcompat.app.AppCompatActivity;

import com.hashim.mapswithgeofencing.databinding.ActivityCompassBinding;


public class CompassActivity extends AppCompatActivity implements SensorEventListener {


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
    private ActivityCompassBinding hActivityCompassBinding;

    private SensorManager hSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hActivityCompassBinding = ActivityCompassBinding.inflate(getLayoutInflater());
        setContentView(hActivityCompassBinding.getRoot());

        UIHelper.hOreoOrientationCheck(this);


        ToolBarHelper hToolBarHelper = new ToolBarHelper(this);
        hToolBarHelper.hSetToolbar(hActivityCompassBinding.hACappBar.toolbar);
        hToolBarHelper.hSetToolbarTitle(hActivityCompassBinding.hACappBar.toolbarTitle, "Compass");


        hSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        hAccelerometerSensor = hSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        hMagnetometerSensor = hSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


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

            hActivityCompassBinding.imageViewCompass.startAnimation(ra);
            mCurrentDegree = -azimuthInDegress;


            long hCurrentTime = System.currentTimeMillis();
            if ((hCurrentTime - hLastUpdate) > 1000) {
                hLastUpdate = hCurrentTime;
                UIHelper.hSetTextToTextView(hActivityCompassBinding.tvHeading, "Value : " + String.valueOf((int) mCurrentDegree));

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
