/*
 * Copyright (c) 2021/  4/ 6.  Created by Hashim Tahir 
 */

package com.hashim.mapswithgeofencing.utils

import android.content.Context
import androidx.navigation.NavController
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.others.prefrences.HlatLng
import com.hashim.mapswithgeofencing.others.prefrences.PrefTypes
import com.hashim.mapswithgeofencing.others.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.ui.main.fragments.MainFragmentDirections


fun hHandleNavigationToMainFragment(
        id: Int,
        hNavController: NavController
) {
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

 fun hHandleNavigationFromWeatherFragment(
        id: Int,
        hNavController: NavController
) {
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

 fun hHandleNavigationFromSettingsFragment(
        id: Int,
        hNavController: NavController
) {
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

 fun hHandleNavigationFromCalculateRouteFragment(
        id: Int,
        hNavController: NavController,
) {
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

 fun hHandleNavigationFromCompassFragment(
        id: Int,
        hNavController: NavController
) {
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

 fun hHandleTemplatesNav(menuItem: Int) {
    /*Todo: add later*/
}


fun hHandleNavigationFromMainFragment(
        id: Int,
        hNavController: NavController,
        hContext: Context
) {
    when (id) {
        R.id.hCalculateRounteMenu -> {
            hNavController.navigate(R.id.action_hMainFragment_to_hCalculateRouteFragment)
        }
        R.id.hWeatherMenu -> {
            val hSettingsPrefrences = SettingsPrefrences(hContext)
            val hCurrentLocation: HlatLng = hSettingsPrefrences
                    .hGetSettings(PrefTypes.CURRENT_LAT_LNG_PT) as HlatLng
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
