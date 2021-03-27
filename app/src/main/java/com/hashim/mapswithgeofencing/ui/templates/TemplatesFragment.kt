/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.templates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.SettingsPrefrences
import com.hashim.mapswithgeofencing.databinding.FragmentTemplatesBinding
import com.hashim.mapswithgeofencing.ui.dialogs.AddMessageTemplateDialog
import com.hashim.mapswithgeofencing.ui.templates.TemplatesAdapter.AdapterType.CUSTOM
import com.hashim.mapswithgeofencing.ui.templates.TemplatesAdapter.AdapterType.DEFAULT
import com.hashim.mapswithgeofencing.utils.Constants


class TemplatesFragment : Fragment() {


    lateinit var hFragmentTempBinding: FragmentTemplatesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        hFragmentTempBinding = FragmentTemplatesBinding.inflate(
                layoutInflater,
                container,
                false
        )
        return hFragmentTempBinding.root
    }

    private fun hInitAdapters() {
        val hSettingsPrefrences = SettingsPrefrences(requireContext())

        val hDefaultTemplatesList = resources.getStringArray(R.array.default_templates_array).toMutableList()
        val hCustomTemplatesList = hSettingsPrefrences.hGetCustomTemplates()

        val hDefaultTemplatesAdapter = TemplatesAdapter(requireContext(), DEFAULT)
        val hCustomTemplatesAdapter = TemplatesAdapter(requireContext(), CUSTOM)

        hDefaultTemplatesAdapter.hSetData(hDefaultTemplatesList)
        hCustomTemplatesAdapter.hSetData(hCustomTemplatesList)

        hCustomTemplatesList?.let {
            hFragmentTempBinding.hCustomTemplateRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = hCustomTemplatesAdapter
            }
        }


        hFragmentTempBinding.hDefaultTemplateRv.apply {
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