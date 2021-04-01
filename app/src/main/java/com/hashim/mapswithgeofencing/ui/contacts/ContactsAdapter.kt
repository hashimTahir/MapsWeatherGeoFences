/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

import android.R
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SectionIndexer
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerContactsBinding
import com.hashim.mapswithgeofencing.db.entities.Contact
import com.hashim.mapswithgeofencing.tobeDeleted.ViewHolders.ContactsVH

class ContactsAdapter(
        private val hContext: Context,
        val hCallBack: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>(), SectionIndexer {


    var hList = mutableListOf<Contact>()
    private val hSelectedItems: SparseBooleanArray = SparseBooleanArray()
    private var hSectionPostion = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactsVH(
                ItemRecyclerContactsBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        hBindContatsVB(holder as ContactsVH, hList.get(position), position)
    }

    private fun hBindContatsVB(hContactsVH: ContactsVH, contact: Contact, position: Int) {


        hContactsVH.hItemRecyclerContactsBinding.title.text = contact.hName
        hContactsVH.hItemRecyclerContactsBinding.numberTextView.text = contact.hNumber


        hSelectUnselectItems(position, hContactsVH)

        hContactsVH.hItemRecyclerContactsBinding.rootView.setOnClickListener {
            hToggleSelection(position)
            hCallBack(position)
        }

    }

    private fun hToggleSelection(position: Int) {
        hSelectedItems.put(position, !hSelectedItems[position, false])
        notifyItemChanged(position)
    }

    private fun hSelectUnselectItems(position: Int, hContactsVH: ContactsVH) {
        if (hSelectedItems[position, false]) {

            hContactsVH.hItemRecyclerContactsBinding.rootView.setForeground(
                    ColorDrawable(
                            ContextCompat.getColor(
                                    hContext,
                                    R.color.darker_gray
                            )
                    )
            )
        } else {
            hContactsVH.hItemRecyclerContactsBinding.rootView.setForeground(
                    ColorDrawable(
                            ContextCompat.getColor(
                                    hContext,
                                    R.color.transparent
                            )
                    )
            )
        }
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

    fun hSetData(hTestList: MutableList<Contact>) {
        hList = hTestList
    }

}