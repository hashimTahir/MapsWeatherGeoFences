/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Prefrences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hashim.mapswithgeofencing.Contacts.ContactsModelWithIds;
import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Models.HLatLngModel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SettingsPrefrences {

    private final static String H_TEMPLATES_DATA = "H_TEMPLATES_DATA";
    private final String NOT_FIRST_RUN = "not_first_run";
    private final String H_PAID_STATUS = "paid_status";
    private final String H_SETTINGS_DATA = "settings_data";
    private final String H_MAPS_TYPE = "MapsType";
    private final String H_DISTANCE_UNIT = "hDistanceUnit";
    private final String H_RADIUS = "hRadiusInKms";
    private final String H_TRACKING = "hTracking";
    private final String H_LANGUAGE = "hLanguage";
    private final String H_APP_COUNTER = "H_APP_COUNTER";
    private final String H_PERMISSIONS_CHECK = "H_PERMISSIONS_CHECK";
    private final String H_TEMPRATURE_UNIT = "H_TEMP_UNIT";
    private final String PREF_NAME="com.hashim.mapswithgeofencing.Prefrences";
    private SharedPreferences.Editor hEditor;
    private SharedPreferences hProfilePrefrences;
    private final String H_CONTACTS_DATA = "H_SAVED_CONTACTS";
    private Context hContext;
    private String H_FILE_NAME = "H_HASHIM_TEMPLATES.txt";

    private String H_CURRENT_LAT_LNG = "H_CURRENT_LAT_LNG";
    private String H_EMERGENCY_MESSAGE = "H_EMERGENCY_MESSAGE";
    private String H_TRACK_ME_MESSAGE="H_TRACK_ME_MESSAGE";

    @SuppressLint("CommitPrefEdits")
    public SettingsPrefrences(Context context) {
        this.hContext = context;
        int PRIVATE_MODE = Context.MODE_PRIVATE;
        hProfilePrefrences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        hEditor = hProfilePrefrences.edit();
    }

    public String hGetSettingsData() {
        return hProfilePrefrences.getString(H_SETTINGS_DATA, "VALUE");
    }

    public void hSetSettingsData(String string) {
        hEditor.putString(H_SETTINGS_DATA, string);
        hEditor.apply();
    }

    public boolean hGetPaidStatus() {
        return hProfilePrefrences.getBoolean(H_PAID_STATUS, false);
    }

    public void hSetPaidStatus(boolean a) {
        hEditor.putBoolean(H_PAID_STATUS, a);
        hEditor.apply();
    }


    public void hSaveMapType(int mapTypeNormal) {
        hEditor.putInt(H_MAPS_TYPE, mapTypeNormal);
        hEditor.apply();
    }

    public int hGetMapsType() {
        return hProfilePrefrences.getInt(H_MAPS_TYPE, GoogleMap.MAP_TYPE_NORMAL);
    }

    public void hSaveDistanceUnit(int position) {
        hEditor.putInt(H_DISTANCE_UNIT, position);
        hEditor.apply();
    }

    public void hSaveRadius(int progress) {
        hEditor.putInt(H_RADIUS, progress);
        hEditor.apply();
    }

    public void hSaveTrackingValue(boolean isChecked) {
        hEditor.putBoolean(H_TRACKING, isChecked);
        hEditor.apply();
    }

    public int hGetDistanceUnit() {
        return hProfilePrefrences.getInt(H_DISTANCE_UNIT, 0);
    }

    public int hGetRadius() {
        return hProfilePrefrences.getInt(H_RADIUS, 1);
    }

    public boolean hGetTracking() {
        return hProfilePrefrences.getBoolean(H_TRACKING, true);
    }

    public void hSaveLanguage(int position) {
        hEditor.putInt(H_LANGUAGE, position);
        hEditor.apply();
    }

    public int hGetLanguage() {
        return hProfilePrefrences.getInt(H_LANGUAGE, 0);
    }

    public long hGetAppCounter() {
        return hProfilePrefrences.getLong(H_APP_COUNTER, 0);
    }

    public void hSaveAppCounter(long hAppCounter) {
        hEditor.putLong(H_APP_COUNTER, hAppCounter);
        hEditor.apply();
    }

    public void hSetPermissionCheck(boolean b) {
        hEditor.putBoolean(H_PERMISSIONS_CHECK, b);
        hEditor.apply();
    }

    public boolean hIsAllPermissionsGranted() {
        return hProfilePrefrences.getBoolean(H_PERMISSIONS_CHECK, false);
    }

    public int hGetTempUnit() {
        return hProfilePrefrences.getInt(H_TEMPRATURE_UNIT, 0);
    }

    public void hSaveTempUnit(int position) {
        hEditor.putInt(H_TEMPRATURE_UNIT, position);
        hEditor.apply();
    }

    public List<ContactsModelWithIds> hGetSavedContacts() {
        String hJsonString = hProfilePrefrences.getString(H_CONTACTS_DATA, null);
        if (hJsonString != null) {
            Type type = new TypeToken<List<ContactsModelWithIds>>() {
            }.getType();
            Gson hGson = new Gson();
            return hGson.fromJson(hJsonString, type);
        }
        return null;
    }

    public void hSaveContacts(List<ContactsModelWithIds> contactsModels) {
//        List<ContactsModelWithIds> hTestContactModelWithIds = hGetSavedContacts();
//        if (hTestContactModelWithIds != null) {
//            hTestContactModelWithIds.addAll(contactsModels);
//            Gson hGson = new Gson();
//            String hJson = hGson.toJson(hTestContactModelWithIds);
//            hEditor.putString(H_CONTACTS_DATA, hJson);
//            hEditor.commit();
//        } else {
        Gson hGson = new Gson();
        Type listType = new TypeToken<List<ContactsModelWithIds>>() {
        }.getType();
        String hJson = hGson.toJson(contactsModels, listType);
        hEditor.putString(H_CONTACTS_DATA, hJson);
        hEditor.apply();
//        }
    }

    public void hDeleteSavedContacts() {
        hEditor.remove(H_CONTACTS_DATA);
        hEditor.apply();
    }

    public List<String> hGetCustomTemplates() {

        List<String> hList = new ArrayList<>();
        FileInputStream input = null;
        try {
            input = hContext.openFileInput(H_FILE_NAME);

            DataInputStream din = new DataInputStream(input);
            int sz = din.readInt(); // Read line count
            for (int i = 0; i < sz; i++) {
                String line = din.readUTF();
                hList.add(line);
            }
            din.close();
        } catch (IOException e) {
            hList = null;
            e.printStackTrace();
        }
        return hList;
    }

    public void hSaveCustomTemplate(String hText) {

        List<String> hList = hGetCustomTemplates();

        if (hList != null) {
            hList.add(hText);
        } else {
            hList = new ArrayList<>();
            hList.add(hText);
        }

        try {
            FileOutputStream output = hContext.openFileOutput(H_FILE_NAME, Context.MODE_PRIVATE);
            DataOutputStream dout = new DataOutputStream(output);
            dout.writeInt(hList.size());
            for (String line : hList)
                dout.writeUTF(line);
            dout.flush();
            dout.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public void hSetEnableDisableEmergencySettings(boolean enableDisable) {
        hEditor.putBoolean(Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS, enableDisable);
        hEditor.apply();
    }


    public boolean hGetEnableDisableEmergencySettings() {
        return hProfilePrefrences.getBoolean(Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS, false);
    }


    public void hSetEnableDisableTrackMeSettings(boolean enableDisable) {
        hEditor.putBoolean(Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS, enableDisable);
        hEditor.apply();
    }


    public boolean hGetEnableDisableTrackMeSettings() {
        return hProfilePrefrences.getBoolean(Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS, false);
    }

    public void hSaveCurrentLocation(HLatLngModel hLatLng) {

        Gson hGson = new Gson();
        Type hType = new TypeToken<HLatLngModel>() {
        }.getType();
        String hJson = hGson.toJson(hLatLng, hType);
        hEditor.putString(H_CURRENT_LAT_LNG, hJson);
        hEditor.apply();
    }

    public HLatLngModel hGetCurrentLocation() {
        String hJsonString = hProfilePrefrences.getString(H_CURRENT_LAT_LNG, null);
        if (hJsonString != null) {
            Type type = new TypeToken<HLatLngModel>() {
            }.getType();
            Gson hGson = new Gson();
            return hGson.fromJson(hJsonString, type);
        }
        return null;
    }

    public void hSaveEmergencyMessage(String hUserName) {
        hEditor.putString(H_EMERGENCY_MESSAGE, hUserName);
        hEditor.apply();
    }

    public String hGetEmergencyMessage() {
        return hProfilePrefrences.getString(H_EMERGENCY_MESSAGE, null);
    }

    public void hSaveTrackMeMessage(String hUserName) {
        hEditor.putString(H_TRACK_ME_MESSAGE, hUserName);
        hEditor.apply();
    }
    public String hGetTrackMeMessage() {
        return hProfilePrefrences.getString(H_TRACK_ME_MESSAGE, null);
    }
}



