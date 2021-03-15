package com.hashim.mapswithgeofencing.ViewHolders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.hashim.mapswithgeofencing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodaysWeatherVH extends RecyclerView.ViewHolder {

    @BindView(R.id.hIrwTimeTv)
    public TextView hIrwTimeTv;

    @BindView(R.id.hIrwImageV)
    public ImageView hIrwIv;

    @BindView(R.id.hIrwTempTv)
    public TextView hIrwTemp;


    @BindView(R.id.hIrwDescriptionTv)
    public TextView hDescriptionTv;


    public TodaysWeatherVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}