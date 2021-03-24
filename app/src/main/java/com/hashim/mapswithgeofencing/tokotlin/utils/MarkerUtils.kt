/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.tokotlin.ui.main.Category


object MarkerUtils {
    fun hGetCustomMapMarker(
            context: Context,
            category: Category?,
            width: Int = 100,
            height: Int = 100,
    ): Bitmap {
        val hBitmapDrawable = if (category != null) {
            category.icon
        } else {
            ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.current_marker,
                    context.theme
            )
        }
        val hBitmap: Bitmap
        if (hBitmapDrawable is BitmapDrawable) {
            hBitmap = hBitmapDrawable.bitmap
        } else {
            hBitmap = hBitmapDrawable?.toBitmap()!!
        }
        return Bitmap.createScaledBitmap(hBitmap, width, height, false)
    }


//            67 -> R.drawable.dog_icon
//            82 -> R.drawable.current_marker
//            83 -> R.drawable.destination_marker
//            else -> R.drawable.current_location
}
