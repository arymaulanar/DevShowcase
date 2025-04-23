package com.paopeye.devshowcase.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paopeye.devshowcase.base.BaseViewModel
import com.paopeye.devshowcase.util.StateWrapper
import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.model.Article
import com.paopeye.domain.model.Articles
import com.paopeye.domain.usecase.news.GetArticlesUseCase
import kotlinx.coroutines.launch

class NewsViewModel(
    private val getArticlesUseCase: GetArticlesUseCase
) : BaseViewModel() {
    sealed class Event {
        data object OnCreate : Event()
    }

    sealed class State {
        data class ShowArticles(val articles: List<Article>) : State()
    }

    private val _state = MutableLiveData<StateWrapper<State>>()
    val state: LiveData<StateWrapper<State>> = _state

    private fun setState(state: State) {
        _state.value = StateWrapper(state)
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.OnCreate -> onLoadArticles()
        }
    }

    private fun onLoadArticles() = launch {
        try {
            val result = getArticlesUseCase.invoke()
            if (result is DataState.ERROR) return@launch
            val articles = result.data
            setState(State.ShowArticles(sortAscByName(articles)))
        } catch (e: Exception) {
            Log.e("NewsViewModel", "onLoadArticles: " + e.message)
        }
    }

    private fun sortAscByName(articles: Articles?): List<Article> {
        if (articles == null) return emptyList()
        return articles.articles.sortedBy { it.title }
    }
}
