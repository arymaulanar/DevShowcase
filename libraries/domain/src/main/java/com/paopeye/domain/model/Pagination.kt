package com.paopeye.domain.model

import android.os.Parcelable
import com.paopeye.kit.extension.emptyInt
import com.paopeye.kit.extension.emptyString
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pagination(
    val pageSize: String = emptyString(),
    val currentPage: Int = emptyInt(),
    val totalPage: Int = emptyInt(),
) : Parcelable
