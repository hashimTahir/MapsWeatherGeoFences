/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerTemplatesBinding
import com.hashim.mapswithgeofencing.db.entities.Templates
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates.TemplatesAdapter.AdapterType.CUSTOM
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates.TemplatesAdapter.AdapterType.DEFAULT
import com.hashim.mapswithgeofencing.ui.viewholders.CustomTemplatesVh
import com.hashim.mapswithgeofencing.ui.viewholders.DefaultTemplatesVh

class TemplatesAdapter(
        private val hContext: Context,
        private val adapterType: AdapterType,
        private val hCalback: (Any?) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private var hList = listOf<Any>()

    enum class AdapterType {
        DEFAULT,
        CUSTOM,
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (adapterType) {
            DEFAULT -> {
                return hGetDefaultVh(parent)
            }
            CUSTOM -> {
                return hGetCustomVh(parent)
            }
        }
    }

    private fun hGetDefaultVh(parent: ViewGroup): RecyclerView.ViewHolder {
        return DefaultTemplatesVh(
                ItemRecyclerTemplatesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    private fun hGetCustomVh(parent: ViewGroup): RecyclerView.ViewHolder {
        return CustomTemplatesVh(
                ItemRecyclerTemplatesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (adapterType) {
            DEFAULT -> {
                hBindDefaultVh(holder as DefaultTemplatesVh, hList.get(position) as String)
            }
            CUSTOM ->
                hBindCustomVh(holder as CustomTemplatesVh, hList.get(position) as Templates)
        }
    }

    private fun hBindCustomVh(customTemplatesVh: CustomTemplatesVh, s: Templates) {
        customTemplatesVh.hItemRecyclerTemplatesBinding.irtTV.text = s.hMessage

        customTemplatesVh.hItemRecyclerTemplatesBinding.root.setOnClickListener {
            hCalback(s)
        }

    }

    private fun hBindDefaultVh(defaultTemplatesVh: DefaultTemplatesVh, s: String) {
        defaultTemplatesVh.hItemRecyclerTemplatesBinding.irtTV.text = s

        defaultTemplatesVh.hItemRecyclerTemplatesBinding.root.setOnClickListener {
            hCalback(s)
        }
    }

    override fun getItemCount(): Int {
        return hList.size
    }

    fun hSetData(list: List<Any>?) {
        if (list != null) {
            hList = list
            notifyDataSetChanged()
        }
    }

}

