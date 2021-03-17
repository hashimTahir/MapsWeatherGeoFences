package com.hashim.mapswithgeofencing.tokotlin.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hashim.mapswithgeofencing.R

class MainActivity : AppCompatActivity() {
    val hMainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

    }
}