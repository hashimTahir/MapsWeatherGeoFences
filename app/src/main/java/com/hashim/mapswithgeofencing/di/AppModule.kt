/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hashim.mapswithgeofencing.BaseApplication
import com.hashim.mapswithgeofencing.prefrences.SettingsPrefrences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    fun hProvidesApplication(
            @ApplicationContext app: Context
    ): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun hProvidesGson(): Gson {
        return GsonBuilder()
                .setPrettyPrinting()
                .setLenient()
                .create()
    }

    @Singleton
    @Provides
    fun hProvidesSettingPrefrences(@ApplicationContext context: Context): SettingsPrefrences {
        return SettingsPrefrences(context)
    }

}