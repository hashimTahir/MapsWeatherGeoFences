/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.FragmentAddGeoFencesBinding
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences.GeoFenceStateEvent.*
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences.GeoFenceViewState.CurrentLocationGeoFenceVS
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences.GeoFenceViewState.MakerFenceVS
import com.xw.repo.BubbleSeekBar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddGeoFencesFragment : Fragment() {

    lateinit var hFragmentAddGeoFencesBinding: FragmentAddGeoFencesBinding
    val hGeoFenceViewModel: GeoFenceViewModel by viewModels()
    private var hGoogleMap: GoogleMap? = null
    private var hRadius: Float = 0.5F

    @SuppressLint("MissingPermission")
    private val hMapCallBack = OnMapReadyCallback { googleMap ->
        hGoogleMap = googleMap
        hGoogleMap?.isMyLocationEnabled = true

        hGeoFenceViewModel.hSetStateEvent(OnMapReady(hRadius))

        hGoogleMap?.setOnMapClickListener { clickedLatLng ->
            hGeoFenceViewModel.hSetStateEvent(OnMapClicked(clickedLatLng, hRadius))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        hFragmentAddGeoFencesBinding = FragmentAddGeoFencesBinding.inflate(
                inflater,
                container,
                false
        )
        return hFragmentAddGeoFencesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hSetupListerns()
        hInitMap()

        hSubscribeObservers()

    }

    private fun hSubscribeObservers() {
        hGeoFenceViewModel.hDataState.observe(viewLifecycleOwner) { dataState ->
            dataState.hLoading.let {
                when (it) {
                    /*Show Hide Progress views*/
                    true -> {
                    }
                    false -> {
                    }
                }
            }
            dataState.hData?.let {

            }


        }
        hGeoFenceViewModel.hGeoFenceViewState.observe(viewLifecycleOwner) { geoFenceViewState ->
            geoFenceViewState.hGeoFenceFields.hCurrentLocationVS?.let { currentLocationGeoFenceVS ->
                hSetMap(currentLocationGeoFenceVS)
            }
            geoFenceViewState.hGeoFenceFields.hMakerFenceVS?.let { markerFenceVS ->
                hSetMapWithMarker(markerFenceVS)
            }
            geoFenceViewState.hGeoFenceFields.hAdjustRadiusVS?.let { adjustRadiusVS ->
                hAdjustRadius(adjustRadiusVS)
            }
        }
    }

    private fun hAdjustRadius(adjustRadiusVS: GeoFenceViewState.AdjustRadiusVS) {
        hGoogleMap?.addCircle(adjustRadiusVS.hCircleOptions)
    }

    private fun hSetMapWithMarker(markerFenceVS: MakerFenceVS) {
        hGoogleMap?.clear()
        hGoogleMap?.addCircle(markerFenceVS.hCircleOptions)
        hGoogleMap?.addMarker(markerFenceVS.hMarkerOptions)
    }

    private fun hSetMap(currentLocationGeoFenceVS: CurrentLocationGeoFenceVS) {
        Timber.d("Setting the map")
        hGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(
                        currentLocationGeoFenceVS.hLat!!,
                        currentLocationGeoFenceVS.hLng!!
                ),
                currentLocationGeoFenceVS.hCameraZoom!!))
        hGoogleMap?.addCircle(currentLocationGeoFenceVS.hCircleOptions)
    }


    private fun hSetupListerns() {
        val hListener = object : BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(
                    bubbleSeekBar: BubbleSeekBar?,
                    progress: Int,
                    progressFloat: Float
            ) {
                hRadius = progressFloat
                hGeoFenceViewModel.hSetStateEvent(OnRadiusChanged(hRadius))
            }

            override fun getProgressOnActionUp(
                    bubbleSeekBar: BubbleSeekBar?,
                    progress: Int,
                    progressFloat: Float
            ) {
            }

            override fun getProgressOnFinally(
                    bubbleSeekBar: BubbleSeekBar?,
                    progress: Int,
                    progressFloat: Float) {
            }

        }
        hFragmentAddGeoFencesBinding.hRadiusSeekBar.onProgressChangedListener = hListener

        hFragmentAddGeoFencesBinding.hSaveContactsB.setOnClickListener {
            hGeoFenceViewModel.hSetStateEvent(OnSaveDisplayedFences(
                    hFenceName = hFragmentAddGeoFencesBinding.hEditTv.text.toString()
            ))
        }
    }

    private fun hInitMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.hGoogleMap) as SupportMapFragment?
        mapFragment?.getMapAsync(hMapCallBack)
    }

}