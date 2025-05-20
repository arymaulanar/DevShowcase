package com.paopeye.data.entity

import com.paopeye.data.mapper.FromModelMapper
import com.paopeye.data.mapper.ToModelMapper
import com.paopeye.domain.model.Pagination
import com.paopeye.kit.extension.orEmpty

data class PaginationEntity(
    val pageSize: String? = null,
    val currentPage: Int? = null,
    val totalPage: Int? = null
) : ToModelMapper<Pagination> {
    override fun toModel() = Pagination(
        pageSize = pageSize.orEmpty(),
        currentPage = currentPage.orEmpty(),
        totalPage = totalPage.orEmpty()
    )

    companion object : FromModelMapper<Pagination, PaginationEntity> {
        override fun fromModel(model: Pagination) = PaginationEntity(
            pageSize = model.pageSize,
            currentPage = model.currentPage.orEmpty(),
            totalPage = model.totalPage.orEmpty()
        )
    }
}
