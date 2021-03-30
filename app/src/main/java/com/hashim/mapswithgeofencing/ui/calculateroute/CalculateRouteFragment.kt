/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.calculateroute

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.tabs.TabLayout
import com.google.maps.android.PolyUtil
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.FragmentCalculateRouteBinding
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteStateEvent.OnFindDirections
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteViewState.DrawPathVS
import com.hashim.mapswithgeofencing.utils.Constants.Companion.H_DRIVING_MODE
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CalculateRouteFragment : Fragment() {

    private val hCalculateRouteViewModel: CalculateRouteViewModel by viewModels()

    lateinit var hFragmentCalculateRouteBinding: FragmentCalculateRouteBinding
    private var hGoogleMap: GoogleMap? = null


    @SuppressLint("MissingPermission")
    private val hMapCallBack = OnMapReadyCallback { googleMap ->
        hGoogleMap = googleMap
        hGoogleMap?.isMyLocationEnabled = true

        /*
        * //        hGoogleMap.setMapType(hSettingsPrefrences.hGetMapsType());
//        hGoogleMap.setMyLocationEnabled(true);
//        hGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
//        hGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hCurrentLatLng, 12.0f));
        * */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(requireContext(), getString(R.string.google_maps_key))
        val placesClient = Places.createClient(requireContext())
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
            hFragmentCalculateRouteBinding.placeAutocompleteCard.visibility =
                    VISIBLE
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

        hInitPlaceView()
    }

    private fun hInitPlaceView() {
        val hAutoCompleteFragment =
                childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                        as AutocompleteSupportFragment

        hAutoCompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        hAutoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Timber.d("Place: ${place.name}, ${place.id}")
                val hLocation = Location(LocationManager.GPS_PROVIDER).also {
                    it.latitude = place.latLng?.latitude!!
                    it.longitude = place.latLng?.longitude!!
                }

                hCalculateRouteViewModel.hSetStateEvent(
                        OnFindDirections(
                                hDestinationLocation = hLocation,
                                hMode = H_DRIVING_MODE
                        )
                )

                hFragmentCalculateRouteBinding.placeAutocompleteCard.visibility =
                        GONE

                /*Set text to the view*/
            }

            override fun onError(status: Status) {
                Timber.d("An error occurred: $status")
            }
        })
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
                                    Timber.d("Driving Mode")
                                    /*Todo:Find directions too*/
                                }
                                1 -> {
                                    Timber.d("Cycling Mode")
                                }
                                2 -> {
                                    Timber.d("Walking Mode")
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
                            /*TODO("Not yet implemented")*/
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
                calculateRouteViewSate.hCalculateRouteFields.hDrawPathVS?.let {
                    hCalculateRouteViewModel.hSetDrawPathVs(it)
                }
            }
        }

        hCalculateRouteViewModel.hCalculateRouteViewState.observe(viewLifecycleOwner) { calculateRouteViewState ->
            calculateRouteViewState.hCalculateRouteFields.hDrawPathVS?.let {
                hDrawPathOnMap(it)

            }

        }
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