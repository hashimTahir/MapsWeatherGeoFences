/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.DataBase;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@androidx.room.Dao
public interface LocationDoa {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void hInsertLocationEntity(LocationEntitiy hLocationEntitiy);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void hInsertAllLocationEntity(List<LocationEntitiy> hLocationEntitiys);

    @Query("Select * from locationtable")
    List<LocationEntitiy> hGetAllLocationData();


    @Query("select count(*) from locationtable")
    int hGetCount();

    @Query("select lid From  locationtable")
    List<Integer> hGetAllLocationsId();


    @Query("SELECT locationtable.* FROM locationtable INNER JOIN " +
            "contactstable ON locationId=locationtable.lid where LocationTable.lid =:hId")
    LocationEntitiy hLocationById(int hId);

    @Query("DELETE FROM locationtable")
    void hNukeLocationTable();

    @Delete
    void hDeleteLocation(LocationEntitiy hLocationEntitiy);
}
