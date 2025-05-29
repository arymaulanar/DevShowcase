package com.paopeye.domain.model

import android.content.Context
import android.os.Parcelable
import com.paopeye.kit.extension.emptyInt
import com.paopeye.kit.extension.emptyString
import kotlinx.parcelize.Parcelize

@Parcelize
data class Copyright(
    val title: String = emptyString(),
    val titleResource: Int = emptyInt(),
    val subtitle: String = emptyString(),
    val subtitleResource: Int = emptyInt(),
    val logo: Int = emptyInt(),
    val url: String = emptyString()
) : Parcelable {
    fun withResolvedResources(context: Context): Copyright {
        return copy(
            title = if (title.isEmpty() && titleResource != emptyInt()) context.getString(
                titleResource
            ) else title,
            subtitle = if (subtitle.isEmpty() && subtitleResource != emptyInt()) context.getString(
                subtitleResource
            ) else subtitle
        )
    }
}
