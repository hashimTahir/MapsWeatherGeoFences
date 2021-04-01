/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.local

import com.hashim.mapswithgeofencing.db.ContactsDao
import com.hashim.mapswithgeofencing.db.entities.Contact

class LocalRepoImpl(
        private val hContactsDao: ContactsDao
) : LocalRepo {
    override suspend fun hInsertContact(Contact: Contact): Long {
        return hContactsDao.hInsertContact(Contact)
    }

    override suspend fun hGetAllContacts(): List<Contact> {
        return hContactsDao.hGetAllContacts()
    }
}