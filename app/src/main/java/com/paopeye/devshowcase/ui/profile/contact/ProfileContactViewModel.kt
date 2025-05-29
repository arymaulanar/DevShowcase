package com.paopeye.devshowcase.ui.profile.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paopeye.devshowcase.base.BaseViewModel
import com.paopeye.devshowcase.util.StateWrapper
import com.paopeye.kit.constant.Urls

class ProfileContactViewModel : BaseViewModel() {
    private val _state = MutableLiveData<StateWrapper<State>>()
    val state: LiveData<StateWrapper<State>> = _state

    sealed class Event {
        data object OnClickLinkedinCard : Event()
        data class OnClickSendEmail(val subject: String, val message: String) : Event()
    }

    sealed class State {
        data class NavigateToMailApp(
            val email: String,
            val subject: String,
            val message: String
        ) : State()

        data class NavigateToExternalBrowser(val url: String) : State()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.OnClickLinkedinCard -> onClickLinkedinCard()
            is Event.OnClickSendEmail -> onClickSendEmail(event.subject, event.message)
        }
    }

    private fun onClickSendEmail(subject: String, message: String) {
        setState(State.NavigateToMailApp(Urls.MY_EMAIL, subject, message))
    }

    private fun onClickLinkedinCard() {
        setState(State.NavigateToExternalBrowser(Urls.MY_LINKEDIN))
    }

    private fun setState(state: State) {
        _state.value = StateWrapper(state)
    }
}
