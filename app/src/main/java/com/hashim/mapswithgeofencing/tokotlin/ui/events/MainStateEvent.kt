/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.ui.events

import android.location.Location
import com.hashim.mapswithgeofencing.tokotlin.ui.main.Category

sealed class MainStateEvent {

    class OnCurrentLocationFound(val location: Location?) : MainStateEvent()

    class OnCategorySelected(val category: Category) : MainStateEvent()

    class OnMapReady : MainStateEvent()

    class None : MainStateEvent()

}