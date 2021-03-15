package com.hashim.mapswithgeofencing.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HLatLngModel {


    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}
