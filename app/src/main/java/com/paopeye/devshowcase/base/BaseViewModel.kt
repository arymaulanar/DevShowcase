package com.paopeye.devshowcase.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}

/** Key Takeaways:
Scenario <>	Recommended Dispatcher
UI Updates <> Dispatchers.Main
CPU-heavy work (sorting, math, algorithms) <> Dispatchers.Default
Network/disk I/O (Retrofit, Room DB, files) <> Dispatchers.IO
Parent-child coroutine hierarchy <> Inherits parent's dispatcher
Best Practices:
- Never block Dispatchers.Main (Android will freeze or crash).
- Use withContext to switch dispatchers when needed.
- Avoid GlobalScope (use structured concurrency with viewModelScope/lifecycleScope in Android).
 **/
