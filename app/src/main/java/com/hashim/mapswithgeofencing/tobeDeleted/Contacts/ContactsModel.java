/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.Contacts;

public class ContactsModel {
    private String ContactImage;
    private String ContactName;
    private String ContactNumber;

    public ContactsModel() {
    }

    public ContactsModel(String contactImage, String contactName, String contactNumber) {
        ContactImage = contactImage;
        ContactName = contactName;
        ContactNumber = contactNumber;
    }

    public ContactsModel(String contactName, String contactNumber) {
        ContactName = contactName;
        ContactNumber = contactNumber;
    }

    public String getContactImage() {
        return ContactImage;
    }

    public void setContactImage(String contactImage) {
        this.ContactImage = ContactImage;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }
}