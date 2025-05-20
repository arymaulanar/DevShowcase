package com.paopeye.data.datasource.remote

import com.paopeye.data.entity.ArticlesEntity
import com.paopeye.data.entity.PaginationEntity
import com.paopeye.data.entity.ResponseEntity

interface NewsRemoteDataSource {
    suspend fun getArticles(request: PaginationEntity): ResponseEntity<ArticlesEntity>
}
