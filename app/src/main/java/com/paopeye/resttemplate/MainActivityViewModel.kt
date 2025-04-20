package com.paopeye.resttemplate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.model.User
import com.paopeye.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


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
        val request = User("001")
        val resultData = getUserUseCase.invoke(request)
        if (resultData is DataState.ERROR) {
            setState(State.ShowUserData(User("ERROR", "ERROR_NAME")))
            return@launch
        }
        setState(State.ShowUserData(resultData.data ?: User()))
    }
}
