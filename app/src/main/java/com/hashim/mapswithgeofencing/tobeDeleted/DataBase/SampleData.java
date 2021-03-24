/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.DataBase;

import java.util.ArrayList;
import java.util.List;

public class SampleData {

    List<LocationEntitiy> hLocationEntitiyList = new ArrayList<>();

    public List<LocationEntitiy> gethLocationEntitiyList() {
        return hLocationEntitiyList;
    }

    public void sethLocationEntitiyList(List<LocationEntitiy> hLocationEntitiyList) {
        this.hLocationEntitiyList = hLocationEntitiyList;
    }

    public List<ContactsEntity> gethContactsEntityList() {
        return hContactsEntityList;
    }

    public void sethContactsEntityList(List<ContactsEntity> hContactsEntityList) {
        this.hContactsEntityList = hContactsEntityList;
    }

    List<ContactsEntity> hContactsEntityList = new ArrayList<>();

    public static LocationEntitiy hGetLocationEntityData(int id, String name, int radius) {
        return new LocationEntitiy(id, 3.123, 3.12, name, radius);
    }

    public static ContactsEntity hGetContactsEntityData(int id, String name, String number, int locationid) {
        ContactsEntity hContactsEntity = new ContactsEntity(id, number, name, locationid);
        return hContactsEntity;
    }


    public void hCreateTestData() {
        hLocationEntitiyList.add(hGetLocationEntityData(1, "work", 4));
        hLocationEntitiyList.add(hGetLocationEntityData(2, "home", 3));
        hLocationEntitiyList.add(hGetLocationEntityData(3, "test", 2));
        hLocationEntitiyList.add(hGetLocationEntityData(4, "office", 2));

        hContactsEntityList.add(hGetContactsEntityData(1, "t1", "1", 1));
        hContactsEntityList.add(hGetContactsEntityData(2, "t2", "12", 1));
        hContactsEntityList.add(hGetContactsEntityData(3, "t3", "123", 1));
        hContactsEntityList.add(hGetContactsEntityData(4, "t4", "1234", 1));
        hContactsEntityList.add(hGetContactsEntityData(5, "t5", "12345", 2));
        hContactsEntityList.add(hGetContactsEntityData(6, "t6", "123456", 2));
        hContactsEntityList.add(hGetContactsEntityData(7, "t7", "1234567", 2));
        hContactsEntityList.add(hGetContactsEntityData(8, "t8", "12345678", 3));
        hContactsEntityList.add(hGetContactsEntityData(9, "t9", "123456789", 3));
        hContactsEntityList.add(hGetContactsEntityData(10, "t10", "1234567890", 3));
        hContactsEntityList.add(hGetContactsEntityData(11, "t11", "12565", 4));
        hContactsEntityList.add(hGetContactsEntityData(12, "t12", "1234580", 4));
        hContactsEntityList.add(hGetContactsEntityData(13, "t13", "12345", 4));
    }
}

