/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.ui.main

import android.location.Location
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.ActivityMain2Binding
import com.mancj.materialsearchbar.MaterialSearchBar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MaterialSearchBar.OnSearchActionListener{
    private var hLocation: Location? = null
    private val hMainSharedViewModel: MainSharedViewModel by viewModels()
    private lateinit var hActivityMainBinding: ActivityMain2Binding
    private lateinit var hNavHostFragments: NavHostFragment
    private lateinit var hNavController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hActivityMainBinding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(hActivityMainBinding.root)

        hInitNavView()

        hSetupListeners()

    }


    private fun hSetupListeners() {
        hActivityMainBinding.hSearchBar.setHint("Search here")
        hActivityMainBinding.hSearchBar.setSpeechMode(true)
        hActivityMainBinding.hSearchBar.setOnSearchActionListener(this)

        hActivityMainBinding.hBottomNav.setOnNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.hNavigateToMenu -> {
                    Timber.d("hNavigateToMenu")
                }
                R.id.hWeatherMenu -> {
                    hNavController.navigate(R.id.action_hMainFragment_to_hWeatherFragment)
                }
                R.id.hDirectionsMenu -> {
                    Timber.d("hDirectionsMenu")
                }
                R.id.hExit -> {
                    Timber.d("hExit")
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