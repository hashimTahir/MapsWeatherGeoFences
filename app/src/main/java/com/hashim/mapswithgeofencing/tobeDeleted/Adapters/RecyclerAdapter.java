/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hashim.mapswithgeofencing.tobeDeleted.DataBase.LocationEntitiy;
import com.hashim.mapswithgeofencing.tobeDeleted.Interfaces.RecyclerInterface;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.tobeDeleted.ViewHolders.TemplatesVH;
import com.hashim.mapswithgeofencing.tobeDeleted.ViewHolders.TrackMeVH;
import com.hashim.mapswithgeofencing.tobeDeleted.ViewHolders.ViewAllVH;
import com.hashim.mapswithgeofencing.databinding.ItemButtonsBinding;
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerTemplatesBinding;
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerTrackMeBinding;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerInterface hRecyclerInterface;
    List<String> hPlaceStrings;
    private Context hContext;
    private int hRecylerType;
    public static final int H_VIEW_ALL_ADAPTER = 0;
    public static final int H_TEMPLATES_ADAPTER = 1;
    public static final int H_TRACK_ME_ADAPTER = 2;

    List<LocationEntitiy> hLocationEntitiys = new ArrayList<>();

    private int[] hImages = {
            R.drawable.ic_atm, R.drawable.ic_icon_bank, R.drawable.ic_hospitals,
            R.drawable.ic_mosques, R.drawable.ic_doctors, R.drawable.ic_train_stations,
            R.drawable.ic_parking_spots, R.drawable.ic_parks, R.drawable.ic_cafe,
            R.drawable.ic_restaurant, R.drawable.ic_gas_station,
            R.drawable.ic_police_stations, R.drawable.ic_book_stores,
            R.drawable.ic_bus_stop, R.drawable.ic_pharmacy,
            R.drawable.ic_clothes_stores, R.drawable.ic_schools, R.drawable.ic_super_market
    };


    private int[] hColors = {
            R.color.atm_color, R.color.bank_color, R.color.hospital_color,
            R.color.mosque_color, R.color.e, R.color.f, R.color.g,
            R.color.h, R.color.cafe_color, R.color.j, R.color.k,
            R.color.police_color, R.color.m,
            R.color.bus_station_color, R.color.o, R.color.p, R.color.q, R.color.r
    };

    public RecyclerAdapter(Context context, List<String> placeStrings, int recylerType) {
        hContext = context;
        hPlaceStrings = placeStrings;
        hRecyclerInterface = (RecyclerInterface) context;
        hRecylerType = recylerType;
    }


    public RecyclerAdapter(Context context, List<LocationEntitiy> locationEntitiys,
                           int hTrackMeAdapter, RecyclerInterface recyclerInterface) {
        hContext = context;
        hRecylerType = hTrackMeAdapter;
        this.hLocationEntitiys = locationEntitiys;
        hRecyclerInterface = recyclerInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (hRecylerType) {
            case H_VIEW_ALL_ADAPTER:
                return hGetViewAllVH(parent);
            case H_TEMPLATES_ADAPTER:
                return hGetTemplatesVH(parent);
            case H_TRACK_ME_ADAPTER:
                return hGetTrackMeVH(parent);
            default:
                return null;
        }
    }

    private RecyclerView.ViewHolder hGetTrackMeVH(ViewGroup parent) {
        return new TrackMeVH(
                ItemRecyclerTrackMeBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @NonNull
    private RecyclerView.ViewHolder hGetTemplatesVH(ViewGroup parent) {
        return new TemplatesVH(
                ItemRecyclerTemplatesBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (hRecylerType) {
            case H_VIEW_ALL_ADAPTER:
                ViewAllVH hViewAllVH = (ViewAllVH) holder;
                hBindViewAllVH(hViewAllVH, position);
                break;
            case H_TEMPLATES_ADAPTER:
                TemplatesVH hTemplatesVH = (TemplatesVH) holder;
                hBindTemplatesVH(hTemplatesVH, position);
                break;
            case H_TRACK_ME_ADAPTER:
                TrackMeVH hTrackMeVH = (TrackMeVH) holder;
                hBindTrackMeVH(hTrackMeVH, position);
                break;
        }
    }

    private void hBindTrackMeVH(TrackMeVH hTrackMeVH, int position) {
        LocationEntitiy hLocationEntitiy = hLocationEntitiys.get(position);

        SpannableString hSpannableString = new SpannableString("Edit");
        hSpannableString.setSpan(new UnderlineSpan(), 0, hSpannableString.length(), 0);

        hTrackMeVH.hItemRecyclerTrackMeBinding.hEditTv.setOnClickListener(v -> {
            hRecyclerInterface.hOnClickListener(v, hLocationEntitiy.getLid());
        });
    }

    private void hBindTemplatesVH(TemplatesVH hTemplatesVH, int position) {
        String hText = hPlaceStrings.get(position);
        hTemplatesVH.hItemRecyclerTemplatesBinding.hTemplateCl.setOnClickListener(v -> hRecyclerInterface.hOnClickListener(v, position, hText));
    }

    private void hBindViewAllVH(ViewAllVH viewAllVH, final int position) {
        viewAllVH.setIsRecyclable(false);

        Drawable myFabSrc = ResourcesCompat.getDrawable(hContext.getResources(), hImages[position], hContext.getTheme());
        Drawable willBeWhite = myFabSrc.getConstantState().newDrawable();
        willBeWhite.mutate().setColorFilter(hColors[position], PorterDuff.Mode.SRC_ATOP);
        viewAllVH.hItemButtonsBinding.hFloatingActionButton.setImageDrawable(willBeWhite);
        viewAllVH.hItemButtonsBinding.hFloatingActionButton.setBackgroundTintList
                (ColorStateList.valueOf(ContextCompat.getColor(hContext, (R.color.white))));
        viewAllVH.hItemButtonsBinding.buttonLayou.setOnClickListener(v -> hRecyclerInterface.hOnClickListener(v, position));
    }


    @Override
    public int getItemCount() {
        if (hRecylerType == RecyclerAdapter.H_TRACK_ME_ADAPTER) {
            return hLocationEntitiys.size();
        }
        return hPlaceStrings.size();
    }


    @NonNull
    private RecyclerView.ViewHolder hGetViewAllVH(ViewGroup parent) {
        return new ViewAllVH(
                ItemButtonsBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    public Context getContext() {

        return hContext;
    }
}
