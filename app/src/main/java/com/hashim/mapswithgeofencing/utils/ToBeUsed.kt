/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

import android.content.Context
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException

fun hIsLocationEnabled(context: Context) {
    var hLocationEnabled: Boolean = false
    var locationMode = 0
    try {
        locationMode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
    } catch (e: SettingNotFoundException) {
        e.printStackTrace()
    }
    hLocationEnabled = locationMode != Settings.Secure.LOCATION_MODE_OFF
}


/*
*
* hGeoFenceUtil = new GeoFenceUtil(this);
*
            hGeoFenceUtil.hAddLocationAlert(hLatLng.latitude, hLatLng.longitude);
            hGoogleMap.addCircle(hGeoFenceUtil.hShowVisibleGeoFence(hLatLng.latitude, hLatLng.longitude));

*
* */



/*
*
* //            if (ApplicationClass.hIsLocationPermissionGranted) {
            hGoogleMap = googleMap;
            hGoogleMap.setMyLocationEnabled(true);
            hGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            hGoogleMap.setOnMarkerClickListener(this);
            hGoogleMap.setOnMapClickListener(this);
            hGoogleMap.setMapType(hSettingsPrefrences.hGetMapsType());
            hBuildGoogleApiClient();
* */


/*
*     private String hGeoCode(LatLng latLng) {
        String hAddress = null;
        try {
            Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses.isEmpty()) {
            } else {

                if (addresses.size() > 0) {
                    hAddress = addresses.get(0).getAddressLine(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hAddress;
    }

*
*
*
    @Override
    public boolean onMarkerClick(Marker marker) {

        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }
        if (hNearByPlaces) {
            if (marker.getTitle() != null) {
                hDestName = marker.getTitle();
            }
            if (hIsLocationRetrieved && hCurrentLocation != null) {
                hSendRequest(new LatLng(hCurrentLocation.getLatitude(), hCurrentLocation.getLongitude()), marker.getPosition());
            } else {
                LogToastSnackHelper.hMakeShortToast(this, "Unable to retive current Location, Refresh");
                UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hRefreshImageView, Constants.H_VISIBLE);
            }
        }
        return true;
    }
*
*
*
*
*
    private void hPromptSpeechInput() {
        Intent hIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        hIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        hIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        hIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        hIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        hIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale.getDefault().getLanguage().trim());
        hIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        hIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 100);
        hIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        startActivityForResult(hIntent, Constants.REQ_CODE_SPEECH_INPUT);

    }





    private void hShowDialog(int hLocationDialog) {
        switch (hLocationDialog) {
            case Constants.H_TYPE_EXIT_DIALOG:
                hDialogHelper = new DialogHelper(this, this);
                hDialogHelper.hSetDialogType(Constants.H_TYPE_EXIT_DIALOG);
                hDialogHelper.hConformationDialogWithTitle
                        ("Exit App!", "Do you Want to exit the app?", "Exit", null, "Cancel", true);
                break;
            case Constants.H_LOCATION_DIALOG:
                hDialogHelper = new DialogHelper(this, this);
                hDialogHelper.hSetDialogType(Constants.H_LOCATION_DIALOG);
                hDialogHelper.hConformationDialog("Location service is diasabled. Kindly enable it first",
                        "Ok", null, "Cancel", true);
                break;
            case Constants.H_NETWORK_DIALOG:
                hDialogHelper = new DialogHelper(this, this);
                hDialogHelper.hSetDialogType(Constants.H_NETWORK_DIALOG);
                hDialogHelper.hConformationDialog("Network connectivity is not available",
                        "Ok", "Settings", "Cancel", true);
                break;
            case Constants.H_RATE_US_DIALOG:
                hDialogHelper = new DialogHelper(this, this);
                hDialogHelper.hSetDialogType(Constants.H_RATE_US_DIALOG);
//                hDialogHelper.hConformationDialogWithTitle(getString(R.string.app_name), getString(R.string.rate_us_on_playstore),
//                        getString(R.string.rate_us_1), null, getString(android.R.string.cancel), true);
                break;
            case Constants.H_REMOVE_ADS_DIALOG:
                hDialogHelper = new DialogHelper(this, this);
                hDialogHelper.hSetDialogType(Constants.H_REMOVE_ADS_DIALOG);

//                hDialogHelper.hConformationDialogWithTitle(getString(R.string.remove_ads), getString(R.string.purchase_the_app),
//                        getString(R.string.purchase), null, getString(R.string.cancel), true);
                break;


            case Constants.H_INFO_LIST_DIALOG:
                hDialogHelper = new DialogHelper(this, this);
                hDialogHelper.hSetDialogType(Constants.H_INFO_LIST_DIALOG);
                List<String> listItems = ListUtils.hConvertArrayToArrayList(getResources().getStringArray(R.array.maps_type_array));

                final CharSequence[] charSequenceItems = listItems.toArray(new CharSequence[listItems.size()]);
                hDialogHelper.hListDialog(getString(R.string.select_a_map_type), charSequenceItems, true);
                break;

            case Constants.H_INFO_DIALOG:
                hDialogHelper = new DialogHelper(this, this);
                hDialogHelper.hSetDialogType(Constants.H_INFO_DIALOG);
                hDialogHelper.hConformationDialogWithTitle(getString(R.string.current_location_details),
                        hInfo, getString(android.R.string.ok), "Share", getString(R.string.dismiss), true);
                break;
        }
    }


    @Override
    public void onDirectionFinderStart() {
        if (hOriginMarkers != null) {
            for (Marker marker : hOriginMarkers) {
                marker.remove();
            }
        }
        if (hDestinationMarkers != null) {
            for (Marker marker : hDestinationMarkers) {
                marker.remove();
            }
        }
        if (hPolylinePaths != null) {
            for (Polyline polyline : hPolylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {

        hPolylinePaths = new ArrayList<>();
        hOriginMarkers = new ArrayList<>();
        hDestinationMarkers = new ArrayList<>();

        hUpdatePolyLine(routes);

    }

    @SuppressLint("MissingPermission")
    private void hUpdatePolyLine(List<Route> routes) {
        for (Route route : routes) {

            PolylineOptions polylineOptions = new PolylineOptions().geodesic(true).
                    color(ContextCompat.getColor(this, R.color.colorPrimary)).width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));
            hPolylinePaths.add(hGoogleMap.addPolyline(polylineOptions));

        }
        hCurrentMarker = hCurentDogMarker;
        hGoogleMap.setMyLocationEnabled(false);
    }


    @Override
    public void onPostiveResponse(int which, int dialogueType, CharSequence charSequence) {
        switch (dialogueType) {
            case Constants.H_INFO_DIALOG:

                break;
            case Constants.H_INFO_LIST_DIALOG:
                switch (which + 1) {
                    case GoogleMap.MAP_TYPE_NORMAL:
                        hGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        hSettingsPrefrences.hSaveMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case GoogleMap.MAP_TYPE_SATELLITE:
                        hGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        hSettingsPrefrences.hSaveMapType(GoogleMap.MAP_TYPE_SATELLITE);

                        break;
                    case GoogleMap.MAP_TYPE_TERRAIN:
                        hGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        hSettingsPrefrences.hSaveMapType(GoogleMap.MAP_TYPE_TERRAIN);

                        break;
                    case GoogleMap.MAP_TYPE_HYBRID:
                        hGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        hSettingsPrefrences.hSaveMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    default:
                        if (hGoogleMap.isTrafficEnabled()) {
                            hGoogleMap.setTrafficEnabled(false);
                        } else {
                            hGoogleMap.setTrafficEnabled(true);
                        }

                }
                break;
            case Constants.H_REMOVE_ADS_DIALOG:
                if (dialogueType == Constants.H_DIALOG_NEGTIVE_RESPONSE) {
                }
                break;


            case Constants.H_LOCATION_DIALOG:
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                break;

            case Constants.H_NETWORK_DIALOG:

                break;

        }

    }
* */