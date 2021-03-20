/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.network

import retrofit2.http.GET
import retrofit2.http.Url

interface RetroService {
    @GET
    fun hGetWeather(@Url url: String?)


}