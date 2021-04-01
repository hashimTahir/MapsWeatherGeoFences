/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

import androidx.lifecycle.ViewModel
import com.hashim.mapswithgeofencing.repository.local.LocalRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DisplayContactsViewModel @Inject constructor(
        private val hLocationRepo: LocalRepo
) : ViewModel() {
}