/*
 * Copyright (c) 2021/  3/ 28.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.calculateroute

import androidx.lifecycle.*
import com.hashim.mapswithgeofencing.Domain.model.Directions
import com.hashim.mapswithgeofencing.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteStateEvent
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteStateEvent.None
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteStateEvent.OnFindDirections
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteViewState
import com.hashim.mapswithgeofencing.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculateRouteViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo,
        private val hSettingsPrefrences: SettingsPrefrences
) : ViewModel() {

    private val _CalculateRouteStateEvent = MutableLiveData<CalculateRouteStateEvent>()

    private val _hCalculateRouteViewState = MutableLiveData<CalculateRouteViewState>()

    val hCalculateRouteViewState: LiveData<CalculateRouteViewState>
        get() = _hCalculateRouteViewState


    val hDataState: LiveData<DataState<CalculateRouteViewState>> = Transformations
            .switchMap(_CalculateRouteStateEvent) {
                it?.let { calculateRouteViewState ->
                    hHandleStateEvent(calculateRouteViewState)
                }
            }


    private fun hHandleStateEvent(stateEvent: CalculateRouteStateEvent): LiveData<DataState<CalculateRouteViewState>>? {
        when (stateEvent) {
            is OnFindDirections -> {
                viewModelScope.launch {
                    val hDirections = hRemoteRepo.hGetDirections(
                            startLocation = stateEvent.hStartLocation,
                            endLocation = stateEvent.hDestinationLocation,
                            mode = stateEvent.hMode.toString()
                    )
                    hDrawPath(hDirections)
                }

            }
            is None -> {
            }
            else -> {
            }
        }
        return null
    }

    private fun hDrawPath(hDirections: Directions) {

    }


    fun hSetStateEvent(calculateRouteStateEvent: CalculateRouteStateEvent) {
        _CalculateRouteStateEvent.value = calculateRouteStateEvent
    }

    private fun hGetCurrentViewStateOrNew(): CalculateRouteViewState {
        return hCalculateRouteViewState.value ?: CalculateRouteViewState()
    }

}