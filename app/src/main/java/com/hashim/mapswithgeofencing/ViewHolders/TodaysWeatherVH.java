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