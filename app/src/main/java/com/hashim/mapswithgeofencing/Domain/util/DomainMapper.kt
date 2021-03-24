/*
 * Copyright (c) 2021/  3/ 23.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Domain.util

interface DomainMapper<T, DomainModel> {

    fun hMapToDomainModel(model: T): DomainModel

    fun hMapFromDomailModel(domainModel: DomainModel): T
}