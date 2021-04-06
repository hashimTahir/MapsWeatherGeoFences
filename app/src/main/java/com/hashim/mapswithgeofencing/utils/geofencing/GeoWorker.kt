/*
 * Copyright (c) 2021/  4/ 6.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils.geofencing

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hashim.mapswithgeofencing.utils.Constants.Companion.H_WORKER_DATA
import timber.log.Timber

class GeoWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {

        return try {
            val hData = inputData.getString(H_WORKER_DATA)

            Timber.d("Worker Result Retrieved $hData")

            /*Todo: Notify user
            * get emergency contacts
            *  Trigger messaging to emergency contacts*/

            Result.success()
        } catch (exception: Exception) {
            Timber.e(exception)
            Result.failure()
        }
    }
}