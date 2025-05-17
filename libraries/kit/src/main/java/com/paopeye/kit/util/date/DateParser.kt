package com.paopeye.kit.util.date

import com.paopeye.kit.extension.emptyString
import com.paopeye.kit.extension.silence
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateParser {
    fun convertDateFormat(date: String, format: String, expectedFormat: String): String {
        if (date.isBlank()) return emptyString()
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        var formattedDate = emptyString()
        silence {
            val expectedSimpleDateFormat = SimpleDateFormat(expectedFormat, Locale.getDefault())
            val convertedDate = simpleDateFormat.parse(date)
            convertedDate?.let { formattedDate = expectedSimpleDateFormat.format(it) }
        }
        return formattedDate
    }

    fun getCurrentTime(): String {
        return SimpleDateFormat(DateFormat.TIME, Locale.getDefault())
            .format(Date(System.currentTimeMillis()))
    }

    fun getTimestampInSecond(): Long {
        return System.currentTimeMillis() / 1000
    }
}
