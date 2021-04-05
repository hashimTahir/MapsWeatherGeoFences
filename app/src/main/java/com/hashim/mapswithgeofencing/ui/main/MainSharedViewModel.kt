/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.main

import androidx.lifecycle.ViewModel
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainSharedViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo
) : ViewModel()