/*
 * Copyright (c) 2021/  4/ 6.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils.geofencing

data class GeoData(
        val errorCode: Int? = null,
        val geofenceTransition: Int? = null,
        val latitude: Double? = null,
        val longitude: Double? = null,
        val bearing: Float? = null,
        val speed: Float? = null,
        val toList: List<String>? = null,
)