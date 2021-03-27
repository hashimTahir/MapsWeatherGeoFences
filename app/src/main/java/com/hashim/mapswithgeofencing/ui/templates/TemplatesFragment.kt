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
import com.hashim.mapswithgeofencing.ui.templates.TemplatesAdapter.AdapterType.CUSTOM
import com.hashim.mapswithgeofencing.ui.templates.TemplatesAdapter.AdapterType.DEFAULT


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


//    private List<String> hDefaultTempList;
//    private List<String> hCustomTempList = new ArrayList<>();
//    private RecyclerAdapter hDefaultTempAdapter;
//    private RecyclerAdapter hCustomTempAdapter;
//    private SettingsPrefrences hSettingsPrefrences;
//    private ActivityTemplatesBinding hActivityTemplatesBinding;


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

        }
    }

//
//    public void hSetupListeners() {
//        hActivityTemplatesBinding.hAddTextTemplate.setOnClickListener(v -> {
//            HcustomDialog dialog = new HcustomDialog();
//            dialog.show(getSupportFragmentManager(), "H_Dialog");
//        });
//
//    }
//
//    @Override
//    public void hSubmitText(String hText) {
//    }
//
//    @Override
//    public void hSubmitNegativeResponse(DialogFragment hDialogFragment) {
//        hDialogFragment.dismiss();
//    }
//
//    @Override
//    public void hSubmitNeutralResponse(DialogFragment hDialogFragment) {
//    }
//
//    @Override
//    public void hSubmitPositiveResponse(DialogFragment hDialogFragment, String string) {
//        hCustomTempList.add(string);
//        hCustomTempAdapter.notifyDataSetChanged();
//        hSettingsPrefrences.hSaveCustomTemplate(string);
//        hDialogFragment.dismiss();
//    }
//
//    @Override
//    public void hSubmitCloseResponse(boolean b) {
//    }
//
//    @Override
//    public void hOnClickListener(View hClickedView, int hClickedPosition) {
//    }
//
//    @Override
//    public void hOnClickListener(View v, int position, String hText) {
//        hSettingsPrefrences.hSaveCustomTemplate(hText);
//    }
//}

}