/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

sealed class SettingsStateEvent {
    class OnDistanceSettingsChanged(position: Int) : SettingsStateEvent()

    class OnTempratureSettingsChanged(position: Int) : SettingsStateEvent()

    class OnLanguageSettingsChanged(position: Int) : SettingsStateEvent()

    class OnAddRemoveContacts:SettingsStateEvent()

    class OnEditMessage:SettingsStateEvent()

    class OnAddRemoveLocations:SettingsStateEvent()

    class None : SettingsStateEvent()
}