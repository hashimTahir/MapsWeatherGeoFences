/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.db

import androidx.room.*
import com.hashim.mapswithgeofencing.db.entities.Contact

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun hInsertContact(Contact: Contact)



    @Transaction
    @Query("SELECT * FROM Contact WHERE hTemp = :ContactName")
    suspend fun hGetAllContacts(ContactName: String): List<Contact>


}