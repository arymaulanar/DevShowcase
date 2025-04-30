package com.paopeye.devshowcase.ui.news_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paopeye.devshowcase.base.BaseViewModel
import com.paopeye.devshowcase.util.StateWrapper
import com.paopeye.domain.model.Article

class NewsDetailViewModel : BaseViewModel() {
    sealed class Event {
        data class OnCreate(val article: Article) : Event()
    }

    sealed class State {
        data class ShowArticle(val article: Article) : State()
    }

    private val _state = MutableLiveData<StateWrapper<State>>()
    val state: LiveData<StateWrapper<State>> = _state

    private fun setState(state: State) {
        _state.value = StateWrapper(state)
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.OnCreate -> onCreate(event.article)
        }
    }

    private fun onCreate(article: Article) {
        setState(State.ShowArticle(article))
    }
}
