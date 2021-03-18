package com.hashim.mapswithgeofencing.tokotlin.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerCategoryBinding


class CategoriesAdapter(
        private val hContext: Context,
        private val hCategoriesList: List<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryVh(
                ItemRecyclerCategoryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }


    override fun getItemCount(): Int {
        return hCategoriesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        hBindCategoryVh(holder as CategoryVh, position)
    }

    private fun hBindCategoryVh(categoryVh: CategoryVh, position: Int) {
        categoryVh.hItemRecyclerCategoryBinding.hCategoriesChip.text = hCategoriesList.get(position)
    }
}