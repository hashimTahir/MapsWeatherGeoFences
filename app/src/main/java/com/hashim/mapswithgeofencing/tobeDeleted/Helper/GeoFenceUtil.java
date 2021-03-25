/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.Helper;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.hashim.mapswithgeofencing.SettingsPrefrences;


@SuppressWarnings("HardCodedStringLiteral")
public class GeoFenceUtil {
    private Context hContext;
    private GeofencingClient hGeofencingClient;
    int hRadius;
    SettingsPrefrences hSettingsPrefrences;


    public GeoFenceUtil(Context context) {
        this.hContext = context;
        hGeofencingClient = LocationServices.getGeofencingClient(hContext);
        hSettingsPrefrences = new SettingsPrefrences(hContext);
        hRadius = hSettingsPrefrences.hGetRadius();
        hRadius = hRadius * 1000;
    }

    @SuppressLint("MissingPermission")
    public void hAddLocationAlert(double latitude, double longitude) {
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

    private GeofencingRequest getGeofencingRequest(Geofence geofence) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofence(geofence);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
//        LogToastSnackHelper.hLogField(hTag, "Pending intent added");
//        Intent intent = new Intent(hContext, LocationUpdatesBroadcastReceiver.class);
//
//        return PendingIntent.getBroadcast(hContext, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
        return null;
    }

    private Geofence getGeofence(double lat, double lang, String key) {

        return new Geofence.Builder()
                .setRequestId(key)
                .setCircularRegion(lat, lang, hSettingsPrefrences.hGetRadius() * 1000)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .setLoiteringDelay(1)
                .build();
    }

    public CircleOptions hShowVisibleGeoFence(double latitude, double longitude) {
        return new CircleOptions()
                .strokeColor(Color.argb(50, 70, 6, 70)) //Outer black border
                .fillColor(Color.argb(100, 150, 150, 150)) //inside of the geofence will be transparent, change to whatever color you prefer like 0x88ff0000 for mid-transparent red
                .center(new LatLng(latitude, longitude)) // the LatLng Object of your geofence location
                .radius(hSettingsPrefrences.hGetRadius() * 1000);
        //        ; // The radius (in meters) of your geofence
        // Get back the mutable Circle
        //        Circle circle =
    }
}
