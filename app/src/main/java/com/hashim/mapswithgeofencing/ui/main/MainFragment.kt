/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
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
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.FragmentMainBinding
import com.hashim.mapswithgeofencing.location.LocationUtis
import com.hashim.mapswithgeofencing.ui.events.MainStateEvent
import com.hashim.mapswithgeofencing.ui.events.MainStateEvent.OnCurrentLocationFound
import com.hashim.mapswithgeofencing.ui.events.MainStateEvent.OnMapReady
import com.hashim.mapswithgeofencing.ui.events.MainViewState.CurrentLocationVS
import com.hashim.mapswithgeofencing.ui.events.MainViewState.NearByPlacesVS
import com.hashim.mapswithgeofencing.utils.UiHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainFragment : Fragment() {
    lateinit var hFragmentMainBinding: FragmentMainBinding
    private val hMainViewModel: MainViewModel by viewModels()
    private var hGoogleMap: GoogleMap? = null
    private lateinit var hCategoriesAdapter: CategoriesAdapter

    @SuppressLint("MissingPermission")
    private val hMapCallBack = OnMapReadyCallback { googleMap ->
        hGoogleMap = googleMap
        hGoogleMap?.isMyLocationEnabled = true
        hMainViewModel.hSetStateEvent(OnMapReady())
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
                    MainStateEvent.OnCategorySelected(category)
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

        if (hHasPermission()) {
            hInitMap()
            var hLocationUtis = LocationUtis(
                    context = requireContext(),
                    onLocationRetrieved = { location ->
                        hMainViewModel.hSetStateEvent(
                                OnCurrentLocationFound(location)
                        )
//                        hOnFragmentInteraction.hSetLocation(location)
                    },
                    onLocationUpdated = {}
            )
        } else {
            hRequestPermissions()
        }


    }

    private fun hSetupListeners() {
        hFragmentMainBinding.hSearchBar.setHint("Search here")
        hFragmentMainBinding.hSearchBar.setSpeechMode(true)
//        hFragmentMainBinding.hSearchBar.setOnSearchActionListener(this)

    }

    private fun hSubscribeObservers() {
        hMainViewModel.hDataState.observe(viewLifecycleOwner) { dataState ->
            dataState.hData?.let { mainViewState ->
                mainViewState.hMainFields.hCurrentLocationVS?.let { currentLocationVS ->
                    hMainViewModel.hSetCurrentLocationData(currentLocationVS)

                }
                mainViewState.hMainFields.hNearByPlacesVS?.let { nearByPlacesVS ->
                    hMainViewModel.hSetNearByPlacesData(nearByPlacesVS)

                }
            }
        }

        hMainViewModel.hMainViewState.observe(viewLifecycleOwner) { mainviewstate ->
            mainviewstate.hMainFields.hNearByPlacesVS?.let { nearByPlacesVS ->
                hCreateNearByMarker(nearByPlacesVS)
            }
            mainviewstate.hMainFields.hCurrentLocationVS?.let { currentLocationVS ->
                hSetCurrentMarker(currentLocationVS)
            }

        }
    }

    private fun hCreateNearByMarker(nearByPlacesVS: NearByPlacesVS) {
        Timber.d("Create markers")
        hGoogleMap?.clear()
        nearByPlacesVS.hMarkerList?.forEach {
            Timber.d("Adding Marking")
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

    private fun hHasPermission(): Boolean {
        return (
                ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED
                )
    }

    private fun hRequestPermissions() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            hRequestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            UiHelper.hShowSnackBar(
                    view = hFragmentMainBinding.hSnackBarCL,
                    message = getString(R.string.location_permision),
                    onTakeAction = {
                        hRequestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
            )
        }
    }

    private fun hInitMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.hGoogleMap) as SupportMapFragment?
        mapFragment?.getMapAsync(hMapCallBack)
    }


    private val hRequestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted: Boolean ->
                if (isGranted) {
                    hInitMap()
                } else {
                    UiHelper.hShowSnackBar(
                            view = hFragmentMainBinding.hSnackBarCL,
                            message = getString(R.string.location_permision),
                            onTakeAction = {}
                    )
                }
            }


}