package com.paopeye.domain.usecase.news

import com.paopeye.domain.model.Pagination
import com.paopeye.domain.repository.NewsRepository

class GetArticlesUseCase(private val repository: NewsRepository) {
    suspend fun invoke(request: Pagination) = repository.getArticles(request)
}
