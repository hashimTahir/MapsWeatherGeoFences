/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.di

import android.content.Context
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.network.RetroService
import com.hashim.mapswithgeofencing.network.model.NearByPlacesDtoMapper
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepoImpl
import com.hashim.mapswithgeofencing.utils.Constants.Companion.H_MAPS_KEYTYPE
import com.hashim.mapswithgeofencing.utils.Constants.Companion.H_WEATHER_KEYTYPE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun hProvidesRemoteRepo(
            retrofitService: RetroService,
            @Named(H_MAPS_KEYTYPE) mapsKey: String,
            @Named(H_WEATHER_KEYTYPE) weatherKey: String,
            mapper: NearByPlacesDtoMapper,
    ): RemoteRepo {
        return RemoteRepoImpl(
                hRetroService = retrofitService,
                hMapper = mapper,
                hMapsKey = mapsKey,
                hWeatherKey = weatherKey
        )
    }

    @Singleton
    @Provides
    @Named(H_MAPS_KEYTYPE)
    fun hProvidesMapsApiKey(@ApplicationContext context: Context): String {
        return context.getString(R.string.MAPS_API_KEY)
    }

    @Singleton
    @Provides
    @Named(H_WEATHER_KEYTYPE)
    fun hProvidesWeatherApiKey(@ApplicationContext context: Context): String {
        return context.getString(R.string.WEATHER_API_KEY)
    }

}