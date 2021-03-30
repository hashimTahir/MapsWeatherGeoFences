/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

import android.location.Location

sealed class CalculateRouteStateEvent {
    class OnFindDirections(
            val hStartLocation: Location? = null,
            val hDestinationLocation: Location,
            val hMode: String,
    ) : CalculateRouteStateEvent()

    class OnSwitchPlaces() : CalculateRouteStateEvent()

    class OnMapReady() : CalculateRouteStateEvent()

    class None : CalculateRouteStateEvent()

}