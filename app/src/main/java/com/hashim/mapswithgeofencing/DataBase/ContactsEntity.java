/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.DataBase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.concurrent.atomic.AtomicInteger;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "ContactsTable", foreignKeys =
@ForeignKey(entity = LocationEntitiy.class, parentColumns = "lid",
        childColumns = "locationId", onDelete = CASCADE), indices = @Index(value = "locationId"))

public class ContactsEntity {
    @PrimaryKey(autoGenerate = true)
    private int contactsId = 0;
    private String number;
    private String name;
    private int locationId;


    private static final AtomicInteger hAtomicCounter = new AtomicInteger(0);


    public ContactsEntity() {
        this.contactsId = hAtomicCounter.getAndIncrement();

    }

    public ContactsEntity(int contactsId, String number, String name, int locationId) {
        this.contactsId = contactsId;
        this.number = number;
        this.name = name;
        this.locationId = locationId;
    }

    public int getContactsId() {
        return contactsId;
    }

    public void setContactsId(int contactsId) {
        this.contactsId = contactsId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}
