package com.paopeye.devshowcase.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paopeye.devshowcase.base.BaseViewModel
import com.paopeye.devshowcase.util.StateWrapper
import com.paopeye.domain.model.User

class NewsViewModel : BaseViewModel() {
    sealed class Event {
        data object OnCreate : Event()
    }

    sealed class State {
        data class ShowUserData(val user: User) : State()
    }

    private val _state = MutableLiveData<StateWrapper<State>>()
    val state: LiveData<StateWrapper<State>> = _state

    private fun setState(state: State) {
        _state.value = StateWrapper(state)
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.OnCreate -> onCreate()
        }
    }

    private fun onCreate() = launch {
    }
}
