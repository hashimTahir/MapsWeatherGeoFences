/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.local

import com.hashim.mapswithgeofencing.db.entities.Contact
import com.hashim.mapswithgeofencing.db.entities.GeoFence
import com.hashim.mapswithgeofencing.db.entities.Templates

interface LocalRepo {

    suspend fun hInsertContact(Contact: Contact): Long

    suspend fun hGetAllContacts(): List<Contact>

    suspend fun hInsertGeoFence(geoFence: GeoFence): Long

    suspend fun hGetAllGeoFences(): List<GeoFence>

    suspend fun hInsertTemplate(templates: Templates): Long

    suspend fun hGetTemplates(): List<Templates>

}