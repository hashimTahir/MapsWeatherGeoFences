package com.hashim.mapswithgeofencing;

import android.app.Application;

import com.hashim.mapswithgeofencing.Prefrences.SettingsPrefrences;

@SuppressWarnings("HardCodedStringLiteral")
public class ApplicationClass extends Application {

        public static final String H_AD_MOB_TEST_ID = "ca-app-pub-1553263417351965~4631220552";
//    private static final String H_AD_MOB_TEST_ID = "hashim";
    public static long hAppCounter;
    public static boolean hIsAppPaid = false;
    public static boolean hIsLocationPermissionGranted = false;


    @Override
    public void onCreate() {
        super.onCreate();

        SettingsPrefrences hSettingsPrefrences = new SettingsPrefrences(getApplicationContext());
        hIsAppPaid = hSettingsPrefrences.hGetPaidStatus();
        hAppCounter = hSettingsPrefrences.hGetAppCounter();
        hIsLocationPermissionGranted = hSettingsPrefrences.hIsAllPermissionsGranted();
    }
}
