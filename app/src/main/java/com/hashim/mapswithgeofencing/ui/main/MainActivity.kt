/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.main

import android.location.Location
import android.os.Bundle
import android.view.View.GONE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.SettingsPrefrences
import com.hashim.mapswithgeofencing.databinding.ActivityMainBinding
import com.mancj.materialsearchbar.MaterialSearchBar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
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

        }

        hActivityMainBinding.hBottomNav.setOnNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.hCompassMenu -> {
                    Timber.d("hNavigateToMenu")
                }
                R.id.hWeatherMenu -> {
                    val hSettingsPrefrences = SettingsPrefrences(this)
                    val actionHMainFragmentToHWeatherFragment = MainFragmentDirections.actionHMainFragmentToHWeatherFragment(
                            hSettingsPrefrences.hGetCurrentLocation()
                    )
                    hNavController.navigate(actionHMainFragmentToHWeatherFragment)
                }
                R.id.hCalculateRounteMenu -> {
                    Timber.d("hDirectionsMenu")
                }
                R.id.hSettingMenu -> {
                    Timber.d("hExit")
                }
                R.id.hSettingMenu -> {
                    hNavController.navigate(R.id.action_hMainFragment_to_hSettingsFragment)
                }
            }

            false
        }
    }


    private fun hInitNavView() {
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