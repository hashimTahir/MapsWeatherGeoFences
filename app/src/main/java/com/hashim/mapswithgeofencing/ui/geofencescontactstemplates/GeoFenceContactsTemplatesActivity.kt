/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.ActivityGeofenceContactsTemplatesBinding
import com.hashim.mapswithgeofencing.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class GeoFenceContactsTemplatesActivity : AppCompatActivity() {
    lateinit var hActivityGeofenceContactsTemplatesBinding: ActivityGeofenceContactsTemplatesBinding
    private lateinit var hNavHostFragment: NavHostFragment
    private lateinit var hNavController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hActivityGeofenceContactsTemplatesBinding =
                ActivityGeofenceContactsTemplatesBinding.inflate(
                        layoutInflater
                )
        setContentView(hActivityGeofenceContactsTemplatesBinding.root)

        hInitNavView(hGetData())

    }

    private fun hGetData(): LaunchTypes? {
        val string = intent.extras?.getString(Constants.H_GEOFENCE_CONTACTS_TEMPLATES_DATA)
        Timber.d("Data is $string")
        string?.let {
            return LaunchTypes.valueOf(string)
        }
        return null
    }

    private fun hInitNavView(launchTypes: LaunchTypes?) {

        setSupportActionBar(hActivityGeofenceContactsTemplatesBinding.toolbar)



        hNavHostFragment = supportFragmentManager
                .findFragmentById(R.id.hFragmentContainer) as NavHostFragment
        hNavController = hNavHostFragment.navController

        val hNavInflater = hNavController.navInflater
        val hNavGraph = hNavInflater.inflate(R.navigation.contacts_geofence_templates_nav)


        val hDestinaiton = when (launchTypes) {
            LaunchTypes.TEMPLATES -> R.id.hTemplatesFragment
            LaunchTypes.CONTACTS -> R.id.hAllContactsFragment
            LaunchTypes.GEOFENCE -> R.id.hMySavedGeoFences
            else -> throw Exception()
        }

        hNavGraph.startDestination = hDestinaiton
        hNavController.graph = hNavGraph

    }

}