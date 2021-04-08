/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences

import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.hashim.mapswithgeofencing.db.entities.GeoFence

data class GeoFenceViewState(
        val hGeoFenceFields: GeoFenceFields = GeoFenceFields(),
) {


    data class GeoFenceFields(
            var hCurrentLocationVS: CurrentLocationGeoFenceVS? = null,
            val hMakerFenceVS: MakerFenceVS? = null,
            val hAdjustRadiusVS: AdjustRadiusVS? = null,
            val hSavedGeoFencesVS: SavedGeoFencesVS? = null,
    )

    data class CurrentLocationGeoFenceVS(
            val hCircleOptions: CircleOptions? = null,
            val hCameraZoom: Float? = null,
            val hLat: Double? = null,
            val hLng: Double? = null,
    )

    data class MakerFenceVS(
            val hCircleOptions: CircleOptions? = null,
            val hMarkerOptions: MarkerOptions? = null,
    )


    data class AdjustRadiusVS(
            val hCircleOptions: CircleOptions? = null,
    )

    data class SavedGeoFencesVS(
            val hGeofenceList: List<GeoFence>? = null,
    )
}
