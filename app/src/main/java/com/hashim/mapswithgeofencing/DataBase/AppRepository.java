/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.DataBase;

import android.annotation.SuppressLint;
import android.content.Context;

import com.hashim.mapswithgeofencing.Helper.LogToastSnackHelper;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppRepository {
    private static final String hTag = LogToastSnackHelper.hMakeTag(AppRepository.class);
    private static AppDataBase hAppDataBase;
    private int hId;
    private static ExecutorService hExecuter = Executors.newSingleThreadExecutor();
    @SuppressLint("StaticFieldLeak")
    private static AppRepository hInstance = null;


    public AppRepository(Context context) {
        hAppDataBase = AppDataBase.gethAppDbInstance(context);
    }

    public static AppRepository hGetInstance(Context context) {
        if (hInstance == null) {
            hInstance = new AppRepository(context);
        }
        return hInstance;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////Callables//////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Callable<List<LocationEntitiy>> hAllLocationsListCallable = new Callable<List<LocationEntitiy>>() {
        @Override
        public List<LocationEntitiy> call() {
            return hAppDataBase.getLocationDao().hGetAllLocationData();
        }
    };


    private Callable<List<ContactsEntity>> hAllContactsListCallable = new Callable<List<ContactsEntity>>() {
        @Override
        public List<ContactsEntity> call() {
            return hAppDataBase.getContactsDao().hGetAllContacts();
        }
    };

    private Callable<List<Integer>> hAllLocationIdsCallable = new Callable<List<Integer>>() {
        @Override
        public List<Integer> call() {
            return hAppDataBase.getLocationDao().hGetAllLocationsId();
        }
    };
    private Callable<List<Integer>> hAllContactsIdsCallable = new Callable<List<Integer>>() {
        @Override
        public List<Integer> call() {
            return hAppDataBase.getContactsDao().hGetAllContactsId();
        }
    };


    private Callable<LocationEntitiy> hLocationsByIdCallable = new Callable<LocationEntitiy>() {
        @Override
        public LocationEntitiy call() {
            return hAppDataBase.getLocationDao().hLocationById(hId);
        }
    };
    private Callable<List<ContactsEntity>> hContactsByIdCallable = new Callable<List<ContactsEntity>>() {
        @Override
        public List<ContactsEntity> call() {
            return hAppDataBase.getContactsDao().hContactsById(hId);
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void hAddSampleData() {
        hExecuter.execute(() -> {
            SampleData hSampleData = new SampleData();
            hSampleData.hCreateTestData();

            hAppDataBase.getLocationDao().hInsertAllLocationEntity(hSampleData.gethLocationEntitiyList());
            hAppDataBase.getContactsDao().hInsertAllContactsEntity(hSampleData.gethContactsEntityList());
            LogToastSnackHelper.hLogField(hTag, "Execution Successfull");
        });
    }

    public List<LocationEntitiy> hGetAllLocationsData() {
        Future<List<LocationEntitiy>> future = hExecuter.submit(hAllLocationsListCallable);

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ContactsEntity> hGetAllContactsData() {
        Future<List<ContactsEntity>> future = hExecuter.submit(hAllContactsListCallable);

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocationEntitiy hQueryLocationDataById(String id) {
        hId = Integer.parseInt(id);
        Future<LocationEntitiy> future = hExecuter.submit(hLocationsByIdCallable);
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ContactsEntity> hQueryContactsDataById(String mParam1) {
        hId = Integer.parseInt(mParam1);
        Future<List<ContactsEntity>> future = hExecuter.submit(hContactsByIdCallable);
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }


    public List<Integer> hGetAllLocationIds() {
        Future<List<Integer>> future = hExecuter.submit(hAllLocationIdsCallable);
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Integer> hGetAllContactsIds() {
        Future<List<Integer>> future = hExecuter.submit(hAllContactsIdsCallable);
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }


    public void hDeleteContact(ContactsEntity contactsEntity) {
        hExecuter.execute(() -> hAppDataBase.getContactsDao().hDeleteContact(contactsEntity));

    }

    public void hDeleteContactList(List<ContactsEntity> contactsEntityList) {
        hExecuter.execute(() -> hAppDataBase.getContactsDao().hDeleteAllContacts(contactsEntityList));

    }


    public void hAddAllContacts(List<ContactsEntity> contactsEntityList) {
        hExecuter.execute(() -> hAppDataBase.getContactsDao().hInsertAllContactsEntity(contactsEntityList));

    }

    public void hAddLocation(LocationEntitiy locationEntitiy) {
        hExecuter.execute(() -> hAppDataBase.getLocationDao().hInsertLocationEntity(locationEntitiy));

    }

    public void hNukeDataBase() {
        hExecuter.execute(() -> hAppDataBase.getLocationDao().hNukeLocationTable());
        hExecuter.execute(() -> hAppDataBase.getContactsDao().hNukeContactsTable());
    }

    public void hDeleteLocationEntity(LocationEntitiy hLocationEntitiy) {
        hExecuter.execute(() -> hAppDataBase.getLocationDao().hDeleteLocation(hLocationEntitiy));
    }
}
