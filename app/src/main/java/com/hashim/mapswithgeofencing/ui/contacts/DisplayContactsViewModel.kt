/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hashim.mapswithgeofencing.db.entities.Contact
import com.hashim.mapswithgeofencing.repository.local.LocalRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DisplayContactsViewModel @Inject constructor(
        private val hLocationRepo: LocalRepo,
        @ApplicationContext private val hContext: Context,
) : ViewModel() {


    fun hFindContacts() = viewModelScope.launch {
        val hContactsList = mutableListOf<Contact>()
        val hContentResolver = hContext.contentResolver
        val hCursor = hContentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        hCursor?.let {
            while (hCursor.moveToNext()) {
                val hPhoneNumber = hCursor.getString(
                        hCursor.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER
                        )
                ).toInt()
                if (hPhoneNumber > 0) {
                    val hId = hCursor.getString(hCursor.getColumnIndex(ContactsContract.Contacts._ID));
                    val hName = hCursor.getString(hCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    val hPhoneCursor = hContentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(hId),
                            null
                    )
                    if (hPhoneCursor?.moveToNext() == true) {
                        val hPhoneNumber = hPhoneCursor.getString(hPhoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        hContactsList.add(
                                Contact(
                                        hNumber = hPhoneNumber,
                                        hName = hName,
                                )
                        )
                        hPhoneCursor.close()
                    }

                }
            }
            hCursor.close()
        }

        Timber.d("Contacts list $hContactsList")
    }
}