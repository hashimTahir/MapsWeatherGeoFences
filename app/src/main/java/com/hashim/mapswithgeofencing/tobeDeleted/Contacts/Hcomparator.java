/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.Contacts;


import com.hashim.mapswithgeofencing.tobeDeleted.DataBase.ContactsEntity;

import java.util.Comparator;

public class Hcomparator implements Comparator<ContactsEntity> {

    @Override
    public int compare(ContactsEntity o1, ContactsEntity o2) {
        return extractInt(o1.getName()) - extractInt(o2.getName());
    }

    private int extractInt(String contactName) {

        String num = contactName.replaceAll("\\D", "");
        // return 0 if no digits found
        return num.isEmpty() ? 0 : Integer.parseInt(num);
    }
}