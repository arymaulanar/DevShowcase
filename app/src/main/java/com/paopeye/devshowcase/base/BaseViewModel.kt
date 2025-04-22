package com.paopeye.devshowcase.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel: ViewModel(), CoroutineScope {
    private val viewModelJob = SupervisorJob()
    protected open val defaultDispatcher: CoroutineContext = Dispatchers.Main
    override val coroutineContext: CoroutineContext =
        defaultDispatcher + viewModelJob + CoroutineExceptionHandler { _, throwable ->
            handleCoroutineError(throwable)
        }

    protected fun launch(
        context: CoroutineContext = defaultDispatcher,
        block: suspend CoroutineScope.() -> Unit
    ): Job = launch(context + coroutineContext, block)

    protected suspend fun <T> io(block: suspend CoroutineScope.() -> T): T {
        return withContext(Dispatchers.IO) { block() }
    }

    protected suspend fun <T> compute(block: suspend CoroutineScope.() -> T): T {
        return withContext(Dispatchers.Default) { block() }
    }

    protected open fun handleCoroutineError(throwable: Throwable) {
        throwable.printStackTrace()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
