package com.hashim.mapswithgeofencing.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;
import com.hashim.mapswithgeofencing.DirectionsApi.DirectionFinder;
import com.hashim.mapswithgeofencing.DirectionsApi.DirectionFinderListener;
import com.hashim.mapswithgeofencing.DirectionsApi.Route;
import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.GeoFenceUtil;
import com.hashim.mapswithgeofencing.Helper.LogToastSnackHelper;
import com.hashim.mapswithgeofencing.Helper.MapsUtils;
import com.hashim.mapswithgeofencing.Helper.ToolBarHelper;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.Interfaces.HDialogResponseInterface;
import com.hashim.mapswithgeofencing.Interfaces.LocationCallBackInterface;
import com.hashim.mapswithgeofencing.MapsModels.MainMapReturn;
import com.hashim.mapswithgeofencing.MapsModels.Result;
import com.hashim.mapswithgeofencing.MarkerAnimation.LatLngInterpolator;
import com.hashim.mapswithgeofencing.MarkerAnimation.MarkerAnimation;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.databinding.ActivityMapsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        LocationCallBackInterface,
        HDialogResponseInterface,
        GoogleMap.OnMarkerClickListener,
        DirectionFinderListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        PlaceSelectionListener {


    private GoogleMap hGoogleMap;
    private Location hCurrentLocation;
    private List<Marker> hOriginMarkers = new ArrayList<>();
    private List<Marker> hDestinationMarkers = new ArrayList<>();
    private List<Polyline> hPolylinePaths = new ArrayList<>();


    /*Todo : initilize R.array.search_strings*/
    String[] hSearchStrings;

    private SpotsDialog hAlertDialog;
    private Location hLastLocation;
    GoogleApiClient hGoogleApiClient;

    private FusedLocationProviderClient hFusedLocationProviderClient;
    LocationRequest hLocationRequest;
    LocationCallback hLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location hNewLocation = null;
            if (hIsPathDrawn) {

                for (Location location : locationResult.getLocations()) {
                    hNewLocation = location;
//                hMarkerAnimationInterface.hAnimateMarker(location.getLatitude(), location.getLongitude());

//                LatLng hLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                }

//hMarkerAnimationInterface.
                LatLng fromLocation = new LatLng(hCurrentLocation.getLatitude(), hCurrentLocation.getLongitude()); // Whatever origin coordinates
//            LatLng toLocation = new LatLng(hNewLocation.getLatitude(), hNewLocation.getLongitude());

                LatLng hTestLocatoin = new LatLng(Objects.requireNonNull(hNewLocation).getLatitude(), hNewLocation.getLongitude());

//            Double hlat = hTestLocatoin.latitude;
//            Double hLong = hTestLocatoin.longitude;
//
//            hlat = hlat + .02;
//            hLong = hLong + .02;


//            hTestLocatoin = new LatLng(hlat, hLong);


                // Whatever destination coordinates

//            Marker marker = hGoogleMap.addMarker(new MarkerOptions().position(fromLocation));
                MarkerAnimation.animateMarkerToICS(hCurentDogMarker, hTestLocatoin, new LatLngInterpolator.Spherical());
                hCurentDogMarker.setPosition(hTestLocatoin);


                hCurrentLocation = hNewLocation;

            }

            for (Location location : locationResult.getLocations()) {
                hNewLocation = location;
//                hMarkerAnimationInterface.hAnimateMarker(location.getLatitude(), location.getLongitude());

//                LatLng hLatLng = new LatLng(location.getLatitude(), location.getLongitude());

            }
            if (hNewLocation != null) {

                hCurrentMarker.setPosition(new LatLng(hNewLocation.getLatitude(), hNewLocation.getLongitude()));
            }


        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
        }
    };
    private String hWhatToLoad;
    private boolean hNearByPlaces = false;
    private boolean hIsPathDrawn = false;
    private int hLoadDogIcon = 93;
    private Marker hCurentDogMarker;
    private Marker hCurrentMarker;
    private boolean hIsNetworkConnected;
    private boolean hIsLocationEnabled;
    private boolean hIsNearBySearch;
    private Double hLat;
    private Double hLng;
    private GeoFenceUtil hGeoFenceUtil;
    private boolean hIsCircleDrawn = false;
    private ActivityMapsBinding hActivityMapsBinding;

    //meters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hActivityMapsBinding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(hActivityMapsBinding.getRoot());

        UIHelper.hOreoOrientationCheck(this);

        hCheckNetworkNLocationServices();


        ToolBarHelper hToolBarHelper = new ToolBarHelper(this);
        hToolBarHelper.hSetToolbar(hActivityMapsBinding.toolbar.toolbar);

