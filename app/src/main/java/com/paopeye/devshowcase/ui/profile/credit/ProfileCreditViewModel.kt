package com.paopeye.devshowcase.ui.profile.credit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paopeye.devshowcase.base.BaseViewModel
import com.paopeye.devshowcase.enums.ApiProviderType
import com.paopeye.devshowcase.util.StateWrapper
import com.paopeye.domain.model.Copyright

class ProfileCreditViewModel : BaseViewModel() {
    private val _state = MutableLiveData<StateWrapper<State>>()
    val state: LiveData<StateWrapper<State>> = _state

    sealed class Event {
        data object OnCreate : Event()
        data class OnClickCopyright(val url: String) : Event()
    }

    sealed class State {
        data class ShowCopyrights(val copyrights: List<Copyright>) : State()
        data class NavigateToExternalBrowser(val url: String) : State()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.OnCreate -> onCreate()
            is Event.OnClickCopyright -> onClickCopyright(event.url)
        }
    }

    private fun onClickCopyright(url: String) {
        var updatedUrl = url
        if (!updatedUrl.startsWith("http://") && !updatedUrl.startsWith("https://")) {
            updatedUrl = "http://$url"
        }
        setState(State.NavigateToExternalBrowser(updatedUrl))
    }

    private fun onCreate() {
        val apiProviders = ApiProviderType.entries
        val copyrights = apiProviders.map {
            Copyright(
                titleResource = it.getTitleResource(),
                subtitleResource = it.getSubtitleResource(),
                logo = it.getLogoResource(),
                url = it.getWebsiteString()
            )
        }
        setState(State.ShowCopyrights(copyrights))
    }

    private fun setState(state: State) {
        _state.value = StateWrapper(state)
    }
}
