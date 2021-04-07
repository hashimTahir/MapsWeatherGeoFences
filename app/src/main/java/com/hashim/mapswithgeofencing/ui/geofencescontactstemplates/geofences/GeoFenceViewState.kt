/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences

data class GeoFenceViewState(
        val hGeoFenceFields: GeoFenceFields = GeoFenceFields(),
)


data class GeoFenceFields(
        val hTemp: String? = null
)