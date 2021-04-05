/*
 * Copyright (c) 2021/  4/ 4.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.others.prefrences

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hashim.mapswithgeofencing.others.prefrences.PrefTypes.*
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Type
import java.util.*


class SettingsPrefrences(context: Context) {

    private val PREF_NAME = "com.hashim.mapswithgeofencing.Prefrences"
    private var hProfilePrefrences: SharedPreferences? = null
    private val H_SETTINGS = "Settings"

    init {
        hProfilePrefrences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun hGetSettings(hPrefTypes: PrefTypes): Any? {
        val hMap: HashMap<String, Any>
        val hJsonString = hProfilePrefrences?.getString(H_SETTINGS, null)
        val hGson = Gson()

        if (hJsonString != null) {
            when (hPrefTypes) {
                ALL_PT -> {
                    hMap = hGson.fromJson(hJsonString, hGetTokenType(null))
                    return hMap
                }
                CURRENT_LAT_LNG_PT -> {
                    hMap = hGson.fromJson(hJsonString, hGetTokenType(null))
                    return hGson.fromJson<HlatLng?>(
                            hMap[hPrefTypes.name].toString(),
                            hGetTokenType(hPrefTypes)
                    )
                }

                TRACKING_PT,
                EMERGENCY_PT,
                -> {
                    hMap = hGson.fromJson(hJsonString, hGetTokenType(null))
                    return hGson.fromJson<Boolean?>(
                            hMap[hPrefTypes.name].toString(),
                            hGetTokenType(hPrefTypes)
                    )
                }
                LANGUAGE_PT,
                RADIUS_UNIT_PT,
                TEMPRATURE_UNIT_PT,
                DISTANCE_UNIT_PT,
                MAPS_TYPE_PT,
                -> {
                    hMap = hGson.fromJson(hJsonString, hGetTokenType(null))
                    return hGson.fromJson<Int?>(
                            hMap[hPrefTypes.name].toString(),
                            hGetTokenType(hPrefTypes)
                    )
                }

                TRACK_ME_MESSAGE_PT -> {
                    return null
                }
                else ->
                    return null
            }
        } else {
            return null
        }
    }

    fun hSaveSettings(hPrefTypes: PrefTypes, value: Any) {
        var hMap = HashMap<String, Any>()
        val hGson = Gson()
        val hJsonString = hProfilePrefrences?.getString(H_SETTINGS, null)
        if (hJsonString != null) {

            hMap = hGson.fromJson(hJsonString, hGetTokenType(null))
            hMap[hPrefTypes.name] = value

            hProfilePrefrences?.edit()
                    ?.putString(H_SETTINGS, hGson.toJson(hMap, hGetTokenType(null)))
                    ?.apply()
        } else {
            hMap[hPrefTypes.name] = value


            hProfilePrefrences?.edit()
                    ?.putString(H_SETTINGS, hGson.toJson(hMap, hGetTokenType(null)))
                    ?.apply()
        }
    }
}

private fun hGetTokenType(prefTypes: PrefTypes?): Type? {
    val hToken: Type
    return when (prefTypes) {
        ALL_PT, TRACK_ME_MESSAGE_PT -> {
            hToken = object : TypeToken<String?>() {}.type
            hToken
        }
        CURRENT_LAT_LNG_PT -> {
            hToken = object : TypeToken<HlatLng?>() {}.type
            hToken
        }
        TRACKING_PT, EMERGENCY_PT -> {
            hToken = object : TypeToken<Boolean?>() {}.type
            hToken
        }
        LANGUAGE_PT,
        MAPS_TYPE_PT,
        DISTANCE_UNIT_PT,
        RADIUS_UNIT_PT,
        TEMPRATURE_UNIT_PT -> {
            hToken = object : TypeToken<Int?>() {}.type
            hToken
        }
        else -> {
            hToken = object : TypeToken<HashMap<String, Any>>() {}.type
            hToken
        }
    }
}

@Parcelize
data class HlatLng(
        val hLat: Double? = null,
        val hLng: Double? = null,
) : Parcelable