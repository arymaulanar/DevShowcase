package com.paopeye.remote.model.responses

import com.paopeye.data.entity.ArticleEntity
import com.paopeye.data.mapper.ToEntityMapper
import com.paopeye.kit.extension.orEmpty

data class ResponseArticle(
    val link: String? = null,
    val source: String? = null,
    val title: String? = null,
    val summary: String? = null,
    val publishDate: String? = null,
    val language: String? = null,
    val images: List<String>? = null
) : ToEntityMapper<ArticleEntity> {
    override fun toEntity() = ArticleEntity(
        link = link.orEmpty(),
        source = source.orEmpty(),
        title = title.orEmpty(),
        summary = summary.orEmpty(),
        publishDate = publishDate.orEmpty(),
        language = language.orEmpty(),
        images = images.orEmpty()
    )
}
