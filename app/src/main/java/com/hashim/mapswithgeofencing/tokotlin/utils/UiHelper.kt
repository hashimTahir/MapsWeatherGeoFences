/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.hashim.mapswithgeofencing.R

class UiHelper {
    companion object {
        fun hShowSnackBar(
                view: View,
                message: String,
                onTakeAction: () -> Unit
        ) {
            Snackbar.make(
                    view,
                    message,
                    Snackbar.LENGTH_INDEFINITE
            )
                    .setAction(R.string.ok) {
                        onTakeAction()
                    }
                    .show()
        }
    }
}