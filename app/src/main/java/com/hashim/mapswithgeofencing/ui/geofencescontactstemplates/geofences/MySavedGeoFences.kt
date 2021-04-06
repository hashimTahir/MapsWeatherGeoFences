/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hashim.mapswithgeofencing.databinding.FragmentMySavedGeoFencesBinding

class MySavedGeoFences : Fragment() {

    lateinit var hMySavedGeoFencesBinding: FragmentMySavedGeoFencesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        hMySavedGeoFencesBinding = FragmentMySavedGeoFencesBinding.inflate(
                layoutInflater,
                container,
                false
        )
        return hMySavedGeoFencesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hSetupListeners()

    }

    private fun hSetupListeners() {
        hMySavedGeoFencesBinding.hAddLoactionFb.setOnClickListener {
        }
    }

}