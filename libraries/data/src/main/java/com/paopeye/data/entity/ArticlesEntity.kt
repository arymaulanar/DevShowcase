package com.paopeye.data.entity

import com.paopeye.data.mapper.FromModelMapper
import com.paopeye.data.mapper.ToModelMapper
import com.paopeye.domain.model.Articles
import com.paopeye.kit.extension.orEmpty

data class ArticlesEntity(
    val status: String? = null,
    val page: Int? = null,
    val pageSize: Int? = null,
    val articles: List<ArticleEntity>? = null
) : ToModelMapper<Articles> {
    override fun toModel() = Articles(
        status = status.orEmpty(),
        page = page.orEmpty(),
        pageSize = pageSize.orEmpty(),
        articles = articles?.map { it.toModel() }.orEmpty()
    )

    companion object : FromModelMapper<Articles, ArticlesEntity> {
        override fun fromModel(model: Articles) = ArticlesEntity(
            status = model.status,
            page = model.page.orEmpty(),
            pageSize = model.pageSize.orEmpty(),
            articles = model.articles.map { ArticleEntity.fromModel(it) }.orEmpty()
        )
    }
}
