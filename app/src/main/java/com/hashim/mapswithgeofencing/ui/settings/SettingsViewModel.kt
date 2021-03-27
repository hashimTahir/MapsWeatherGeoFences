/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.hashim.mapswithgeofencing.prefrences.PrefTypes
import com.hashim.mapswithgeofencing.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.ui.events.SettingViewState
import com.hashim.mapswithgeofencing.ui.events.SettingsStateEvent
import com.hashim.mapswithgeofencing.ui.events.SettingsStateEvent.*
import com.hashim.mapswithgeofencing.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
        private val hSettingsPrefrences: SettingsPrefrences
) : ViewModel() {
    private val _hSettingsStateEvent = MutableLiveData<SettingsStateEvent>()

    private val _hSettingViewState = MutableLiveData<SettingViewState>()

    enum class Settings

    val hSettingViewState: LiveData<SettingViewState>
        get() = _hSettingViewState

    @Inject
    lateinit var hGson: Gson

    val hDataState: LiveData<DataState<SettingViewState>> = Transformations
            .switchMap(_hSettingsStateEvent) {
                it?.let { settingsStateEvent ->
                    hHandleStateEvent(settingsStateEvent)
                }
            }


    private fun hHandleStateEvent(settingsStateEvent: SettingsStateEvent):
            LiveData<DataState<SettingViewState>>? {

        when (settingsStateEvent) {
            is OnAddRemoveContacts -> {
            }
            is OnDistanceSettingsChanged -> {
                hSettingsPrefrences.hSaveSettings(
                        hPrefTypes = PrefTypes.DISTANCE_UNIT_PT,
                        value = settingsStateEvent.hDistance
                )
            }
            is OnTempratureSettingsChanged -> {
                hSettingsPrefrences.hSaveSettings(
                        hPrefTypes = PrefTypes.TEMPRATURE_UNIT_PT,
                        value = settingsStateEvent.hTemperature
                )

            }
            is OnLanguageSettingsChanged -> {
                hSettingsPrefrences.hSaveSettings(
                        hPrefTypes = PrefTypes.LANGUAGE_PT,
                        value = settingsStateEvent.hLanguage
                )
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