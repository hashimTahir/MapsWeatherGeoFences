package com.hashim.mapswithgeofencing.ViewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.hashim.mapswithgeofencing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackMeVH extends RecyclerView.ViewHolder {
    @BindView(R.id.hTitleTV)
    public TextView hTitleTv;

    @BindView(R.id.hDetailTV)
    public TextView hDetailTv;

    @BindView(R.id.hEditTv)
    public TextView hEditTv;


    public TrackMeVH(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
