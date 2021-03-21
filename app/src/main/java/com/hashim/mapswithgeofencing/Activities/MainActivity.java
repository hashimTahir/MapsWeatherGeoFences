/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Activities;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.hashim.mapswithgeofencing.DirectionsApi.DirectionFinderListener;
import com.hashim.mapswithgeofencing.DirectionsApi.Route;
import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.DialogHelper;
import com.hashim.mapswithgeofencing.Helper.GeoFenceUtil;
import com.hashim.mapswithgeofencing.Helper.ListUtils;
import com.hashim.mapswithgeofencing.Helper.LogToastSnackHelper;
import com.hashim.mapswithgeofencing.Helper.MapsUtils;
import com.hashim.mapswithgeofencing.Helper.MarkerUtils;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.Interfaces.HDialogResponseInterface;
import com.hashim.mapswithgeofencing.Interfaces.LocationCallBackInterface;
import com.hashim.mapswithgeofencing.MapsModels.Result;
import com.hashim.mapswithgeofencing.Models.HLatLngModel;
import com.hashim.mapswithgeofencing.Models.VoiceReturnModel.VoiceReturnModel;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.databinding.ActivityMainBinding;
import com.hashim.mapswithgeofencing.tokotlin.SettingsPrefrences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;


public class MainActivity extends AppCompatActivity implements
        EasyPermissions.PermissionCallbacks,
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, LocationCallBackInterface,
        GoogleMap.OnMarkerClickListener, DrawerLayout.DrawerListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        DirectionFinderListener, HDialogResponseInterface, PlaceSelectionListener,
        GoogleMap.OnMapClickListener {


    private GoogleMap hGoogleMap;
    private Location hCurrentLocation;
    private Marker hCurrentMarker;
    private GeoFenceUtil hGeoFenceUtil;
    private SettingsPrefrences hSettingsPrefrences;
    GoogleApiClient hGoogleApiClient;
    private FusedLocationProviderClient hFusedLocationProviderClient;
    private String hWhatToLoad;
    private boolean hNearByPlaces = false;
    private Marker hCurentDogMarker;
    private List<Marker> hOriginMarkers = new ArrayList<>();
    private List<Marker> hDestinationMarkers = new ArrayList<>();
    private List<Polyline> hPolylinePaths = new ArrayList<>();
    private String hInfo;
    private AutocompleteSupportFragment hPlaceAutocompleteFragment;
    private BottomSheetBehavior hBottomSheetBehavior;
    private SpotsDialog hAlertDialog;
    private boolean hIsNetworkConnected;
    private boolean hIsLocationEnabled;
    private MapsUtils hMapsUtils;
    private DialogHelper hDialogHelper;
    Handler hHandler;
    private String hDestName = null;
    private LatLng hDestLatLng;
    private boolean hIsPlaceSelected;
    private ImageView hHamburerIcon;
    private boolean hDrawerIsOpened;
    private final int H_LOCATION_PERMISSION_CODE = 223;
    private final int H_WRITE_PERMISSION_CODE = 222;
    private boolean hIsLocationRetrieved = false;
    private boolean hIsVoiceCommad = false;
    private static final String hTag = LogToastSnackHelper.hMakeTag(MainActivity.class);
    private VoiceReturnModel hVoiceReturnModel;
    private ActivityMainBinding hActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(hActivityMainBinding.getRoot());

        UIHelper.hOreoOrientationCheck(this);

        hCheckNetworkConnection();
        hIsLocationEnabled();

        hHandler = new Handler();
        hInitView();
        hSettingsPrefrences = new SettingsPrefrences(this);


        hCheckAppCounter();

    }

    private void hPermissionCheck(String permissionType, String task) {
        switch (permissionType) {
            case Constants.H_LOCATION_PERMISSION:
                if (EasyPermissions.hasPermissions(this, Constants.H_LOCATION_PERMISSION)) {
                    if (hIsLocationEnabled && hIsNetworkConnected) {
                        switch (task) {
                            case Constants.H_CHECK_STRING:
                                hRunCore();
                                break;
                            case Constants.H_NEAR_BY_PLACES_PC:
                                hViewAllActivity();

                                break;
                            case Constants.H_CALCULATE_ROUTE_FALSE_PC:
                                hStartCalculateRouteActivity(false);

                                break;
                            case Constants.H_CALCULATE_ROUTE_TRUE_PC:
                                hStartCalculateRouteActivity(true);

                                break;
                            case Constants.H_SHARE_LOCATION_PC:
                                hShareLocationLayout();
                                break;
                            case Constants.H_VIEW_ALL_PC:
                                hViewAllActivity();

                                break;
                            case Constants.H_WEATHER_PC:
                                hStartWeatherActivity();

                                break;
                            default:
                                hAllocateTasks(task);
                                break;
                        }
                    } else {
                        if (!hIsLocationEnabled) {
                            hShowDialog(Constants.H_LOCATION_DIALOG);
                        }
                        if (!hIsNetworkConnected) {
                            hShowDialog(Constants.H_NETWORK_DIALOG);
                        }
                    }
                } else {
                    hAskForPermissions(Constants.H_LOCATION_PERMISSION);
                }
                break;
            case Constants.H_WRITE_PERMISSION:
                if (EasyPermissions.hasPermissions(this, Constants.H_WRITE_PERMISSION)) {
                    hShareApp();
                } else {
                    hAskForPermissions(Constants.H_WRITE_PERMISSION);
                }
                break;
        }
    }

    private void hAllocateTasks(String task) {
        if (hIsLocationRetrieved && hCurrentLocation != null) {
            if (Constants.H_ATM == Integer.parseInt(task)) {
                hFindNearByPlaces("atm", hCurrentLocation, String.valueOf(Constants.H_ATM));
            } else if (Constants.H_BANK == Integer.parseInt(task)) {
                hFindNearByPlaces("bank", hCurrentLocation, String.valueOf(Constants.H_BANK));
            } else if (Constants.H_POLICE == Integer.parseInt(task)) {
                hFindNearByPlaces("police", hCurrentLocation, String.valueOf(Constants.H_POLICE));
            } else if (Constants.H_MOSQUE == Integer.parseInt(task)) {
                hFindNearByPlaces("mosque", hCurrentLocation, String.valueOf(Constants.H_MOSQUE));
            } else if (Constants.H_BUS_STOP == Integer.parseInt(task)) {
                hFindNearByPlaces("bus_station", hCurrentLocation, String.valueOf(Constants.H_BUS_STOP));
            } else if (Constants.H_HOSPITAL == Integer.parseInt(task)) {
                hFindNearByPlaces("hospital", hCurrentLocation, String.valueOf(Constants.H_HOSPITAL));
            } else if (Constants.H_CAFE == Integer.parseInt(task)) {
                hFindNearByPlaces("cafe", hCurrentLocation, String.valueOf(Constants.H_CAFE));
            }
        } else {
            LogToastSnackHelper.hMakeShortToast(this, "Unable to retive current Location, Refresh");
            UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hRefreshImageView, Constants.H_VISIBLE);
        }
    }

    private void hCheckAppCounter() {
//        ApplicationClass.hAppCounter++;
//        hSettingsPrefrences.hSaveAppCounter(ApplicationClass.hAppCounter);
//        if (ApplicationClass.hAppCounter % 4 == 0) {
//            hDialogHelper = new DialogHelper(this, this);
//            hDialogHelper.hSetDialogType(Constants.H_RATE_US_DIALOG);
//            hDialogHelper.hConformationDialogWithTitle(getString(R.string.app_name), getString(R.string.if_you_like),
//                    getString(R.string.give_feedback), getString(R.string.no_thanks), getString(R.string.remind_me_later), true);
//        }
    }

    private void hAskForPermissions(String permission) {

        switch (permission) {
            case Constants.H_LOCATION_PERMISSION:
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this, H_LOCATION_PERMISSION_CODE, permission)
                                .setRationale("Grant the necessary Permissions")
                                .setPositiveButtonText("ok")
                                .setNegativeButtonText("Cancel")
                                .build());


                break;
            case Constants.H_WRITE_PERMISSION:
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this, H_WRITE_PERMISSION_CODE, permission)
                                .setRationale("Grant the necessary Permissions")
                                .setPositiveButtonText("ok")
                                .setNegativeButtonText("Cancel")
                                .build());
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    private void hRunCore() {
        UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hRefreshImageView, Constants.H_INVISIBLE);

        //initilize map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        hMapsUtils = new MapsUtils(this, this);
        hMapsUtils.hGetCurrentLocationCoOrdinates();

        hShowLoader();
        hGeoFenceUtil = new GeoFenceUtil(this);

    }

    @Override
    public void onBackPressed() {
        hShowDialog(Constants.H_TYPE_EXIT_DIALOG);

    }

    @Override
    protected void onResume() {
        super.onResume();

        UIHelper.hOreoOrientationCheck(this);

        hIsLocationEnabled();
        hCheckNetworkConnection();


        if (!hIsLocationRetrieved || hCurrentLocation == null) {
            hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_CHECK_STRING);
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ApplicationClass.hIsLocationPermissionGranted) {
            if (hIsLocationEnabled && hIsNetworkConnected) {
                if (hPlaceAutocompleteFragment != null) {
                    hPlaceAutocompleteFragment.setText("");
                }
                if (!hIsPlaceSelected) {
                    UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hRefreshImageView, Constants.H_INVISIBLE);
                    if (hCurrentLocation != null) {
                        hGoogleMap.clear();
                        Bitmap hSmallMarkerBitmap = MarkerUtils.hGetCustomMapMarker(this, String.valueOf(Constants.H_CURRENT_MARKER));


                        hCurrentMarker = hGoogleMap.addMarker(new MarkerOptions().
                                position(new LatLng(hCurrentLocation.getLatitude(), hCurrentLocation.getLongitude()))
                                .title(getString(R.string.current_location))
                                .icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap)));
                        hCurrentMarker.showInfoWindow();
                        UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hBoottomSheet.bottomSheet, Constants.H_VISIBLE);
                        UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hDetailCardView, Constants.H_INVISIBLE);

                    }
                }
