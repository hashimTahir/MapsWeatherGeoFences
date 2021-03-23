/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.di

import com.google.gson.Gson
import com.hashim.mapswithgeofencing.tokotlin.network.RetroService
import com.hashim.mapswithgeofencing.tokotlin.network.model.NearByPlacesDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun hProvidesNearByPlacesDtoMapper(): NearByPlacesDtoMapper {
        return NearByPlacesDtoMapper()
    }

    @Singleton
    @Provides
    fun hProvidesLogginInterceptor() =
            HttpLoggingInterceptor()


    @Singleton
    @Provides
    fun hProvidesHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(4, TimeUnit.SECONDS)
                .connectTimeout(4, TimeUnit.SECONDS)
                .addInterceptor(
                        loggingInterceptor.setLevel(
                                HttpLoggingInterceptor.Level.BODY
                        )
                )
                .build()
    }


    @Singleton
    @Provides
    fun hProvidesRecipeRetrofitService(
            httpClient: OkHttpClient,
            gson: Gson
    ): RetroService {
        return Retrofit.Builder()
                .baseUrl("https://your.api.url/")
                .addConverterFactory(
                        GsonConverterFactory.create(
                                gson
                        )
                )
                .client(httpClient)
                .build()
                .create(RetroService::class.java)

    }
}