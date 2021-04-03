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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.FragmentAllContactsBinding
import com.hashim.mapswithgeofencing.ui.events.ContactsStateEvent.OnFetchContacts
import com.hashim.mapswithgeofencing.ui.events.ContactsStateEvent.OnSaveContacts
import com.hashim.mapswithgeofencing.utils.UiHelper
import timber.log.Timber

class AllContactsFragment : Fragment() {

    lateinit var hAllContactsBinding: FragmentAllContactsBinding
    lateinit var hAllContactsAdapter: AllContactsAdapter
    val hContactsSharedViewModel: ContactsSharedViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        hAllContactsBinding = FragmentAllContactsBinding.inflate(
                inflater,
                container,
                false
        )

        return hAllContactsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hInitRecyclerView()

        hSetListeners()

        hSubscribeObservers()

        if (hHasPermission()) {
            hContactsSharedViewModel.hSetStateEvent(OnFetchContacts())
        } else {
            hRequestPermissions()
        }
    }

    private fun hSetListeners() {

        hAllContactsBinding.hSaveContactsB.setOnClickListener {
            val hSelectedContacts = hAllContactsAdapter.hGetSelectedContacts()
            hContactsSharedViewModel.hSetStateEvent(OnSaveContacts(hSelectedContacts))
        }

        hAllContactsBinding.hViewContactsB.setOnClickListener {
            findNavController().navigate(R.id.action_hAllContactsFragment_to_hSavedContactsFragment)
        }

    }


    private fun hInitRecyclerView() {
        hAllContactsAdapter = AllContactsAdapter(requireContext()) { postion: Int, isChecked: Boolean ->
            Timber.d("Postion $postion isChecked $isChecked")
        }

        hAllContactsBinding.hContactsRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = hAllContactsAdapter


            setIndexTextSize = 12
            setIndexBarColor("#ff212121")
            mIndexBarCornerRadius = 5
            mIndexbarMargin = 0f
            mIndexbarWidth = 50f
            mPreviewPadding = 2
            mIndexBarTransparentValue = 0.4.toFloat()
            setIndexBarVisibility(true)
            setIndexBarHighLightTextVisibility(true)
            indexbarHighLightTextColor = ContextCompat.getColor(requireContext(), R.color.rvColor)
            mIndexbarTextColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

    private fun hSubscribeObservers() {
        hContactsSharedViewModel.hDataState.observe(viewLifecycleOwner) { dataState ->
            dataState.hData?.let { contactsViewState ->
                contactsViewState.hContactsFields.hContactList?.let { contactList ->
                    Timber.d("hDataState $contactList")
                }
            }

        }
        hContactsSharedViewModel.hContactsViewState.observe(viewLifecycleOwner) { contactsViewState ->
            contactsViewState.hContactsFields.hContactList?.let { contactList ->
                hAllContactsAdapter.hSetData(contactList)
            }

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
                    view = hAllContactsBinding.hSnackBarCL,
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
                    /*Execute contacts search*/
                } else {
                    UiHelper.hShowSnackBar(
                            view = hAllContactsBinding.hSnackBarCL,
                            message = getString(R.string.contacts_permision),
                            onTakeAction = {}
                    )
                }
            }
}