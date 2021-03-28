/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.compass

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener2
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.hashim.mapswithgeofencing.databinding.FragmentCompassBinding


class CompassFragment : Fragment(), SensorEventListener2 {


    lateinit var hFragmentCompassBinding: FragmentCompassBinding

    private val hSensorManager: SensorManager?
        get() {
            return getSystemService(requireContext(), SensorManager::class.java)
        }

    lateinit var hAccelerometerSensor: Sensor
    lateinit var hMagnetometerSensor: Sensor


    private val hLastAccelerometerReading = FloatArray(3)
    private val hLastMagnetometerReading = FloatArray(3)
    private val mR = FloatArray(9)
    private val mOrientation = FloatArray(3)

    private var hIsLastAccelerometerSet: Boolean = false
    private var hIsLastMagnetometerSet: Boolean = false

    private var mCurrentDegree = 0f
    private var hLastUpdate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        hAccelerometerSensor = hSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        hMagnetometerSensor = hSensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        hFragmentCompassBinding = FragmentCompassBinding.inflate(
                inflater,
                container,
                false
        )
        return hFragmentCompassBinding.root
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor == hAccelerometerSensor) {
            System.arraycopy(event.values, 0, hLastAccelerometerReading, 0, event.values.size)
            hIsLastAccelerometerSet = true
        } else if (event?.sensor == hMagnetometerSensor) {
            System.arraycopy(event.values, 0, hLastMagnetometerReading, 0, event.values.size)
            hIsLastMagnetometerSet = true
        }
        if (hIsLastAccelerometerSet && hIsLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, hLastAccelerometerReading, hLastMagnetometerReading)
            SensorManager.getOrientation(mR, mOrientation)
            val azimuthInRadians = mOrientation[0]
            val azimuthInDegress = (Math.toDegrees(azimuthInRadians.toDouble()) + 360) % 360.toFloat()
            val ra = RotateAnimation(
                    mCurrentDegree,
                    -azimuthInDegress.toFloat(),
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f)

            ra.duration = 1000

            ra.fillAfter = true

            hFragmentCompassBinding.imageViewCompass.startAnimation(ra)
            mCurrentDegree = (-azimuthInDegress).toFloat()


            val hCurrentTime = System.currentTimeMillis()
            if ((hCurrentTime - hLastUpdate) > 1000) {
                hLastUpdate = hCurrentTime
//                UIHelper.hSetTextToTextView(hActivityCompassBinding.tvHeading, "Value : " + String.valueOf((int) mCurrentDegree));

            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onFlushCompleted(sensor: Sensor?) {
    }


    override fun onResume() {
        super.onResume()
        hSensorManager?.registerListener(this, hAccelerometerSensor, SensorManager.SENSOR_STATUS_ACCURACY_LOW)
        hSensorManager?.registerListener(this, hMagnetometerSensor, SensorManager.SENSOR_STATUS_ACCURACY_LOW)
    }

    override fun onPause() {
        super.onPause()
        hSensorManager?.unregisterListener(this, hAccelerometerSensor)
        hSensorManager?.unregisterListener(this, hMagnetometerSensor)
    }
}
