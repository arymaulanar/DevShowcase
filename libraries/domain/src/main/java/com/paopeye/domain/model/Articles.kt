package com.paopeye.domain.model

import com.paopeye.kit.extension.emptyInt
import com.paopeye.kit.extension.emptyString

data class Articles(
    val status: String = emptyString(),
    val page: Int = emptyInt(),
    val pageSize: Int = emptyInt(),
    val articles: List<Article> = emptyList()
)
