package com.paopeye.kit.util

fun <T> catchOrDefault(default: T, block: () -> T): T {
    return try {
        block()
    } catch (e: Throwable) {
        default
    }
}
