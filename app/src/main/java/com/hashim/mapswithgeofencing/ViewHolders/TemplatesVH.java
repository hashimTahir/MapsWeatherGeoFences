package com.hashim.mapswithgeofencing.ViewHolders;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.hashim.mapswithgeofencing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemplatesVH extends RecyclerView.ViewHolder {


    @BindView(R.id.irt_TV)
    public TextView hTextV;

    @BindView(R.id.irt_IV)
    public ImageView hImageV;

    @BindView(R.id.hTemplateCl)
    public ConstraintLayout hTempCL;


    public TemplatesVH(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}
