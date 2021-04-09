/*
 * Copyright (c) 2021/  4/ 10.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.db.entities

import androidx.room.*

@Dao
interface DbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun hInsertContact(Contact: Contact): Long


    @Transaction
    @Query("SELECT * FROM Contact")
    suspend fun hGetAllContacts(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun hInsertTemplate(templates: Templates): Long


    @Transaction
    @Query("SELECT * FROM Templates")
    suspend fun hGetTemplates(): List<Templates>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun hInsertGeoFence(geoFence: GeoFence): Long


    @Transaction
    @Query("SELECT * FROM GeoFence")
    suspend fun hGetAllGeoFences(): List<GeoFence>


}