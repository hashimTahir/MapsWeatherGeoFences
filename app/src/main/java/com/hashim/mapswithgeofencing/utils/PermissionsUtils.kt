package com.hashim.mapswithgeofencing.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import timber.log.Timber
import java.util.*


private val MAX_NUMBER_REQUEST_PERMISSIONS = 4
private var hPermissionsCount: Int = 0
private val hPermissions = Arrays.asList(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.ACCESS_FINE_LOCATION,
)
private val H_READ_EXTERNAL_STORAGE = 0
private val H_READ_INTERNAL_STORAGE = 1
private val H_READ_CONTACTS = 2
private val H_LOCATION = 3

class PermissionsUtils {
    companion object {
        fun hRequestPermission(
                context: Context,
                launcher: ActivityResultLauncher<Array<String>>,
                permisionCallBack: () -> Unit,
        ) {
            Timber.d("Requesting permissions")

            if (!hHasListOfPermissions(context, hPermissions)) {
                if (hPermissionsCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                    launcher.launch(hPermissions.toTypedArray())
                }
            } else {
                permisionCallBack()
            }
        }

        private fun hHasListOfPermissions(context: Context, hPermissionsList: List<String>): Boolean {
            var hasPermissions = true
            for (permission in hPermissionsList) {
                hasPermissions = hasPermissions and hIsPermissionGranted(context, permission)
            }
            return hasPermissions
        }

        private fun hIsPermissionGranted(context: Context, permission: String) =
                ContextCompat.checkSelfPermission(context, permission) ==
                        PackageManager.PERMISSION_GRANTED

        fun hRequestContactPermission(
                context: Context,
                launcher: ActivityResultLauncher<String>,
                permisionCallBack: () -> Unit,
        ) {
            Timber.d("Requesting permissions")

            if (!hHasListOfPermissions(
                            context,
                            Arrays.asList(hPermissions.get(H_READ_CONTACTS))
                    )
            ) {

                launcher.launch(hPermissions.get(H_READ_CONTACTS))

            } else {
                permisionCallBack()
            }
        }


        fun hRequestLocationPermission(
                context: Context,
                launcher: ActivityResultLauncher<String>,
                permisionCallBack: () -> Unit,
        ) {
            Timber.d("Requesting permissions")

            if (!hHasListOfPermissions(
                            context,
                            Arrays.asList(hPermissions.get(H_LOCATION))
                    )
            ) {

                launcher.launch(hPermissions.get(H_LOCATION))

            } else {
                permisionCallBack()
            }
        }
    }


}