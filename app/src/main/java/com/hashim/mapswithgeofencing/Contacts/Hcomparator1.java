/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Contacts;



import java.util.Comparator;

public class Hcomparator1 implements Comparator<ContactsModelWithIds> {

    @Override
    public int compare(ContactsModelWithIds o1, ContactsModelWithIds o2) {
        return extractInt(o1.getContactName()) - extractInt(o2.getContactName());
    }

    private int extractInt(String contactName) {

        String num = contactName.replaceAll("\\D", "");
        // return 0 if no digits found
        return num.isEmpty() ? 0 : Integer.parseInt(num);
    }
}