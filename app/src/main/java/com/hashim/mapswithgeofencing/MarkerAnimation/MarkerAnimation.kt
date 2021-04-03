/*
 * Copyright (c) 2021/  4/ 4.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.MarkerAnimation

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.os.Build
import android.os.Handler
import android.os.SystemClock
import android.util.Property
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

object MarkerAnimation {
    fun animateMarkerToGB(marker: Marker, finalPosition: LatLng?, latLngInterpolator: LatLngInterpolator) {
        val startPosition = marker.position
        val handler = Handler()
        val start = SystemClock.uptimeMillis()
        val interpolator: Interpolator = AccelerateDecelerateInterpolator()
        val durationInMs = 3000f
        handler.post(object : Runnable {
            var elapsed: Long = 0
            var t = 0f
            var v = 0f
            override fun run() {
                // Calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start
                t = elapsed / durationInMs
                v = interpolator.getInterpolation(t)
                marker.position = latLngInterpolator.interpolate(v, startPosition, finalPosition)

                // Repeat till progress is complete.
                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16)
                }
            }
        })
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    fun animateMarkerToHC(marker: Marker, finalPosition: LatLng?, latLngInterpolator: LatLngInterpolator) {
        val startPosition = marker.position
        val valueAnimator = ValueAnimator()
        valueAnimator.addUpdateListener { animation ->
            val v = animation.animatedFraction
            val newPosition = latLngInterpolator.interpolate(v, startPosition, finalPosition)
            marker.position = newPosition
        }
        valueAnimator.setFloatValues(0f, 1f) // Ignored.
        valueAnimator.duration = 3000
        valueAnimator.start()
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun animateMarkerToICS(marker: Marker?, finalPosition: LatLng?, latLngInterpolator: LatLngInterpolator) {
        val typeEvaluator = TypeEvaluator<LatLng> { fraction, startValue, endValue -> latLngInterpolator.interpolate(fraction, startValue, endValue) }
        val property = Property.of(Marker::class.java, LatLng::class.java, "position")
        val animator = ObjectAnimator.ofObject(marker, property, typeEvaluator, finalPosition)
        animator.duration = 3000
        animator.start()
    }
}