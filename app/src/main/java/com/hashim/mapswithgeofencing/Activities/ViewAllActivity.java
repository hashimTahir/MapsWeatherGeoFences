package com.hashim.mapswithgeofencing.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.hashim.mapswithgeofencing.Adapters.RecyclerAdapter;
import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.ListUtils;
import com.hashim.mapswithgeofencing.Helper.ToolBarHelper;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.Interfaces.RecyclerInterface;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.databinding.ActivityViewAllBinding;

import java.util.List;

public class ViewAllActivity extends AppCompatActivity implements RecyclerInterface {


    private ActivityViewAllBinding hActivityViewAllBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hActivityViewAllBinding = ActivityViewAllBinding.inflate(getLayoutInflater());
        setContentView(hActivityViewAllBinding.getRoot());
        UIHelper.hOreoOrientationCheck(this);

        ToolBarHelper hToolBarHelper = new ToolBarHelper(this);
        hToolBarHelper.hSetToolbar(hActivityViewAllBinding.hExAppBar.toolbar);
        hToolBarHelper.hSetToolbarTitle(hActivityViewAllBinding.hExAppBar.toolbarTitle,
                getString(R.string.explore_nearby_places));

        List<String> hPlacesString = ListUtils.hConvertArrayToArrayList(getResources().getStringArray(R.array.place_strings));

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        RecyclerAdapter adapter = new RecyclerAdapter(this, hPlacesString, RecyclerAdapter.H_VIEW_ALL_ADAPTER);
        hActivityViewAllBinding.recyclerView.setLayoutManager(layoutManager);
        hActivityViewAllBinding.recyclerView.setAdapter(adapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        UIHelper.hOreoOrientationCheck(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hOnClickListener(View hClickedView, int hClickedPosition) {
        Intent hIntent = new Intent();
        hIntent.putExtra(Constants.H_LOCATION_TAG, hClickedPosition);
        setResult(RESULT_OK, hIntent);
        finish();
    }

    @Override
    public void hOnClickListener(View v, int position, String hText) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
