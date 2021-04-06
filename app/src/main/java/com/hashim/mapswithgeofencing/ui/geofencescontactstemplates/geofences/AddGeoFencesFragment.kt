/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hashim.mapswithgeofencing.databinding.FragmentAddGeoFencesBinding

class AddGeoFencesFragment : Fragment() {
    lateinit var hFragmentAddGeoFencesBinding: FragmentAddGeoFencesBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        hFragmentAddGeoFencesBinding = FragmentAddGeoFencesBinding.inflate(
                inflater,
                container,
                false
        )
        return hFragmentAddGeoFencesBinding.root

    }

}