//                }

                if (hDrawerIsOpened) {
                    UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hBoottomSheet.bottomSheet, Constants.H_INVISIBLE);
                } else {

                    hBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        } else {
            if (hIsLocationEnabled && hIsNetworkConnected) {
                if (hPlaceAutocompleteFragment != null) {
                    hPlaceAutocompleteFragment.setText("");

                }
                if (!hIsPlaceSelected) {
                    UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hRefreshImageView, Constants.H_INVISIBLE);
                    if (hCurrentLocation != null) {
                        hGoogleMap.clear();
                        Bitmap hSmallMarkerBitmap = MarkerUtils.hGetCustomMapMarker(this, String.valueOf(Constants.H_CURRENT_MARKER));


                        hCurrentMarker = hGoogleMap.addMarker(new MarkerOptions().
                                position(new LatLng(hCurrentLocation.getLatitude(), hCurrentLocation.getLongitude()))
                                .title(getString(R.string.current_location))
                                .icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap)));
                        hCurrentMarker.showInfoWindow();
                        UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hBoottomSheet.bottomSheet, Constants.H_VISIBLE);
                        UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hDetailCardView, Constants.H_INVISIBLE);


                    }
                }
            }


            if (hDrawerIsOpened) {
                UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hBoottomSheet.bottomSheet, Constants.H_INVISIBLE);
            } else {

                hBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }

        }
        hIsVoiceCommad = false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
