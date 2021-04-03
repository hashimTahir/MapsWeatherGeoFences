/*
 * Copyright (c) 2021/  3/ 31.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.ui.main.Category

fun hLatLngToLocation(
        hLat: Double,
        hLng: Double,
) = Location(LocationManager.GPS_PROVIDER).apply {
    latitude = hLat
    longitude = hLng
}


fun hCreateMarkerOptions(
        hContext: Context,
        hLat: Double,
        hLng: Double,
        hType: MarkerUtils.MarkerType? = null,
        hCategory: Category? = null,
): MarkerOptions {

    val hLatLng = LatLng(hLat, hLng)

    val hSmallMarkerBitmap = MarkerUtils.hGetCustomMapMarker(
            hContext = hContext,
            hCategory = hCategory,
            hType = hType,
    )


    val hMarkerOptions =
            if (hCategory != null) {
                MarkerOptions().position(hLatLng)
                        .title(hCategory.name)
                        .snippet(hCategory.name)
                        .icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap))
            } else {
                MarkerOptions().position(hLatLng)
                        .title(hContext.getString(R.string.you_are_here))
                        .icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap))
            }


    return hMarkerOptions
}

fun hGetCategroyList(context: Context): MutableList<Category> {
    val hCategoryList = mutableListOf<Category>()
    val hNameList = context.resources.getStringArray(R.array.place_strings).asList()
    val hIconsTypedArray = context.resources.obtainTypedArray(R.array.places_icons_array)

    hNameList.forEachIndexed { index, name ->
        hCategoryList.add(
                Category(
                        name = name,
                        icon = hIconsTypedArray.getDrawable(index)!!
                )
        )
    }
    return hCategoryList
}