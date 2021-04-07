/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences

import com.google.android.gms.maps.model.LatLng

sealed class GeoFenceStateEvent {

    class OnMapClicked(
            val hClickedLatLng: LatLng,
            val hRadius: Float,
    ) : GeoFenceStateEvent()

    class OnMapReady(
            val hRadius: Float
    ) : GeoFenceStateEvent()

    class OnRadiusChanged(
            val hProgress: Float
    ) : GeoFenceStateEvent()

    class OnDisplaySavedFences : GeoFenceStateEvent()

    class OnSaveDisplayedFences(
            val hFenceName: String
    ) : GeoFenceStateEvent()

    class None : GeoFenceStateEvent()

}