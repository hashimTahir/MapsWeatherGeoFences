/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ViewHolders;

import androidx.recyclerview.widget.RecyclerView;

import com.hashim.mapswithgeofencing.databinding.ItemRecyclerWeatherBinding;


public class TodaysWeatherVH extends RecyclerView.ViewHolder {

    public ItemRecyclerWeatherBinding hItemRecyclerWeatherBinding;


    public TodaysWeatherVH(ItemRecyclerWeatherBinding binding) {
        super(binding.getRoot());
        hItemRecyclerWeatherBinding = binding;
    }

}