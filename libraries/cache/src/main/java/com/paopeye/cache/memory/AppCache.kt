package com.paopeye.cache.memory

class AppCache: Cache {
    private val cache = HashMap<String, Any>()

    override val size: Int
        get() = cache.size

    override fun set(key: String, value: Any) {
        cache[key] = value
    }

    override fun remove(key: String) = cache.remove(key)

    override fun get(key: String) = cache[key]

    override fun clear() = cache.clear()
}
