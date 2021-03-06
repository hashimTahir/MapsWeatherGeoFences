/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.contcats

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
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.ContactsSharedViewModel
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.contcats.adapter.AllContactsAdapter
import com.hashim.mapswithgeofencing.utils.PermissionsUtils.Companion.hRequestContactPermission
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

        hRequestPermissons()
    }

    private fun hRequestPermissons() {
        hRequestContactPermission(
                requireContext(),
                hRequestPermissionLauncher) {
            hContactsSharedViewModel.hSetStateEvent(OnFetchContacts())
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


    private val hRequestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted: Boolean ->
                if (isGranted) {
                    hContactsSharedViewModel.hSetStateEvent(OnFetchContacts())
                } else {
                    UiHelper.hShowSnackBar(
                            view = hAllContactsBinding.hSnackBarCL,
                            message = getString(R.string.contacts_permision),
                            onTakeAction = {
                                hRequestPermissons()
                            }
                    )
                }
            }
}