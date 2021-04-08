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
import com.hashim.mapswithgeofencing.databinding.FragmentTemplatesBinding
import com.hashim.mapswithgeofencing.db.entities.Templates
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates.TemplatesAdapter.AdapterType.CUSTOM
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates.TemplatesAdapter.AdapterType.DEFAULT
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates.TemplatesStateEvent.OnSaveTemplate
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates.TemplatesStateEvent.OnViewReady
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates.dialogs.AddMessageTemplateDialog
import com.hashim.mapswithgeofencing.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class TemplatesFragment : Fragment() {


    private lateinit var hFragmentTempBinding: FragmentTemplatesBinding
    private val hTemplatesViewModel: TemplatesViewModel by viewModels()
    private lateinit var hDefaultTemplatesAdapter: TemplatesAdapter
    private lateinit var hCustomTemplatesAdapter: TemplatesAdapter

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
        hTemplatesViewModel.hData.observe(viewLifecycleOwner) { dataState ->
            dataState.hData?.let { templatesViewState ->
                templatesViewState.hTemplatesFields.hSetStartDataVS?.let {

                }
            }
        }
        hTemplatesViewModel.hTemplatesViewState.observe(viewLifecycleOwner) { templatesViewState ->
            templatesViewState.hTemplatesFields.hSetStartDataVS?.let {
                hDefaultTemplatesAdapter.hSetData(it.hDefaultTempList)
                hCustomTemplatesAdapter.hSetData(it.hCustomTempList)
            }
        }
    }


    private fun hInitAdapters() {
        hDefaultTemplatesAdapter = TemplatesAdapter(requireContext(), DEFAULT) { defaultTemplate ->
            defaultTemplate?.let {
                val text = it as String
                Timber.d("defaultTemplate  $text")
            }

        }
        hFragmentTempBinding.hDefaultTemplateRv
                .apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = hDefaultTemplatesAdapter
                }

        hCustomTemplatesAdapter = TemplatesAdapter(requireContext(), CUSTOM) { customTemplate ->
            customTemplate?.let {
                val text = it as Templates
                Timber.d("Custom Template $text")
            }
        }
        hFragmentTempBinding.hCustomTemplateRv
                .apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = hCustomTemplatesAdapter
                }
    }

    private fun hSetupListeners() {
        hFragmentTempBinding.hAddTextTemplate.setOnClickListener {
            val hAddMessageTemplateDialog = AddMessageTemplateDialog.newInstance(null)
            { message ->
                hTemplatesViewModel.hSetStateEvent(OnSaveTemplate(message))
            }
            hAddMessageTemplateDialog.show(childFragmentManager, Constants.H_BOTTOM_DIALOG)
        }
    }


}