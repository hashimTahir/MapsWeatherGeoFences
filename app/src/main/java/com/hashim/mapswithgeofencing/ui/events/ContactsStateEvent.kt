/*
 * Copyright (c) 2021/  4/ 4.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

import com.hashim.mapswithgeofencing.db.entities.Contact

sealed class ContactsStateEvent {
    class OnFetchContacts : ContactsStateEvent()

    class OnContactsFound : ContactsStateEvent()

    class OnSaveContacts(val hSelectedContacts: List<Contact>) : ContactsStateEvent()

    class None : ContactsStateEvent()

    class OnGetContacts : ContactsStateEvent()
}