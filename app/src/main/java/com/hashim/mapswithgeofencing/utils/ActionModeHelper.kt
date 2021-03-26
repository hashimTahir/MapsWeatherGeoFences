/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.tobeDeleted.Interfaces.onActionModeListener


class ActionModeHelper(var hContext: Context, onActionModeListener: onActionModeListener, menuResId: Int, title: String?, subtitle: String, whatToShow: Int) : ActionMode.Callback {
    private val hOnActionModeListener: onActionModeListener
    private var hActionMode: ActionMode? = null
    private var hMenuId = 0
    private var hTtitle: String? = null
    private val hSubtitle: String
    var hWhatToShow: Int
    fun hStartActionMode() {
        val hAppCompatActivity = hContext as AppCompatActivity
        hAppCompatActivity.startSupportActionMode(this)
    }

    fun finishActionMode() {
        hActionMode!!.finish()
    }

    override fun onCreateActionMode(actionMode: ActionMode, menu: Menu): Boolean {
        hActionMode = actionMode
        hActionMode!!.menuInflater.inflate(hMenuId, menu)
        hActionMode!!.title = hTtitle
        hActionMode!!.subtitle = hSubtitle
        when (hWhatToShow) {
            Constants.H_SAVED_LIST -> menu.removeItem(menu.getItem(1).itemId)
            Constants.H_T_SAVED_LIST -> {
                menu.removeItem(menu.getItem(1).itemId)
                menu.add(Menu.NONE, Constants.MENU_ITEM_MESSAGE, Menu.NONE, "Send Message").setIcon(R.drawable.ic_message)
            }
        }
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode, menu: Menu): Boolean {
        return false
    }

    override fun onActionItemClicked(actionMode: ActionMode, menuItem: MenuItem): Boolean {
        hOnActionModeListener.onActionItemClicked(actionMode, menuItem)
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode) {
        hOnActionModeListener.onDestroyActionMode(actionMode)
        hActionMode = null
    }

    fun setTitle(s: String?) {
        hTtitle = s
        hActionMode!!.title = hTtitle
    }

    init {
        hMenuId = menuResId
        hTtitle = title
        hSubtitle = subtitle
        hWhatToShow = whatToShow
        hOnActionModeListener = onActionModeListener
    }
}