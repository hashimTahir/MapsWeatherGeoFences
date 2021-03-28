/*
 * Copyright (c) 2021/  3/ 28.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.calculateroute

import androidx.lifecycle.ViewModel
import com.hashim.mapswithgeofencing.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculateRouteViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo,
        private val hSettingsPrefrences: SettingsPrefrences
) : ViewModel() {
}