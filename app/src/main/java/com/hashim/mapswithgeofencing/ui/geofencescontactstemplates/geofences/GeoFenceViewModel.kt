/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences

import android.content.Context
import androidx.lifecycle.*
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hashim.mapswithgeofencing.db.entities.GeoFence
import com.hashim.mapswithgeofencing.others.prefrences.HlatLng
import com.hashim.mapswithgeofencing.others.prefrences.PrefTypes
import com.hashim.mapswithgeofencing.others.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.repository.local.LocalRepo
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences.GeoFenceStateEvent.*
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences.GeoFenceViewState.*
import com.hashim.mapswithgeofencing.utils.DataState
import com.hashim.mapswithgeofencing.utils.geofencing.GeoFenceUtils
import com.hashim.mapswithgeofencing.utils.geofencing.GeoMapdata
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class GeoFenceViewModel @Inject constructor(
        @ApplicationContext val hContext: Context,
        val hSettingsPrefrences: SettingsPrefrences,
        val hLocalRepo: LocalRepo,
) : ViewModel() {
    private val _hGeoFenceViewState = MutableLiveData<GeoFenceViewState>()
    private val _hGeoFenceStatEvent = MutableLiveData<GeoFenceStateEvent>()
    private val hGeoFenceUtils = GeoFenceUtils(hContext)
    private var hLastCreatedCircleOptions: CircleOptions? = null

    val hGeoFenceViewState: LiveData<GeoFenceViewState>
        get() = _hGeoFenceViewState

    val hDataState: LiveData<DataState<GeoFenceViewState>> =
            Transformations.switchMap(_hGeoFenceStatEvent) {
                hSetStateEvent(it)
            }


    fun hSetStateEvent(geoFenceStateEvent: GeoFenceStateEvent):
            LiveData<DataState<GeoFenceViewState>>? {
        when (geoFenceStateEvent) {
            is OnRadiusChanged -> {
                hAdjustTheRadius(geoFenceStateEvent.hProgress)
            }
            is OnMapClicked -> {
                Timber.d("OnMap Clicked ${geoFenceStateEvent.hClickedLatLng}")
                hCreateMakerWithCircle(geoFenceStateEvent.hClickedLatLng)
            }

            is OnSaveDisplayedFences -> {
                hSaveGeoFence(geoFenceStateEvent.hFenceName, hLastCreatedCircleOptions)
            }
            is OnMapReady -> {
                hPrepareMap(hRadius = geoFenceStateEvent.hRadius)
            }
            is OnDisplaySavedFences -> {
                hGetSavedFences()
            }
            is None -> {
            }
        }
        return null
    }

    private fun hGetSavedFences() {
        viewModelScope.launch {
            _hGeoFenceViewState.value = GeoFenceViewState(
                    hGeoFenceFields = GeoFenceFields(
                            hSavedGeoFencesVS = SavedGeoFencesVS(
                                    hGeofenceList = hLocalRepo.hGetAllGeoFences()
                            )
                    )
            )
        }
    }

    private fun hSaveGeoFence(hFenceName: String, hLastCreatedCircleOptions: CircleOptions?) {
        val hMap = mutableMapOf<String, GeoMapdata>().apply {
            put(
                    key = hFenceName,
                    value = GeoMapdata(
                            hLat = hLastCreatedCircleOptions?.center?.latitude,
                            hLng = hLastCreatedCircleOptions?.center?.longitude,
                            hRadius = hLastCreatedCircleOptions?.radius?.toFloat()
                    )
            )
        }
        hGeoFenceUtils.hCreateAllGeoFences(hMap as HashMap<String, GeoMapdata>)
        hGeoFenceUtils.hStartService()

        viewModelScope.launch {
            val htest = hLocalRepo.hInsertGeoFence(
                    GeoFence(
                            hFenceName = hFenceName,
                            hLng = hLastCreatedCircleOptions?.center?.longitude!!,
                            hLat = hLastCreatedCircleOptions.center?.latitude!!,
                    )
            )
            Timber.d("Data Inserted $htest")
        }

    }

    private fun hAdjustTheRadius(hProgressFloat: Float) {
        hLastCreatedCircleOptions?.apply {
            radius((hProgressFloat * 1000).toDouble())
        }
        _hGeoFenceViewState.value = GeoFenceViewState(
                hGeoFenceFields = GeoFenceFields(
                        hAdjustRadiusVS = AdjustRadiusVS(
                                hCircleOptions = hLastCreatedCircleOptions,
                        ),
                )
        )
    }

    private fun hCreateMakerWithCircle(hClickedLatLng: LatLng) {
        val hMarkerOptions = MarkerOptions()
                .position(hClickedLatLng)


        hLastCreatedCircleOptions = hGeoFenceUtils.hShowVisibleGeoFence(
                hMarkerOptions = hMarkerOptions,
                hRadius = 1000F
        )
        _hGeoFenceViewState.value = GeoFenceViewState(
                hGeoFenceFields = GeoFenceFields(
                        hMakerFenceVS = MakerFenceVS(
                                hCircleOptions = hLastCreatedCircleOptions,
                                hMarkerOptions = hMarkerOptions,
                        ),
                        hAdjustRadiusVS = AdjustRadiusVS(
                                hCircleOptions = hLastCreatedCircleOptions,
                        )
                )

        )
    }

    private fun hPrepareMap(
            hRadius: Float,
            hMarkerOptions: MarkerOptions? = null
    ) {
        val hCurrentHlatLng: HlatLng = hSettingsPrefrences.hGetSettings(PrefTypes.CURRENT_LAT_LNG_PT) as HlatLng


        hLastCreatedCircleOptions = hGeoFenceUtils.hShowVisibleGeoFence(
                hRadius = hRadius * 1000,
                hMarkerOptions = hMarkerOptions,
                hLatLng = LatLng(
                        hCurrentHlatLng.hLat!!,
                        hCurrentHlatLng.hLng!!,
                )
        )
        _hGeoFenceViewState.value = GeoFenceViewState(
                hGeoFenceFields = GeoFenceFields(
                        hCurrentLocationVS = CurrentLocationGeoFenceVS(
                                hCircleOptions = hLastCreatedCircleOptions,
                                hCameraZoom = 12.0F,
                                hLat = hCurrentHlatLng.hLat,
                                hLng = hCurrentHlatLng.hLng,
                        ),
                        hAdjustRadiusVS = AdjustRadiusVS(
                                hCircleOptions = hLastCreatedCircleOptions,
                        ),
                )

        )
    }

}