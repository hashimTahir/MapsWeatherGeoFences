/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SectionIndexer
import androidx.core.util.forEach
import androidx.recyclerview.widget.RecyclerView
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerAllContactsBinding
import com.hashim.mapswithgeofencing.db.entities.Contact
import com.hashim.mapswithgeofencing.ui.viewholders.AllContactsVh

class AllContactsAdapter(
        private val hContext: Context,
        val hCallBack: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>(), SectionIndexer {


    var hList = listOf<Contact>()
    private val hSelectedItems: SparseBooleanArray = SparseBooleanArray()
    private var hSectionPostion = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AllContactsVh(
                ItemRecyclerAllContactsBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        hBindContatsVB(holder as AllContactsVh, hList.get(position), position)
    }

    private fun hBindContatsVB(hContactsVH: AllContactsVh, contact: Contact, position: Int) {


        hContactsVH.hItemRecyclerAllContactsBinding.title.text = contact.hName
        hContactsVH.hItemRecyclerAllContactsBinding.numberTextView.text = contact.hNumber


        hSelectUnselectItems(position, hContactsVH)

        hContactsVH.hItemRecyclerAllContactsBinding.rootView.setOnClickListener {
            hToggleSelection(position)
            hCallBack(position, hContactsVH.hItemRecyclerAllContactsBinding.hCheckBox.isChecked)
        }


    }

    private fun hToggleSelection(position: Int) {
        hSelectedItems.put(position, !hSelectedItems[position, false])
        notifyItemChanged(position)
    }

    private fun hSelectUnselectItems(position: Int, hContactsVH: AllContactsVh) {
        hContactsVH.hItemRecyclerAllContactsBinding.hCheckBox
                .isChecked = hSelectedItems[position, false]
    }


    override fun getItemCount(): Int {
        return hList.size
    }

    override fun getSections(): Array<String> {
        val hSectionsList = mutableListOf<String>()
        hSectionPostion = mutableListOf(26)
        var i = 0
        val size: Int = hList.size
        while (i < size) {
            val section: String = hList.get(i).hName.toString().get(0).toUpperCase().toString()
            if (!hSectionsList.contains(section)) {
                hSectionsList.add(section)
                hSectionPostion.add(i)
            }
            i++
        }
        return hSectionsList.toTypedArray()
    }

    override fun getPositionForSection(sectionIndex: Int): Int {
        return hSectionPostion.get(sectionIndex)

    }

    override fun getSectionForPosition(position: Int): Int {
        return 0
    }

    fun hSetData(list: List<Contact>) {
        hList = list
        notifyDataSetChanged()
    }

    fun hGetSelectedContacts(): List<Contact> {
        val hToSaveContactList = mutableListOf<Contact>()
        hSelectedItems.forEach { key, value ->
            if (value)
                hToSaveContactList.add(hList.get(key))
        }
        return hToSaveContactList.toList()
    }

}