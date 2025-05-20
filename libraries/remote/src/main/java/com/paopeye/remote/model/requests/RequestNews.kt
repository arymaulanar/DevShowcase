package com.paopeye.remote.model.requests

import com.paopeye.data.entity.PaginationEntity
import com.paopeye.data.mapper.FromEntityMapper
import com.paopeye.kit.extension.orEmpty

data class RequestNews(
    val pageSize: String? = null,
    val page: String? = null
) {
    fun toQuery() = mapOf(
        "pageSize" to pageSize.orEmpty(),
        "page" to page.orEmpty(),
    )

    companion object : FromEntityMapper<PaginationEntity, RequestNews> {
        override fun fromEntity(entity: PaginationEntity?) = RequestNews(
            pageSize = entity?.pageSize,
            page = entity?.currentPage.orEmpty().toString()
        )
    }
}

