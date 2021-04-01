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
        TODO("Not yet implemented")
    }

    override suspend fun hGetAllContacts(Contact: Contact): List<Contact> {
        TODO("Not yet implemented")
    }
}