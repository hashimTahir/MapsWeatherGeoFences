/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.calculateroute

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hashim.mapswithgeofencing.databinding.FragmentCalculateRouteBinding

class CalculateRouteFragment : Fragment() {


    lateinit var hFragmentCalculateRouteBinding: FragmentCalculateRouteBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        hFragmentCalculateRouteBinding = FragmentCalculateRouteBinding.inflate(
                layoutInflater,
                container,
                false
        )
        return hFragmentCalculateRouteBinding.root
    }

    /*
    *
public class CalculateActivity extends AppCompatActivity /*implements
        TabLayout.BaseOnTabSelectedListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
      *//*  DirectionFinderListener*//*, PlaceSelectionListener*/ {


    private LatLng hCurrentLatLng;
    private LatLng hDestLatLng;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    FragmentManager hFragmentManager;
    private GoogleMap hGoogleMap;
    private FusedLocationProviderClient hFusedLocationProviderClient;
    private GoogleApiClient hGoogleApiClient;
    private List<Marker> hOriginMarkers = new ArrayList<>();
    private List<Marker> hDestinationMarkers = new ArrayList<>();
    private List<Polyline> hPolylinePaths = new ArrayList<>();
    private AutocompleteSupportFragment hPlaceAutocompleteFragment;
    private String hDestName;
    private boolean hIsFromSearch;
    private String hMode;
    private boolean hIsSwitced = false;
    private String hCurrentLocationName;
    private boolean hIsVoiceCommand;
    private AcitivityCalculateRouteBinding hAcitivityCalculateRouteBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hAcitivityCalculateRouteBinding = AcitivityCalculateRouteBinding.inflate(getLayoutInflater());

        setContentView(hAcitivityCalculateRouteBinding.getRoot());


        hGetIntentData();
//        hMode = DirectionFinder.H_DRIVING_MODE;

        hInitView();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;

    }

    private void hInitView() {
//        TabLayout hTabs = hAcitivityCalculateRouteBinding.hTabs;
//        hTabs.addTab(hTabs.newTab().setText(getString(R.string.drive_tab)).setIcon(R.drawable.ic_car));
//        hTabs.addTab(hTabs.newTab().setText(getString(R.string.cycling_tab)).setIcon(R.drawable.cycle));
//        hTabs.addTab(hTabs.newTab().setText(getString(R.string.walking_tab)).setIcon(R.drawable.ic_walking));
//        hTabs.addOnTabSelectedListener(this);
//
//
//        hTabs.getTabAt(0).getIcon().setColorFilter(ContextCompat.getColor(this, R.color.share_loc_color), PorterDuff.Mode.SRC_IN);
//        hTabs.getTabAt(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
//        hTabs.getTabAt(2).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
//
//
//        /*Todo initilize later*/
//        hPlaceAutocompleteFragment = null;
//        hPlaceAutocompleteFragment.setOnPlaceSelectedListener(this);
//
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.hMap);
//        mapFragment.getMapAsync(this);

        hAcitivityCalculateRouteBinding.hGetDirectionsB.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,
                (R.color.share_loc_color))));
    }

    private void hGetIntentData() {

//        Bundle hBundle = getIntent().getExtras();
//        if (hBundle != null) {
////            hIsFromSearch = hBundle.getBoolean(Constants.H_FROM_SEARCH_IC);
//            if (hIsFromSearch) {
////                hDestName = hBundle.getString(Constants.H_LOC_NAME);
//
//                hDestLatLng = new LatLng(hBundle.getDouble(Constants.H_DEST_LAT), hBundle.getDouble(Constants.H_DEST_LNG));
//                hCurrentLocationName = hBundle.getString(Constants.H_CURRENT_LOCATION_NAME);
//
//            } else {
//                if (hBundle.containsKey(Constants.H_VOICE_COMMAD)) {
//                    hDestName = hBundle.getString(Constants.H_LOC_NAME);
//                    hIsVoiceCommand = hBundle.getBoolean(Constants.H_VOICE_COMMAD);
//
//                    hDestLatLng = new LatLng(hBundle.getDouble(Constants.H_DEST_LAT), hBundle.getDouble(Constants.H_DEST_LNG));
//
//                }
//            }
//            hCurrentLatLng = new LatLng(hBundle.getDouble(Constants.H_LATITUDE), hBundle.getDouble(Constants.H_LONGITUDE));
//        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.settings) {
//            Intent hIntent = new Intent(this, SettingsActivity.class);
//            startActivity(hIntent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
//
//        UIHelper.hOreoOrientationCheck(this);
//
//        UIHelper.hSetTextToTextView(
//                hAcitivityCalculateRouteBinding.hFromTV,
//                getString(R.string.current_location));
//
//        if (hDestName != null) {
//            UIHelper.hSetTextToTextView(
//                    hAcitivityCalculateRouteBinding.hToTV, hDestName);
//
//            try {
//                hFindDirections(hMode);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//        }


    }

    /*Todo: Call later from on create*/

    private void hSetupListeners() {
        hAcitivityCalculateRouteBinding.hToTV.setOnClickListener(v -> {
            //                UIHelper.hMakeVisibleInVisible(hPlaceAutocompleteCard, Constants.H_VISIBLE);
//            final View root = hPlaceAutocompleteCard.getRootView();
//            root.post(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
        });
//        hAcitivityCalculateRouteBinding.hGetDirectionsB.setOnClickListener(v -> {
//            hLaunchGoogleMaps();
//        });
//        hAcitivityCalculateRouteBinding.hIconSwitch.setOnClickListener(v -> {
//            if (!hIsSwitced) {
//                UIHelper.hSetTextToTextView(hAcitivityCalculateRouteBinding.hToTV, getString(R.string.current_location));
//                UIHelper.hSetTextToTextView(hAcitivityCalculateRouteBinding.hFromTV, hDestName);
//
//
//                LatLng hTempLatLng = hCurrentLatLng;
//                hCurrentLatLng = hDestLatLng;
//                hDestLatLng = hTempLatLng;
//                hIsSwitced = true;
//            } else {
//                UIHelper.hSetTextToTextView(hAcitivityCalculateRouteBinding.hToTV, hDestName);
//                UIHelper.hSetTextToTextView(hAcitivityCalculateRouteBinding.hFromTV, getString(R.string.current_location));
//
//
//                LatLng hTempLatLng = hCurrentLatLng;
//                hCurrentLatLng = hDestLatLng;
//                hDestLatLng = hTempLatLng;
//
//                hIsSwitced = false;
//            }
//        });
    }


    private void hLaunchGoogleMaps() {

        Uri gmmIntentUri1 = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" +
                hCurrentLatLng.latitude + "," + hCurrentLatLng.longitude
                + "&destination=" + hDestLatLng.latitude + "," + hDestLatLng.longitude);

        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri1);
        intent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri1);
                startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
            }
        }
    }

