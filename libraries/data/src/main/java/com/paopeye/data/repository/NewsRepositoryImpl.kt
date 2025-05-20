package com.paopeye.data.repository

import com.paopeye.data.datasource.DataStateBoundResource
import com.paopeye.data.datasource.remote.NewsRemoteDataSource
import com.paopeye.data.entity.PaginationEntity
import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.model.Articles
import com.paopeye.domain.model.Pagination
import com.paopeye.domain.repository.NewsRepository

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource) : NewsRepository {
    override suspend fun getArticles(request: Pagination): DataState<Articles> =
        DataStateBoundResource.createNetworkCall {
            newsRemoteDataSource.getArticles(PaginationEntity.fromModel(request))
        }.getResult()
}
