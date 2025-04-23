package com.paopeye.remote.source

import com.paopeye.data.datasource.remote.NewsRemoteDataSource
import com.paopeye.data.entity.ArticlesEntity
import com.paopeye.data.entity.ResponseEntity
import com.paopeye.remote.service.NewsService

class NewsRemoteDataSourceImpl(private val service: NewsService) : NewsRemoteDataSource {
    override suspend fun getArticles(): ResponseEntity<ArticlesEntity> {
        return ResponseEntity(
            responseCode = ResponseEntity.SUCCESS_RESPONSE_CODE,
            data = service.getArticles().toEntity()
        )
    }
}