//        hSetUpNavigationView();
        hGetIntentData();


        hGeoFenceUtil = new GeoFenceUtil(this);

        /*Todo initilize later*/
        AutocompleteSupportFragment hPlaceAutocompleteFragment = null;
        hPlaceAutocompleteFragment.setOnPlaceSelectedListener(this);
        if (hIsNetworkConnected && hIsLocationEnabled) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            MapsUtils hMapsUtils = new MapsUtils(this, this);
            hMapsUtils.hGetCurrentLocationCoOrdinates();
            hShowLoader();
//            if (hWhatToLoad.equals(Constants.H_CURRENT_LOCATION)) {
//
//            }
        } else {
            LogToastSnackHelper.hMakeLongToast(this, "Please Turn on Network");
        }


    }

    private void hCheckNetworkNLocationServices() {
        hCheckNetworkConnection();
        hIsLocationEnabled();
    }

    private void hCheckNetworkConnection() {
        ConnectivityManager hConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = hConnectivityManager.getActiveNetworkInfo();
        hIsNetworkConnected = (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    public void hIsLocationEnabled() {
        int locationMode = 0;
        String locationProviders;
        try {
            locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        hIsLocationEnabled = locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }


    @Override
    public void onResume() {
        super.onResume();

        UIHelper.hOreoOrientationCheck(this);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void hGetIntentData() {
        Bundle hBundle = getIntent().getExtras();
        if (hBundle.containsKey(Constants.H_MAIN_IC)) {
            hWhatToLoad = hBundle.getString(Constants.H_MAIN_IC);
        }

        if (hBundle.containsKey(Constants.H_NEAR_BY_PLACES)) {
            hWhatToLoad = String.valueOf(hBundle.getInt(Constants.H_NEAR_BY_PLACES));
            hNearByPlaces = true;
        }
        if (hBundle.containsKey(Constants.H_NEAR_BY_SEARCH_PLACES)) {
            hWhatToLoad = String.valueOf(hBundle.getInt(Constants.H_NEAR_BY_SEARCH_PLACES));
            hIsNearBySearch = true;
//            hLat = hBundle.getDouble(Constants.H_LATITUDE);
//            hLng = hBundle.getDouble(Constants.H_LONGITUDE);
        }
    }

    protected synchronized void hBuildGoogleApiClient() {
        hFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        hGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        hGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        hStopLoctionUpdates();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void hStopLoctionUpdates() {
        if (hFusedLocationProviderClient != null) {
            hFusedLocationProviderClient.removeLocationUpdates(hLocationCallback);

        }
    }

    public void hPauseFusedLocationProvider() {
        if (hFusedLocationProviderClient != null) {
            hFusedLocationProviderClient.removeLocationUpdates(hLocationCallback);
        }
    }

    public void hRequestLocationUpdates() {
        hLocationRequest = LocationRequest.create();
        hLocationRequest.setInterval(Constants.H_INTERVAL); // two minute interval
        hLocationRequest.setFastestInterval(Constants.H_FASTEST_INTERVAL);
        hLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            hFusedLocationProviderClient.requestLocationUpdates(hLocationRequest, hLocationCallback, this.getMainLooper());
        }
    }

    public void hShowLoader() {
        SpotsDialog.Builder hBuilder = new SpotsDialog.Builder().setContext(this).setMessage("Loading...");
//        new SpotsDialog.Builder().setContext(this).setMessage("Loading...").build().show();
        hAlertDialog = (SpotsDialog) hBuilder.build();
        hAlertDialog.show();
    }

    public void hHideLoader() {
        hAlertDialog.dismiss();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        hGoogleMap = googleMap;
        hGoogleMap.setOnMarkerClickListener(this);
        hGoogleMap.setTrafficEnabled(true);
    }
//    private void hSetUpNavigationView() {
//        hNavigationView.setItemIconTintList(null);
//        hNavigationView.setNavigationItemSelectedListener(this);
//
//        ActionBarDrawerToggle hToggle = new ActionBarDrawerToggle(this, hDrawerLayout, hToolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        hDrawerLayout.addDrawerListener(hToggle);
//        hToggle.syncState();
//    }

    @Override
    public void hGetCurrentLocation(Location location) {
        hHideLoader();
        hCurrentLocation = location;
        LatLng hLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        hCurrentMarker = hGoogleMap.addMarker(new MarkerOptions().position(hLatLng).title("I am here"));
        hCurrentMarker.showInfoWindow();

        hGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hLatLng, 12.0f));
        hGoogleMap.setTrafficEnabled(true);


        Double hLat = hCurrentLocation.getLatitude();
        Double hLng = hCurrentLocation.getLongitude();
        hGeoFenceUtil.hAddLocationAlert(hLat, hLng);

        if (!hIsCircleDrawn) {
            hGoogleMap.addCircle(hGeoFenceUtil.hShowVisibleGeoFence(hLat, hLng));
        }


        try {
            Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.isEmpty()) {
                //yourtextfieldname.setText("Waiting for Location");
                // markerOptions.title("Current Position");
            } else {

                if (addresses.size() > 0) {


                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                    String city = addresses.get(0).getLocality();
//                    String state = addresses.get(0).getAdminArea();
//                    String country = addresses.get(0).getCountryName();
//                    String postalCode = addresses.get(0).getPostalCode();
//                    String knownName = addresses.get(0).getFeatureName(); // On


//                    Address returnAddress = addresses.get(0);

                    UIHelper.hSetTextToTextView(hActivityMapsBinding.adressHere, address);


//                    String zipcode = returnAddress.getPostalCode();
//                    StringBuilder str = new StringBuilder();
//                    str.append(addresses.get(0).getAddressLine(1) + " " + addresses.get(0).getAddressLine(2) + " ");
//                    str.append(localityString + " ");
//                    str.append(city + " ");
//                    str.append(region_code + " ");
//                    str.append(zipcode + " ");
//                    yourtextfieldname.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
//                    markerOptions.title(str.toString());
                    //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (hNearByPlaces) {
            hShowLoader();
            hFindNearByPlaces(hSearchStrings[Integer.parseInt(hWhatToLoad)], hCurrentLocation);
        }
        if (hIsNearBySearch && hLat != null && hLng != null) {
            hSendRequest(new LatLng(hCurrentLocation.getLatitude(), hCurrentLocation.getLongitude()), new LatLng(hLat, hLng));
        }


    }


    private GeofencingRequest getGeofencingRequest(Geofence geofence) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofence(geofence);
        return builder.build();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.remove_ads_menu:
//                DialogHelper hDialogHelper = new DialogHelper(this, this);
//                hDialogHelper.hConformationDialogWithTitle(getString(R.string.remove_ads), getString(R.string.purchase_the_app),
//                        getString(R.string.purchase), getString(R.string.cancel), true, Constants.H_REMOVE_ADS_DIALOG);
//                break;
//            case R.id.rate_us_menu:
//                DialogHelper hDialogHelper1 = new DialogHelper(this, this);
//                hDialogHelper1.hConformationDialogWithTitle(getString(R.string.rate_us_1), getString(R.string.rate_us_on_playstore),
//                        getString(R.string.rate_us_1), getString(android.R.string.cancel), true, Constants.H_RATE_US_DIALOG);
//                break;
//            case R.id.menu_privacy_policy:
//                Intent hIntent = new Intent(this, NearByCategoriesActivity.class);
//                startActivity(hIntent);
//
//                break;
//            case R.id.menu_about:
//                LogToastSnackHelper.hLogField(hTag, getString(R.string.about));
//                Intent hAboutIntent = new Intent(this, AboutUsActivity.class);
//                startActivity(hAboutIntent);
//                break;
//        }
//
//        hDrawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//
//    }

    private void hFindNearByPlaces(String locationTag, Location hLocation) {
        Double hLat = hLocation.getLatitude();
        Double hLong = hLocation.getLongitude();
        String hApiRequestUrl = Constants.H_G_DIRECTIONS_API_URL
                + locationTag + Constants.H_G_LOCATION + hLat + "," + hLong +
                Constants.H_G_RADIUS + Constants.H_API_KEY;
        hCreateHttpClient(hApiRequestUrl);
    }


    private void hCreateHttpClient(String hApiRequestUrl) {
        OkHttpClient hOkHttpClient;
        hOkHttpClient = new OkHttpClient.Builder().build();
        final Request hRequest = new Request.Builder().url(hApiRequestUrl).build();
        hOkHttpClient.newCall(hRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String hJsonString = response.body().string();

                List<Result> hResultsList = new ArrayList<>();

                try {
                    JSONObject hJsonObject = new JSONObject(hJsonString);
                    JSONArray hJsonResultArray = hJsonObject.getJSONArray("results");

                    for (int i = 0; i < hJsonResultArray.length(); i++) {
                        String hArrayitem = hJsonResultArray.get(i).toString();
                        Gson hGson = new Gson();
                        Result hResult = hGson.fromJson(hArrayitem, Result.class);

                        hResultsList.add(hResult);

                    }
                    for (int i = 0; i < hResultsList.size(); i++) {
                        final LatLng hLatLng = new LatLng(hResultsList.get(i).getGeometry().getLocation().getLat(),
                                hResultsList.get(i).getGeometry().getLocation().getLng());
                        final String hName = hResultsList.get(i).getName();


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hCreateMarker(hLatLng, hWhatToLoad, hName);
                                hHideLoader();

                            }
                        });


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Gson hGson = new Gson();
                MainMapReturn hMainMapReturn = hGson.fromJson(hJsonString, MainMapReturn.class);
            }
        });

    }

    private void hCreateMarker(LatLng hLatLng, String category, String name) {
        MarkerOptions hMarkerOptions = new MarkerOptions();
        hMarkerOptions.position(hLatLng);


        Bitmap hSmallMarkerBitmap = hGetCustomMapMarker(category);


        hMarkerOptions.title(name);
        hMarkerOptions.snippet(name);
        hMarkerOptions.icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap));
        hGoogleMap.addMarker(hMarkerOptions).showInfoWindow();

    }

    private Bitmap hGetCustomMapMarker(String category) {
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(hChooseIcon(category));
        Bitmap hBitmap = bitmapdraw.getBitmap();
        return Bitmap.createScaledBitmap(hBitmap, width, height, false);
    }

    private int hChooseIcon(String category) {

        switch (Integer.parseInt(category)) {
            case 0:
                return R.drawable.atm;
            case 1:
                return R.drawable.bank;
            case 2:
                return R.drawable.hospital;
            case 3:
                return R.drawable.mosque;
            case 4:
                return R.drawable.doctor;
            case 5:
                return R.drawable.train_station;
            case 6:
                return R.drawable.parking;
            case 7:
                return R.drawable.park;
            case 8:
                return R.drawable.cafe;
            case 9:
                return R.drawable.restaurant;
            case 10:
                return R.drawable.gas_station;
            case 11:
                return R.drawable.police;
            case 12:
                return R.drawable.book_store;
            case 13:
                return R.drawable.bus_stop;
            case 14:
                return R.drawable.pharmacy;
            case 15:
                return R.drawable.clothing_store;
            case 16:
                return R.drawable.school;
            case 17:
                return R.drawable.super_market;
            case 67:
                return R.drawable.dog_icon;
            default:
                return R.drawable.atm;
        }

    }


    @Override
    public void onNegtiveResponse(int id, int dialogueType) {
        switch (dialogueType) {
            case Constants.H_RATE_US_DIALOG:
                if (id == Constants.H_DIALOG_NEGTIVE_RESPONSE) {
                    hAlertDialog.dismiss();
                }
                break;
            case Constants.H_REMOVE_ADS_DIALOG:
                if (id == Constants.H_DIALOG_NEGTIVE_RESPONSE) {
                    hAlertDialog.dismiss();
                }
                break;
        }
    }

    @Override
    public void onPostiveResponse(int id, int dialogueType) {
        switch (dialogueType) {
            case Constants.H_RATE_US_DIALOG:
                if (id == Constants.H_DIALOG_POSITIVE_RESPONSE) {
                    Intent hRateUsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + this.getPackageName()));
                    startActivity(hRateUsIntent);
                }
                break;
            case Constants.H_REMOVE_ADS_DIALOG:

                break;
        }


    }

    @Override
    public void onPostiveResponse(int which, int dialogueType, CharSequence charSequence) {

    }

    @Override
    public void onNeutralResponse(int which, int dialogueType) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        hBuildGoogleApiClient();
        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }

        if (hNearByPlaces && !hIsPathDrawn) {
            hSendRequest(new LatLng(hCurrentLocation.getLatitude(), hCurrentLocation.getLongitude()), marker.getPosition());
            hShowLoader();
        }
