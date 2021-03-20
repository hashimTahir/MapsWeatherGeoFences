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

    //            // Connect the recycler to the scroller (to let the scroller scroll the list)
//            fastScroller.setRecyclerView(hRecyclerView);
//
//            // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
//            hRecyclerView.setOnScrollListener(fastScroller.getOnScrollListener());
//            https://github.com/myinnos/AlphabetIndex-Fast-Scroll-RecyclerView
//    https://github.com/volsahin/volx-recyclerview-fast-scroll?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5553
    private static int hChooseIcon(String category) {

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
            case 82:
                return R.drawable.current_marker;
            case 83:
                return R.drawable.destination_marker;
            default:
                return R.drawable.current_location;
        }

    }
}
