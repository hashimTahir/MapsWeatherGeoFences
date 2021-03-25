/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Domain.model

data class Weather(
        val lat: Double? = null,
        val lon: Double? = null,
        val description: String? = null,
        val icon: String? = null,
        val main: String? = null,
        val temp: Double? = null,
        val tempMax: Double? = null,
        val tempMin: Double? = null,
        val feelsLike: Double? = null,
        val humidity: Int? = null,
        val pressure: Int? = null,
        val speed: Double? = null,
        val sunrise: Int? = null,
        val sunset: Int? = null,
        val country: String
)
