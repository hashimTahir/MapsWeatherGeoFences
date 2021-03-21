/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin

import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.maps.GoogleMap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hashim.mapswithgeofencing.Contacts.ContactsModelWithIds
import com.hashim.mapswithgeofencing.Helper.Constants
import com.hashim.mapswithgeofencing.Models.HLatLngModel
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class SettingsPrefrences {

    private val H_TEMPLATES_DATA = "H_TEMPLATES_DATA"
    private val NOT_FIRST_RUN = "not_first_run"
    private val H_PAID_STATUS = "paid_status"
    private val H_SETTINGS_DATA = "settings_data"
    private val H_MAPS_TYPE = "MapsType"
    private val H_DISTANCE_UNIT = "hDistanceUnit"
    private val H_RADIUS = "hRadiusInKms"
    private val H_TRACKING = "hTracking"
    private val H_LANGUAGE = "hLanguage"
    private val H_APP_COUNTER = "H_APP_COUNTER"
    private val H_PERMISSIONS_CHECK = "H_PERMISSIONS_CHECK"
    private val H_TEMPRATURE_UNIT = "H_TEMP_UNIT"
    private val PREF_NAME = "com.hashim.mapswithgeofencing.Prefrences"
    private var hEditor: SharedPreferences.Editor? = null
    private var hProfilePrefrences: SharedPreferences? = null
    private val H_CONTACTS_DATA = "H_SAVED_CONTACTS"
    private var hContext: Context? = null
    private val H_FILE_NAME = "H_HASHIM_TEMPLATES.txt"

    private val H_CURRENT_LAT_LNG = "H_CURRENT_LAT_LNG"
    private val H_EMERGENCY_MESSAGE = "H_EMERGENCY_MESSAGE"
    private val H_TRACK_ME_MESSAGE = "H_TRACK_ME_MESSAGE"


    constructor(context: Context) {
        hContext = context
        val PRIVATE_MODE = Context.MODE_PRIVATE
        hProfilePrefrences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        hEditor = hProfilePrefrences?.edit()
    }


    fun hGetSettingsData(): String? {
        return hProfilePrefrences!!.getString(H_SETTINGS_DATA, "VALUE")
    }

    fun hSetSettingsData(string: String?) {
        hEditor!!.putString(H_SETTINGS_DATA, string)
        hEditor!!.apply()
    }

    fun hGetPaidStatus(): Boolean {
        return hProfilePrefrences!!.getBoolean(H_PAID_STATUS, false)
    }

    fun hSetPaidStatus(a: Boolean) {
        hEditor!!.putBoolean(H_PAID_STATUS, a)
        hEditor!!.apply()
    }


    fun hSaveMapType(mapTypeNormal: Int) {
        hEditor!!.putInt(H_MAPS_TYPE, mapTypeNormal)
        hEditor!!.apply()
    }

    fun hGetMapsType(): Int {
        return hProfilePrefrences!!.getInt(H_MAPS_TYPE, GoogleMap.MAP_TYPE_NORMAL)
    }

    fun hSaveDistanceUnit(position: Int) {
        hEditor!!.putInt(H_DISTANCE_UNIT, position)
        hEditor!!.apply()
    }

    fun hSaveRadius(progress: Int) {
        hEditor!!.putInt(H_RADIUS, progress)
        hEditor!!.apply()
    }

    fun hSaveTrackingValue(isChecked: Boolean) {
        hEditor!!.putBoolean(H_TRACKING, isChecked)
        hEditor!!.apply()
    }

    fun hGetDistanceUnit(): Int {
        return hProfilePrefrences!!.getInt(H_DISTANCE_UNIT, 0)
    }

    fun hGetRadius(): Int {
        return hProfilePrefrences!!.getInt(H_RADIUS, 1)
    }

    fun hGetTracking(): Boolean {
        return hProfilePrefrences!!.getBoolean(H_TRACKING, true)
    }

    fun hSaveLanguage(position: Int) {
        hEditor!!.putInt(H_LANGUAGE, position)
        hEditor!!.apply()
    }

    fun hGetLanguage(): Int {
        return hProfilePrefrences!!.getInt(H_LANGUAGE, 0)
    }

    fun hGetAppCounter(): Long {
        return hProfilePrefrences!!.getLong(H_APP_COUNTER, 0)
    }

    fun hSaveAppCounter(hAppCounter: Long) {
        hEditor!!.putLong(H_APP_COUNTER, hAppCounter)
        hEditor!!.apply()
    }

    fun hSetPermissionCheck(b: Boolean) {
        hEditor!!.putBoolean(H_PERMISSIONS_CHECK, b)
        hEditor!!.apply()
    }

    fun hIsAllPermissionsGranted(): Boolean {
        return hProfilePrefrences!!.getBoolean(H_PERMISSIONS_CHECK, false)
    }

    fun hGetTempUnit(): Int {
        return hProfilePrefrences!!.getInt(H_TEMPRATURE_UNIT, 0)
    }

    fun hSaveTempUnit(position: Int) {
        hEditor!!.putInt(H_TEMPRATURE_UNIT, position)
        hEditor!!.apply()
    }

    fun hGetSavedContacts(): List<ContactsModelWithIds?>? {
        val hJsonString = hProfilePrefrences!!.getString(H_CONTACTS_DATA, null)
        if (hJsonString != null) {
            val type = object : TypeToken<List<ContactsModelWithIds?>?>() {}.type
            val hGson = Gson()
            return hGson.fromJson(hJsonString, type)
        }
        return null
    }

    fun hSaveContacts(contactsModels: List<ContactsModelWithIds?>?) {
//        List<ContactsModelWithIds> hTestContactModelWithIds = hGetSavedContacts();
//        if (hTestContactModelWithIds != null) {
//            hTestContactModelWithIds.addAll(contactsModels);
//            Gson hGson = new Gson();
//            String hJson = hGson.toJson(hTestContactModelWithIds);
//            hEditor.putString(H_CONTACTS_DATA, hJson);
//            hEditor.commit();
//        } else {
        val hGson = Gson()
        val listType = object : TypeToken<List<ContactsModelWithIds?>?>() {}.type
        val hJson = hGson.toJson(contactsModels, listType)
        hEditor!!.putString(H_CONTACTS_DATA, hJson)
        hEditor!!.apply()
//        }
    }

    fun hDeleteSavedContacts() {
        hEditor!!.remove(H_CONTACTS_DATA)
        hEditor!!.apply()
    }

    fun hGetCustomTemplates(): MutableList<String?>? {
        var hList: MutableList<String?>? = ArrayList()
        var input: FileInputStream? = null
        try {
            input = hContext!!.openFileInput(H_FILE_NAME)
            val din = DataInputStream(input)
            val sz = din.readInt() // Read line count
            for (i in 0 until sz) {
                val line = din.readUTF()
                hList!!.add(line)
            }
            din.close()
        } catch (e: IOException) {
            hList = null
            e.printStackTrace()
        }
        return hList
    }

    fun hSaveCustomTemplate(hText: String?) {
        var hList = hGetCustomTemplates()
        if (hList != null) {
            hList.add(hText)
        } else {
            hList = ArrayList()
            hList.add(hText)
        }
        try {
            val output = hContext!!.openFileOutput(H_FILE_NAME, Context.MODE_PRIVATE)
            val dout = DataOutputStream(output)
            dout.writeInt(hList.size)
            for (line in hList) dout.writeUTF(line)
            dout.flush()
            dout.close()
        } catch (exc: IOException) {
            exc.printStackTrace()
        }
    }

    fun hSetEnableDisableEmergencySettings(enableDisable: Boolean) {
        hEditor!!.putBoolean(Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS, enableDisable)
        hEditor!!.apply()
    }


    fun hGetEnableDisableEmergencySettings(): Boolean {
        return hProfilePrefrences!!.getBoolean(Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS, false)
    }


    fun hSetEnableDisableTrackMeSettings(enableDisable: Boolean) {
        hEditor!!.putBoolean(Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS, enableDisable)
        hEditor!!.apply()
    }


    fun hGetEnableDisableTrackMeSettings(): Boolean {
        return hProfilePrefrences!!.getBoolean(Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS, false)
    }

    fun hSaveCurrentLocation(hLatLng: HLatLngModel?) {
        val hGson = Gson()
        val hType = object : TypeToken<HLatLngModel?>() {}.type
        val hJson = hGson.toJson(hLatLng, hType)
        hEditor!!.putString(H_CURRENT_LAT_LNG, hJson)
        hEditor!!.apply()
    }

    fun hGetCurrentLocation(): HLatLngModel? {
        val hJsonString = hProfilePrefrences!!.getString(H_CURRENT_LAT_LNG, null)
        if (hJsonString != null) {
            val type = object : TypeToken<HLatLngModel?>() {}.type
            val hGson = Gson()
            return hGson.fromJson(hJsonString, type)
        }
        return null
    }

    fun hSaveEmergencyMessage(hUserName: String?) {
        hEditor!!.putString(H_EMERGENCY_MESSAGE, hUserName)
        hEditor!!.apply()
    }

    fun hGetEmergencyMessage(): String? {
        return hProfilePrefrences!!.getString(H_EMERGENCY_MESSAGE, null)
    }

    fun hSaveTrackMeMessage(hUserName: String?) {
        hEditor!!.putString(H_TRACK_ME_MESSAGE, hUserName)
        hEditor!!.apply()
    }

    fun hGetTrackMeMessage(): String? {
        return hProfilePrefrences!!.getString(H_TRACK_ME_MESSAGE, null)
    }
}