package com.hashim.mapswithgeofencing.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.hashim.mapswithgeofencing.Adapters.RecyclerAdapter;
import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.ListUtils;
import com.hashim.mapswithgeofencing.Helper.ToolBarHelper;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.Interfaces.RecyclerInterface;
import com.hashim.mapswithgeofencing.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewAllActivity extends AppCompatActivity implements RecyclerInterface {

    @BindView(R.id.recycler_view)
    RecyclerView hRecyclerView;
    @BindView(R.id.toolbar_title)
    TextView hToolbarTitle;
    @BindView(R.id.hExAppBar)
    Toolbar hToolbar;


    @BindView(R.id.adView)
    AdView mAdView;
    private HAdmob hAdmob;

    private H_InterstetialAdd h_interstetialAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        ButterKnife.bind(this);
        UIHelper.hOreoOrientationCheck(this);

        ToolBarHelper hToolBarHelper = new ToolBarHelper(this);
        hToolBarHelper.hSetToolbar(hToolbar);
        hToolBarHelper.hSetToolbarTitle(hToolbarTitle,
                getString(R.string.explore_nearby_places));

        h_interstetialAdd = new H_InterstetialAdd(this);
        List<String> hPlacesString = ListUtils.hConvertArrayToArrayList(getResources().getStringArray(R.array.place_strings));

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        RecyclerAdapter adapter = new RecyclerAdapter(this, hPlacesString, RecyclerAdapter.H_VIEW_ALL_ADAPTER);
        hRecyclerView.setLayoutManager(layoutManager);
        hRecyclerView.setAdapter(adapter);

        hAdmob = new HAdmob(this, mAdView);
        hAdmob.hLoadAd();

    }


    @Override
    protected void onResume() {
        super.onResume();
        UIHelper.hOreoOrientationCheck(this);
        h_interstetialAdd.hLoadAd();
        hAdmob.hResumeAdd();

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
        h_interstetialAdd.hShowInterstitial();
    }
}
