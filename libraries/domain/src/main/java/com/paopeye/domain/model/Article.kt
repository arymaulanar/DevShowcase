package com.paopeye.domain.model

import android.os.Parcelable
import com.paopeye.kit.extension.emptyString
import com.paopeye.kit.extension.getDomainName
import com.paopeye.kit.util.date.DateFormat
import com.paopeye.kit.util.date.DateParser
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val link: String = emptyString(),
    val source: String = emptyString(),
    val title: String = emptyString(),
    val summary: String = emptyString(),
    val publishDate: String = emptyString(),
    val language: String = emptyString(),
    val images: List<String> = emptyList()
) : Parcelable {
    fun getSourceDomain(): String {
        return getDomainName(source)
    }

    fun getPublishDateFormatted(): String {
        return DateParser.convertDateFormat(
            publishDate,
            DateFormat.ISO8601_1,
            DateFormat.FULL_FORMAT_DOT
        )
    }
}

