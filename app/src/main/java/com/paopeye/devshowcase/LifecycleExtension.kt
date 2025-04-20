package com.paopeye.devshowcase

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

inline fun <T> LifecycleOwner.subscribeSingleState(
    liveData: LiveData<StateWrapper<T>>,
    crossinline onEventUnhandled: (T) -> Unit
) {
    liveData.observe(this) { it?.getEventIfNotHandled()?.let(onEventUnhandled) }
}
