/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.DataBase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {LocationEntitiy.class, ContactsEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract LocationDoa getLocationDao();

    public abstract ContactsDao getContactsDao();

    public static final String H_DATABASE_NAME = "AppDatabase.db";
    private static volatile AppDataBase hAppDbInstance;
    private static final Object H_LOCK = new Object();


    public static synchronized AppDataBase gethAppDbInstance(Context context) {
        if (hAppDbInstance == null) {
            synchronized (H_LOCK) {
                if (hAppDbInstance == null) {
                    hAppDbInstance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, H_DATABASE_NAME).build();
                }
            }
        }
        return hAppDbInstance;
    }
}



