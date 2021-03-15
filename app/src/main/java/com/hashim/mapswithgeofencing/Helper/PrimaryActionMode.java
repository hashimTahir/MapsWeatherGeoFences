package com.hashim.mapswithgeofencing.Helper;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.hashim.mapswithgeofencing.Contacts.ContactsActivity;
import com.hashim.mapswithgeofencing.Interfaces.onActionModeListener;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.TrackMe.TrackMeActivity;


public class PrimaryActionMode implements ActionMode.Callback {


    private onActionModeListener hOnActionModeListener;

    private ActionMode hActionMode;
    private int hMenuId = 0;
    private String hTtitle = null;
    private String hSubtitle;
    Context hContext;
    int hWhatToShow;

    public PrimaryActionMode(Context context, onActionModeListener
            onActionModeListener, int menuResId, String title, String subtitle, int whatToShow) {
        this.hContext = context;
        this.hMenuId = menuResId;
        this.hTtitle = title;
        this.hSubtitle = subtitle;
        this.hWhatToShow = whatToShow;
        this.hOnActionModeListener = onActionModeListener;

    }

    public void hStartActionMode() {
        AppCompatActivity hAppCompatActivity = (AppCompatActivity) hContext;
        hAppCompatActivity.startSupportActionMode(this);
    }

    public void finishActionMode() {
        hActionMode.finish();
    }


    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        this.hActionMode = actionMode;
        hActionMode.getMenuInflater().inflate(hMenuId, menu);
        hActionMode.setTitle(hTtitle);
        hActionMode.setSubtitle(hSubtitle);
        switch (hWhatToShow) {
            case ContactsActivity.H_SAVED_LIST:
                menu.removeItem(menu.getItem(1).getItemId());
                break;
            case TrackMeActivity.H_T_SAVED_LIST:
                menu.removeItem(menu.getItem(1).getItemId());
                menu.add(Menu.NONE, TrackMeActivity.MENU_ITEM_MESSAGE, Menu.NONE, "Send Message").setIcon(R.drawable.ic_message);
                break;
        }
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        hOnActionModeListener.onActionItemClicked(actionMode, menuItem);
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        hOnActionModeListener.onDestroyActionMode(actionMode);
        hActionMode = null;
    }


    public void setTitle(String s) {
        this.hTtitle = s;
        hActionMode.setTitle(hTtitle);
    }
}
