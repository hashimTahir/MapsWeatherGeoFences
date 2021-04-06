/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.*
import com.hashim.mapswithgeofencing.db.entities.Contact
import com.hashim.mapswithgeofencing.repository.local.LocalRepo
import com.hashim.mapswithgeofencing.ui.events.ContactsStateEvent
import com.hashim.mapswithgeofencing.ui.events.ContactsStateEvent.*
import com.hashim.mapswithgeofencing.ui.events.ContactsViewState
import com.hashim.mapswithgeofencing.ui.events.ContactsViewState.ContactsFields
import com.hashim.mapswithgeofencing.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContactsSharedViewModel @Inject constructor(
        private val hLocalRepo: LocalRepo,
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
                hFindContacts()
            }
            is OnGetContacts -> {
                hGetContacts()
            }
            is OnSaveContacts -> {
                hSaveToDb(contactsStateEvent.hSelectedContacts)
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
        _hContactsViewState.value = ContactsViewState(
                hContactsFields = ContactsFields(
                        hContactList = hContactsList
                )
        )
    }

    private fun hSaveToDb(hContactsList: List<Contact>) {
        viewModelScope.launch {
            hContactsList.forEach {
                Timber.d("Contacts Saved ${hLocalRepo.hInsertContact(it)}")
            }
        }
    }

    private fun hGetContacts() {
        /*Fetch contacts from device in backgroud. save if any is missing in backgroud*/
        viewModelScope.launch {
            val hContactsList = hLocalRepo.hGetAllContacts()
            if (hContactsList.size > 0) {
                _hContactsViewState.value = ContactsViewState(
                        hContactsFields = ContactsFields(
                                hContactList = hContactsList
                        )
                )
            } else {
                /*Notifiy no contacts in db.*/
            }
        }
    }

    fun hSetStateEvent(contactsStateEvent: ContactsStateEvent) {
        _hContactsSateEvent.value = contactsStateEvent
    }

    private fun hGetCurrentViewStateOrNew(): ContactsViewState {
        return hContactsViewState.value ?: ContactsViewState()
    }

}