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

    private var hCategoriesCallback: ((String) -> Unit)? = null

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
        var hCategoryItem = hCategoriesList.get(position)
        categoryVh.hItemRecyclerCategoryBinding.hCategoriesChip.text = hCategoryItem
        categoryVh.hItemRecyclerCategoryBinding.hCategoriesChip.setOnClickListener {
            hCategoriesCallback?.invoke(hCategoryItem)
        }
    }

    fun hSetCategoriesCallback(categoriesCallback: (String) -> Unit) {
        hCategoriesCallback = categoriesCallback
    }

}