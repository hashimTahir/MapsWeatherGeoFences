/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.DataBase;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface ContactsDao {
    //////////////////////////////////////////////////
    //////////////////insert Quries///////////////////
    /////////////////////////////////////////////////
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void hInsertContactsEntity(ContactsEntity hContactsEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void hInsertAllContactsEntity(List<ContactsEntity> hContactsEntity);

    //////////////////////////////////////////////////////////////
    //////////////////Select quries//////////////////////////////
    /////////////////////////////////////////////////////////////
    @Query("select count(*) from contactstable")
    int hGetCount();

    @Query("SELECT  contactstable.* FROM locationtable INNER JOIN " +
            "contactstable ON locationId=locationtable.lid where LocationTable.lid =:hId")
    List<ContactsEntity> hContactsById(int hId);


    @Query("select contactsId From  contactstable")
    List<Integer> hGetAllContactsId();


    @Query("Select * from contactstable")
    List<ContactsEntity> hGetAllContacts();
/////////////////////////////////////////////////////////////////////

    @Delete
    void hDeleteContact(ContactsEntity contactsEntity);


    @Delete
    void hDeleteAllContacts(List<ContactsEntity> hContactsEntityList);


    @Query("DELETE FROM contactstable")
    void hNukeContactsTable();

}
