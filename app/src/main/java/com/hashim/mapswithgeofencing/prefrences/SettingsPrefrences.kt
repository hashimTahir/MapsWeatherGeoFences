/*
 * Copyright (c) 2021/  3/ 28.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.prefrences

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
                PrefTypes.ALL_PT -> {
                    return hJsonString
                }
                PrefTypes.CURRENT_LAT_LNG_PT -> {
                    hMap = hGson.fromJson(hJsonString, hGetTokenType(null))
                    return hGson.fromJson<HlatLng?>(
                            hMap[hPrefTypes.name].toString(),
                            hGetTokenType(hPrefTypes)
                    )
                }
                PrefTypes.MAPS_TYPE_PT -> {
                    return null
                }
                PrefTypes.DISTANCE_UNIT_PT -> {
                    return null
                }
                PrefTypes.RADIUS_UNIT_PT -> {
                    return null
                }
                PrefTypes.TRACKING_PT -> {
                    hMap = hGson.fromJson(hJsonString, hGetTokenType(null))
                    return hGson.fromJson<Boolean?>(
                            hMap[hPrefTypes.name].toString(),
                            hGetTokenType(hPrefTypes)
                    )
                }
                PrefTypes.LANGUAGE_PT -> {
                    return null
                }
                PrefTypes.TEMPRATURE_UNIT_PT -> {
                    return null
                }
                PrefTypes.TRACK_ME_MESSAGE_PT -> {
                    return null
                }
                PrefTypes.EMERGENCY_PT -> {
                    hMap = hGson.fromJson(hJsonString, hGetTokenType(null))
                    return hGson.fromJson<Boolean?>(
                            hMap[hPrefTypes.name].toString(),
                            hGetTokenType(hPrefTypes)
                    )
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
        PrefTypes.ALL_PT, PrefTypes.TRACK_ME_MESSAGE_PT -> {
            hToken = object : TypeToken<String?>() {}.type
            hToken
        }
        PrefTypes.CURRENT_LAT_LNG_PT -> {
            hToken = object : TypeToken<HlatLng?>() {}.type
            hToken
        }
        PrefTypes.TRACKING_PT, PrefTypes.EMERGENCY_PT -> {
            hToken = object : TypeToken<Boolean?>() {}.type
            hToken
        }
        PrefTypes.LANGUAGE_PT,
        PrefTypes.MAPS_TYPE_PT,
        PrefTypes.DISTANCE_UNIT_PT,
        PrefTypes.RADIUS_UNIT_PT,
        PrefTypes.TEMPRATURE_UNIT_PT -> {
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