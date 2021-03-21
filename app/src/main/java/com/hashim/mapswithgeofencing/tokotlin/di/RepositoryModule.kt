/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.di

import android.content.Context
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.tokotlin.network.RetroService
import com.hashim.mapswithgeofencing.tokotlin.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.tokotlin.repository.remote.RemoteRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun hProvidesRemoteRepo(
            retrofitService: RetroService,
            key: String
    ): RemoteRepo {
        return RemoteRepoImpl(
                retrofitService,
                key
        )
    }

    @Singleton
    @Provides
    fun hProvidesApi(@ApplicationContext context: Context): String {
        return context.getString(R.string.MAPS_API_KEY)
    }
}