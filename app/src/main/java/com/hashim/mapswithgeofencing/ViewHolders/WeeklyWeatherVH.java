package com.hashim.mapswithgeofencing.ViewHolders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.hashim.mapswithgeofencing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeeklyWeatherVH extends RecyclerView.ViewHolder {


    @BindView(R.id.hIrw1DayTv)
    public TextView hDayTv;

    @BindView(R.id.hIrw1TimeTv)
    public TextView hTimeTv;

    @BindView(R.id.hIrw1ImageV)
    public ImageView hImageTv;

    @BindView(R.id.hIrw1MaxTempTv)
    public TextView hMaxTempTv;

    @BindView(R.id.hIrw1DescriptionTv)
    public TextView hDescriptionTv;

    @BindView(R.id.hIrw1MinTempTv)
    public TextView hMinTempTv;

    public WeeklyWeatherVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}