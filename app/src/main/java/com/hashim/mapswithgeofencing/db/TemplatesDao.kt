/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.db

import androidx.room.*
import com.hashim.mapswithgeofencing.db.entities.Templates

@Dao
interface TemplatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun hInsertTemplate(templates: Templates): Long


    @Transaction
    @Query("SELECT * FROM Templates")
    suspend fun hGetTemplates(): List<Templates>



}