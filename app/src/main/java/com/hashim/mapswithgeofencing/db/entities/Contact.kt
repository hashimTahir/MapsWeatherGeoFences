/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
        @PrimaryKey(autoGenerate = false)
        val hTemp: String
)