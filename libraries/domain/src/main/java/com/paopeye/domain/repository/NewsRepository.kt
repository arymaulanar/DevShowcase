package com.paopeye.domain.repository

import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.model.Articles
import com.paopeye.domain.model.Pagination

interface NewsRepository {
    suspend fun getArticles(request: Pagination): DataState<Articles>
}
