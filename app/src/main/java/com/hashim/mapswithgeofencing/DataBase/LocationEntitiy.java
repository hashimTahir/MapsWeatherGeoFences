/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.DataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "LocationTable")
public class LocationEntitiy {
    @PrimaryKey(autoGenerate = true)
    private int lid = 0;

    private Double latitude;
    private Double longitude;
    private String locationName;
    private int radius;


    public LocationEntitiy(int lid, Double latitude, Double longitude, String locationName, int radius) {
        this.lid = lid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
        this.radius = radius;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }






    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}

