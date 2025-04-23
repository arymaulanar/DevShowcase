package com.paopeye.data.datasource.remote

import com.paopeye.data.entity.ArticlesEntity
import com.paopeye.data.entity.ResponseEntity

interface NewsRemoteDataSource {
    suspend fun getArticles(): ResponseEntity<ArticlesEntity>
}
