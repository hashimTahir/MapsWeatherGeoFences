/*
 * Copyright (c) 2021/  4/ 4.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.main.fragments

import android.content.Context
import android.location.Location
import androidx.lifecycle.*
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hashim.mapswithgeofencing.Domain.model.NearByPlaces
import com.hashim.mapswithgeofencing.others.prefrences.HlatLng
import com.hashim.mapswithgeofencing.others.prefrences.PrefTypes.CURRENT_LAT_LNG_PT
import com.hashim.mapswithgeofencing.others.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.ui.events.MainStateEvent
import com.hashim.mapswithgeofencing.ui.events.MainStateEvent.*
import com.hashim.mapswithgeofencing.ui.events.MainViewState
import com.hashim.mapswithgeofencing.ui.events.MainViewState.*
import com.hashim.mapswithgeofencing.ui.main.fragments.adapter.Category
import com.hashim.mapswithgeofencing.utils.DataState
import com.hashim.mapswithgeofencing.utils.MarkerUtils
import com.hashim.mapswithgeofencing.utils.hCreateMarkerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo,
        @ApplicationContext private val hContext: Context,
) : ViewModel() {
    private val _hMainStateEvent = MutableLiveData<MainStateEvent>()
    private var hCurrentLocation: Location? = null

    @Inject
    lateinit var hSettingsPrefrences: SettingsPrefrences

    private val _hMainViewState = MutableLiveData<MainViewState>()
    val hMainViewState: LiveData<MainViewState>
        get() = _hMainViewState


    val hDataState: LiveData<DataState<MainViewState>> = Transformations
            .switchMap(_hMainStateEvent) {
                it?.let { mainStateEvent ->
                    hHandleStateEvent(mainStateEvent)
                }
            }


    private fun hHandleStateEvent(
            stateEvent: MainStateEvent
    ): LiveData<DataState<MainViewState>>? {
        when (stateEvent) {
            is OnCurrentLocationFound -> {
                hCurrentLocation = stateEvent.location
                hSubmitCurrentLocationData(hCurrentLocation)
                hSettingsPrefrences.hSaveSettings(
                        hPrefTypes = CURRENT_LAT_LNG_PT,
                        value = HlatLng(
                                hLng = hCurrentLocation?.longitude,
                                hLat = hCurrentLocation?.latitude,
                        )
                )
                hSubmitCurrentLocationData(hCurrentLocation)
            }
            is OnMapReady -> {
            }
            is OnCategorySelected -> {
                viewModelScope.launch {
                    /*Todo: get radius from app settings*/
                    hCurrentLocation?.let {
                        var hFindNearybyPlaces = hRemoteRepo.hFindNearybyPlaces(
                                category = stateEvent.category,
                                location = it
                        )
                        hSubmitNearByMarkerList(hFindNearybyPlaces, stateEvent.category)
                    }
                }
            }
            is None -> {
            }
            is OnMarkerClicked -> {
                hChangeBottomView(stateEvent.marker)
            }
            else -> {
            }
        }
        return null

    }

    private fun hChangeBottomView(marker: Marker) {
        Timber.d("hChangeBottomView $marker")


        _hMainViewState.value = MainViewState(
                hMainFields = MainFields(
                        hOnMarkerClickVS = OnMarkerClickVS("test")
                )
        )
    }

    private fun hSubmitNearByMarkerList(
            nearyByPlacesList: List<NearByPlaces>,
            category: Category
    ) {
        val hMarkerList = mutableListOf<MarkerOptions>()
        nearyByPlacesList.forEach { place ->
            hMarkerList.add(
                    hCreateMarkerOptions(
                            hContext = hContext,
                            hLat = place.lat!!,
                            hLng = place.lng!!,
                            hCategory = category,
                    )
            )
        }
        _hMainViewState.value = MainViewState(
                hMainFields = MainFields(
                        hNearByPlacesVS = NearByPlacesVS(
                                hMarkerList = hMarkerList
                        )
                )
        )
    }


    private fun hSubmitCurrentLocationData(location: Location?) {
        location?.let { location ->

            _hMainViewState.value = MainViewState(
                    hMainFields = MainFields(
                            hCurrentLocationVS = CurrentLocationVS(
                                    currentLocation = location,
                                    currentMarkerOptions = hCreateMarkerOptions(
                                            hContext = hContext,
                                            hLat = location.latitude,
                                            hLng = location.longitude,
                                            hType = MarkerUtils.MarkerType.CURRENT,
                                    ),
                                    cameraZoom = 12.0F,
                            ),
                    )
            )
        }
    }


    fun hSetStateEvent(mainStateEvent: MainStateEvent) {
        _hMainStateEvent.value = mainStateEvent
    }

    fun hSetCurrentLocationData(currentLocationVS: CurrentLocationVS) {
        val hUpdate = hGetCurrentViewStateOrNew()
        hUpdate.hMainFields.hCurrentLocationVS = currentLocationVS
        _hMainViewState.value = hUpdate
    }

    private fun hGetCurrentViewStateOrNew(): MainViewState {
        return hMainViewState.value ?: MainViewState()
    }

    fun hSetNearByPlacesData(nearByPlacesVS: NearByPlacesVS) {
        val hUpdate = hGetCurrentViewStateOrNew()
        hUpdate.hMainFields.hNearByPlacesVS = nearByPlacesVS
        _hMainViewState.value = hUpdate
    }

    fun hSetMarkerClickData(onMarkerClickVS: OnMarkerClickVS) {
        val hUpdate = hGetCurrentViewStateOrNew()
        hUpdate.hMainFields.hOnMarkerClickVS = onMarkerClickVS
        _hMainViewState.value = hUpdate
    }
}