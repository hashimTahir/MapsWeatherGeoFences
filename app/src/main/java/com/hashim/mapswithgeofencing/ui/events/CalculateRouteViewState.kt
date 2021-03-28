/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalculateRouteViewState(
        val hCalculateRouteFields: CalculateRouteFields = CalculateRouteFields(),
) : Parcelable {

    @Parcelize
    data class CalculateRouteFields(
            var hTemp: String? = null
    ) : Parcelable

}

