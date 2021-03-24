/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class LocationUtis(
        private val context: Context,
        private val onLocationRetrieved: (location: Location?) -> Unit,
        private val onLocationUpdated: (location: Location?) -> Unit
) {
    private var hFusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)


    private val hLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                onLocationUpdated(location)
            }
        }
    }


    init {

        hGetLastKnownLocation()
    }

    @SuppressLint("MissingPermission")
    private fun hGetLastKnownLocation() {
        hFusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    onLocationRetrieved(location)
                }
    }

    fun hCreateLocationRequest() {
        val hLocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val hBuilder = LocationSettingsRequest.Builder()
                .addLocationRequest(hLocationRequest)


        val hClient: SettingsClient = LocationServices.getSettingsClient(context)
        val hTask: Task<LocationSettingsResponse> = hClient.checkLocationSettings(hBuilder.build())


        hTask.addOnSuccessListener { locationSettingsResponse ->
            /*Can start location updates*/
        }.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(context as Activity,
                            0)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun hStartLocationUpdates(locationRequest: LocationRequest) {
        hFusedLocationProviderClient.requestLocationUpdates(locationRequest,
                hLocationCallback,
                Looper.getMainLooper())
    }

    private fun hStopLocationUpdates() {
        hFusedLocationProviderClient.removeLocationUpdates(hLocationCallback)
    }



}