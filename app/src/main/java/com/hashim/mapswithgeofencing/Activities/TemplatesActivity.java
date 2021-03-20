/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hashim.mapswithgeofencing.Adapters.RecyclerAdapter;
import com.hashim.mapswithgeofencing.CustomView.HcustomDialog;
import com.hashim.mapswithgeofencing.Helper.ListUtils;
import com.hashim.mapswithgeofencing.Helper.LogToastSnackHelper;
import com.hashim.mapswithgeofencing.Interfaces.DialogResponseInterface;
import com.hashim.mapswithgeofencing.Interfaces.RecyclerInterface;
import com.hashim.mapswithgeofencing.Prefrences.SettingsPrefrences;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.databinding.ActivityTemplatesBinding;

import java.util.ArrayList;
import java.util.List;


public class TemplatesActivity extends AppCompatActivity implements DialogResponseInterface, RecyclerInterface {

    private String hTag = LogToastSnackHelper.hMakeTag(TemplatesActivity.class);


    private List<String> hDefaultTempList;
    private List<String> hCustomTempList = new ArrayList<>();
    private RecyclerAdapter hDefaultTempAdapter;
    private RecyclerAdapter hCustomTempAdapter;
    private SettingsPrefrences hSettingsPrefrences;
    private ActivityTemplatesBinding hActivityTemplatesBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hActivityTemplatesBinding = ActivityTemplatesBinding.inflate(getLayoutInflater());
        setContentView(hActivityTemplatesBinding.getRoot());
        hInit();
    }

    private void hInit() {
        hSettingsPrefrences = new SettingsPrefrences(this);
        hDefaultTempList = ListUtils.hConvertArrayToArrayList(getResources().getStringArray(R.array.default_templates_array));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        hDefaultTempAdapter = new RecyclerAdapter(this, hDefaultTempList, RecyclerAdapter.H_TEMPLATES_ADAPTER);
        hActivityTemplatesBinding.hDefaultTemplateRv.setLayoutManager(layoutManager);
        hActivityTemplatesBinding.hDefaultTemplateRv.setAdapter(hDefaultTempAdapter);

        hInitCustomAdapter();
    }

    private void hInitCustomAdapter() {

        hCustomTempList = hSettingsPrefrences.hGetCustomTemplates();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        if (hCustomTempList == null) {
            hCustomTempList = new ArrayList<>();
            hCustomTempAdapter = new RecyclerAdapter(this, hCustomTempList, RecyclerAdapter.H_TEMPLATES_ADAPTER);
        } else {
            hCustomTempAdapter = new RecyclerAdapter(this, hCustomTempList, RecyclerAdapter.H_TEMPLATES_ADAPTER);
            hCustomTempAdapter.notifyDataSetChanged();
        }
        hActivityTemplatesBinding.hCustomTemplateRv.setLayoutManager(layoutManager);
        hActivityTemplatesBinding.hCustomTemplateRv.setAdapter(hCustomTempAdapter);
    }

    public void hSetupListeners() {
        hActivityTemplatesBinding.hAddTextTemplate.setOnClickListener(v -> {
            HcustomDialog dialog = new HcustomDialog();
            dialog.show(getSupportFragmentManager(), "H_Dialog");
        });

    }

    @Override
    public void hSubmitText(String hText) {
    }

    @Override
    public void hSubmitNegativeResponse(DialogFragment hDialogFragment) {
        hDialogFragment.dismiss();
    }

    @Override
    public void hSubmitNeutralResponse(DialogFragment hDialogFragment) {
    }

    @Override
    public void hSubmitPositiveResponse(DialogFragment hDialogFragment, String string) {
        LogToastSnackHelper.hLogField(hTag, string);
        hCustomTempList.add(string);
        hCustomTempAdapter.notifyDataSetChanged();
        hSettingsPrefrences.hSaveCustomTemplate(string);
        hDialogFragment.dismiss();
    }

    @Override
    public void hSubmitCloseResponse(boolean b) {
    }

    @Override
    public void hOnClickListener(View hClickedView, int hClickedPosition) {
    }

    @Override
    public void hOnClickListener(View v, int position, String hText) {
        LogToastSnackHelper.hLogField(hTag, String.valueOf(position));
        hSettingsPrefrences.hSaveCustomTemplate(hText);
    }
}
