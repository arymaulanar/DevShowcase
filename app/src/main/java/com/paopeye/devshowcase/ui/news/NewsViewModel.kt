package com.paopeye.devshowcase.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paopeye.devshowcase.base.BaseViewModel
import com.paopeye.devshowcase.util.StateWrapper
import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.model.Article
import com.paopeye.domain.model.Articles
import com.paopeye.domain.usecase.news.GetArticlesUseCase
import com.paopeye.kit.extension.orEmpty
import kotlinx.coroutines.launch

class NewsViewModel(
    private val getArticlesUseCase: GetArticlesUseCase
) : BaseViewModel() {
    sealed class Event {
        data object OnCreate : Event()
    }

    sealed class State {
        data class ShowArticles(val articles: List<Article>) : State()
        data object ShowLoading : State()
        data object HideLoading : State()
    }

    private val _state = MutableLiveData<StateWrapper<State>>()
    val state: LiveData<StateWrapper<State>> = _state

    private fun setState(state: State) {
        _state.value = StateWrapper(state)
    }

    private var _articles : Articles? = null

    fun onEvent(event: Event) {
        when (event) {
            is Event.OnCreate -> onLoadArticles()
        }
    }

    private fun onLoadArticles() = launch {
        if (_articles != null) return@launch
        setState(State.ShowLoading)
        val result = getArticlesUseCase.invoke()
        setState(State.HideLoading)
        if (result is DataState.ERROR) return@launch
        val articles = result.data
        _articles = articles
        setState(State.ShowArticles(articles?.articles.orEmpty()))
    }
}
