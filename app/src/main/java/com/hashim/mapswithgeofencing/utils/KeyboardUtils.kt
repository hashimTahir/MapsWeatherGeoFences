/*
 * Copyright (c) 2021/  3/ 27.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager


class KeyboardUtils(private val hDecorView: View, private val hContentView: View) {
    fun enable() {
        hDecorView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    fun disable() {
        hDecorView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    //a small helper to allow showing the editText focus
    var onGlobalLayoutListener = OnGlobalLayoutListener {
        val r = Rect()
        //r will be populated with the coordinates of your view that area still visible.
        hDecorView.getWindowVisibleDisplayFrame(r)

        //get screen height and calculate the difference with the useable area from the r
        val height = hDecorView.context.resources.displayMetrics.heightPixels
        val diff = height - r.bottom

        //if it could be a keyboard add the padding to the view
        if (diff != 0) {
            // if the use-able screen height differs from the total screen height we assume that it shows a keyboard now
            //check if the padding is 0 (if yes set the padding for the keyboard)
            if (hContentView.paddingBottom != diff) {
                //set the padding of the contentView for the keyboard
                hContentView.setPadding(0, 0, 0, diff)
            }
        } else {
            //check if the padding is != 0 (if yes reset the padding)
            if (hContentView.paddingBottom != 0) {
                //reset the padding of the contentView
                hContentView.setPadding(0, 0, 0, 0)
            }
        }
    }

    companion object {
        fun hideKeyboard(act: Activity?) {
            if (act != null && act.currentFocus != null) {
                val inputMethodManager = act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(act.currentFocus!!.windowToken, 0)
            }
        }
    }

    init {

        //only required on newer android versions. it was working on API level 19
        hDecorView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }
}

