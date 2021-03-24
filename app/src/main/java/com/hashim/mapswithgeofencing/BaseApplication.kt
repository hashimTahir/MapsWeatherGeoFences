/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing

import android.app.Application
import com.hashim.mapswithgeofencing.utils.Constants
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        hInitTimber()
    }

    private fun hInitTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : DebugTree() {
                override fun log(
                        priority: Int,
                        tag: String?,
                        message: String,
                        t: Throwable?) {
                    super.log(
                            priority,
                            String.format(Constants.hTag, tag),
                            message,
                            t
                    )
                }
            })
        }
    }
}