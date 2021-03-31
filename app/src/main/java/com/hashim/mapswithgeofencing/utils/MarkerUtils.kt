/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.ui.main.Category


object MarkerUtils {
    fun hGetCustomMapMarker(
            hContext: Context,
            hCategory: Category? = null,
            hType: MarkerType? = null,
            hWidth: Int = 100,
            hHeight: Int = 100,
    ): Bitmap {
        val hBitmapDrawable = if (hCategory != null) {
            hCategory.icon
        } else {
            hGetResourse(hContext, hType)
        }
        val hBitmap: Bitmap
        if (hBitmapDrawable is BitmapDrawable) {
            hBitmap = hBitmapDrawable.bitmap
        } else {
            hBitmap = hBitmapDrawable?.toBitmap()!!
        }
        return Bitmap.createScaledBitmap(hBitmap, hWidth, hHeight, false)
    }

    private fun hGetResourse(hContext: Context, hType: MarkerType?): Drawable? {
        return when (hType) {
            MarkerType.CURRENT -> {
                ResourcesCompat.getDrawable(
                        hContext.resources,
                        R.drawable.current_marker,
                        hContext.theme
                )
            }
            MarkerType.DESTINATION -> {
                ResourcesCompat.getDrawable(
                        hContext.resources,
                        R.drawable.destination_marker,
                        hContext.theme
                )
            }
            MarkerType.CURRENT_LOCATION -> {
                ResourcesCompat.getDrawable(
                        hContext.resources,
                        R.drawable.current_location,
                        hContext.theme
                )
            }
            else -> {
                ResourcesCompat.getDrawable(
                        hContext.resources,
                        R.drawable.current_location,
                        hContext.theme
                )
            }
        }
    }

    enum class MarkerType {
        CURRENT,
        DESTINATION,
        CURRENT_LOCATION,
    }
}
