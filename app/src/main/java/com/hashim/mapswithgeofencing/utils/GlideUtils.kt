/*
 * Copyright (c) 2021/  3/ 25.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions

class GlideUtils {
    companion object {

        fun hSetImage(
                hContext: Context,
                hUrl: String,
                hImageView: ImageView

        ) {
            val hRequestOptions = RequestOptions()
                    .override(200, 200)
                    .centerCrop()
                    .priority(Priority.HIGH)
            Glide.with(hContext)
                    .load(hUrl)
                    .apply(hRequestOptions)
                    .into(hImageView)
        }

    }
}