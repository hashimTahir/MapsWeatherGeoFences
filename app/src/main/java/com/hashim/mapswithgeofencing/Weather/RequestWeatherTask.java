package com.hashim.mapswithgeofencing.Weather;

import android.content.Context;
import android.os.AsyncTask;


import com.google.gson.Gson;
import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.EnCodeDeCodeByteArray;
import com.hashim.mapswithgeofencing.Models.WeatherModel.LatLngReturnModel.LatLonReturnModel;
import com.hashim.mapswithgeofencing.Models.WeatherModel.ListWeatherModel.WeatherMainReturnResponse;
import com.hashim.mapswithgeofencing.Prefrences.SettingsPrefrences;

import java.lang.ref.WeakReference;

import dmax.dialog.SpotsDialog;

public class RequestWeatherTask extends AsyncTask<Void, Void, Object> {

    private final WeakReference<Context> hContext;
    private final Double hLat;
    private final int hType;
    private final Double hLng;
    WeatherAysncCallBack hWeatherAsyncCallBack;
    private String hDecodedString;
    private SpotsDialog hAlertDialog;
    private int hTempUnit;

    public RequestWeatherTask(Context context, Double lat, Double lng, int type) {
        this.hContext = new WeakReference<>(context);
        this.hLat = lat;
        this.hLng = lng;
        this.hType = type;
        hWeatherAsyncCallBack = (WeatherAysncCallBack) context;
        SettingsPrefrences hSettingsPrefrences = new SettingsPrefrences(hContext.get().getApplicationContext());
        this.hTempUnit = hSettingsPrefrences.hGetTempUnit();
    }


    @Override
    protected Object doInBackground(Void... strings) {
        WeatherClient hWeatherClient = new WeatherClient(hLat, hLng, hType, hTempUnit);

        byte[] hBytes = hWeatherClient.hCreateHttpClient();
        hDecodedString = EnCodeDeCodeByteArray.hDecodeToString(hBytes);
        Gson hGson = new Gson();
        switch (hType) {
            case Constants.H_GET_FORECAST:
                return hGson.fromJson(hDecodedString, WeatherMainReturnResponse.class);
            case Constants.H_GET_WEATHER:
                return hGson.fromJson(hDecodedString, LatLonReturnModel.class);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object latLonReturnModel) {
        super.onPostExecute(latLonReturnModel);
        hWeatherAsyncCallBack.onWeatherFetch(latLonReturnModel, hDecodedString);
        hHideLoader();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        hShowLoader();
    }


    public void hShowLoader() {
        SpotsDialog.Builder hBuilder = new SpotsDialog.Builder().setContext(hContext.get()).setMessage("Loading...");
//        new SpotsDialog.Builder().setContext(this).setMessage("Loading...").build().show();
        hAlertDialog = (SpotsDialog) hBuilder.build();
        hAlertDialog.show();


    }

    public void hHideLoader() {
        hAlertDialog.dismiss();
        hAlertDialog = null;
    }
}
