/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.Interfaces;

import androidx.appcompat.view.ActionMode;
import android.view.MenuItem;

public interface onActionModeListener {
    void onActionItemClicked(ActionMode actionMode, MenuItem menuItem);

    void onDestroyActionMode(ActionMode actionMode);
}