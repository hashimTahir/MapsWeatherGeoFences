package com.hashim.mapswithgeofencing.ViewHolders;

import androidx.recyclerview.widget.RecyclerView;

import com.hashim.mapswithgeofencing.databinding.ItemRecyclerContactsBinding;

public class ContactsVH extends RecyclerView.ViewHolder {


    public ItemRecyclerContactsBinding hItemRecyclerContactsBinding;

    public ContactsVH(ItemRecyclerContactsBinding binding) {
        super(binding.getRoot());
        hItemRecyclerContactsBinding = binding;
    }


}