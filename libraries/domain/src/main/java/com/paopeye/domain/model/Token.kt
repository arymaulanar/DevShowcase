package com.paopeye.domain.model

import android.os.Parcelable
import com.paopeye.kit.extension.emptyDouble
import com.paopeye.kit.extension.emptyLong
import com.paopeye.kit.extension.emptyString
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token(
    val accessToken: String = emptyString(),
    val tokenType: String = emptyString(),
    val lifetime: Double = emptyDouble(),
    val expiredAt: Long = emptyLong(),
    val scope: String = emptyString()
) : Parcelable
