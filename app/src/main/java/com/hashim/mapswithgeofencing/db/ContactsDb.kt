/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hashim.mapswithgeofencing.db.entities.Contact

@Database(
        entities = [
            Contact::class,
        ],
        version = 1
)
abstract class ContactsDb : RoomDatabase() {
    abstract val hContactsDao: ContactsDao

    companion object {
        @Volatile
        private var INSTANCE: ContactsDb? = null

        fun hGetInstance(context: Context): ContactsDb {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                        context.applicationContext,
                        ContactsDb::class.java,
                        "contacts_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}