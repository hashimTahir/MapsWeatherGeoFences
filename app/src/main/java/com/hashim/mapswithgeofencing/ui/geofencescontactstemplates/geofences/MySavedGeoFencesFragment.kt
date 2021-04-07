/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.FragmentMySavedGeoFencesBinding
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences.GeoFenceStateEvent.OnDisplaySavedFences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MySavedGeoFencesFragment : Fragment() {

    lateinit var hMySavedGeoFencesBinding: FragmentMySavedGeoFencesBinding
    val hGeoFenceViewModel: GeoFenceViewModel by viewModels()

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

        hSubscribeObservers()

        hGeoFenceViewModel.hSetStateEvent(OnDisplaySavedFences())

    }

    private fun hSubscribeObservers() {
        hGeoFenceViewModel.hDataState.observe(viewLifecycleOwner) { dataState ->
            dataState.hLoading.let {
                when (it) {
                    /*Show Hide Progress views*/
                    true -> {
                    }
                    false -> {
                    }
                }
            }
            dataState.hData?.let {

            }


        }
        hGeoFenceViewModel.hGeoFenceViewState.observe(viewLifecycleOwner) { geoFenceViewState ->
            geoFenceViewState.hGeoFenceFields.let {

            }
        }
    }

    private fun hSetupListeners() {
        hMySavedGeoFencesBinding.hAddLoactionFb.setOnClickListener {
            findNavController().navigate(R.id.action_mySavedGeoFences_to_addGeoFencesFragment)
        }
    }

}