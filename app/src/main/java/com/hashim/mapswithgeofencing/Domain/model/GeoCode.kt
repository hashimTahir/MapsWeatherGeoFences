/*
 * Copyright (c) 2021/  4/ 5.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Domain.model

import com.hashim.mapswithgeofencing.network.response.geocode.Location

data class GeoCode(
        val status: String? = null,
        val formattedAddress: String? = null,
        val location: Location? = null,
        val placeId: String? = null,
)
