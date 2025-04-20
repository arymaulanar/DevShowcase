package com.paopeye.kit.extension

inline fun silence(body: () -> Unit) {
    try {
        body()
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}

inline fun <R> silenceWithResult(default: R, body: () -> R): R {
    return try {
        body()
    } catch (t: Throwable) {
        t.printStackTrace()
        default
    }
}
