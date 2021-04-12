/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

import android.location.Location
import com.google.android.gms.maps.model.Marker
import com.hashim.mapswithgeofencing.ui.main.fragments.adapter.Category

sealed class MainStateEvent {

    class OnCurrentLocationFound(val location: Location?) : MainStateEvent()

    class OnCategorySelected(val category: Category) : MainStateEvent()

    class OnFindAutoCompleteSuggestions(val suggestion: String) : MainStateEvent()

    class OnMapReady : MainStateEvent()

    class OnMarkerClicked(val marker: Marker) : MainStateEvent()

    class None : MainStateEvent()

}