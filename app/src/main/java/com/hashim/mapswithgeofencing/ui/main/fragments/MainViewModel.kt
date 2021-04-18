/*
 * Copyright (c) 2021/  4/ 4.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.main.fragments

import PlaceUtils
import android.content.Context
import android.location.Location
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hashim.mapswithgeofencing.Domain.model.Directions
import com.hashim.mapswithgeofencing.Domain.model.GeoCode
import com.hashim.mapswithgeofencing.Domain.model.NearByPlaces
import com.hashim.mapswithgeofencing.Domain.model.PlaceSuggestions
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.others.prefrences.HlatLng
import com.hashim.mapswithgeofencing.others.prefrences.PrefTypes
import com.hashim.mapswithgeofencing.others.prefrences.PrefTypes.CURRENT_LAT_LNG_PT
import com.hashim.mapswithgeofencing.others.prefrences.PrefTypes.RADIUS_UNIT_PT
import com.hashim.mapswithgeofencing.others.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.ui.calculateroute.DirectionsMode.DRIVING
import com.hashim.mapswithgeofencing.ui.events.MainStateEvent
import com.hashim.mapswithgeofencing.ui.events.MainStateEvent.*
import com.hashim.mapswithgeofencing.ui.events.MainViewState
import com.hashim.mapswithgeofencing.ui.events.MainViewState.*
import com.hashim.mapswithgeofencing.ui.main.fragments.adapter.Category
import com.hashim.mapswithgeofencing.utils.*
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
    private var hLastSuggestionsList: List<PlaceSuggestions>? = null

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
        Timber.d("Handle State Event $stateEvent")
        when (stateEvent) {
            is OnCurrentLocationFound -> {
                Timber.d("OnCurrentLocationFound")
                hCurrentLocation = stateEvent.location
                hSubmitCurrentLocationData(hCurrentLocation)
                hSettingsPrefrences.hSaveSettings(
                        hPrefTypes = CURRENT_LAT_LNG_PT,
                        value = HlatLng(
                                hLng = hCurrentLocation?.longitude,
                                hLat = hCurrentLocation?.latitude,
                        )
                )
                return hSubmitCurrentLocationData(hCurrentLocation)
            }
            is OnMapReady -> {
            }
            is OnCategorySelected -> {

                val hResult = MediatorLiveData<DataState<MainViewState>>()
                val hResponse = MutableLiveData<List<NearByPlaces>>()
                hResult.value = DataState.hLoading(true)


                viewModelScope.launch {
                    hCurrentLocation?.let {
                        hResponse.value = hRemoteRepo.hFindNearybyPlaces(
                                category = stateEvent.category,
                                location = it,
                                radius = hSettingsPrefrences
                                        .hGetSettings(RADIUS_UNIT_PT) as Int?
                                        ?: Constants.H_DEFAULT_RADIUS
                        )
                    }
                    hResult.addSource(hResponse) {
                        hResult.removeSource(hResponse)
                        hSubmitNearByMarkerList(it, stateEvent.category, hResult)

                    }
                }
                return hResult
            }
            is OnFindAutoCompleteSuggestions -> {
                val hResult = MediatorLiveData<DataState<MainViewState>>()
                val hResponse = MutableLiveData<List<PlaceSuggestions>>()
                hResult.value = DataState.hLoading(true)


                viewModelScope.launch {

                    hResponse.value = hRemoteRepo.hGetPlacesAutoComplete(
                            query = stateEvent.suggestion,
                            radius = hSettingsPrefrences
                                    .hGetSettings(RADIUS_UNIT_PT) as Int?
                                    ?: Constants.H_DEFAULT_RADIUS
                    )

                    hResult.addSource(hResponse) {
                        hResult.removeSource(hResponse)
                        hLastSuggestionsList = it
                        hSubmitPlaceSuggetstions(hResult)
                    }
                }
                return hResult
            }
            is OnSuggestionSelected -> {
                hSetPlaceSelected(stateEvent.postion)
            }
            is None -> {
            }
            is OnMarkerClicked -> {
                return hChangeBottomView(stateEvent.marker)
            }
            else -> {
            }
        }
        return null

    }

    private fun hSetPlaceSelected(postion: Int) {

        hLastSuggestionsList?.let {
            val value = it.get(postion)
            val hPlaceUtils = PlaceUtils(hContext)
            hPlaceUtils.hFetchAPlaceById(value.placeId) { hPlace, errorMessage ->
                Timber.d("Place Found ${hPlace.toString()}")
                if (hPlace != null) {
                    hPlace.latLng?.let { latLng ->
                        hFindDirections(latLng)
                    }
                }
            }
        }
    }


    fun hFindDirections(hLatLng: LatLng) {
        viewModelScope.launch {
            val hCurrentLocation: Location

            val hCurrentHlatLng: HlatLng = hSettingsPrefrences
                    .hGetSettings(CURRENT_LAT_LNG_PT) as HlatLng
            hCurrentLocation = hLatLngToLocation(
                    hLat = hCurrentHlatLng.hLat!!,
                    hLng = hCurrentHlatLng.hLng!!,
            )


            val hDirections = hRemoteRepo.hGetDirections(
                    startLocation = hCurrentLocation,
                    endLocation = hLatLngToLocation(
                            hLat = hLatLng.latitude,
                            hLng = hLatLng.longitude,
                    ),
                    mode = DRIVING
            )
            hDrawPath(hDirections)
        }
    }

    private fun hDrawPath(hDirections: Directions) {
        Timber.d("Draw Path")
        val hDistance: String

        val hUnit = hSettingsPrefrences.hGetSettings(PrefTypes.DISTANCE_UNIT_PT) as Int?
        hDistance = when (hUnit) {
            1 -> {
                hDirections.distance?.value?.div(1600).toString()
            }
            else -> {
                hDirections.distance?.text.toString()
            }
        }

        _hMainViewState.value = MainViewState(
                hMainFields = MainFields(
                        hPlaceSelectedVs = PlaceSelectedVS(
                                hDistance = hDirections.distance,
                                hOverviewPolyline = hDirections.overviewPolyline,
                                hSteps = hDirections.steps,
                                hDistanceUnit = hDistance,
                                hEta = String.format(hContext.getString(R.string.time), " ${hDirections.duration?.text}"),
                                hStartMarker = hCreateMarkerOptions(
                                        hContext = hContext,
                                        hLat = hDirections.startLocation?.lat!!,
                                        hLng = hDirections.startLocation.lng,
                                        hType = MarkerUtils.MarkerType.CURRENT,
                                ),
                                hEndMarker = hCreateMarkerOptions(
                                        hContext = hContext,
                                        hLat = hDirections.endLocation?.lat!!,
                                        hLng = hDirections.endLocation.lng,
                                        hType = MarkerUtils.MarkerType.DESTINATION,
                                ),
                        ),
                )
        )
    }


    private fun hSubmitPlaceSuggetstions(
            hResult: MutableLiveData<DataState<MainViewState>>
    ) {
        val hSuggestionsList = mutableListOf<String>()
        hLastSuggestionsList?.map {
            hSuggestionsList.add(it.description)
        }

        hResult.value = DataState(
                hData = MainViewState(
                        hMainFields = MainFields(
                                hPlaceSuggestionsVS = PlaceSuggestionsVS(
                                        hPlaceSuggestionsList = hSuggestionsList
                                )
                        )
                )
        )
    }

    private fun hChangeBottomView(marker: Marker): LiveData<DataState<MainViewState>> {

        val hResult = MediatorLiveData<DataState<MainViewState>>()
        hResult.value = DataState.hLoading(true)

        viewModelScope.launch {
            val hResponse = MutableLiveData<List<GeoCode>>()
            hResult.value = DataState.hLoading(true)

            viewModelScope.launch {
                hCurrentLocation?.let {
                    hResponse.value = hRemoteRepo.hReverseGeoCode(marker.position)
                }
                hResult.addSource(hResponse) {
                    hResult.removeSource(hResponse)


                    viewModelScope.launch {
                        val hDirections = hRemoteRepo.hGetDirections(
                                startLocation = hCurrentLocation!!,
                                endLocation = hLatLngToLocation(
                                        hLat = marker.position.latitude,
                                        hLng = marker.position.longitude,
                                ),
                                mode = DRIVING
                        )
                        val hDistance: String

                        val hUnit = hSettingsPrefrences.hGetSettings(PrefTypes.DISTANCE_UNIT_PT) as Int?
                        hDistance = when (hUnit) {
                            1 -> {
                                hDirections.distance?.value?.div(1600).toString()
                            }
                            else -> {
                                hDirections.distance?.text.toString()
                            }
                        }

                        hResult.value = DataState(
                                hData = MainViewState(
                                        hMainFields = MainFields(
                                                hOnMarkerClickVS = OnMarkerClickVS(
                                                        hPlaceName = marker.title,
                                                        hAddress = hResponse.value?.get(0)?.formattedAddress,
                                                        hDistance = hDirections.distance,
                                                        hOverviewPolyline = hDirections.overviewPolyline,
                                                        hSteps = hDirections.steps,
                                                        hDistanceUnit = hDistance,
                                                        hEta = String.format(hContext.getString(R.string.time), " ${hDirections.duration?.text}"),
                                                        hStartMarker = hCreateMarkerOptions(
                                                                hContext = hContext,
                                                                hLat = hDirections.startLocation?.lat!!,
                                                                hLng = hDirections.startLocation.lng,
                                                                hType = MarkerUtils.MarkerType.CURRENT,
                                                        ),
                                                        hEndMarker = hCreateMarkerOptions(
                                                                hContext = hContext,
                                                                hLat = hDirections.endLocation?.lat!!,
                                                                hLng = hDirections.endLocation.lng,
                                                                hType = MarkerUtils.MarkerType.DESTINATION,
                                                        ),
                                                )
                                        ),
                                )
                        )
                    }
                }
            }
        }
        return hResult
    }

    private fun hSubmitNearByMarkerList(
            nearyByPlacesList: List<NearByPlaces>,
            category: Category,
            hResult: MutableLiveData<DataState<MainViewState>>
    ) {
        val hMarkerList = mutableListOf<MarkerOptions>()
        nearyByPlacesList.forEach { place ->
            hMarkerList.add(
                    hCreateMarkerOptions(
                            hContext = hContext,
                            hLat = place.lat!!,
                            hLng = place.lng!!,
                            hPlaceName = place.name,
                            hCategory = category,
                    )
            )
        }
        hResult.value = DataState(
                hData = MainViewState(
                        hMainFields = MainFields(
                                hNearByPlacesVS = NearByPlacesVS(
                                        hMarkerList = hMarkerList
                                )
                        ),
                )
        )
    }

    private fun hSubmitCurrentLocationData(location: Location?): LiveData<DataState<MainViewState>>? {
        location?.let { hCurrentLocation ->

            val hCurrentLocationDataState = MutableLiveData<DataState<MainViewState>>()
            hCurrentLocationDataState.value = DataState(
                    hLoading = false,
                    hData = MainViewState(
                            hMainFields = MainFields(
                                    hCurrentLocationVS = CurrentLocationVS(
                                            currentLocation = hCurrentLocation,
                                            currentMarkerOptions = hCreateMarkerOptions(
                                                    hContext = hContext,
                                                    hLat = hCurrentLocation.latitude,
                                                    hLng = hCurrentLocation.longitude,
                                                    hType = MarkerUtils.MarkerType.CURRENT,
                                            ),
                                            cameraZoom = 12.0F,
                                    ),
                            ),
                    )
            )
            return hCurrentLocationDataState
        }
        return null
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
        hClearOtherVS(hUpdate)
        hUpdate.hMainFields.hNearByPlacesVS = nearByPlacesVS
        _hMainViewState.value = hUpdate
    }

    fun hSetMarkerClickData(onMarkerClickVS: OnMarkerClickVS) {
        val hUpdate = hGetCurrentViewStateOrNew()
        hClearOtherVS(hUpdate)
        hUpdate.hMainFields.hOnMarkerClickVS = onMarkerClickVS
        _hMainViewState.value = hUpdate
    }

    fun hSetPlaceSuggestionsData(placeSuggestionsVS: PlaceSuggestionsVS) {
        val hUpdate = hGetCurrentViewStateOrNew()
        hClearOtherVS(hUpdate)
        hUpdate.hMainFields.hPlaceSuggestionsVS = placeSuggestionsVS
        _hMainViewState.value = hUpdate
    }

    fun hSetSelectedPlaceData(hPlaceSelectedVs: PlaceSelectedVS) {
        val hUpdate = hGetCurrentViewStateOrNew()
        hClearOtherVS(hUpdate)
        hUpdate.hMainFields.hPlaceSelectedVs = hPlaceSelectedVs
        _hMainViewState.value = hUpdate
    }

    private fun hClearOtherVS(hUpdate: MainViewState) {
        hUpdate.hMainFields.hPlaceSelectedVs = null
        hUpdate.hMainFields.hNearByPlacesVS =null
        hUpdate.hMainFields.hPlaceSuggestionsVS =null
        hUpdate.hMainFields.hOnMarkerClickVS=null


    }
}