//        marker.showInfoWindow();
        return true;
    }


    private void hSendRequest(LatLng currentLatLng, LatLng destinationLatLng) {
        hGoogleMap.clear();

        Bitmap hSmallMarkerBitmap = hGetCustomMapMarker(hWhatToLoad);
        Bitmap hDogMarkerBitmap = hGetCustomMapMarker("67");


        hCurentDogMarker = hGoogleMap.addMarker(new MarkerOptions().
                position(currentLatLng)
                .title("Current Location")
                .icon(BitmapDescriptorFactory.fromBitmap(hDogMarkerBitmap)));
        hCurentDogMarker.showInfoWindow();

        hGoogleMap.addMarker(new MarkerOptions()
                .position(destinationLatLng).
                        title("Destination").
                        icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap)).
                        snippet("Move along highlighted path to reach destination")).
                showInfoWindow();

//        hCreateMarker(currentLatLng);
//        hCreateMarker(destinationLatLng);
        hGoogleMap.setTrafficEnabled(true);


        String origin = String.valueOf(currentLatLng.latitude).concat(",").concat(String.valueOf(currentLatLng.longitude));
        String destination = String.valueOf(destinationLatLng.latitude).concat(",").concat(String.valueOf(destinationLatLng.longitude));

//        String destination = hDestinationLatLng.toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination, DirectionFinder.H_DRIVING_MODE).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

