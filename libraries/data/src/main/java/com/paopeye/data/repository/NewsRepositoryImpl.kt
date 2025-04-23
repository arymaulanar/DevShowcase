package com.paopeye.data.repository

import com.paopeye.data.datasource.DataStateBoundResource
import com.paopeye.data.datasource.remote.NewsRemoteDataSource
import com.paopeye.domain.repository.NewsRepository

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource) : NewsRepository {
    override suspend fun getArticles() = DataStateBoundResource.createNetworkCall {
        newsRemoteDataSource.getArticles()
    }.getResult()
}
