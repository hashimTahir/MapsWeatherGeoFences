package com.hashim.mapswithgeofencing.ViewHolders;

import androidx.recyclerview.widget.RecyclerView;

import com.hashim.mapswithgeofencing.databinding.ItemRecyclerTemplatesBinding;

public class TemplatesVH extends RecyclerView.ViewHolder {

    public ItemRecyclerTemplatesBinding hItemRecyclerTemplatesBinding;


    public TemplatesVH(ItemRecyclerTemplatesBinding binding) {
        super(binding.getRoot());
        hItemRecyclerTemplatesBinding = binding;

    }
}
