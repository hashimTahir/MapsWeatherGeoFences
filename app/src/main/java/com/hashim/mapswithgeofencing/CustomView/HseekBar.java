/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.util.AttributeSet;

public class HseekBar extends AppCompatSeekBar {

    public HseekBar(Context context) {
        super(context);
    }

    public HseekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HseekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        int thumb_x = (int) (((double) this.getProgress() / this.getMax()) * (double) this.getWidth());
        float middle = (float) (this.getHeight());

        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        c.drawText("" + this.getProgress(), thumb_x, middle, paint);
    }
}

