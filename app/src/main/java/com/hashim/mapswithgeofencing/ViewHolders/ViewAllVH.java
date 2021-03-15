package com.hashim.mapswithgeofencing.ViewHolders;

import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hashim.mapswithgeofencing.R;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewAllVH extends RecyclerView.ViewHolder {
    @BindView(R.id.buttonLayou)

    public LinearLayout hLinearLayout;

    @BindView(R.id.FloatingActionButton)
    public FloatingActionButton hFloatingActionButton;

    @BindView(R.id.ButtonTexts)

    public AppCompatTextView hAppCompatTextView;


    public ViewAllVH(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}
