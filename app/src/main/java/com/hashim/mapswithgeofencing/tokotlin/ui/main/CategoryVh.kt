package com.hashim.mapswithgeofencing.tokotlin.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerCategoryBinding

class CategoryVh(itemRecyclerCategoryBinding: ItemRecyclerCategoryBinding)
    : RecyclerView.ViewHolder(itemRecyclerCategoryBinding.root) {
    lateinit var hItemRecyclerCategoryBinding: ItemRecyclerCategoryBinding

    init {
        hItemRecyclerCategoryBinding = itemRecyclerCategoryBinding
    }

}


