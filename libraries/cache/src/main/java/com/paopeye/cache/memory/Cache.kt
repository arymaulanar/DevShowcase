package com.paopeye.cache.memory

interface Cache {
    val size: Int

    operator fun set(key: String, value: Any)
    operator fun get(key: String): Any?
    fun remove(key: String): Any?
    fun clear()
}
