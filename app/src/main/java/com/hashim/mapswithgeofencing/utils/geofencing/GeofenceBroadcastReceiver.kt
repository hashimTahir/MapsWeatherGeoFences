/*
 * Copyright (c) 2021/  4/ 6.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils.geofencing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.GeofencingEvent
import com.google.gson.GsonBuilder
import timber.log.Timber

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        hParseDataFromLocationService(intent)
    }

    private fun hParseDataFromLocationService(intent: Intent?) {
        val fromIntent = GeofencingEvent.fromIntent(intent)
        val hGson = GsonBuilder().setPrettyPrinting().create()
        Timber.d("Reciever called error code ${hGson.toJson(fromIntent)}")

    }
}
