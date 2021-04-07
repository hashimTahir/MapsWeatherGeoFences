/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences

import com.google.android.gms.maps.model.LatLng

sealed class GeoFenceStateEvent {
    class OnMapClicked(
            val hClickedLatLng: LatLng,
    ) : GeoFenceStateEvent()

    class OnMapReady : GeoFenceStateEvent()

    class OnRadiusChanged(
            val hProgress: Int,
            val hProgressFloat: Float
    ) : GeoFenceStateEvent()

    class OnGeoFenceSaved : GeoFenceStateEvent()

    class OnDisplaySavedFences : GeoFenceStateEvent()

    class None : GeoFenceStateEvent()

}