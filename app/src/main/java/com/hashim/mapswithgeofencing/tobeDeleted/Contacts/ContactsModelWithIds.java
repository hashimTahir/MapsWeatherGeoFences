/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.Contacts;


import java.util.concurrent.atomic.AtomicInteger;

public class ContactsModelWithIds extends ContactsModel {
    private static final AtomicInteger hAtomicCounter = new AtomicInteger(0);

    public int gethId() {
        return hId;
    }


    int hId;

    public ContactsModelWithIds() {
        hId = hAtomicCounter.getAndIncrement();
    }


    public ContactsModelWithIds(ContactsModel contactsModel) {
        super(contactsModel.getContactImage(), contactsModel.getContactName(), contactsModel.getContactNumber());
        hId = hAtomicCounter.getAndIncrement();
    }


}
