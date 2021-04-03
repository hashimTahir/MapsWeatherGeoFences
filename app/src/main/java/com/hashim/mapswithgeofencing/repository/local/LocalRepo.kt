/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.local

import com.hashim.mapswithgeofencing.db.entities.Contact

interface LocalRepo {

    suspend fun hInsertContact(Contact: Contact): Long

    suspend fun hGetAllContacts(): List<Contact>


}