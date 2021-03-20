/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.hashim.mapswithgeofencing.Interfaces.LocationCallBackInterface;


public class MapsUtils implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private final FusedLocationProviderClient hFusedLocationProviderClient;
    private final LocationCallBackInterface hLocationCallBackInterface;
    private final Context hContext;
    private LatLng hLatLng;


    public MapsUtils(Context context, LocationCallBackInterface locationCallBackInterface) {
        this.hContext = context;
        this.hLocationCallBackInterface = locationCallBackInterface;
//        GeoDataClient hGeoDataClient = Places.getGeoDataClient(hContext);
//        PlaceDetectionClient hPlaceDetectionClient = Places.getPlaceDetectionClient(hContext);
        // Construct a PlaceDetectionClient.

        // Construct a FusedLocationProviderClient.
        hFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(hContext);
    }

    public void hGetCurrentLocationCoOrdinates() {
        if (ActivityCompat.checkSelfPermission(hContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(hContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        hFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener((Activity) hContext, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {


                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Location hLastKnownLocation = task.getResult();
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                    new LatLng(mLastKnownLocation.getLatitude(),
//                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            hLocationCallBackInterface.hGetCurrentLocation(hLastKnownLocation);
                        } else {

//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
//                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }

//                    @Override
//                    public void onComplete(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location!= null) {
//                            Double hLatitude = location.getLatitude();
//                            Double hLongitude = location.getLongitude();
//                            hLatLng = new LatLng(hLatitude, hLongitude);
//                            hLocationCallBackInterface.hGetCurrentLocation(hLatLng);
//                            // Logic to handle location object
//                        } else {
//                            hLocationCallBackInterface.hGetCurrentLocation(null);
//                        }
//                    }
                });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}
