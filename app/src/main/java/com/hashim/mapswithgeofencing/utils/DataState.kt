/*
 * Copyright (c) 2021/  3/ 23.  Created by Hashim Tahir
 */
package com.hashim.mapswithgeofencing.utils

data class DataState<T>(
    var hMessage: String? = null,
    var hLoading: Boolean = false,
    var hData: T? = null
) {

    companion object {
        fun <T> hError(errorMessage: String)
                : DataState<T> {
            return DataState(
                hMessage = errorMessage,
                hLoading = false,
                hData = null
            )
        }

        fun <T> hLoading(loading: Boolean): DataState<T> {
            return DataState(
                hMessage = null,
                hLoading = true,
                hData = null
            )
        }

        fun <T> hData(
            message: String? = null,
            data: T? = null
        ): DataState<T> {
            return DataState(
                hMessage = null,
                hLoading = false,
                hData = data
            )
        }
    }

    override fun toString(): String {
        return "DataState(hMessage=$hMessage, hLoading=$hLoading, hData=$hData)"
    }

}