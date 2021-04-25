/*
 * Copyright (c) 2021/  4/ 4.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.main.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.FragmentMainBinding
import com.hashim.mapswithgeofencing.others.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.ui.events.MainStateEvent.*
import com.hashim.mapswithgeofencing.ui.events.MainViewState.*
import com.hashim.mapswithgeofencing.ui.main.fragments.adapter.CategoriesAdapter
import com.hashim.mapswithgeofencing.utils.PermissionsUtils.Companion.hRequestLocationPermission
import com.hashim.mapswithgeofencing.utils.UiHelper
import com.hashim.mapswithgeofencing.utils.UiHelper.Companion.hHideView
import com.hashim.mapswithgeofencing.utils.UiHelper.Companion.hShowView
import com.hashim.mapswithgeofencing.utils.location.LocationUtis
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {
    lateinit var hFragmentMainBinding: FragmentMainBinding
    private val hMainViewModel: MainViewModel by viewModels()
    private var hGoogleMap: GoogleMap? = null
    private lateinit var hCategoriesAdapter: CategoriesAdapter

    @Inject
    lateinit var hSettingsPrefrences: SettingsPrefrences

    @SuppressLint("MissingPermission")
    private val hMapCallBack = OnMapReadyCallback { googleMap ->
        hGoogleMap = googleMap
        hGoogleMap?.isMyLocationEnabled = true
        hMainViewModel.hSetStateEvent(OnMapReady())
        hSetMapListerns()
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun hSetMapListerns() {
        hGoogleMap?.setOnMarkerClickListener { marker ->
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            } else {
                marker.showInfoWindow()
            }
            hMainViewModel.hSetStateEvent(OnMarkerClicked(marker))
            return@setOnMarkerClickListener true
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        hFragmentMainBinding = FragmentMainBinding.inflate(
                inflater,
                container,
                false
        )
        return hFragmentMainBinding.root
    }

    private fun hInitCategoryRv() {


        hCategoriesAdapter = CategoriesAdapter(requireContext())
        hCategoriesAdapter.hSetCategoriesCallback { category ->
            hMainViewModel.hSetStateEvent(
                    OnCategorySelected(category)
            )
        }


        hFragmentMainBinding.hCategoriesRv.apply {
            adapter = hCategoriesAdapter
            layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hSubscribeObservers()

        hInitCategoryRv()

        hSetupListeners()

        hRequestPermissions()

    }

    private fun hSetupListeners() {

        hFragmentMainBinding.hSearchBar.setHint("Search here")
        hFragmentMainBinding.hSearchBar.setSpeechMode(true)
        hFragmentMainBinding.hSearchBar.addTextChangeListener(
                object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        Timber.d("After Text Changed")
                        hMainViewModel.hSetStateEvent(OnFindAutoCompleteSuggestions(suggestion = s.toString()))
                    }

                }
        )

        hFragmentMainBinding.hSearchBar.setSuggestionsClickListener(
                object : SuggestionsAdapter.OnItemViewClickListener {
                    override fun OnItemClickListener(position: Int, v: View?) {
                        hFragmentMainBinding.hSearchBar.clearSuggestions()
                        hFragmentMainBinding.hSearchBar.closeSearch()
                        hMainViewModel.hSetStateEvent(OnSuggestionSelected(postion = position))

                    }

                    override fun OnItemDeleteListener(position: Int, v: View?) {
                        TODO("Not yet implemented")
                    }

                }
        )
        hFragmentMainBinding.hSearchBar.setOnSearchActionListener(
                object : MaterialSearchBar.OnSearchActionListener {
                    override fun onSearchStateChanged(enabled: Boolean) {
                        Timber.d("onSearchStateChanged $enabled")
                    }

                    override fun onSearchConfirmed(text: CharSequence?) {
                        Timber.d("Search Confirmed $text")
                    }

                    override fun onButtonClicked(buttonCode: Int) {
                        Timber.d("onButtonClicked $buttonCode")
                    }

                }
        )
        hFragmentMainBinding.hDetailCardView.setOnClickListener {
            /*Todo Execute route*/
        }

        hFragmentMainBinding.hClearAllFab.setOnClickListener {
            hClearMapnResetBottomView()
        }


    }

    private fun hSubscribeObservers() {
        hMainViewModel.hDataState.observe(viewLifecycleOwner) { dataState ->
            dataState.hLoading.let {
                when (it) {
                    true -> {
                        hShowView(hFragmentMainBinding.hProgressbar)
                    }
                    false -> {
                        hHideView(hFragmentMainBinding.hProgressbar)
                    }
                }
            }

            dataState.hData?.let { mainViewState ->
                mainViewState.hMainFields.hCurrentLocationVS?.let { currentLocationVS ->
                    hMainViewModel.hSetCurrentLocationData(currentLocationVS)
                }
                mainViewState.hMainFields.hNearByPlacesVS?.let { nearByPlacesVS ->
                    hMainViewModel.hSetNearByPlacesData(nearByPlacesVS)
                }
                mainViewState.hMainFields.hOnMarkerClickVS?.let { onMarkerClickVS ->
                    hMainViewModel.hSetMarkerClickData(onMarkerClickVS)
                }
                mainViewState.hMainFields.hPlaceSuggestionsVS?.let { placeSuggestionsVS ->
                    hMainViewModel.hSetPlaceSuggestionsData(placeSuggestionsVS)
                }

                mainViewState.hMainFields.hPlaceSelectedVs?.let { placeSelectedVs ->
                    hMainViewModel.hSetSelectedPlaceData(placeSelectedVs)
                }
            }
        }

        hMainViewModel.hMainViewState.observe(viewLifecycleOwner) { mainviewstate ->
            Timber.d("View hNearByPlacesVS ${mainviewstate.hMainFields.hNearByPlacesVS}")
            Timber.d("View hCurrentLocationVS ${mainviewstate.hMainFields.hCurrentLocationVS}")
            Timber.d("View hOnMarkerClickVS ${mainviewstate.hMainFields.hOnMarkerClickVS}")
            Timber.d("View hPlaceSuggestionsVS ${mainviewstate.hMainFields.hPlaceSuggestionsVS}")
            Timber.d("View hPlaceSelectedVs ${mainviewstate.hMainFields.hPlaceSelectedVs}")
            mainviewstate.hMainFields.hNearByPlacesVS?.let { nearByPlacesVS ->
                hCreateNearByMarker(nearByPlacesVS)
            }
            mainviewstate.hMainFields.hCurrentLocationVS?.let { currentLocationVS ->
                hSetCurrentMarker(currentLocationVS)
            }
            mainviewstate.hMainFields.hOnMarkerClickVS?.let { onMarkerClickVS ->
                hSetBottomCardnDrawPath(onMarkerClickVS)

            }

            mainviewstate.hMainFields.hPlaceSuggestionsVS?.let { placeSuggestionsVS ->
                hSetSetAdapterSuggestions(placeSuggestionsVS)

            }


            mainviewstate.hMainFields.hPlaceSelectedVs?.let { placeSelectedVs ->
                hSetSelectedPlace(placeSelectedVs)
            }
        }
    }

    private fun hSetSelectedPlace(placeSelectedVs: PlaceSelectedVS) {
        hClearMapnResetBottomView()
        val hPolylineOptions = PolylineOptions().apply {
            color(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
            geodesic(true)
            width(10F)
        }

        val hPolyline = PolyUtil.decode(placeSelectedVs.hOverviewPolyline?.points)

        hPolyline.forEach {
            hPolylineOptions.add(it)
        }

        hGoogleMap?.addPolyline(hPolylineOptions)

        hGoogleMap?.addMarker(placeSelectedVs.hStartMarker)
        hGoogleMap?.addMarker(placeSelectedVs.hEndMarker)
    }


    private fun hSetSetAdapterSuggestions(placeSuggestionsVS: PlaceSuggestionsVS) {
        Timber.d("Setting Suggestions Adapter")
        hFragmentMainBinding.hSearchBar.lastSuggestions = placeSuggestionsVS.hPlaceSuggestionsList
        hFragmentMainBinding.hSearchBar.showSuggestionsList()
    }

    private fun hSetBottomCardnDrawPath(onMarkerClickVS: OnMarkerClickVS) {
        hClearMapnResetBottomView()
        hShowView(hFragmentMainBinding.hDetailCardView)
        hFragmentMainBinding.hAddressTv.text = onMarkerClickVS.hAddress
        hFragmentMainBinding.hNameTv.text = onMarkerClickVS.hPlaceName

        /*Todo: Code Duplication refactor later.*/
        val hPolylineOptions = PolylineOptions().apply {
            color(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
            geodesic(true)
            width(10F)
        }

        val hPolyline = PolyUtil.decode(onMarkerClickVS.hOverviewPolyline?.points)

        hPolyline.forEach {
            hPolylineOptions.add(it)
        }

        hGoogleMap?.addPolyline(hPolylineOptions)

        hGoogleMap?.addMarker(onMarkerClickVS.hStartMarker)
        hGoogleMap?.addMarker(onMarkerClickVS.hEndMarker)
    }

    private fun hCreateNearByMarker(nearByPlacesVS: NearByPlacesVS) {
        hClearMapnResetBottomView()
        nearByPlacesVS.hMarkerList?.forEach {
            hGoogleMap?.addMarker(it)
        }
    }

    private fun hSetCurrentMarker(mainfields: CurrentLocationVS) {
        hGoogleMap?.addMarker(mainfields.currentMarkerOptions)?.showInfoWindow()
        hGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(
                        mainfields.currentLocation?.latitude!!,
                        mainfields.currentLocation.longitude

                ), mainfields.cameraZoom!!))

    }

    private fun hClearMapnResetBottomView() {
        Timber.d("Clearing.............")
        hGoogleMap?.clear()
        hHideView(hFragmentMainBinding.hDetailCardView)
    }

    private fun hRequestPermissions() {
        hShowView(hFragmentMainBinding.hProgressbar)

        hRequestLocationPermission(
                requireContext(),
                hRequestLocationPermissionLauncher) {
            hInitMap()
            hGetLocationUpdates()
        }
    }

    private fun hGetLocationUpdates() {
        var hLocationUtis = LocationUtis(
                context = requireContext(),
                onLocationRetrieved = { location ->
                    hMainViewModel.hSetStateEvent(
                            OnCurrentLocationFound(location)
                    )
                },
                onLocationUpdated = {}
        )
    }

    private fun hInitMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.hGoogleMap) as SupportMapFragment?
        mapFragment?.getMapAsync(hMapCallBack)
    }


    private val hRequestLocationPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted: Boolean ->
                if (isGranted) {
                    hInitMap()
                    hGetLocationUpdates()
                } else {
                    UiHelper.hShowSnackBar(
                            view = hFragmentMainBinding.hSnackBarCL,
                            message = getString(R.string.location_permision),
                            onTakeAction = {
                                hRequestPermissions()
                            }
                    )
                }
            }


}