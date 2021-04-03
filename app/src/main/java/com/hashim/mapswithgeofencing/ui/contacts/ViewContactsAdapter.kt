/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerViewContactsBinding
import com.hashim.mapswithgeofencing.db.entities.Contact
import com.hashim.mapswithgeofencing.ui.viewholders.ViewContactVh
import timber.log.Timber

class ViewContactsAdapter(
        private val hContext: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {


    var hList = listOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewContactVh(
                ItemRecyclerViewContactsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        hBindContatsVB(holder as ViewContactVh, hList.get(position), position)
    }

    private fun hBindContatsVB(hContactsVH: ViewContactVh, contact: Contact, position: Int) {


        hContactsVH.hItemRecyclerViewContactsBinding.title.text = contact.hName
        hContactsVH.hItemRecyclerViewContactsBinding.numberTextView.text = contact.hNumber



        hContactsVH.hItemRecyclerViewContactsBinding.rootView.setOnClickListener {

        }


    }


    override fun getItemCount(): Int {
        return hList.size
    }


    fun hSetData(list: List<Contact>) {
        hList = list
        notifyDataSetChanged()
    }

    fun hRemoveItem(adapterPosition: Int) {
        Timber.d("Remove item$adapterPosition")


    }

}