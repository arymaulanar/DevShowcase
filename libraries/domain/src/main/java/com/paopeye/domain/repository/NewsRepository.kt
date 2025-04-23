package com.paopeye.domain.repository

import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.model.Articles

interface NewsRepository {
    suspend fun getArticles(): DataState<Articles>
}
