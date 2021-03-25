/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.EmergencyContacts;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.hashim.mapswithgeofencing.tobeDeleted.Interfaces.OnFragmentInteractionListener;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.databinding.ActivityTrackMeSettingsBinding;


public class EmergencyContactsActivity extends AppCompatActivity {
    /*implements
} OnFragmentInteractionListener {

    private ActivityTrackMeSettingsBinding hActivityTrackMeSettingsBinding;
    private FragmentManager hFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hActivityTrackMeSettingsBinding = ActivityTrackMeSettingsBinding.inflate(getLayoutInflater());
        setContentView(hActivityTrackMeSettingsBinding.getRoot());

        ToolBarHelper hToolBarHelper = new ToolBarHelper(this);
        hToolBarHelper.hSetToolbar(hActivityTrackMeSettingsBinding.hATmAppBar.toolbar);
        hToolBarHelper.hSetToolbarTitle(hActivityTrackMeSettingsBinding.hATmAppBar.toolbarTitle, getString(R.string.add_remove_locations));

        hFragmentManager = getSupportFragmentManager();

        hLoadFragment(0, Constants.H_MAIN_TRACK_ME_FRAGMENT);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (hFragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {

            super.onBackPressed();
        }
    }

    private void hLoadFragment(int hClickedPosition, String fragmentType) {
        switch (fragmentType) {
            case Constants.H_MAIN_TRACK_ME_FRAGMENT:
                MainTrackMeFragment hMainTrackMeFragment = MainTrackMeFragment.newInstance(null, null);
                hFragmentManager.beginTransaction().
                        add(R.id.hFragmentContainer, hMainTrackMeFragment, Constants.H_MAIN_TRACK_ME_FRAGMENT).
                        addToBackStack(Constants.H_MAIN_TRACK_ME_FRAGMENT).
                        commit();
                break;
            case Constants.H_LOCATION_FRAGMENT:
                AddLocationFragment hAddLocationFragment = AddLocationFragment.newInstance(String.valueOf(hClickedPosition));
                hFragmentManager.beginTransaction().
                        replace(R.id.hFragmentContainer, hAddLocationFragment, Constants.H_LOCATION_FRAGMENT).
                        addToBackStack(Constants.H_LOCATION_FRAGMENT).
                        commit();
                break;
            case Constants.H_CONTACTS_FRAGMENT:
                ContactsFragment hContactsFragment = ContactsFragment.newInstance(null, null);
                hFragmentManager.beginTransaction().
                        add(R.id.hFragmentContainer, hContactsFragment, Constants.H_CONTACTS_FRAGMENT).
                        addToBackStack(Constants.H_CONTACTS_FRAGMENT).
                        commit();
                break;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.fragmenta_menu, menu);
//        return true;
//    }

    @Override
    public void onFragmentInteraction(String string) {
        switch (string) {
            case Constants.H_LOCATION_FRAGMENT:
                hLoadFragment(Constants.H_ADD_NEW_LOCATION, string);

                break;


        }
    }

    @Override
    public void onFragmentInteraction(int hClickedPosition, String hLocationFragment) {
        hLoadFragment(hClickedPosition, hLocationFragment);

    }*/
}
