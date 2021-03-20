/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Interfaces;

import android.view.View;


public interface RecyclerInterface {
    void hOnClickListener(View hClickedView, int hClickedPosition);

    void hOnClickListener(View v, int position, String hText);

//    void hOnClickListener(View v, int position);

//    void hOnCardClickListener(View view, int hRecyclerNumber, int position, CalendarEventModel calendarEventModel);
}
