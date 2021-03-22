/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.ui.events

import android.location.Location

sealed class MainStateEvent {

    class OnCurrentLocationFound(val location: Location?) : MainStateEvent() {

    }

    class OnMapReady : MainStateEvent()

    class None : MainStateEvent()

}