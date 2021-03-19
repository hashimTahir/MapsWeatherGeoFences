package com.hashim.mapswithgeofencing.tokotlin.repository.weather

import com.hashim.mapswithgeofencing.Helper.Constants

class WeatherUrl(
        lat: Double,
        lng: Double,
        type: Int,
        tempUnit: Int
) {
    private var hType = type
    private var hTempUnit = tempUnit
    //    metric,imperial

    //    metric,imperial
    private val hCity: String? = null
    private val hCountry: String? = null
    private var hLat: String? = null
    private var hLng: String? = null

    private val H_CELCIUS_UNIT = "&units=metric"
    private val H_FARENHEIT_UNIT = "&units=imperial"
    private val H_KELVIL_UNIT = ""
    private val H_API_KEY = "2a9456a6312730f0b6a84a5e4e850977"
    private val H_BASE_URL = "http://api.openweathermap.org/data/2.5/"


    //wetather urls
    var H_COUNTRY_CITY_SAMPLE_URL = H_BASE_URL + "weather?q=" + hCity + "," + hCountry + "&APPID=" + H_API_KEY
    var H_LAT_LNG_URL = H_BASE_URL + "weather?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY


    //forecast url
//    public  String H_FORECAST_LAT_LNG_URL = H_BASE_URL + "forecast?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY;

    //forecast url
    //    public  String H_FORECAST_LAT_LNG_URL = H_BASE_URL + "forecast?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY;
    var hUrl: String? = null


    init {
        hLat = lat.toString()
        hLng = lng.toString()
    }


    public fun hCreateUrl(): String? {
        var hUrl: String? = null
        when (hType) {
            Constants.H_GET_FORECAST ->
                when (hTempUnit) {
                    Constants.H_CELCIUS_UNIT ->
                        hUrl = H_BASE_URL + "forecast?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY + H_CELCIUS_UNIT

                    Constants.H_FARENHEIT_UNIT ->
                        hUrl = H_BASE_URL + "forecast?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY + H_FARENHEIT_UNIT

                    Constants.H_KELVIL_UNIT ->
                        hUrl = H_BASE_URL + "forecast?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY + H_KELVIL_UNIT
                }
            Constants.H_GET_WEATHER ->
                when (hTempUnit) {
                    Constants.H_CELCIUS_UNIT ->
                        hUrl = H_BASE_URL + "weather?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY + H_CELCIUS_UNIT

                    Constants.H_FARENHEIT_UNIT ->
                        hUrl = H_BASE_URL + "weather?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY + H_FARENHEIT_UNIT

                    Constants.H_KELVIL_UNIT ->
                        hUrl = H_BASE_URL + "weather?lat=" + hLat + "&lon=" + hLng + "&APPID=" + H_API_KEY + H_KELVIL_UNIT

                }
        }
        return hUrl
    }
}