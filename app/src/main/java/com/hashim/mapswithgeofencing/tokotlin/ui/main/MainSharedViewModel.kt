/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.ui.main

import androidx.lifecycle.ViewModel
import timber.log.Timber

class MainSharedViewModel : ViewModel() {



    init {
        Timber.d("View Model Init")
    }


    fun hHandleCategoriesCallBack(category: Category) {
        Timber.d("Handle Callback $category")
    }
}