package com.hashim.mapswithgeofencing.tokotlin.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.ActivityMain2Binding

class MainActivity : AppCompatActivity() {
    private val hMainViewModel: MainViewModel by viewModels()
    private lateinit var hActivityMainBinding: ActivityMain2Binding

    private lateinit var hNavHostFragments: NavHostFragment

    private lateinit var hNavController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hActivityMainBinding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(hActivityMainBinding.root)

        hInitNavView()
    }


    private fun hInitNavView() {
        hNavHostFragments = supportFragmentManager
                .findFragmentById(R.id.hMainFragmentContainerV) as NavHostFragment
        hNavController = hNavHostFragments.navController

        hNavController.setGraph(R.navigation.main_nav)
        NavigationUI.setupWithNavController(hActivityMainBinding.hBottomNav, hNavController)
    }
}