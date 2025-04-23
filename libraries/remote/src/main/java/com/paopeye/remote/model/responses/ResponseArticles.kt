package com.paopeye.remote.model.responses

import com.paopeye.data.entity.ArticlesEntity
import com.paopeye.data.mapper.ToEntityMapper
import com.paopeye.kit.extension.orEmpty

data class ResponseArticles(
    val status: String? = null,
    val page: Int? = null,
    val pageSize: Int? = null,
    val articles: List<ResponseArticle>? = null
) : ToEntityMapper<ArticlesEntity> {
    override fun toEntity() = ArticlesEntity(
        status = status.orEmpty(),
        page = page.orEmpty(),
        pageSize = pageSize.orEmpty(),
        articles = articles?.map { it.toEntity() }.orEmpty()
    )
}
