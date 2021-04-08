/*
 * Copyright (c) 2021/  4/ 8.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates

import android.content.Context
import androidx.lifecycle.*
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.db.entities.Templates
import com.hashim.mapswithgeofencing.repository.local.LocalRepo
import com.hashim.mapswithgeofencing.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplatesViewModel @Inject constructor(
        @ApplicationContext private val hContext: Context,
        private val hLocalRepo: LocalRepo,
) : ViewModel() {
    private val _hTemplatesViewState = MutableLiveData<TemplatesViewState>()
    private val _hTemplatesStateEvent = MutableLiveData<TemplatesStateEvent>()

    val hTemplatesViewState: LiveData<TemplatesViewState>
        get() = _hTemplatesViewState

    val hData: LiveData<DataState<TemplatesViewState>> = Transformations
            .switchMap(_hTemplatesStateEvent) {
                hSetStateEvent(it)
            }


    fun hSetStateEvent(templatesStateEvent: TemplatesStateEvent): LiveData<DataState<TemplatesViewState>>? {
        when (templatesStateEvent) {
            is TemplatesStateEvent.OnViewReady -> {
                hGetDefaultnCustomTemplates()
            }
        }
        return null
    }

    private fun hGetDefaultnCustomTemplates() {
        val hDefaultTemplatesList = hContext.resources
                .getStringArray(R.array.default_templates_array).toList()

        viewModelScope.launch {
            val hCustomTemplatesList = hLocalRepo.hGetTemplates()


            val hData = TemplatesViewState.TemplatesFields(
                    hSetStartDataVS = TemplatesViewState.SetStartDataVS(
                            hDefaultTempList = hDefaultTemplatesList,
                            hCustomTempList = hCustomTemplatesList
                    )
            )
        }

    }

    private fun hSaveTemplate(template: Templates) {
        viewModelScope.launch {
            hLocalRepo.hInsertTemplate(template)
        }
    }

}