//            case R.id.remove_ads_menu:
//                break;
//            case R.id.rate_us_menu:
//                hShowDialog(Constants.H_RATE_US_DIALOG);
//                break;
//            case R.id.menu_privacy_policy:
//                hShareIntent(Constants.H_PRIVACY_POLICY_URL);
//                break;
            case R.id.nav_currentLocationInfo:
                hShowDialog(Constants.H_INFO_DIALOG);
                break;
            case R.id.nav_nearbyPlaces:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_NEAR_BY_PLACES_PC);
                break;
            case R.id.nav_weather:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_WEATHER_PC);
                break;
            case R.id.nav_share_location:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_SHARE_LOCATION_PC);
                break;
            case R.id.nav_findRoute:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_CALCULATE_ROUTE_FALSE_PC);

                break;
            case R.id.nav_compass:
                startActivity(new Intent(this, CompassActivity.class));

                break;

//            case R.id.nav_ContactUs:
//                break;
//            case R.id.share_app:
//                hPermissionCheck(Constants.H_WRITE_PERMISSION, Constants.H_SHARE_APP);
//                break;
            case R.id.menu_settings:

                hLoadSettings();
                break;
        }

        hActivityMainBinding.ctDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hShareIntent(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void hShareApp() {
        hCreateBitmapnShare();
    }

    private void hCreateBitmapnShare() {
        Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher), 100, 100, false);
        File sd = Environment.getExternalStorageDirectory();
        String fileName = "ic_launcher.png";
        File dest = new File(sd, fileName);
        try {
            FileOutputStream out;
            out = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {

        }
        String completePath = Environment.getExternalStorageDirectory() + "/" + fileName;

        File file = new File(completePath);
        Uri imageUri = FileProvider.getUriForFile(this,
                getApplicationContext().getPackageName() + ".my.package.name.provider", file);

        Intent hShareIntent = new Intent(Intent.ACTION_SEND);
        hShareIntent.setType("*/*");
        hShareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));

        String hAppLink = "https://play.google.com/store/apps/details?id=".concat(getPackageName());
//        hShareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_body) + "\n" + hAppLink);
        hShareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        hShareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(hShareIntent, "Share via"));
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

//            if (ApplicationClass.hIsLocationPermissionGranted) {
            hGoogleMap = googleMap;
            hGoogleMap.setMyLocationEnabled(true);
            hGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            hGoogleMap.setOnMarkerClickListener(this);
            hGoogleMap.setOnMapClickListener(this);
            hGoogleMap.setMapType(hSettingsPrefrences.hGetMapsType());
            hBuildGoogleApiClient();
        }
    }
