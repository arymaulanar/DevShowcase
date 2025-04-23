package com.paopeye.data.entity

import com.paopeye.data.mapper.FromModelMapper
import com.paopeye.data.mapper.ToModelMapper
import com.paopeye.domain.model.Article
import com.paopeye.kit.extension.orEmpty

data class ArticleEntity(
    val link: String? = null,
    val source: String? = null,
    val title: String? = null,
    val summary: String? = null,
    val publishDate: String? = null,
    val language: String? = null,
    val images: List<String>? = null
) : ToModelMapper<Article> {
    override fun toModel() = Article(
        link = link.orEmpty(),
        source = source.orEmpty(),
        title = title.orEmpty(),
        summary = summary.orEmpty(),
        publishDate = publishDate.orEmpty(),
        language = language.orEmpty(),
        images = images.orEmpty()
    )

    companion object : FromModelMapper<Article, ArticleEntity> {
        override fun fromModel(model: Article) = ArticleEntity(
            link = model.link,
            source = model.source,
            title = model.title,
            summary = model.summary,
            publishDate = model.publishDate,
            language = model.language,
            images = model.images
        )
    }
}

