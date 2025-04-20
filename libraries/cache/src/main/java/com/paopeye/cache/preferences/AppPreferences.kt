package com.paopeye.cache.preferences

import android.content.SharedPreferences
import android.util.Base64
import com.paopeye.kit.extension.emptyInt
import com.paopeye.kit.extension.emptyString

class AppPreferences(
    sharedPreferences: SharedPreferences
) : SharedPreferences by sharedPreferences {

    fun getInt(key: String) = getInt(key, emptyInt())
    fun getString(key: String) = getString(key, emptyString())

    fun remove(key: String) {
        edit().remove(key).apply()
    }

    fun put(key: String, value: Any) {
        if (value is String) return putString(key, value)
        if (value is Int) return putInt(key, value)
        if (value is Boolean) return putBoolean(key, value)
        if (value is ByteArray) return putByteArray(key, value)
        putString(key, value.toString())
    }

    fun getByteArray(key: String): ByteArray {
        val encodedValue = getString(key, emptyString())
        return Base64.decode(encodedValue, Base64.DEFAULT)
    }

    fun clear() {
        edit().clear().apply()
    }

    private fun putString(key: String, value: String) {
        edit().putString(key, value).apply()
    }

    private fun putInt(key: String, value: Int) {
        edit().putInt(key, value).apply()
    }

    private fun putBoolean(key: String, value: Boolean) {
        edit().putBoolean(key, value).apply()
    }

    private fun putByteArray(key: String, value: ByteArray) {
        val stringValue = Base64.encodeToString(value, Base64.DEFAULT)
        edit().putString(key, stringValue).apply()
    }
}