//            }
//        } else {
//            hGoogleMap = googleMap;
//            hGoogleMap.setMyLocationEnabled(true);
//            hGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
//            hGoogleMap.setOnMarkerClickListener(this);
//            hGoogleMap.setOnMapClickListener(this);
//            hGoogleMap.setMapType(hSettingsPrefrences.hGetMapsType());
//            hBuildGoogleApiClient();
//        }

    @Override
    public void hGetCurrentLocation(Location hLocation) {
        hHandler.postDelayed(() ->
                hHideLoader(), Constants.H_1Secs_Timer);
        if (hLocation != null) {
            hIsLocationRetrieved = true;
            hCurrentLocation = hLocation;
            LatLng hLatLng = new LatLng(hCurrentLocation.getLatitude(), hCurrentLocation.getLongitude());
            Bitmap hSmallMarkerBitmap = MarkerUtils.hGetCustomMapMarker(this, String.valueOf(Constants.H_CURRENT_MARKER));

            hCurrentMarker = hGoogleMap.addMarker(new MarkerOptions().
                    position(hLatLng)
                    .title(getString(R.string.you_are_here))
                    .icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap)));
            hCurrentMarker.showInfoWindow();

            hGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hLatLng, 12.0f));


            hGeoFenceUtil.hAddLocationAlert(hLatLng.latitude, hLatLng.longitude);
            hGoogleMap.addCircle(hGeoFenceUtil.hShowVisibleGeoFence(hLatLng.latitude, hLatLng.longitude));


            hInfo = hGeoCode(hLatLng);

            HLatLngModel hLatLngModel = new HLatLngModel();
            hLatLngModel.setLatitude(String.valueOf(hLatLng.latitude));
            hLatLngModel.setLongitude(String.valueOf(hLatLng.longitude));
            hSettingsPrefrences.hSaveCurrentLocation(hLatLngModel);

        } else {
            hIsLocationRetrieved = false;
            hMapsUtils.hGetCurrentLocationCoOrdinates();
        }
    }

    private String hGeoCode(LatLng latLng) {
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
    protected void onDestroy() {
        super.onDestroy();
    }

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

    private void hSendRequest(LatLng currentLatLng, LatLng destinationLatLng) {
        hGoogleMap.clear();

        Bitmap hSmallMarkerBitmap = MarkerUtils.hGetCustomMapMarker(this, String.valueOf(Constants.H_DEST_MARKER));
        Bitmap hDogMarkerBitmap = MarkerUtils.hGetCustomMapMarker(this, String.valueOf(Constants.H_CURRENT_MARKER));


        hCurentDogMarker = hGoogleMap.addMarker(new MarkerOptions().
                position(currentLatLng)
                .title(getString(R.string.current_location))
                .icon(BitmapDescriptorFactory.fromBitmap(hDogMarkerBitmap)));
        hCurentDogMarker.showInfoWindow();

        hGoogleMap.addMarker(new MarkerOptions()
                .position(destinationLatLng).
                        title(getString(R.string.destination)).
                        icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap)));

        hDestLatLng = new LatLng(destinationLatLng.latitude, destinationLatLng.longitude);

        hShowCardHideBottomSheet(hGeoCode(hDestLatLng));
    }


    private void hFindNearByPlaces(String locationTag, Location hLocation, String whatToLoad) {
        hShowLoader();
        hNearByPlaces = true;
        hWhatToLoad = whatToLoad;
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
                hHandler.post(() -> hGoogleMap.clear());

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

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                for (int i = 0; i < hResultsList.size(); i++) {
                    final LatLng hLatLng = new LatLng(hResultsList.get(i).getGeometry().getLocation().getLat(),
                            hResultsList.get(i).getGeometry().getLocation().getLng());
                    final String hName = hResultsList.get(i).getName();


                    hHandler.post(() -> {
                        hCreateMarker(hLatLng, hWhatToLoad, hName);
                    });


                }
                hHandler.postDelayed(() -> {
                    hHideLoader();

                }, Constants.H_1Secs_Timer);
            }
        });
    }

    private void hCreateMarker(LatLng hLatLng, String category, String name) {
        MarkerOptions hMarkerOptions = new MarkerOptions();
        hMarkerOptions.position(hLatLng);


        Bitmap hSmallMarkerBitmap = MarkerUtils.hGetCustomMapMarker(this, category);


        hMarkerOptions.title(name);
        hMarkerOptions.snippet(name);
        hMarkerOptions.icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap));
        hGoogleMap.addMarker(hMarkerOptions).showInfoWindow();

    }


    private void hInitView() {

        hActivityMainBinding.hGetDirectionsB.setBackgroundTintList
                (ColorStateList.valueOf(ContextCompat.getColor(this, R.color.share_loc_color)));

        //setup autocomplete fragment
//        hPlaceAutocompleteFragment
        hPlaceAutocompleteFragment.setOnPlaceSelectedListener(this);

        // set listeners for hamburger icon and cancel search
        hHamburerIcon = (ImageView) ((LinearLayout) hPlaceAutocompleteFragment.getView()).getChildAt(0);
        ImageView hCancelSearch = (ImageView) ((LinearLayout) hPlaceAutocompleteFragment.getView()).getChildAt(2);
        hHamburerIcon.setImageDrawable(getResources().getDrawable(R.drawable.hamburger));
        hCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hDetailCardView, Constants.H_INVISIBLE);
                UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hBoottomSheet.bottomSheet, Constants.H_VISIBLE);
                hPlaceAutocompleteFragment.setText("");

                if (hCurrentLocation != null) {


                    hGoogleMap.clear();
                    Bitmap hCurrentMarker = MarkerUtils.hGetCustomMapMarker(MainActivity.this,
                            String.valueOf(Constants.H_CURRENT_MARKER));
                    hGoogleMap.addMarker(new MarkerOptions().
                            position(new LatLng(hCurrentLocation.getLatitude(),
                                    hCurrentLocation.getLongitude())).icon(BitmapDescriptorFactory.fromBitmap(hCurrentMarker)));
                }
            }
        });

        hHamburerIcon.setOnClickListener(v -> {
            if (hActivityMainBinding.ctDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                hActivityMainBinding.ctDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                hActivityMainBinding.ctDrawerLayout.openDrawer(GravityCompat.START);

            }
        });


        String locale = getResources().getConfiguration().locale.getCountry();

        String locale1 = getResources().getConfiguration().locale.getDisplayCountry();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        hActivityMainBinding.ctDrawerLayout.addDrawerListener(this);

        hActivityMainBinding.ctNavgationView.setItemIconTintList(null);
        hActivityMainBinding.ctNavgationView.setNavigationItemSelectedListener(this);
        hBottomSheetBehavior = BottomSheetBehavior.from(hActivityMainBinding.hBoottomSheet.bottomSheet);
        hBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset <= -1.0) {
                    hBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        FloatingActionButton hShareLoactionFb =
                hActivityMainBinding.hBoottomSheet.fButtonLayout1.hFloatingActionButton;
        FloatingActionButton hFindRouteFb = hActivityMainBinding.hBoottomSheet.fButtonLayout2.hFloatingActionButton;
        FloatingActionButton hTrackMeFb = hActivityMainBinding.hBoottomSheet.fButtonLayout3.hFloatingActionButton;
        FloatingActionButton hInfoFb = hActivityMainBinding.hBoottomSheet.fButtonLayout4.hFloatingActionButton;
        FloatingActionButton hAtmsFb = hActivityMainBinding.hBoottomSheet.fButtonLayout5.hFloatingActionButton;
        FloatingActionButton hBanksFb = hActivityMainBinding.hBoottomSheet.fButtonLayout6.hFloatingActionButton;
        FloatingActionButton hPoliceFb = hActivityMainBinding.hBoottomSheet.fButtonLayout7.hFloatingActionButton;
        FloatingActionButton hMosqueFB = hActivityMainBinding.hBoottomSheet.fButtonLayout8.hFloatingActionButton;
        FloatingActionButton hBusStationFb = hActivityMainBinding.hBoottomSheet.fButtonLayout9.hFloatingActionButton;
        FloatingActionButton hHospitalFb = hActivityMainBinding.hBoottomSheet.fButtonLayout10.hFloatingActionButton;
        FloatingActionButton hCafeFb = hActivityMainBinding.hBoottomSheet.fButtonLayout11.hFloatingActionButton;
        FloatingActionButton hViewAllFb = hActivityMainBinding.hBoottomSheet.fButtonLayout12.hFloatingActionButton;
        FloatingActionButton hSettingsFB = hActivityMainBinding.hBoottomSheet.hSettingsLayout.hFloatingActionButton;
        FloatingActionButton hCompassFB = hActivityMainBinding.hBoottomSheet.hCompassLayout.hFloatingActionButton;
        FloatingActionButton hRateUsFb = hActivityMainBinding.hBoottomSheet.hRateAppLayout.hFloatingActionButton;
        FloatingActionButton hVoiceCommandFB = hActivityMainBinding.hBoottomSheet.hContactsLayout.hFloatingActionButton;

        AppCompatTextView hRateUsTv = hActivityMainBinding.hBoottomSheet.hRateAppLayout.hButtonTexts;

        AppCompatTextView hSettingsTv = hActivityMainBinding.hBoottomSheet.hSettingsLayout.hButtonTexts;
        AppCompatTextView hCompass = hActivityMainBinding.hBoottomSheet.hCompassLayout.hButtonTexts;
        AppCompatTextView hAppCompatTextView1 = hActivityMainBinding.hBoottomSheet.fButtonLayout1.hButtonTexts;
        AppCompatTextView hAppCompatTextView2 = hActivityMainBinding.hBoottomSheet.fButtonLayout2.hButtonTexts;
        AppCompatTextView hAppCompatTextView3 = hActivityMainBinding.hBoottomSheet.fButtonLayout3.hButtonTexts;
        AppCompatTextView hAppCompatTextView4 = hActivityMainBinding.hBoottomSheet.fButtonLayout4.hButtonTexts;
        AppCompatTextView hAtmsTextView = hActivityMainBinding.hBoottomSheet.fButtonLayout5.hButtonTexts;
        AppCompatTextView hBanksTextView = hActivityMainBinding.hBoottomSheet.fButtonLayout6.hButtonTexts;
        AppCompatTextView hPoliceTv = hActivityMainBinding.hBoottomSheet.fButtonLayout7.hButtonTexts;
        AppCompatTextView hMosqueTv = hActivityMainBinding.hBoottomSheet.fButtonLayout8.hButtonTexts;
        AppCompatTextView hBusStationTextView = hActivityMainBinding.hBoottomSheet.fButtonLayout9.hButtonTexts;
        AppCompatTextView hHospitalsTextView = hActivityMainBinding.hBoottomSheet.fButtonLayout10.hButtonTexts;
        AppCompatTextView hCafesTextView = hActivityMainBinding.hBoottomSheet.fButtonLayout11.hButtonTexts;
        AppCompatTextView hViewAllTextView = hActivityMainBinding.hBoottomSheet.fButtonLayout12.hButtonTexts;
        AppCompatTextView hVoiceCommadTv = hActivityMainBinding.hBoottomSheet.hContactsLayout.hButtonTexts;
        hShareLoactionFb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.share_loc_color))));
        hFindRouteFb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.find_route_color))));
        hTrackMeFb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.weather_button_color))));
        hInfoFb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.info_color))));
        hAtmsFb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.atm_color))));
        hBanksFb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.bank_color))));
        hPoliceFb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.police_color))));
        hMosqueFB.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.mosque_color))));
        hBusStationFb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.bus_station_color))));
        hHospitalFb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.hospital_color))));
        hCafeFb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.cafe_color))));
        hViewAllFb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.view_all_color))));
        hSettingsFB.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.settings_color))));
        hRateUsFb.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.rate_color))));
        hCompassFB.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color.rate_color))));
        hVoiceCommandFB.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, (R.color
                .share_loc_color))));
        hTrackMeFb.setImageResource(R.drawable.weather);
        hInfoFb.setImageResource(R.drawable.ic_info);
        hFindRouteFb.setImageResource(R.drawable.ic_findroute);
        hShareLoactionFb.setImageResource(R.drawable.ic_sharelocation);
        hAtmsFb.setImageResource(R.drawable.ic_atm);
        hBanksFb.setImageResource(R.drawable.ic_bank);
        hPoliceFb.setImageResource(R.drawable.ic_policestation);
        hMosqueFB.setImageResource(R.drawable.ic_mosque);
        hBusStationFb.setImageResource(R.drawable.ic_busstop);
        hHospitalFb.setImageResource(R.drawable.ic_hospital);
        hCafeFb.setImageResource(R.drawable.ic_cafe);
        hViewAllFb.setImageResource(R.drawable.ic_viewall);
        hSettingsFB.setImageResource(R.drawable.ic_settings);
        hRateUsFb.setImageResource(R.drawable.ic_rateus);
        hCompassFB.setImageResource(R.drawable.compass);
        hVoiceCommandFB.setImageResource(R.drawable.ic_mic);

