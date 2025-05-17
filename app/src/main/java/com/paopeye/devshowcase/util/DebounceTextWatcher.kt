package com.paopeye.devshowcase.util

import android.text.Editable
import android.text.TextWatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DebounceTextWatcher(
    private val delayMillis: Long,
    private val onDebouncedTextChanged: (String) -> Unit,
    private val onTextChanged: (String) -> Unit
) : TextWatcher {
    private var debounceJob: Job? = null
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(editable: Editable?) {
        onTextChanged.invoke(editable.toString())
        debounceJob?.cancel()
        debounceJob = CoroutineScope(Dispatchers.Main).launch {
            delay(delayMillis)
            editable?.let { onDebouncedTextChanged(it.toString()) }
        }
    }

    fun detach() {
        debounceJob?.cancel()
    }
}

