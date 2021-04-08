/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.local

import com.hashim.mapswithgeofencing.db.ContactsDao
import com.hashim.mapswithgeofencing.db.GeoFencesDao
import com.hashim.mapswithgeofencing.db.TemplatesDao
import com.hashim.mapswithgeofencing.db.entities.Contact
import com.hashim.mapswithgeofencing.db.entities.GeoFence
import com.hashim.mapswithgeofencing.db.entities.Templates

class LocalRepoImpl(
        private val hContactsDao: ContactsDao,
        private val hGeoFenceDao: GeoFencesDao,
        private val hTemplatesDao: TemplatesDao,
) : LocalRepo {
    override suspend fun hInsertContact(Contact: Contact): Long {
        return hContactsDao.hInsertContact(Contact)
    }

    override suspend fun hGetAllContacts(): List<Contact> {
        return hContactsDao.hGetAllContacts()
    }

    override suspend fun hInsertGeoFence(geoFence: GeoFence): Long {
        return hGeoFenceDao.hInsertGeoFence(geoFence)
    }

    override suspend fun hGetAllGeoFences(): List<GeoFence> {
        return hGeoFenceDao.hGetAllGeoFences()
    }

    override suspend fun hInsertTemplate(templates: Templates): Long {
        return hTemplatesDao.hInsertTemplate(templates)
    }

    override suspend fun hGetTemplates(): List<Templates> {
        return hTemplatesDao.hGetTemplates()
    }
}