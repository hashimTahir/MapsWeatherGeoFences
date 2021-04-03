/*
 * Copyright (c) 2021/  4/ 2.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

import com.hashim.mapswithgeofencing.db.entities.Contact

data class ContactsViewState(
        val hContactsFields: ContactsFields = ContactsFields()
) {
    data class ContactsFields(
            val hContactList: List<Contact>? = null
    )


}
