package com.hashim.mapswithgeofencing.Weather;


import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.LogToastSnackHelper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherClient {
    private final int hType;
    private final int hTempUnit;
    //    metric,imperial

    private String hCity;
    private String hCountry;
    private String hLat;
    private String hLng;

    private static final String H_CELCIUS_UNIT = "&units=metric";
    private static final String H_FARENHEIT_UNIT = "&units=imperial";
    private static final String H_KELVIL_UNIT = "";
    private static final String H_API_KEY = "2a9456a6312730f0b6a84a5e4e850977";
    private static final String H_BASE_URL = "http://api.openweathermap.org/data/2.5/";


    //wetather urls
    public String H_COUNTRY_CITY_SAMPLE_URL = H_BASE_URL + "weather?q=" + hCity + "," + hCountry + "&APPID=" + H_API_KEY;
    public String H_LAT_LNG_URL = H_BASE_URL + "weather?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY;


    //forecast url
//    public  String H_FORECAST_LAT_LNG_URL = H_BASE_URL + "forecast?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY;

    String hUrl;


    public WeatherClient(Double lat, Double lng, int type, int tempUnit) {

        this.hLat = String.valueOf(lat);
        this.hLng = String.valueOf(lng);
        this.hType = type;
        this.hTempUnit = tempUnit;


    }

    public byte[] hCreateHttpClient() {
        String hUrl = null;
        switch (hType) {
            case Constants.H_GET_FORECAST:
                switch (hTempUnit) {
                    case Constants.H_CELCIUS_UNIT:
                        hUrl = H_BASE_URL + "forecast?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY + H_CELCIUS_UNIT;
                        break;
                    case Constants.H_FARENHEIT_UNIT:
                        hUrl = H_BASE_URL + "forecast?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY + H_FARENHEIT_UNIT;
                        break;
                    case Constants.H_KELVIL_UNIT:
                        hUrl = H_BASE_URL + "forecast?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY + H_KELVIL_UNIT;
                        break;
                }
                break;
            case Constants.H_GET_WEATHER:
                switch (hTempUnit) {
                    case Constants.H_CELCIUS_UNIT:
                        hUrl = H_BASE_URL + "weather?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY + H_CELCIUS_UNIT;
                        break;
                    case Constants.H_FARENHEIT_UNIT:
                        hUrl = H_BASE_URL + "weather?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY + H_FARENHEIT_UNIT;
                        break;
                    case Constants.H_KELVIL_UNIT:
                        hUrl = H_BASE_URL + "weather?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY + H_KELVIL_UNIT;
                        break;
                }
                break;
        }
        LogToastSnackHelper.hLogField("H_TAG", hUrl);
        OkHttpClient hOkHttpClient;
        hOkHttpClient = new OkHttpClient.Builder().build();
        assert hUrl != null;
        final Request hRequest = new Request.Builder().url(hUrl).build();
        try {

            Response hResponse = hOkHttpClient.newCall(hRequest).execute();
            return hResponse.body().bytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
