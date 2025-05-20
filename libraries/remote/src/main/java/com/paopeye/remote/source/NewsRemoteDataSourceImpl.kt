package com.paopeye.remote.source

import com.paopeye.data.datasource.remote.NewsRemoteDataSource
import com.paopeye.data.entity.ArticlesEntity
import com.paopeye.data.entity.PaginationEntity
import com.paopeye.data.entity.ResponseEntity
import com.paopeye.remote.base.util.mapResponseCode
import com.paopeye.remote.model.requests.RequestNews
import com.paopeye.remote.service.NewsService

class NewsRemoteDataSourceImpl(private val service: NewsService) : NewsRemoteDataSource {
    override suspend fun getArticles(request: PaginationEntity): ResponseEntity<ArticlesEntity> {
        val response = service.getArticles(RequestNews.fromEntity(request).toQuery())
        val dataEntity = ArticlesEntity(
            status = response.body()?.status,
            page = response.body()?.page,
            pageSize = response.body()?.pageSize,
            articles = response.body()?.articles?.map { it.toEntity() }
        )
        return ResponseEntity(
            responseCode = mapResponseCode(response.code()),
            data = dataEntity
        )
    }
}
