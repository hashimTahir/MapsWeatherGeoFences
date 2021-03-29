/*
 * Copyright (c) 2021/  3/ 28.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Domain.model

import com.hashim.mapswithgeofencing.network.response.directions.*

data class Directions(
        val bounds: Bounds? = null,
        val overviewPolyline: OverviewPolyline? = null,
        val distance: Distance? = null,
        val duration: Duration? = null,
        val endAddress: String? = null,
        val endLocation: EndLocation? = null,
        val startAddress: String? = null,
        val startLocation: StartLocation? = null,
        val steps: List<Step>? = null,
        val status: String? = null,
)