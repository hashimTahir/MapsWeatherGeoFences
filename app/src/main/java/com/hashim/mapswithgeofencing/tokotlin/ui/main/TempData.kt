/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.ui.main

import android.content.Context
import com.hashim.mapswithgeofencing.R
import timber.log.Timber

class TempData {
    companion object {
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
    }
}