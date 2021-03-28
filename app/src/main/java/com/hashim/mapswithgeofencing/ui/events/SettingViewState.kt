/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

data class SettingViewState(
        val hSettingsFields: SettingsFields = SettingsFields(),

        ) {
    data class SettingsFields(
            var hDefaultSavedVS: DefaultSavedVS? = null
    )


    data class DefaultSavedVS(
            val hLanguage: Int? = null,
            val hDistance: Int? = null,
            val hTemprature: Int? = null,
            val hEmergency: Boolean? = null,
            val hTracking: Boolean? = null,
    )
}