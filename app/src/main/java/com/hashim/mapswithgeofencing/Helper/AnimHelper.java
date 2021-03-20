/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Helper;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hashim.mapswithgeofencing.R;


public class AnimHelper {
    public static void hAnimateBottomUp(Context hContext, View view) {

        Animation bottomUp = AnimationUtils.loadAnimation(hContext,
                R.anim.bottom_up);
        view.startAnimation(bottomUp);
        view.setVisibility(View.VISIBLE);

    }

    public static void hAnimateBottomDown(Context context, View view) {
        Animation hBottomDown = AnimationUtils.loadAnimation(context,
                R.anim.bottom_down);
        view.startAnimation(hBottomDown);
        view.setVisibility(View.INVISIBLE);
    }
}
