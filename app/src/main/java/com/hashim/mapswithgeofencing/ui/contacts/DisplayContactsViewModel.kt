/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.*
import com.hashim.mapswithgeofencing.db.entities.Contact
import com.hashim.mapswithgeofencing.repository.local.LocalRepo
import com.hashim.mapswithgeofencing.ui.contacts.ContactsStateEvent.*
import com.hashim.mapswithgeofencing.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayContactsViewModel @Inject constructor(
        private val hLocationRepo: LocalRepo,
        @ApplicationContext private val hContext: Context,
) : ViewModel() {
    private val _hContactsSateEvent = MutableLiveData<ContactsStateEvent>()
    private val _hContactsViewState = MutableLiveData<ContactsViewState>()

    val hContactsViewState: LiveData<ContactsViewState>
        get() = _hContactsViewState

    val hDataState: LiveData<DataState<ContactsViewState>> =
            Transformations.switchMap(_hContactsSateEvent) {
                it?.let { contactsStateEvent ->
                    hHandleStateEvents(it)
                }
            }

    private fun hHandleStateEvents(contactsStateEvent: ContactsStateEvent)
            : LiveData<DataState<ContactsViewState>>? {

        when (contactsStateEvent) {
            is OnContactsFound -> {
            }
            is OnFetchContacts -> {
            }
            is None -> {
            }
        }

        return null
    }

    private fun hFindContacts() = viewModelScope.launch {
        val hContactsList = mutableListOf<Contact>()
        val hContentResolver = hContext.contentResolver
        val hNumberCusor = hContentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        hNumberCusor?.let {
            while (hNumberCusor.moveToNext()) {
                val hPhoneNumber = hNumberCusor.getString(
                        hNumberCusor.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER
                        )
                ).toInt()
                if (hPhoneNumber > 0) {
                    val hId = hNumberCusor.getString(
                            hNumberCusor.getColumnIndex(ContactsContract.Contacts._ID)
                    )
                    val hName = hNumberCusor.getString(
                            hNumberCusor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    )

                    val hPhoneCursor = hContentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(hId),
                            null
                    )
                    if (hPhoneCursor?.moveToNext() == true) {
                        val hPhoneNumber = hPhoneCursor.getString(
                                hPhoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        )
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
            hNumberCusor.close()
        }

        hSaveToDb(hContactsList)
    }

    private fun hSaveToDb(hContactsList: MutableList<Contact>) {
        viewModelScope.launch {
            hContactsList.forEach {
                hLocationRepo.hInsertContact(it)
            }
        }
    }

    private fun hGetContacts() {
        viewModelScope.launch {
            hLocationRepo.hGetAllContacts()
        }
    }

    fun hSetStateEvent(contactsStateEvent: ContactsStateEvent) {
        _hContactsSateEvent.value = contactsStateEvent
    }

    private fun hGetCurrentViewStateOrNew(): ContactsViewState {
        return hContactsViewState.value ?: ContactsViewState()
    }

}