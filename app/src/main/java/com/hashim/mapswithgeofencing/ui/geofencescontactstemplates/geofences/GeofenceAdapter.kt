/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerSavedGeoFenceBinding
import com.hashim.mapswithgeofencing.db.entities.GeoFence
import com.hashim.mapswithgeofencing.ui.viewholders.GeofenceVh

class GeofenceAdapter(
        private val hContext: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    private var hList = listOf<GeoFence>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GeofenceVh(
                ItemRecyclerSavedGeoFenceBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        hBindGeoFenceVB(holder as GeofenceVh, hList.get(position), position)
    }

    private fun hBindGeoFenceVB(hGeofenceVh: GeofenceVh, geofence: GeoFence, position: Int) {
        hGeofenceVh.hItemRecyclerSavedGeoFenceBinding.hGeoFenceNameTv.text = geofence.hFenceName
        hGeofenceVh.hItemRecyclerSavedGeoFenceBinding.hGeoFenceDetailTv.text = "${geofence.hLat} ${geofence.hLng}"
        hGeofenceVh.hItemRecyclerSavedGeoFenceBinding.root.setOnClickListener {

        }


    }


    override fun getItemCount(): Int {
        return hList.size
    }


    fun hSetData(list: List<GeoFence>) {
        hList = list
        notifyDataSetChanged()
    }

}