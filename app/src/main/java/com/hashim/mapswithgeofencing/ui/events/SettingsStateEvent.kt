/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

sealed class SettingsStateEvent {
    class OnDistanceSettingsChanged(val hDistance: Int) : SettingsStateEvent()

    class OnTempratureSettingsChanged(val hTemperature: Int) : SettingsStateEvent()

    class OnLanguageSettingsChanged(val hLanguage: Int) : SettingsStateEvent()

    class OnTrackMeSettingsChanged(val hTrackMeSettingsChanged: Boolean) : SettingsStateEvent()

    class OnEmergencySettingsChanged(val hEmergencySettingsChanged: Boolean) : SettingsStateEvent()

    class OnGetAllSettings : SettingsStateEvent()

    class OnAddRemoveContacts : SettingsStateEvent()

    class OnEditMessage : SettingsStateEvent()

    class OnAddRemoveLocations : SettingsStateEvent()

    class None : SettingsStateEvent()
}