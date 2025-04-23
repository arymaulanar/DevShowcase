package com.paopeye.domain.model

import com.paopeye.kit.extension.emptyString

data class Article(
    val link: String = emptyString(),
    val source: String = emptyString(),
    val title: String = emptyString(),
    val summary: String = emptyString(),
    val publishDate: String = emptyString(),
    val language: String = emptyString(),
    val images: List<String> = emptyList()
)
