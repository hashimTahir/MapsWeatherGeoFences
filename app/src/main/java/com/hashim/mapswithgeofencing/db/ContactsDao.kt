/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.db

import androidx.room.*
import com.hashim.mapswithgeofencing.db.entities.Contact

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun hInsertContact(Contact: Contact): Long


    @Transaction
    @Query("SELECT * FROM Contact WHERE hTemp = :contact")
    suspend fun hGetAllContacts(contact: Contact): List<Contact>


}