/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.hashim.mapswithgeofencing.R


object AnimHelper {
    fun hAnimateBottomUp(hContext: Context?, view: View) {
        val bottomUp = AnimationUtils.loadAnimation(hContext,
                R.anim.bottom_up)
        view.startAnimation(bottomUp)
        view.visibility = View.VISIBLE
    }

    fun hAnimateBottomDown(context: Context?, view: View) {
        val hBottomDown = AnimationUtils.loadAnimation(context,
                R.anim.bottom_down)
        view.startAnimation(hBottomDown)
        view.visibility = View.INVISIBLE
    }
}
