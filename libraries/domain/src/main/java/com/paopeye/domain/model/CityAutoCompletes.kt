package com.paopeye.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityAutoCompletes(
    val cities: List<CityAutoComplete> = emptyList()
) : Parcelable
