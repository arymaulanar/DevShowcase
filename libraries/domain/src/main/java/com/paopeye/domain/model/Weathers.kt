package com.paopeye.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weathers(
    val weathers: List<Weather> = emptyList()
) : Parcelable
