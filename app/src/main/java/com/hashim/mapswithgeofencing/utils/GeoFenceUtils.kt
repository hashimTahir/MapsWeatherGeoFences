/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.hashim.mapswithgeofencing.others.prefrences.PrefTypes.ALL_PT
import com.hashim.mapswithgeofencing.others.prefrences.SettingsPrefrences


class GeoFenceUtils(private val hContext: Context) {
    private val hGeofencingClient: GeofencingClient
    var hRadius: Int
    var hSettingsPrefrences: SettingsPrefrences

    @SuppressLint("MissingPermission")
    fun hAddLocationAlert(latitude: Double, longitude: Double) {
//
//        String key = "" + latitude + "-" + longitude;
//        Geofence hGeofence = getGeofence(latitude, longitude, key);
//
//        hGeofencingClient.addGeofences(getGeofencingRequest(hGeofence),
//                getGeofencePendingIntent())
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(hContext,
//                                "Location alter has been added",
//                                Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(hContext,
//                                "Location alter could not be added",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//        hShowVisibleGeoFence(latitude, longitude);
    }

    private fun getGeofencingRequest(geofence: Geofence): GeofencingRequest {
        val builder = GeofencingRequest.Builder()
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        builder.addGeofence(geofence)
        return builder.build()
    }

    //        LogToastSnackHelper.hLogField(hTag, "Pending intent added");
//        Intent intent = new Intent(hContext, LocationUpdatesBroadcastReceiver.class);
//
//        return PendingIntent.getBroadcast(hContext, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
    private val geofencePendingIntent: PendingIntent?
        private get() =//        LogToastSnackHelper.hLogField(hTag, "Pending intent added");
        //        Intent intent = new Intent(hContext, LocationUpdatesBroadcastReceiver.class);
        //
        //        return PendingIntent.getBroadcast(hContext, 0, intent,
                //                PendingIntent.FLAG_UPDATE_CURRENT);
            null

    private fun getGeofence(lat: Double, lang: Double, key: String): Geofence {
        return Geofence.Builder()
                .setRequestId(key)
                .setCircularRegion(lat, lang, (10/*hSettingsPrefrences.hGetSettings(ALL)*/ * 1000).toFloat())
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .setLoiteringDelay(1)
                .build()
    }

    fun hShowVisibleGeoFence(latitude: Double, longitude: Double): CircleOptions {
        return CircleOptions()
                .strokeColor(Color.argb(50, 70, 6, 70)) //Outer black border
                .fillColor(Color.argb(100, 150, 150, 150)) //inside of the geofence will be transparent, change to whatever color you prefer like 0x88ff0000 for mid-transparent red
                .center(LatLng(latitude, longitude)) // the LatLng Object of your geofence location
                .radius(/*hSettingsPrefrences.hGetRadius()*/(10 * 1000).toDouble())
        //        ; // The radius (in meters) of your geofence
        // Get back the mutable Circle
        //        Circle circle =
    }

    init {
        hGeofencingClient = LocationServices.getGeofencingClient(hContext)
        hSettingsPrefrences = SettingsPrefrences(hContext)
        hRadius = 10
        hSettingsPrefrences.hGetSettings(ALL_PT)
        hRadius = hRadius * 1000
    }
}