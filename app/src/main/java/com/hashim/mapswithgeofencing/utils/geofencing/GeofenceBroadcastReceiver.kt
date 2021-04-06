/*
 * Copyright (c) 2021/  4/ 6.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils.geofencing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.location.GeofencingEvent
import com.google.gson.GsonBuilder
import com.hashim.mapswithgeofencing.utils.Constants.Companion.H_WORKER_DATA


class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val hWorkManager = WorkManager.getInstance(context)
        val hData = hParseDataFromLocationService(intent)

        val hWork = OneTimeWorkRequestBuilder<GeoWorker>()
        hWork.setInputData(hData)

        hWorkManager.enqueue(hWork.build())

    }

    private fun hParseDataFromLocationService(intent: Intent?): Data {

        val hGeofencingEvent = GeofencingEvent.fromIntent(intent)

        val hList = mutableListOf<String>()
        hGeofencingEvent.triggeringGeofences.forEach {
            hList.add(it.requestId)
        }
        var geoData = GeoData(
                hGeofencingEvent.errorCode,
                hGeofencingEvent.geofenceTransition,
                hGeofencingEvent.triggeringLocation.latitude,
                hGeofencingEvent.triggeringLocation.longitude,
                hGeofencingEvent.triggeringLocation.bearing,
                hGeofencingEvent.triggeringLocation.speed,
                hList.toList()
        )

        val hGson = GsonBuilder().create()

        return Data.Builder()
                .putString(
                        H_WORKER_DATA,
                        hGson.toJson(geoData)
                )
                .build()

    }
}
