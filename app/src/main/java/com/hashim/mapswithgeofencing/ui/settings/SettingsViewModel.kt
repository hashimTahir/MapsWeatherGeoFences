/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.hashim.mapswithgeofencing.others.prefrences.PrefTypes.*
import com.hashim.mapswithgeofencing.others.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.ui.events.SettingViewState
import com.hashim.mapswithgeofencing.ui.events.SettingViewState.DefaultSavedVS
import com.hashim.mapswithgeofencing.ui.events.SettingViewState.SettingsFields
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
                        hPrefTypes = DISTANCE_UNIT_PT,
                        value = settingsStateEvent.hDistance
                )
            }
            is OnTempratureSettingsChanged -> {
                hSettingsPrefrences.hSaveSettings(
                        hPrefTypes = TEMPRATURE_UNIT_PT,
                        value = settingsStateEvent.hTemperature
                )

            }
            is OnLanguageSettingsChanged -> {
                hSettingsPrefrences.hSaveSettings(
                        hPrefTypes = LANGUAGE_PT,
                        value = settingsStateEvent.hLanguage
                )
            }
            is OnEditMessage -> {
            }
            is OnEmergencySettingsChanged -> {
                hSettingsPrefrences.hSaveSettings(
                        hPrefTypes = EMERGENCY_PT,
                        value = settingsStateEvent.hEmergencySettingsChanged
                )
            }
            is OnTrackMeSettingsChanged -> {
                hSettingsPrefrences.hSaveSettings(
                        hPrefTypes = TRACKING_PT,
                        value = settingsStateEvent.hTrackMeSettingsChanged
                )
            }
            is OnAddRemoveLocations -> {
            }
            is OnGetAllSettings -> {
                hSubmitSavedOrDefaultSettings()
            }
            is None -> {
            }
        }
        return null
    }

    private fun hSubmitSavedOrDefaultSettings() {
        val hLanguage = hSettingsPrefrences.hGetSettings(LANGUAGE_PT) as Int
        val hDistance = hSettingsPrefrences.hGetSettings(DISTANCE_UNIT_PT) as Int
        val hTemprature = hSettingsPrefrences.hGetSettings(TEMPRATURE_UNIT_PT) as Int
        val hEmergency = hSettingsPrefrences.hGetSettings(EMERGENCY_PT) as Boolean
        val hTracking = hSettingsPrefrences.hGetSettings(TRACKING_PT) as Boolean

        Timber.d("Language $hLanguage , Distance $hDistance , Emergency $hEmergency, Tracking $hTracking , Temperature $hTemprature")

        _hSettingViewState.value = SettingViewState(
                hSettingsFields = SettingsFields(
                        hDefaultSavedVS = DefaultSavedVS(
                                hLanguage = hLanguage,
                                hDistance = hDistance,
                                hTemprature = hTemprature,
                                hEmergency = hEmergency,
                                hTracking = hTracking,
                        )
                )
        )
    }


    fun hSetStateEvent(settingsStateEvent: SettingsStateEvent) {
        _hSettingsStateEvent.value = settingsStateEvent
    }

    fun hSetDefaultSavedSettings(defaultSavedVS: DefaultSavedVS) {
        val hUpdate = hGetCurrentViewStateOrNew()
        hUpdate.hSettingsFields.hDefaultSavedVS = defaultSavedVS
        _hSettingViewState.value = hUpdate
    }

    private fun hGetCurrentViewStateOrNew(): SettingViewState {
        return hSettingViewState.value ?: SettingViewState()
    }


}