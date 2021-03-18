package com.hashim.mapswithgeofencing.tokotlin.ui.main

import androidx.lifecycle.ViewModel
import timber.log.Timber

class MainSharedViewModel : ViewModel() {



    init {
        Timber.d("View Model Init")
    }


    fun hHandleCategoriesCallBack(category: String) {
        Timber.d("Handle Callback $category")
    }
}