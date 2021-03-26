/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

data class SettingViewState(
        var hSettingsFields: SettingsFields = SettingsFields(),

        ) {
    data class SettingsFields(
            val hTemp: String? = null
    )
}