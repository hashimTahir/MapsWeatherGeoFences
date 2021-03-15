package com.hashim.mapswithgeofencing.ViewHolders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.hashim.mapswithgeofencing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsVH extends RecyclerView.ViewHolder {
    @BindView(R.id.title)
    public TextView title;

    @BindView(R.id.numberTextView)
    public TextView hNumberTv;

    @BindView(R.id.root_view)
    public FrameLayout rootView;


    public ContactsVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}