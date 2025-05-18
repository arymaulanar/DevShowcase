package com.paopeye.domain.model

import android.os.Parcelable
import com.paopeye.kit.extension.emptyString
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityAutoComplete(
    val latitude: String = emptyString(),
    val longitude: String = emptyString(),
    val city: String = emptyString(),
    val country: String = emptyString(),
    val postcode: String = emptyString(),
    val query: String = emptyString(),
) : Parcelable {
    fun getFullAddress(): String {
        var result = city
        if (country.isNotEmpty()) result += ", ".plus(country)
        if (postcode.isNotEmpty()) result += ", ".plus(postcode)
        return result
    }
}
