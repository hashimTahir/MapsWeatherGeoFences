/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hashim.mapswithgeofencing.databinding.WeatherFragmentBinding

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var hWeatherViewModel: WeatherViewModel
    private lateinit var hWeatherFragmentBinding: WeatherFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        hWeatherFragmentBinding = WeatherFragmentBinding.inflate(
                inflater,
                container,
                false
        )
        return hWeatherFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hWeatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

}