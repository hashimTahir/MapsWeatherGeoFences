/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.local

import com.hashim.mapswithgeofencing.db.entities.Contact
import com.hashim.mapswithgeofencing.db.entities.DbDao
import com.hashim.mapswithgeofencing.db.entities.GeoFence
import com.hashim.mapswithgeofencing.db.entities.Templates


class LocalRepoImpl(
        val hDbDao: DbDao
) : LocalRepo {
    override suspend fun hInsertContact(Contact: Contact): Long {
        return hDbDao.hInsertContact(Contact)
    }

    override suspend fun hGetAllContacts(): List<Contact> {
        return hDbDao.hGetAllContacts()
    }

    override suspend fun hInsertGeoFence(geoFence: GeoFence): Long {
        return hDbDao.hInsertGeoFence(geoFence)
    }

    override suspend fun hGetAllGeoFences(): List<GeoFence> {
        return hDbDao.hGetAllGeoFences()
    }

    override suspend fun hInsertTemplate(templates: Templates): Long {
        return hDbDao.hInsertTemplate(templates)
    }

    override suspend fun hGetTemplates(): List<Templates> {
        return hDbDao.hGetTemplates()
    }
}