//        UIHelper.hSetTextToTextView(hAppCompatTextView1, getString(R.string.share_location_fb));
        UIHelper.hSetTextToTextView(hAppCompatTextView2, getString(R.string.find_route));
        UIHelper.hSetTextToTextView(hAppCompatTextView3, getString(R.string.weather));
        UIHelper.hSetTextToTextView(hAppCompatTextView4, getString(R.string.info));
        UIHelper.hSetTextToTextView(hAtmsTextView, getString(R.string.atms));
        UIHelper.hSetTextToTextView(hBanksTextView, getString(R.string.banks));
        UIHelper.hSetTextToTextView(hPoliceTv, getString(R.string.police_staions));
        UIHelper.hSetTextToTextView(hMosqueTv, getString(R.string.mosque));
        UIHelper.hSetTextToTextView(hBusStationTextView, getString(R.string.bus_staions));
        UIHelper.hSetTextToTextView(hHospitalsTextView, getString(R.string.hospitals));
        UIHelper.hSetTextToTextView(hCafesTextView, getString(R.string.cafe));
        UIHelper.hSetTextToTextView(hViewAllTextView, getString(R.string.view_all));
        UIHelper.hSetTextToTextView(hRateUsTv, getString(R.string.rate_us));
        UIHelper.hSetTextToTextView(hSettingsTv, getString(R.string.settings));
        UIHelper.hSetTextToTextView(hCompass, getString(R.string.compass));
        UIHelper.hSetTextToTextView(hVoiceCommadTv, getString(R.string.voice_navigation));
    }

    private void hSetupLiseners() {
        /*
        * {R.id.fButtonLayout1, R.id.fButtonLayout2, R.id.fButtonLayout3, R.id.fButtonLayout4,
            R.id.fButtonLayout5, R.id.fButtonLayout6, R.id.fButtonLayout7, R.id.fButtonLayout8,
            R.id.fButtonLayout9, R.id.fButtonLayout10, R.id.fButtonLayout11, R.id.fButtonLayout12,
            R.id.hEmergentTextLayout, R.id.hRateAppLayout, R.id.hSettingsLayout, R.id.hNormalChip,
            R.id.hSatelliteChip, R.id.hMoreChip, R.id.hGoToLocation, R.id.hGoToMyLoaction, R.id.hCompassLayout,
            R.id.hRateUsFb, R.id.hRefreshImageView, R.id.hGetDirectionsB, R.id.hContactsLayout}
        * */

        /*
        *  case R.id.hGetDirectionsB:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_CALCULATE_ROUTE_TRUE_PC);
                break;
            case R.id.fButtonLayout1:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_SHARE_LOCATION_PC);
                break;
            case R.id.fButtonLayout2:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_CALCULATE_ROUTE_FALSE_PC);
                break;
            case R.id.fButtonLayout3:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_WEATHER_PC);
                break;
            case R.id.fButtonLayout4:
                hShowDialog(Constants.H_INFO_DIALOG);
                break;
            case R.id.fButtonLayout5:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, String.valueOf(Constants.H_ATM));
                break;
            case R.id.fButtonLayout6:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, String.valueOf(Constants.H_BANK));
                break;
            case R.id.fButtonLayout7:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, String.valueOf(Constants.H_POLICE));
                break;
            case R.id.fButtonLayout8:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, String.valueOf(Constants.H_MOSQUE));
                break;
            case R.id.fButtonLayout9:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, String.valueOf(Constants.H_BUS_STOP));
                break;
            case R.id.fButtonLayout10:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, String.valueOf(Constants.H_HOSPITAL));
                break;
            case R.id.fButtonLayout11:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, String.valueOf(Constants.H_CAFE));
                break;
            case R.id.fButtonLayout12:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_VIEW_ALL_PC);
                break;
            case R.id.hEmergentTextLayout:
                break;
            case R.id.hRateAppLayout:
                hShowDialog(Constants.H_RATE_US_DIALOG);
                break;
            case R.id.hSettingsLayout:
                hLoadSettings();

                break;
            case R.id.hNormalChip:
                hGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                hSettingsPrefrences.hSaveMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.hSatelliteChip:
                hGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                hSettingsPrefrences.hSaveMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.hMoreChip:
                hShowDialog(Constants.H_INFO_LIST_DIALOG);
                break;
            case R.id.hGoToLocation:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_CALCULATE_ROUTE_FALSE_PC);
                break;
            case R.id.hGoToMyLoaction:
                if (hCurrentLocation != null) {
                    hGoogleMap.setMyLocationEnabled(true);
                    hGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                            (new LatLng(hCurrentLocation.getLatitude(), hCurrentLocation.getLongitude()), 10f));
                } else {
                    hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_CHECK_STRING);
                }
                break;
            case R.id.hRateUsFb:
                hShowDialog(Constants.H_RATE_US_DIALOG);
                break;
            case R.id.hRefreshImageView:
                onResume();
                break;
            case R.id.hCompassLayout:
                startActivity(new Intent(this, CompassActivity.class));
                break;
            case R.id.hContactsLayout:
                hPromptSpeechInput();

                break;
        * */
    }


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.H_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    if (hIsLocationRetrieved && hCurrentLocation != null) {
                        int hLocationTag = data.getExtras().getInt(Constants.H_LOCATION_TAG);
                        List<String> hSearchStringList = ListUtils.hConvertArrayToArrayList
                                (getResources().getStringArray(R.array.search_strings));
                        hFindNearByPlaces(hSearchStringList.get(hLocationTag), hCurrentLocation, String.valueOf(hLocationTag));
                    } else {
                        LogToastSnackHelper.hMakeShortToast(this, "Unable to retive current Location, Refresh");
                        UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hRefreshImageView, Constants.H_VISIBLE);
                    }
                }
            case Constants.REQ_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> hResultText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    hDestName = hResultText.get(0);
                    LogToastSnackHelper.hLogField(hTag, hResultText.get(0));


                    String hUrl = null;
                    try {
                        hUrl = "https://maps.googleapis" +
                                ".com/maps/api/place/findplacefromtext/json?input=" + URLEncoder.encode(hDestName, "UTF-8") +
                                "&inputtype=textquery&fields=formatted_address,name,geometry" + "&key="
                                + Constants.H_API_KEY;
                        LogToastSnackHelper.hLogField(hTag, "url is ".concat(hUrl));

                        new FindLocationCordinatesTask().execute(hUrl);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                }
                break;
        }
    }

    private void hStartCalculateRouteActivity(boolean fromSearch) {
        if (hIsLocationRetrieved && hCurrentLocation != null) {
            Intent hIntent = new Intent(this, CalculateActivity.class);
            hIntent.putExtra(Constants.H_LATITUDE, hCurrentLocation.getLatitude());
            hIntent.putExtra(Constants.H_LONGITUDE, hCurrentLocation.getLongitude());
            hIntent.putExtra(Constants.H_FROM_SEARCH_IC, fromSearch);
            if (fromSearch && hDestName != null && hDestLatLng != null) {
                hIntent.putExtra(Constants.H_CURRENT_LOCATION_NAME, hInfo);
                hIntent.putExtra(Constants.H_LOC_NAME, hDestName);
                hIntent.putExtra(Constants.H_DEST_LAT, hDestLatLng.latitude);
                hIntent.putExtra(Constants.H_DEST_LNG, hDestLatLng.longitude);
            }
            if (hIsVoiceCommad) {
                hIntent.putExtra(Constants.H_LOC_NAME, hDestName);
                hIntent.putExtra(Constants.H_VOICE_COMMAD, hIsVoiceCommad);
                hIntent.putExtra(Constants.H_DEST_LAT, hVoiceReturnModel.getCandidates().get(0).getGeometry().getLocation().getLat());
                hIntent.putExtra(Constants.H_DEST_LNG, hVoiceReturnModel.getCandidates().get(0).getGeometry().getLocation().getLng());
            }
            startActivity(hIntent);
        } else {
            LogToastSnackHelper.hMakeShortToast(this, "Unable to retive current Location, Refresh");
            UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hRefreshImageView, Constants.H_VISIBLE);
        }
    }

    private void hStartWeatherActivity() {
//        if (hIsLocationRetrieved && hCurrentLocation != null) {
//            Intent hIntent = new Intent(this, WeatherActivity.class);
//            hIntent.putExtra(Constants.H_LATITUDE, hCurrentLocation.getLatitude());
//            hIntent.putExtra(Constants.H_LONGITUDE, hCurrentLocation.getLongitude());
//            startActivity(hIntent);
//        } else {
//            LogToastSnackHelper.hMakeShortToast(this, "Unable to retive current Location");
//            UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hRefreshImageView, Constants.H_VISIBLE);
//        }
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

    private void hViewAllActivity() {
        Intent hIntent = new Intent(MainActivity.this, ViewAllActivity.class);
        startActivityForResult(hIntent, Constants.H_REQUEST_CODE);
    }

    public void hShowLoader() {
        SpotsDialog.Builder hBuilder = new SpotsDialog.Builder().setContext(this).setMessage("Loading...");
        hAlertDialog = (SpotsDialog) hBuilder.build();
        hAlertDialog.show();
    }

    public void hHideLoader() {
        hAlertDialog.dismiss();
        hBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }


    private void hLoadSettings() {
        Intent hSettingsIntent1 = new Intent(this, SettingsActivity.class);
        startActivity(hSettingsIntent1);
    }

    private void hTrackMe() {
        Intent hSettingsIntent = new Intent(this, SettingsActivity.class);
        hSettingsIntent.putExtra(Constants.H_SETTINGS_IC, Constants.H_TRACK_ME);
        startActivity(hSettingsIntent);
    }

    private void hFindRoute() {

        hBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        final View root = hActivityMainBinding.placeAutocompleteCard.getRootView();
        root.post(new Runnable() {
            @Override
            public void run() {
//                root.findViewById(R.id.place_autocomplete_search_input)
//                        .performClick();
            }
        });
    }


    private void hShareLocationLayout() {
        if (hIsLocationRetrieved && hCurrentLocation != null) {


            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("*/*");
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_location) +
                    "\n" + hInfo + "\n" + Constants.H_LOCATION_CONST +
                    hCurrentLocation.getLatitude() + "," + hCurrentLocation.getLongitude());
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        } else {
            LogToastSnackHelper.hMakeShortToast(this, "Unable to retive current Location, Refresh");
            UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hRefreshImageView, Constants.H_VISIBLE);
        }
    }


    @Override
    public void onDrawerSlide(@NonNull View view, float v) {

    }

    @Override
    public void onDrawerOpened(@NonNull View view) {
        hDrawerIsOpened = true;
        UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hBoottomSheet.bottomSheet, Constants.H_INVISIBLE);
    }

    @Override
    public void onDrawerClosed(@NonNull View view) {
        hDrawerIsOpened = false;
        UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hBoottomSheet.bottomSheet, Constants.H_VISIBLE);
    }

    @Override
    public void onDrawerStateChanged(int i) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

        hHideLoader();
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
    public void onNegtiveResponse(int id, int dialogueType) {

    }

    @Override
    public void onPostiveResponse(int id, int dialogueType) {
        switch (dialogueType) {
            case Constants.H_TYPE_EXIT_DIALOG:
                finishAffinity();
                break;

            case Constants.H_RATE_US_DIALOG:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;
            case Constants.H_LOCATION_DIALOG:
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                break;

            case Constants.H_NETWORK_DIALOG:
                break;
        }

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

    @Override
    public void onNeutralResponse(int which, int dialogueType) {
        switch (dialogueType) {
            case Constants.H_NETWORK_DIALOG:
                startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                break;
            case Constants.H_INFO_DIALOG:
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_SHARE_LOCATION_PC);
                break;
            case Constants.H_LOCATION_DIALOG:
                break;
        }

    }

    @Override
    protected void onPause() {
        hIsPlaceSelected = false;
        super.onPause();
    }

    @Override
    public void onPlaceSelected(Place place) {
        if (hIsLocationRetrieved && hCurrentLocation != null) {

            hIsPlaceSelected = true;
            hGoogleMap.clear();
            hDestLatLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);


            hDestName = String.valueOf(place.getName());

            String hAddress = String.valueOf(place.getAddress());
            String hId = place.getId();
//            String hLocale = String.valueOf(p());
            String hRatting = String.valueOf(place.getRating());
            String hNumber = String.valueOf(place.getPhoneNumber());


            hShowCardHideBottomSheet(hAddress);

            hNearByPlaces = true;


            Bitmap hDestMarker = MarkerUtils.hGetCustomMapMarker(this, String.valueOf(Constants.H_DEST_MARKER));
            Bitmap hCurrentMarker = MarkerUtils.hGetCustomMapMarker(this, String.valueOf(Constants.H_CURRENT_MARKER));


            hGoogleMap.addMarker(new MarkerOptions().
                    position(hDestLatLng).
                    icon(BitmapDescriptorFactory.fromBitmap(hDestMarker)));

            hGoogleMap.addMarker(new MarkerOptions().
                    position(new LatLng(hCurrentLocation.getLatitude(), hCurrentLocation.getLongitude())).
                    icon(BitmapDescriptorFactory.fromBitmap(hCurrentMarker)));
            hWhatToLoad = "4567";

            hGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                    (hDestLatLng, 10f));

        } else {
            LogToastSnackHelper.hMakeShortToast(this, "Unable to retive current Location, Refresh");
            UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hRefreshImageView, Constants.H_VISIBLE);
        }
    }

    private void hShowCardHideBottomSheet(String hAddress) {
        UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hBoottomSheet.bottomSheet, Constants.H_INVISIBLE);
        UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hDetailCardView, Constants.H_VISIBLE);
        UIHelper.hSetTextToTextView(hActivityMainBinding.hNameTv, hDestName);
        UIHelper.hSetTextToTextView(hActivityMainBinding.hAddressTv, hAddress);
    }

    @Override
    public void onError(Status status) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

        if (hActivityMainBinding.hDetailCardView.getVisibility() == View.VISIBLE) {
            UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hDetailCardView, Constants.H_INVISIBLE);
            UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hBoottomSheet.bottomSheet, Constants.H_VISIBLE);

        }
        if (hActivityMainBinding.hBoottomSheet.bottomSheet.getVisibility() == View.VISIBLE) {
            UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hBoottomSheet.bottomSheet, Constants.H_INVISIBLE);
        } else {

            UIHelper.hMakeVisibleInVisible(hActivityMainBinding.hBoottomSheet.bottomSheet, Constants.H_VISIBLE);
        }
    }

    private void hCheckNetworkConnection() {
        ConnectivityManager hConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = hConnectivityManager.getActiveNetworkInfo();
        hIsNetworkConnected = (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    public void hIsLocationEnabled() {
        int locationMode = 0;
        try {
            locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        hIsLocationEnabled = locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
//
//        if (requestCode == H_LOCATION_PERMISSION_CODE) {
//            ApplicationClass.hIsLocationPermissionGranted = true;
//            hSettingsPrefrences.hSetPermissionCheck(ApplicationClass.hIsLocationPermissionGranted);
//
//        }
        if (requestCode == H_WRITE_PERMISSION_CODE) {
            hCreateBitmapnShare();
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        if (requestCode == H_LOCATION_PERMISSION_CODE) {
            hAskForPermissions(Constants.H_LOCATION_PERMISSION);
        }

        if (requestCode == H_WRITE_PERMISSION_CODE) {
            hAskForPermissions(Constants.H_WRITE_PERMISSION);
        }
    }

    private class FindLocationCordinatesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient hOkHttpClient;
            hOkHttpClient = new OkHttpClient.Builder().build();
            LogToastSnackHelper.hLogField(hTag, strings[0]);
            final Request hRequest = new Request.Builder().url(strings[0]).build();
            try {

                Response hResponse = hOkHttpClient.newCall(hRequest).execute();
                String string = hResponse.body().string();
                LogToastSnackHelper.hLogField(hTag, string);

                return string;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson hGson = new Gson();
            hVoiceReturnModel = hGson.fromJson(s, VoiceReturnModel.class);
            LogToastSnackHelper.hLogField(hTag, hVoiceReturnModel.toString());

            if (hVoiceReturnModel != null && hVoiceReturnModel.getStatus().equals("OK")) {
                hIsVoiceCommad = true;
                hPermissionCheck(Constants.H_LOCATION_PERMISSION, Constants.H_CALCULATE_ROUTE_FALSE_PC);

            } else {
                LogToastSnackHelper.hMakeShortToast(MainActivity.this, "Unable to find any relavent location");
            }

        }
    }
}
