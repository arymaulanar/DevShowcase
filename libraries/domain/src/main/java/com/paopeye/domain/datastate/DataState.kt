package com.paopeye.domain.datastate

sealed class DataState<T>(
    var loading: Boolean,
    var data: T? = null,
    var stateMessage: StateMessage? = null
) {
    class SUCCESS<T>(
        data: T? = null,
        stateMessage: StateMessage? = null
    ) : DataState<T>(
        loading = false,
        data = data,
        stateMessage = stateMessage
    )

    class ERROR<T>(
        stateMessage: StateMessage
    ) : DataState<T>(
        loading = false,
        data = null,
        stateMessage = stateMessage
    )
}
