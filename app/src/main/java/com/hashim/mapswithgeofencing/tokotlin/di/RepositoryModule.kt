package com.hashim.mapswithgeofencing.tokotlin.di

import com.hashim.mapswithgeofencing.tokotlin.network.RetroService
import com.hashim.mapswithgeofencing.tokotlin.repository.remote.RemoteRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun hProvidesRemoteRepo(
            retrofitService: RetroService
    ): RemoteRepoImpl {
        return RemoteRepoImpl(
                retrofitService
        )
    }
}