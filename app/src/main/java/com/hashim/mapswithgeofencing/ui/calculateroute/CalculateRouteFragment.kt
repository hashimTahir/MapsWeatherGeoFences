/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.calculateroute

import PlaceUtils
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.material.tabs.TabLayout
import com.google.maps.android.PolyUtil
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.FragmentCalculateRouteBinding
import com.hashim.mapswithgeofencing.ui.calculateroute.DirectionsMode.*
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteStateEvent.*
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteViewState.DrawPathVS
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteViewState.SetMapVS
import com.hashim.mapswithgeofencing.utils.hLatLngToLocation
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CalculateRouteFragment : Fragment() {

    private val hCalculateRouteViewModel: CalculateRouteViewModel by viewModels()

    lateinit var hFragmentCalculateRouteBinding: FragmentCalculateRouteBinding
    private var hGoogleMap: GoogleMap? = null


    val hStartPlacesForResult = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
        Timber.d("Results Callback")
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val place = Autocomplete.getPlaceFromIntent(it)
                Timber.d("Place is ${place.name} and latlngs are ${place.latLng}")

                hFragmentCalculateRouteBinding.hToTV.text = place.name

                val hLocation = hLatLngToLocation(
                        hLng = place.latLng?.longitude!!,
                        hLat = place.latLng?.latitude!!,
                )

                hCalculateRouteViewModel.hSetStateEvent(
                        OnFindDirections(
                                hDestinationLocation = hLocation,
                                hMode = DRIVING
                        )
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private val hMapCallBack = OnMapReadyCallback { googleMap ->
        hGoogleMap = googleMap
        hGoogleMap?.isMyLocationEnabled = true
        hCalculateRouteViewModel.hSetStateEvent(OnMapReady())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        hFragmentCalculateRouteBinding = FragmentCalculateRouteBinding.inflate(
                layoutInflater,
                container,
                false
        )
        return hFragmentCalculateRouteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hSubscribeObservers()

        hInitView()

        hSetupListeners()
    }

    private fun hSetupListeners() {

        hFragmentCalculateRouteBinding.hToTV.setOnClickListener {
            PlaceUtils.hInit(requireContext())
            hStartPlacesForResult.launch(
                    PlaceUtils.hStartPlacesAutoComplete(this)
            )
        }

        hFragmentCalculateRouteBinding.hGetDirectionsB.setOnClickListener {

        }

        hFragmentCalculateRouteBinding.hIconSwitch.setOnClickListener {
            /*Todo: Switch Coordinates*/
        }

    }

    private fun hInitView() {

        hInitTabView()

        hInitMapView()

    }


    private fun hInitMapView() {

        val hMapFragment = childFragmentManager.findFragmentById(R.id.hGoogleMap) as SupportMapFragment?
        hMapFragment?.getMapAsync(hMapCallBack)
    }

    private fun hInitTabView() {

        val hTabLayout: TabLayout = hFragmentCalculateRouteBinding.hTabs
        hTabLayout.apply {

            addTab(this.newTab().apply {
                text = getString(R.string.drive_tab)
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_car)
            })
            addTab(this.newTab().apply {
                text = getString(R.string.cycling_tab)
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.cycle)
            })
            addTab(this.newTab().apply {
                text = getString(R.string.walking_tab)
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_walking)
            })
            addOnTabSelectedListener(
                    object : TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            when (tab?.position) {
                                0 -> {
                                    hCalculateRouteViewModel.hSetStateEvent(
                                            OnModeChanged(DRIVING)
                                    )
                                }
                                1 -> {
                                    hCalculateRouteViewModel.hSetStateEvent(
                                            OnModeChanged(TRANSIT)
                                    )
                                }
                                2 -> {
                                    hCalculateRouteViewModel.hSetStateEvent(
                                            OnModeChanged(WALKING)
                                    )
                                }

                            }
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                            tab?.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                                    Color.WHITE,
                                    BlendModeCompat.SRC_IN
                            )
                        }

                        override fun onTabReselected(tab: TabLayout.Tab?) {
                        }

                    }
            )
            getTabAt(0)?.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    ContextCompat.getColor(requireContext(), R.color.share_loc_color),
                    BlendModeCompat.SRC_IN
            )
            getTabAt(1)?.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    Color.WHITE,
                    BlendModeCompat.SRC_IN
            )
            getTabAt(2)?.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    Color.WHITE,
                    BlendModeCompat.SRC_IN
            )
        }
    }


    private fun hSubscribeObservers() {
        hCalculateRouteViewModel.hDataState.observe(viewLifecycleOwner) { dataState ->
            dataState.hData?.let { calculateRouteViewSate ->
                calculateRouteViewSate.hCalculateRouteFields.hDrawPathVS?.let { drawPathVS ->
                    hCalculateRouteViewModel.hSetDrawPathVs(drawPathVS)
                }

                calculateRouteViewSate.hCalculateRouteFields.hSetMapVS?.let { currentLocationVS ->
                    hCalculateRouteViewModel.hSetCurrentLocationVs(currentLocationVS)
                }
            }
        }

        hCalculateRouteViewModel.hCalculateRouteViewState
                .observe(viewLifecycleOwner) { calculateRouteViewState ->
                    calculateRouteViewState.hCalculateRouteFields.hDrawPathVS?.let { drawPathVS ->
                        hDrawPathOnMap(drawPathVS)

                        hPutStartEndMarkers(
                                drawPathVS.hStartMarker,
                                drawPathVS.hEndMarker,
                        )

                        hMakeViewsVisible(
                                drawPathVS.hDistanceUnit,
                                drawPathVS.hEta
                        )
                    }
                    calculateRouteViewState.hCalculateRouteFields.hSetMapVS?.let { setMapVS ->
                        hSetupMap(setMapVS)
                    }

                }
    }

    private fun hPutStartEndMarkers(hStartMarker: MarkerOptions?, hEndMarker: MarkerOptions?) {
        hGoogleMap?.addMarker(hStartMarker)
        hGoogleMap?.addMarker(hEndMarker)

    }

    @SuppressLint("MissingPermission")
    private fun hSetupMap(setMapVS: SetMapVS) {
//   hGoogleMap.setMapType(hSettingsPrefrences.hGetMapsType());

        hGoogleMap?.isMyLocationEnabled = true
        hGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(
                        setMapVS.currentLocation.latitude,
                        setMapVS.currentLocation.longitude

                ), setMapVS.cameraZoom))
    }

    private fun hMakeViewsVisible(hDistance: String?, hEta: String?) {

        Timber.d("Distance $hDistance and Eta $hEta")
        hFragmentCalculateRouteBinding.hBottomCardView.visibility = VISIBLE
        hFragmentCalculateRouteBinding.hEtaTV.text = hDistance.toString()
        hFragmentCalculateRouteBinding.hTimeTv.text = hEta
    }

    private fun hDrawPathOnMap(drawPathVS: DrawPathVS) {
        Timber.d("hDrawPathOnMap")
        hGoogleMap?.clear()
        val hPolylineOptions = PolylineOptions().apply {
            color(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
            geodesic(true)
            width(10F)
        }

        val hPolyline = PolyUtil.decode(drawPathVS.hOverviewPolyline?.points)

        hPolyline.forEach {
            hPolylineOptions.add(it)
        }

        hGoogleMap?.addPolyline(hPolylineOptions)

    }
}