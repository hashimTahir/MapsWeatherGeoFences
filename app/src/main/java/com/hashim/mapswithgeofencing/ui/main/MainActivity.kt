/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.main

import android.location.Location
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.ActivityMainBinding
import com.hashim.mapswithgeofencing.others.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.utils.*
import com.mancj.materialsearchbar.MaterialSearchBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MaterialSearchBar.OnSearchActionListener {
    private var hLocation: Location? = null
    private val hMainSharedViewModel: MainSharedViewModel by viewModels()
    private lateinit var hActivityMainBinding: ActivityMainBinding
    private lateinit var hNavHostFragments: NavHostFragment
    private lateinit var hNavController: NavController

    @Inject
    lateinit var hSettingsPrefrences: SettingsPrefrences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(hActivityMainBinding.root)

        hInitNavView()

        hSetupListeners()
    }


    private fun hSetupListeners() {

        hActivityMainBinding.hHomeFab.setOnClickListener {
            hNavController.currentDestination?.id?.let {
                hHandleNavigationToMainFragment(
                        id = hNavController.currentDestination!!.id,
                        hNavController = hNavController
                )
            }
        }

        hActivityMainBinding.hBottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (hNavController.currentDestination?.id) {
                R.id.hTemplatesFragment -> {
                    hHandleNavigationToMainFragment(
                            id = hNavController.currentDestination!!.id,
                            hNavController = hNavController
                    )
                }
                R.id.hCompassFragment -> {
                    hHandleNavigationFromCompassFragment(
                            id = hNavController.currentDestination!!.id,
                            hNavController = hNavController
                    )
                }
                R.id.hCalculateRouteFragment -> {
                    hHandleNavigationFromCalculateRouteFragment(
                            id = hNavController.currentDestination!!.id,
                            hNavController = hNavController
                    )
                }
                R.id.hSettingsFragment -> {
                    hHandleNavigationFromSettingsFragment(
                            id = hNavController.currentDestination!!.id,
                            hNavController = hNavController
                    )
                }
                R.id.hWeatherFragment -> {
                    hHandleNavigationFromWeatherFragment(
                            id = hNavController.currentDestination!!.id,
                            hNavController = hNavController
                    )
                }
                R.id.hMainFragment -> {
                    hHandleNavigationFromMainFragment(
                            id = hNavController.currentDestination!!.id,
                            hNavController = hNavController,
                            hContext = this
                    )
                }
            }

            false
        }
    }


    private fun hInitNavView() {

        hActivityMainBinding.hBottomNav.background = null

        hNavHostFragments = supportFragmentManager
                .findFragmentById(R.id.hMainFragmentContainer)
                as NavHostFragment

        hNavController = hNavHostFragments.navController

        hNavController.setGraph(R.navigation.main_nav)
        NavigationUI.setupWithNavController(
                hActivityMainBinding.hBottomNav,
                hNavController
        )
    }

    override fun onSearchStateChanged(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        TODO("Not yet implemented")
    }

    override fun onButtonClicked(buttonCode: Int) {
        TODO("Not yet implemented")
    }


}