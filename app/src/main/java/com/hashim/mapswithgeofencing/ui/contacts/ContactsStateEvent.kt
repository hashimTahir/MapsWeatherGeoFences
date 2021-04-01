/*
 * Copyright (c) 2021/  4/ 2.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

sealed class ContactsStateEvent {
    class OnFetchContacts() : ContactsStateEvent()

    class OnContactsFound() : ContactsStateEvent()

    class None : ContactsStateEvent()
}