//    private void hCreateMarker(LatLng latLng) {
//        MarkerOptions hMarkerOptions = new MarkerOptions();
//        hMarkerOptions.position(latLng);
//
//        hGoogleMap.addMarker(hMarkerOptions).showInfoWindow();
//    }

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
        //        progressDialog.dismiss();
        hPolylinePaths = new ArrayList<>();
        hOriginMarkers = new ArrayList<>();
        hDestinationMarkers = new ArrayList<>();

        hUpdatePolyLine(routes);
        hHideLoader();

        hIsPathDrawn = true;
    }


    private void hUpdatePolyLine(List<Route> routes) {
        for (Route route : routes) {
            PolylineOptions polylineOptions = new PolylineOptions().geodesic(true).color(Color.MAGENTA).width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));
            hPolylinePaths.add(hGoogleMap.addPolyline(polylineOptions));

        }
        hCurrentMarker = hCurentDogMarker;
    }


//    @Override
//    public void hAnimateMarker(double latitude, double longitude) {
//        hHideLoader();
//        if (hLastLocation == null) {
//
//            hLastLocation = hCurrentLocation;
//        }
//
////        else {
////            hLastLocation.setLatitude(latitude);
////            hLastLocation.setLongitude(longitude);
////        }
//
//
//        hCreateMarker(new LatLng(hLastLocation.getLatitude(), hLastLocation.getLongitude()));
////        LatLng fromLocation = new LatLng(hLastLocation.getLatitude(), hLastLocation.getLongitude()); // Whatever origin coordinates
////        LatLng toLocation = new LatLng(latitude, longitude); // Whatever destination coordinates
////        Marker marker = hGoogleMap.addMarker(new MarkerOptions().position(fromLocation));
////        MarkerAnimation.animateMarkerToICS(marker, toLocation, new LatLngInterpolator.Spherical());
////
////        hLastLocation.setLatitude(latitude);
////        hLastLocation.setLongitude(longitude);
//
//    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        hRequestLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /*Todo: Call Method*/
    public void hSetupLiseners() {
        hActivityMapsBinding.shareLocation.setOnClickListener(
                v -> {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("*/*");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_location) + "\n" + hCurrentLocation.getLatitude() + " , " + hCurrentLocation.getLongitude());
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                }
        );


    }

    @Override
    public void onPlaceSelected(Place place) {
        Double hLatitude = place.getLatLng().latitude;
        Double hLongitude = place.getLatLng().longitude;
        hIsNearBySearch = true;
        hNearByPlaces = true;
        hWhatToLoad = String.valueOf(90);
        hCreateMarker(new LatLng(hLatitude, hLongitude), hWhatToLoad, String.valueOf(place.getName()));
    }

    @Override
    public void onError(Status status) {

    }
}
