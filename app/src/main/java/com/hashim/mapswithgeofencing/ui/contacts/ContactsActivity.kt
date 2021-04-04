/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.ActivityContactsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsActivity : AppCompatActivity() {
    lateinit var hActivityContactsBinding: ActivityContactsBinding
    private lateinit var hNavHostFragments: NavHostFragment
    private lateinit var hNavController: NavController

    val hContactsSharedViewModel: ContactsSharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hActivityContactsBinding = ActivityContactsBinding.inflate(
                layoutInflater
        )
        setContentView(hActivityContactsBinding.root)

        hInitNavView()

    }

    private fun hInitNavView() {

        setSupportActionBar(hActivityContactsBinding.toolbar)


        hNavHostFragments = supportFragmentManager
                .findFragmentById(R.id.hContactsFragmentContainer)
                as NavHostFragment

        hNavController = hNavHostFragments.navController

        hNavController.setGraph(R.navigation.contacts_nav)


    }

}