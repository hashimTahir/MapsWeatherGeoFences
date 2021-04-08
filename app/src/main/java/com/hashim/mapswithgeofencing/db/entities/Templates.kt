/*
 * Copyright (c) 2021/  4/ 8.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Templates(
        @PrimaryKey(autoGenerate = true) val hPk: Int?,
        @ColumnInfo(name = "message") val hMessage: String,
) {
    constructor(hMessage: String) : this(null, hMessage)
}