//
//    @Override
//    public void onTabSelected(TabLayout.Tab tab) {
////        tab.getIcon().setColorFilter(ContextCompat.getColor(this, R.color.share_loc_color), PorterDuff.Mode.SRC_IN);
////
////
////        if (hCurrentLatLng != null && hDestLatLng != null) {
////            switch (tab.getPosition()) {
////                case 0:
////
////                    try {
////                        hMode = DirectionFinder.H_DRIVING_MODE;
////                        hFindDirections(hMode);
////                    } catch (UnsupportedEncodingException e) {
////                        e.printStackTrace();
////                    }
////                    break;
////                case 1:
////                    try {
////                        hMode = DirectionFinder.H_CYCLING_MODE;
////                        hFindDirections(hMode);
////                    } catch (UnsupportedEncodingException e) {
////                        e.printStackTrace();
////                    }
////                    break;
////                case 2:
////                    try {
////                        hMode = DirectionFinder.H_WALKING_MODE;
////                        hFindDirections(hMode);
////                    } catch (UnsupportedEncodingException e) {
////                        e.printStackTrace();
////                    }
////                    break;
////            }
////        } else {
////            LogToastSnackHelper.hMakeLongToast(this, getString(R.string.enter_a_destination_first));
////        }
//
//    }

//    //Todo: change color of eta also its size
//    @Override
//    public void onTabUnselected(TabLayout.Tab tab) {
//        tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
//
//    }
//
//    @Override
//    public void onTabReselected(TabLayout.Tab tab) {
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        hGoogleMap = googleMap;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        SettingsPrefrences hSettingsPrefrences = new SettingsPrefrences(this);
//        hGoogleMap.setMapType(hSettingsPrefrences.hGetMapsType());
//        hGoogleMap.setMyLocationEnabled(true);
//        hGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
//        hGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hCurrentLatLng, 12.0f));
//
//        hBuildGoogleApiClient();
//

//    }
//
//    protected synchronized void hBuildGoogleApiClient() {
//        hFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        hGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        hGoogleApiClient.connect();
//    }
//
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }

//
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
//            SettingsPrefrences hSettingsPrefrences = new SettingsPrefrences(this);
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
}