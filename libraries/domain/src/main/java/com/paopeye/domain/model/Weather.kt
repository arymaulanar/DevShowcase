package com.paopeye.domain.model

import android.os.Parcelable
import com.paopeye.kit.extension.emptyBigDecimal
import com.paopeye.kit.extension.emptyInt
import com.paopeye.kit.extension.emptyLong
import com.paopeye.kit.extension.emptyString
import com.paopeye.kit.util.date.DateFormat
import com.paopeye.kit.util.date.DateParser
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.Date

@Parcelize
data class Weather(
    val latitude: String = emptyString(),
    val longitude: String = emptyString(),
    val timestamp: Long = emptyLong(),
    val currentTemp: Int = emptyInt(),
    val feelsLikeTemp: Int = emptyInt(),
    val humidity: Int = emptyInt(),
    val minTemp: Int = emptyInt(),
    val maxTemp: Int = emptyInt(),
    val weather: String = emptyString(),
    val cloudPct: Int = emptyInt(),
    val windSpeed: BigDecimal = emptyBigDecimal(),
    val windDegrees: Int = emptyInt(),
    val cityName: String = emptyString()
) : Parcelable {
    fun getDate(): String {
        val adjustedTimestamp = timestamp * 1000
        val dateString = Date(adjustedTimestamp).toString()
        return DateParser.convertDateFormat(
            dateString,
            DateFormat.DEFAULT_FORMAT,
            DateFormat.TIME
        )
    }
    fun getHour(): String {
        val adjustedTimestamp = timestamp * 1000
        val dateString = Date(adjustedTimestamp).toString()
        return DateParser.convertDateFormatByTimezone(
            dateString,
            DateFormat.DEFAULT_FORMAT,
            DateFormat.HOUR
        )
    }
}
