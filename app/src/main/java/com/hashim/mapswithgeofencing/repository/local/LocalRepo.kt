/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.local

import android.location.Location
import com.hashim.mapswithgeofencing.Domain.model.Directions
import com.hashim.mapswithgeofencing.Domain.model.Forecast
import com.hashim.mapswithgeofencing.Domain.model.NearByPlaces
import com.hashim.mapswithgeofencing.Domain.model.Weather
import com.hashim.mapswithgeofencing.db.entities.Contact
import com.hashim.mapswithgeofencing.ui.calculateroute.DirectionsMode
import com.hashim.mapswithgeofencing.ui.main.Category

interface LocalRepo {

    suspend fun hInsertContact(Contact: Contact): Long

    suspend fun hGetAllContacts(Contact: Contact): List<Contact>



}