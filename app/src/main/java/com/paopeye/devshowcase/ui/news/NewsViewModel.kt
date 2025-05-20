package com.paopeye.devshowcase.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paopeye.devshowcase.base.BaseViewModel
import com.paopeye.devshowcase.util.StateWrapper
import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.model.Article
import com.paopeye.domain.model.Articles
import com.paopeye.domain.model.Pagination
import com.paopeye.domain.usecase.news.GetArticlesUseCase
import com.paopeye.kit.extension.orEmpty
import kotlinx.coroutines.launch

class NewsViewModel(
    private val getArticlesUseCase: GetArticlesUseCase
) : BaseViewModel() {
    sealed class Event {
        data object OnCreate : Event()
        data object OnLoadMore : Event()
    }

    sealed class State {
        data class ShowArticles(val articles: Articles) : State()
        data class ShowLoadingLoadMore(val isVisible: Boolean) : State()
        data object ShowLoading : State()
        data object HideLoading : State()
    }

    private val _state = MutableLiveData<StateWrapper<State>>()
    val state: LiveData<StateWrapper<State>> = _state

    private fun setState(state: State) {
        _state.value = StateWrapper(state)
    }

    private var _articles = mutableListOf<Article>()
    private var pagination = Pagination(
        currentPage = 1,
        pageSize = "5",
        totalPage = 25
    )

    fun onEvent(event: Event) {
        when (event) {
            is Event.OnCreate -> onCreate()
            is Event.OnLoadMore -> onLoadMore()
        }
    }

    private fun onCreate() {
        if (_articles.isNotEmpty()) return
        val request = pagination.copy(currentPage = 1)
        onLoadArticles(request)
    }

    private fun onLoadMore() {
        if (_articles.size >= pagination.totalPage) return
        Log.d("TAG", "onLoadMore: ")
        onLoadArticles(pagination, true)
    }

    private fun onLoadArticles(request: Pagination, isLoadMore: Boolean = false) = launch {
        if (isLoadMore) {
            pagination = pagination.copy(currentPage = request.currentPage.inc())
        }
        showLoading(true, isLoadMore)
        val result = getArticlesUseCase.invoke(pagination)
        showLoading(false, isLoadMore)
        if (result is DataState.ERROR) return@launch
        val articles = result.data ?: Articles()
        _articles.addAll(articles.articles.orEmpty())
        val data = articles.copy(articles = _articles, page = pagination.currentPage)
        setState(State.ShowArticles(data))
    }

    private fun showLoading(isVisible: Boolean, isLoadMore: Boolean) {
        if (isLoadMore) {
            setState(State.ShowLoadingLoadMore(isVisible))
            return
        }
        if (isVisible) {
            setState(State.ShowLoading)
            return
        }
        setState(State.HideLoading)
    }
}
