/*
 * Copyright (c) 2021/  3/ 25.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

import com.hashim.mapswithgeofencing.utils.DateFormatter.FormatterType.*
import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {


    enum class FormatterType {
        JUST_DATE,
        JUST_DAY_NAME,
        DAYNAME_MONTH_DATE,
        HRS_MINS,
        YEAR_MONTH_DAY_HRS_MINS_SECS,
        YEAR_MONTH_DAY,
        HRS_MINS_SECS,

    }

    companion object {
        private val hYearMonthDayHrsMinsSecFormat = "yyyy-MM-dd HH:mm:ss"
        private val hYearMonthDayFormat = "yyyy-MM-dd"
        private val hHrsMinSecsFormat = "HH:mm:ss"
        private val hJstDayNameFormat = "EEEE"
        private val hOnlyDateFormat = "dd"
        private val hDayNameMonthDate = "EEEE, MMMM d"
        private val hHrsMinTime = " h:mm aa"


        fun hGetSimpleFormatter(formatterType: FormatterType): SimpleDateFormat {
            return when (formatterType) {
                JUST_DATE -> SimpleDateFormat(hOnlyDateFormat, Locale.getDefault())
                JUST_DAY_NAME -> SimpleDateFormat(hJstDayNameFormat, Locale.getDefault())
                DAYNAME_MONTH_DATE -> SimpleDateFormat(hDayNameMonthDate, Locale.getDefault())
                HRS_MINS -> SimpleDateFormat(hHrsMinTime, Locale.getDefault())
                YEAR_MONTH_DAY -> SimpleDateFormat(hYearMonthDayFormat, Locale.getDefault())
                YEAR_MONTH_DAY_HRS_MINS_SECS -> SimpleDateFormat(hYearMonthDayHrsMinsSecFormat, Locale.getDefault())
                HRS_MINS_SECS -> SimpleDateFormat(hHrsMinSecsFormat, Locale.getDefault())
            }
        }


    }
}