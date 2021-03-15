package com.hashim.mapswithgeofencing.Helper;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class ToolBarHelper {
    private WeakReference<AppCompatActivity> hContextWeakReference;

    public ToolBarHelper(Context context) {
        this.hContextWeakReference = new WeakReference<>((AppCompatActivity) context);
    }

    public void hSetToolbar(Toolbar hToolbar) {
        hToolbar.setSelected(true);
        hContextWeakReference.get().setSupportActionBar(hToolbar);
//        setSupportActionBar(hToolbar);

        if (hContextWeakReference.get().getSupportActionBar() != null) {
            hContextWeakReference.get().getSupportActionBar().setDisplayShowTitleEnabled(false);
            hContextWeakReference.get().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void hSetToolbarTitle(TextView hToolbarTitle, String title) {
        UIHelper.hSetTextToTextView(hToolbarTitle, title);
    }
}
