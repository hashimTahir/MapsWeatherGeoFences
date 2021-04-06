/*
 * Copyright (c) 2021/  4/ 6.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils.geofencing

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.gms.location.GeofencingEvent

class GeoWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.success()
    }
}