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
import com.hashim.mapswithgeofencing.others.prefrences.HlatLng
import com.hashim.mapswithgeofencing.others.prefrences.PrefTypes.CURRENT_LAT_LNG_PT
import com.hashim.mapswithgeofencing.others.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.ui.main.fragments.MainFragmentDirections
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

                hHandleToMainNav(hNavController.currentDestination!!.id)
            }

        }

        hActivityMainBinding.hBottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (hNavController.currentDestination?.id) {
                R.id.hTemplatesFragment -> {
                    hHandleTemplatesNav(menuItem.itemId)
                }
                R.id.hCompassFragment -> {
                    hHandleCompassNav(menuItem.itemId)
                }
                R.id.hCalculateRouteFragment -> {
                    hHandleCalulateRouteNav(menuItem.itemId)
                }
                R.id.hSettingsFragment -> {
                    hHandleSettingsNav(menuItem.itemId)
                }
                R.id.hWeatherFragment -> {
                    hHandleWeatherNav(menuItem.itemId)
                }
                R.id.hMainFragment -> {
                    hHandleFromMainNav(menuItem.itemId)
                }
            }

            false
        }
    }

    private fun hHandleFromMainNav(id: Int) {
        when (id) {
            R.id.hCalculateRounteMenu -> {

                hNavController.navigate(R.id.action_hMainFragment_to_hCalculateRouteFragment)
            }
            R.id.hWeatherMenu -> {
                val hSettingsPrefrences = SettingsPrefrences(this)
                val hCurrentLocation: HlatLng = hSettingsPrefrences
                        .hGetSettings(CURRENT_LAT_LNG_PT) as HlatLng
                val actionHMainFragmentToHWeatherFragment = MainFragmentDirections
                        .actionHMainFragmentToHWeatherFragment(hCurrentLocation)
                hNavController.navigate(actionHMainFragmentToHWeatherFragment)
            }
            R.id.hSettingMenu -> {
                hNavController.navigate(R.id.action_hMainFragment_to_hSettingsFragment)
            }
            R.id.hCompassMenu -> {
                hNavController.navigate(R.id.action_hMainFragment_to_hCompassFragment)
            }
        }
    }

    private fun hHandleToMainNav(id: Int) {
        when (id) {
            R.id.hCalculateRouteFragment -> {
                hNavController.navigate(R.id.action_hCalculateRouteFragment_to_hMainFragment)
            }
            R.id.hWeatherFragment -> {
                hNavController.navigate(R.id.action_hWeatherFragment_to_hMainFragment)
            }
            R.id.hSettingsFragment -> {
                hNavController.navigate(R.id.action_hSettingsFragment_to_hMainFragment)
            }
            R.id.hCompassFragment -> {
                hNavController.navigate(R.id.action_hCompassFragment_to_hMainFragment)
            }
        }
    }

    private fun hHandleWeatherNav(id: Int) {

        when (id) {
            R.id.hHomeFab -> {
                hNavController.navigate(R.id.action_hWeatherFragment_to_hMainFragment)
            }
            R.id.hCalculateRounteMenu -> {
                hNavController.navigate(R.id.action_hWeatherFragment_to_hCalculateRouteFragment)
            }
            R.id.hSettingMenu -> {
                hNavController.navigate(R.id.action_hWeatherFragment_to_hSettingsFragment)
            }
            R.id.hCompassMenu -> {
                hNavController.navigate(R.id.action_hWeatherFragment_to_hCompassFragment)
            }
        }

    }

    private fun hHandleSettingsNav(id: Int) {
        when (id) {
            R.id.hHomeFab -> {
                hNavController.navigate(R.id.action_hSettingsFragment_to_hMainFragment)
            }
            R.id.hWeatherMenu -> {
                hNavController.navigate(R.id.action_hSettingsFragment_to_hWeatherFragment)
            }
            R.id.hCalculateRounteMenu -> {
                hNavController.navigate(R.id.action_hSettingsFragment_to_hCalculateRouteFragment)
            }
            R.id.hCompassMenu -> {
                hNavController.navigate(R.id.action_hSettingsFragment_to_hCompassFragment)
            }
        }
    }

    private fun hHandleCalulateRouteNav(id: Int) {
        when (id) {
            R.id.hHomeFab -> {
                hNavController.navigate(R.id.action_hCalculateRouteFragment_to_hMainFragment)
            }
            R.id.hWeatherMenu -> {
                hNavController.navigate(R.id.action_hCalculateRouteFragment_to_hWeatherFragment)
            }
            R.id.hSettingMenu -> {
                hNavController.navigate(R.id.action_hCalculateRouteFragment_to_hSettingsFragment)
            }
            R.id.hCompassMenu -> {
                hNavController.navigate(R.id.action_hCalculateRouteFragment_to_hCompassFragment)
            }
        }
    }

    private fun hHandleCompassNav(id: Int) {

        when (id) {
            R.id.hCalculateRounteMenu -> {
                hNavController.navigate(R.id.action_hCompassFragment_to_hCalculateRouteFragment)
            }
            R.id.hWeatherMenu -> {
                hNavController.navigate(R.id.action_hCompassFragment_to_hWeatherFragment)
            }
            R.id.hSettingMenu -> {
                hNavController.navigate(R.id.action_hCompassFragment_to_hSettingsFragment)
            }
            R.id.hHomeFab -> {
                hNavController.navigate(R.id.action_hCompassFragment_to_hMainFragment)
            }
        }
    }

    private fun hHandleTemplatesNav(menuItem: Int) {
        /*Todo: add later*/
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