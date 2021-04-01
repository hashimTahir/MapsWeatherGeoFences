/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.FragmentDisplayContactsBinding
import com.hashim.mapswithgeofencing.utils.Constants
import com.hashim.mapswithgeofencing.utils.UiHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class DisplayContactsFragment : Fragment() {
    private lateinit var hFragmentDisplayContactsBinding: FragmentDisplayContactsBinding
    val hDisplayContactsViewModel: DisplayContactsViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        hFragmentDisplayContactsBinding = FragmentDisplayContactsBinding.inflate(
                inflater,
                container,
                false
        )
        return hFragmentDisplayContactsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (hHasPermission()) {
            hDisplayContactsViewModel.hFindContacts()
        } else {
            hRequestPermissions()
        }

    }


    private fun hHasPermission(): Boolean {
        return (
                ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_CONTACTS)
                        == PackageManager.PERMISSION_GRANTED
                )
    }

    private fun hRequestPermissions() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            hRequestPermissionLauncher.launch(
                    Manifest.permission.READ_CONTACTS
            )
        } else {
            UiHelper.hShowSnackBar(
                    view = hFragmentDisplayContactsBinding.hSnackBarCL,
                    message = getString(R.string.contacts_permision),
                    onTakeAction = {
                        hRequestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                    }
            )
        }
    }


    private val hRequestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted: Boolean ->
                if (isGranted) {
                    hDisplayContactsViewModel.hFindContacts()
                } else {
                    UiHelper.hShowSnackBar(
                            view = hFragmentDisplayContactsBinding.hSnackBarCL,
                            message = getString(R.string.contacts_permision),
                            onTakeAction = {}
                    )
                }
            }
}