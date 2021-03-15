package com.hashim.mapswithgeofencing.Activities;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hashim.mapswithgeofencing.Adapters.RecyclerAdapter;
import com.hashim.mapswithgeofencing.CustomView.HcustomDialog;
import com.hashim.mapswithgeofencing.Helper.ListUtils;
import com.hashim.mapswithgeofencing.Helper.LogToastSnackHelper;
import com.hashim.mapswithgeofencing.Interfaces.DialogResponseInterface;
import com.hashim.mapswithgeofencing.Interfaces.RecyclerInterface;
import com.hashim.mapswithgeofencing.Prefrences.SettingsPrefrences;
import com.hashim.mapswithgeofencing.R;

import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TemplatesActivity extends AppCompatActivity implements DialogResponseInterface, RecyclerInterface {

    private String hTag = LogToastSnackHelper.hMakeTag(TemplatesActivity.class);

    @BindView(R.id.hAtAppbar)
    Toolbar toolbar;

    @BindView(R.id.hAddTextTemplate)
    FloatingActionButton hAddTextTemplate;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.hDefaultTemplateRv)
    RecyclerView hDefaultTemplateRv;

    @BindView(R.id.hCustomTemplateRv)
    RecyclerView hCustomTemplateRv;

    private List<String> hDefaultTempList;
    private List<String> hCustomTempList = new ArrayList<>();
    private RecyclerAdapter hDefaultTempAdapter;
    private RecyclerAdapter hCustomTempAdapter;
    private SettingsPrefrences hSettingsPrefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);
        ButterKnife.bind(this);
        hInit();
    }

    private void hInit() {
        hSettingsPrefrences = new SettingsPrefrences(this);
        hDefaultTempList = ListUtils.hConvertArrayToArrayList(getResources().getStringArray(R.array.default_templates_array));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        hDefaultTempAdapter = new RecyclerAdapter(this, hDefaultTempList, RecyclerAdapter.H_TEMPLATES_ADAPTER);
        hDefaultTemplateRv.setLayoutManager(layoutManager);
        hDefaultTemplateRv.setAdapter(hDefaultTempAdapter);

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
        hCustomTemplateRv.setLayoutManager(layoutManager);
        hCustomTemplateRv.setAdapter(hCustomTempAdapter);
    }

    @OnClick(R.id.hAddTextTemplate)
    public void onViewClicked() {
        HcustomDialog dialog = new HcustomDialog();
        dialog.show(getSupportFragmentManager(), "H_Dialog");
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
