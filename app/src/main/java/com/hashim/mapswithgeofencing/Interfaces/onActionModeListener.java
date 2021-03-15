package com.hashim.mapswithgeofencing.Interfaces;

import androidx.appcompat.view.ActionMode;
import android.view.MenuItem;

public interface onActionModeListener {
    void onActionItemClicked(ActionMode actionMode, MenuItem menuItem);

    void onDestroyActionMode(ActionMode actionMode);
}