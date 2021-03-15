package com.hashim.mapswithgeofencing.Weather;

public interface WeatherAysncCallBack {
    void onWeatherFetch(Object latLonReturnModel, String hDecodedString);

}
