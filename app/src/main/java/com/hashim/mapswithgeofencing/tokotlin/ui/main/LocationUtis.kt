package com.hashim.mapswithgeofencing.tokotlin.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationUtis(
        private val context: Context,
        private val onLocationRetrieved: (location: Location?) -> Unit
) {
    private lateinit var hFusedLocationProviderClient: FusedLocationProviderClient


    init {

        hFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        hGetLastKnownLocation()
    }

    @SuppressLint("MissingPermission")
    private fun hGetLastKnownLocation() {
        hFusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    onLocationRetrieved(location)
                }
    }
}