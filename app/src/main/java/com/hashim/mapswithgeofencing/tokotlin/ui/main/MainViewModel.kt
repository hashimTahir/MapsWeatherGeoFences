package com.hashim.mapswithgeofencing.tokotlin.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber

class MainViewModel : ViewModel() {
    init {
        Timber.d("INit")
    }

    fun hHandleInputEvent() {
        /*Todo: find Current Location*/
    }
}