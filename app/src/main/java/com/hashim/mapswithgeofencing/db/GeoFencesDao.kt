/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.db

import androidx.room.*
import com.hashim.mapswithgeofencing.db.entities.GeoFence

@Dao
interface GeoFencesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun hInsertGeoFence(geoFence: GeoFence): Long


    @Transaction
    @Query("SELECT * FROM GeoFence")
    suspend fun hGetAllGeoFences(): List<GeoFence>


}