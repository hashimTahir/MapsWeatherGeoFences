/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.contcats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hashim.mapswithgeofencing.databinding.FragmentSavedContactsBinding
import com.hashim.mapswithgeofencing.ui.events.ContactsStateEvent.OnGetContacts
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.ContactsSharedViewModel
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.contcats.adapter.SwipeToDeleteCallback
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.contcats.adapter.ViewContactsAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class SavedContactsFragment : Fragment() {
    private lateinit var hFragmentSavedContactsBinding: FragmentSavedContactsBinding
    val hContactsSharedViewModel: ContactsSharedViewModel by activityViewModels()
    lateinit var hViewContactsAdapter: ViewContactsAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        hFragmentSavedContactsBinding = FragmentSavedContactsBinding.inflate(
                inflater,
                container,
                false
        )
        return hFragmentSavedContactsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hInitRecyclerView()

        hSubscribeObservers()

        hContactsSharedViewModel.hSetStateEvent(OnGetContacts())

    }

    private fun hInitRecyclerView() {
        hViewContactsAdapter = ViewContactsAdapter(requireContext())

        val hSwipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var viewContactsAdapter = hFragmentSavedContactsBinding.hSavedContactsRv.adapter as ViewContactsAdapter
                viewContactsAdapter.hRemoveItem(viewHolder.adapterPosition)
                /*Todo: Notify viewmodel of the changes*/
            }
        }
        val hItemTouchHelper = ItemTouchHelper(hSwipeToDeleteCallback)
        hItemTouchHelper.attachToRecyclerView(hFragmentSavedContactsBinding.hSavedContactsRv)

        hFragmentSavedContactsBinding.hSavedContactsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = hViewContactsAdapter
        }


    }

    private fun hSubscribeObservers() {
        hContactsSharedViewModel.hDataState.observe(viewLifecycleOwner) { dataState ->
            dataState.hData?.let { contactsViewState ->
                contactsViewState.hContactsFields.hContactList?.let {

                }
            }

        }
        hContactsSharedViewModel.hContactsViewState.observe(viewLifecycleOwner) { contactsViewState ->
            contactsViewState.hContactsFields.hContactList?.let {
                Timber.d("Contact list $it")
                hViewContactsAdapter.hSetData(it)

            }

        }
    }


}