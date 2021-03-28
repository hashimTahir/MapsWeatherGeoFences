/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.calculateroute

import android.annotation.SuppressLint
import android.graphics.Color
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
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.tabs.TabLayout
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.FragmentCalculateRouteBinding
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

        hAutoCompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        hAutoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Timber.d("Place: ${place.name}, ${place.id}")

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
            dataState.hData?.let {
                it.hCalculateRouteFields?.let {

                }
            }
        }

        hCalculateRouteViewModel.hCalculateRouteViewState.observe(viewLifecycleOwner) { calculateRouteViewState ->
            calculateRouteViewState.hCalculateRouteFields?.let {

            }

        }
    }
}


/*
*
/*Todo: Call later from on create*/

//    @Override
//    public void onDirectionFinderStart() {
//        if (hOriginMarkers != null) {
//            for (Marker marker : hOriginMarkers) {
//                marker.remove();
//            }
//        }
//
//        if (hDestinationMarkers != null) {
//            for (Marker marker : hDestinationMarkers) {
//                marker.remove();
//            }
//        }
//
//        if (hPolylinePaths != null) {
//            for (Polyline polyline : hPolylinePaths) {
//                polyline.remove();
//            }
//        }
//    }
//
//    @Override
//    public void onDirectionFinderSuccess(List<Route> route) {
//        if (route.size() > 0) {
//            //        progressDialog.dismiss();
//            hPolylinePaths = new ArrayList<>();
//            hOriginMarkers = new ArrayList<>();
//            hDestinationMarkers = new ArrayList<>();
//
//            hUpdatePolyLine(route);
//            hCreateMarker(new LatLng(hCurrentLatLng.latitude, hCurrentLatLng.longitude),
//                    new LatLng(hDestLatLng.latitude, hDestLatLng.longitude));
////        hHideLoader();
//
//
//        } else {
//            LogToastSnackHelper.hMakeLongToast(this, hMode.concat(getString(R.string.not_available)));
//            AnimHelper.hAnimateBottomDown(this, hAcitivityCalculateRouteBinding.hBottomCardView);
//            UIHelper.hMakeVisibleInVisible(hAcitivityCalculateRouteBinding.hBottomCardView, Constants.H_INVISIBLE);
//        }
//
//
//    }

private void hCreateMarker(LatLng currentLatLng, LatLng destLatLng) {
    MarkerOptions hMarkerOptions = new MarkerOptions();
//        hMarkerOptions.position(currentLatLng).icon(BitmapDescriptorFactory.
//                fromBitmap(MarkerUtils.hGetCustomMapMarker(this, String.valueOf(Constants.H_CURRENT_MARKER))));

    MarkerOptions hMarkerOptions1 = new MarkerOptions();
//        hMarkerOptions1.position(destLatLng).icon(BitmapDescriptorFactory.
//                fromBitmap(MarkerUtils.hGetCustomMapMarker(this, String.valueOf(Constants.H_DEST_MARKER))));


    hGoogleMap.addMarker(hMarkerOptions).showInfoWindow();
    hGoogleMap.addMarker(hMarkerOptions1).showInfoWindow();

}

//    private void hUpdatePolyLine(List<Route> routes) {
//
//        if (routes.size() > 0) {
//            hGoogleMap.clear();
//            String hDist = null;
//            String hDuration = null;
//            for (Route route : routes) {
//
//                PolylineOptions polylineOptions = new PolylineOptions().geodesic(true).
//                        color(ContextCompat.getColor(this, R.color.colorPrimary)).width(10);
//
//                for (int i = 0; i < route.points.size(); i++) {
//                    polylineOptions.add(route.points.get(i));
//
//
//                }
//                hPolylinePaths.add(hGoogleMap.addPolyline(polylineOptions));
//
//            }
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//
//            hDist = String.valueOf(routes.get(0).distance.text);
//            int dis = routes.get(0).distance.value;
//            hDuration = String.valueOf(routes.get(0).duration.text);
//
//            SettingsPrefrences.kt hSettingsPrefrences = new SettingsPrefrences.kt(this);
//
//            if (hSettingsPrefrences.hGetDistanceUnit() == Constants.H_MILES_DIS) {
//                dis = dis / 1600;
//
//                UIHelper.hSetTextToTextView(hAcitivityCalculateRouteBinding.hDistanceTv, String.valueOf(dis) + getString(R.string.miles));
//                UIHelper.hSetTextToTextView(hAcitivityCalculateRouteBinding.hEtaTV, "  (".concat(hDuration) + ")");
//            } else {
//
//                UIHelper.hSetTextToTextView(hAcitivityCalculateRouteBinding.hDistanceTv, hDist);
//                UIHelper.hSetTextToTextView(hAcitivityCalculateRouteBinding.hEtaTV, "  (".concat(hDuration) + ")");
//            }
//
//
////        hGoogleMap.setMyLocationEnabled(false);
//
//
//            UIHelper.hSetTextToTextView(hAcitivityCalculateRouteBinding.hTimeTv, hDestName);
//            AnimHelper.hAnimateBottomUp(this, hAcitivityCalculateRouteBinding.hBottomCardView);
//
//        }
//
//    }

//    @Override
//    public void onPlaceSelected(Place place) {
//
//        String hPlaceName = (String) place.getName();
//        hDestLatLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
//        hDestName = hPlaceName;
//        UIHelper.hSetTextToTextView(hAcitivityCalculateRouteBinding.hToTV, hPlaceName);
//
//        try {
//            hFindDirections(hMode);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void hFindDirections(String mode) throws UnsupportedEncodingException {
//        String origin = String.valueOf(hCurrentLatLng.latitude).concat(",").concat(String.valueOf(hCurrentLatLng.longitude));
//        String destination = String.valueOf(hDestLatLng.latitude).concat(",").concat(String.valueOf(hDestLatLng.longitude));
//
//        switch (mode) {
//            case DirectionFinder.H_CYCLING_MODE:
//                new DirectionFinder(this, origin, destination, DirectionFinder.H_CYCLING_MODE).execute();
//                break;
//            case DirectionFinder.H_DRIVING_MODE:
//                new DirectionFinder(this, origin, destination, DirectionFinder.H_DRIVING_MODE).execute();
//                break;
//            case DirectionFinder.H_WALKING_MODE:
//                new DirectionFinder(this, origin, destination, DirectionFinder.H_WALKING_MODE).execute();
//                break;
//        }
//
//
//    }
//
//    @Override
//    public void onError(Status status) {
//
//    }
*/
