package com.hashim.mapswithgeofencing.EmergencyContacts;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.ToolBarHelper;
import com.hashim.mapswithgeofencing.Interfaces.OnFragmentInteractionListener;
import com.hashim.mapswithgeofencing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmergencyContactsActivity extends AppCompatActivity implements OnFragmentInteractionListener {


    @BindView(R.id.hATmAppBar)
    Toolbar hToolbar;

    @BindView(R.id.hFragmentContainer)
    FrameLayout hFragmentContainer;

    private FragmentManager hFragmentManager;
    @BindView(R.id.toolbar_title)
    TextView hToolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_me_settings);
        ButterKnife.bind(this);

        ToolBarHelper hToolBarHelper = new ToolBarHelper(this);
        hToolBarHelper.hSetToolbar(hToolbar);
        hToolBarHelper.hSetToolbarTitle(hToolbarTitle, getString(R.string.add_remove_locations));

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

    }
}
