/*
 * Copyright (c) 2021/  4/ 8.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates

import com.hashim.mapswithgeofencing.db.entities.Templates

data class TemplatesViewState(
        val hTemplatesFields: TemplatesFields = TemplatesFields()

) {
    data class TemplatesFields(
            val hSetStartDataVS: SetStartDataVS? = null
    )

    data class SetStartDataVS(
            val hDefaultTempList: List<String>? = null,
            val hCustomTempList: List<Templates>? = null
    )

}