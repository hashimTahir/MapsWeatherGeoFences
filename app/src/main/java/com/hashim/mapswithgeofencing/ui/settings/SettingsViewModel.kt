/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hashim.mapswithgeofencing.SettingsPrefrences
import com.hashim.mapswithgeofencing.ui.events.MainViewState
import com.hashim.mapswithgeofencing.ui.events.SettingViewState
import com.hashim.mapswithgeofencing.ui.events.SettingsStateEvent
import com.hashim.mapswithgeofencing.ui.events.SettingsStateEvent.*
import com.hashim.mapswithgeofencing.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
        private val hSettingsPrefrences: SettingsPrefrences
) : ViewModel() {
    private val _hSettingsStateEvent = MutableLiveData<SettingsStateEvent>()

    private val _hSettingViewState = MutableLiveData<SettingViewState>()

    enum class Settings
    public val hSettingViewState: LiveData<SettingViewState>
        get() = _hSettingViewState


    val hDataState: LiveData<DataState<SettingViewState>> = Transformations
            .switchMap(_hSettingsStateEvent) {
                it?.let { settingsStateEvent ->
                    hHandleStateEvent(settingsStateEvent)
                }
            }


    init {

    }

    private fun hHandleStateEvent(settingsStateEvent: SettingsStateEvent):
            LiveData<DataState<SettingViewState>>? {
        when (settingsStateEvent) {
            is OnAddRemoveContacts -> {
            }
            is OnDistanceSettingsChanged -> {
//                hSettingsPrefrences.hSaveDistanceUnit(position)

            }
            is OnTempratureSettingsChanged -> {
//                hSettingsPrefrences.hSaveTempUnit(position);

            }
            is OnLanguageSettingsChanged -> {
//                hSettingsPrefrences.hSaveLanguage(position);

            }
            is OnEditMessage -> {
            }
            is OnAddRemoveLocations -> {
            }
            is None -> {
            }
        }
        return null
    }


    fun hSetStateEvent(mainStateEvent: SettingsStateEvent) {
        _hSettingsStateEvent.value = mainStateEvent
    }
//
//    fun hSetMainData() {
//        var hUpdate = hGetCurrentViewStateOrNew()
//        hUpdate.hMainFields = it
//        _hSettingsStateEvent.value = hUpdate
//    }

    fun hGetCurrentViewStateOrNew(): SettingViewState {
        return hSettingViewState.value ?: SettingViewState()
    }

//    fun hSetMarkerData(it: MainViewState.NearByFields) {
//        var hUpdate = hGetCurrentViewStateOrNew()
//        hUpdate.hNearbyFields = it
//        _hMainViewState.value = hUpdate
//    }

//    fun hSetViewStateData(data: Any) {
//        when (data) {
//            is SettingViewState.SettingsFields.
//        }
//
//    }

}