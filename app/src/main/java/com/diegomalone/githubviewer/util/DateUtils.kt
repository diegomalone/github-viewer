package com.diegomalone.githubviewer.util

import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

    private val apiDateFormat = SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault())

    fun getDateAsString(date: Date?): String? {
        return if (date == null) null else apiDateFormat.format(date)

    }

    fun getDateFromString(dateAsString: String?): Date? {
        if (dateAsString == null) return null

        try {
            return apiDateFormat.parse(dateAsString)
        } catch (e: ParseException) {
            Timber.e(e, "Failed to parse date")
        }

        return null
    }
}