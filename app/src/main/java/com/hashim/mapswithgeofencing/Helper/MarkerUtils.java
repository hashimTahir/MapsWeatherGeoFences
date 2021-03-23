/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.hashim.mapswithgeofencing.R;


public class MarkerUtils {


    public static Bitmap hGetCustomMapMarker(Context context, String category) {
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable) context.getResources().getDrawable(hChooseIcon(category));
        Bitmap hBitmap = bitmapdraw.getBitmap();
        return Bitmap.createScaledBitmap(hBitmap, width, height, false);
    }


    private static int hChooseIcon(String category) {

        switch (Integer.parseInt(category)) {
            case 0:
                return R.drawable.ic_atm;
            case 1:
                return R.drawable.ic_icon_bank;
            case 2:
                return R.drawable.ic_hospitals;
            case 3:
                return R.drawable.ic_mosques;
            case 4:
                return R.drawable.ic_doctors;
            case 5:
                return R.drawable.ic_train_stations;
            case 6:
                return R.drawable.ic_parking_spots;
            case 7:
                return R.drawable.ic_parks;
            case 8:
                return R.drawable.ic_cafe;
            case 9:
                return R.drawable.ic_restaurant;
            case 10:
                return R.drawable.ic_gas_station;
            case 11:
                return R.drawable.ic_police_stations;
            case 12:
                return R.drawable.ic_book_stores;
            case 13:
                return R.drawable.ic_bus_stop;
            case 14:
                return R.drawable.ic_pharmacy;
            case 15:
                return R.drawable.ic_clothes_stores;
            case 16:
                return R.drawable.ic_schools;
            case 17:
                return R.drawable.ic_super_market;
            case 67:
                return R.drawable.dog_icon;
            case 82:
                return R.drawable.current_marker;
            case 83:
                return R.drawable.destination_marker;
            default:
                return R.drawable.current_location;
        }

    }
}
