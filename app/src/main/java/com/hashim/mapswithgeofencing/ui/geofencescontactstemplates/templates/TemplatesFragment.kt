/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.FragmentTemplatesBinding
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates.TemplatesAdapter.AdapterType.DEFAULT
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates.TemplatesStateEvent.OnViewReady
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates.dialogs.AddMessageTemplateDialog
import com.hashim.mapswithgeofencing.utils.Constants


class TemplatesFragment : Fragment() {


    private lateinit var hFragmentTempBinding: FragmentTemplatesBinding
    private val hTemplatesViewModel: TemplatesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        hFragmentTempBinding = FragmentTemplatesBinding.inflate(
                layoutInflater,
                container,
                false
        )
        return hFragmentTempBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hSetupListeners()

        hInitAdapters()

        hSubscribeObservers()

        hTemplatesViewModel.hSetStateEvent(OnViewReady())
    }

    private fun hSubscribeObservers() {
//        hSetData(hDefaultTemplatesList)

    }

    private fun hInitAdapters() {
        val hDefaultTemplatesAdapter = TemplatesAdapter(requireContext(), DEFAULT)

        hFragmentTempBinding.hDefaultTemplateRv
                .apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = hDefaultTemplatesAdapter
                }
    }

    private fun hSetupListeners() {
        hFragmentTempBinding.hAddTextTemplate.setOnClickListener {
            val hAddMessageTemplateDialog = AddMessageTemplateDialog.newInstance(null)
            { message ->
                /*Todo: Add message to the list save custom template*/
            }
            hAddMessageTemplateDialog.show(childFragmentManager, Constants.H_BOTTOM_DIALOG)
        }
    }


}