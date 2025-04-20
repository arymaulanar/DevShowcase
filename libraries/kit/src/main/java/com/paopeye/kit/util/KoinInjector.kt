package com.paopeye.kit.util

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinInjector : KoinComponent {
    inline fun <reified T> getInjectedObject(): T {
        val obj: T by inject()
        return obj
    }
}
