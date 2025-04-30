package com.paopeye.kit.extension

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.BundleCompat
import java.io.Serializable

fun <T : Serializable> Bundle.serializable(name: String, clazz: Class<T>): T? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        return getSerializable(name, clazz)
    }
    @Suppress("DEPRECATION", "UNCHECKED_CAST")
    return getSerializable(name) as T?
}

fun <T : Parcelable> Bundle.parcelable(name: String, clazz: Class<T>): T? {
    return BundleCompat.getParcelable(this, name, clazz)
}

fun <T : Parcelable> Bundle.parcelableArrayList(name: String, clazz: Class<T>): ArrayList<T>? {
    return BundleCompat.getParcelableArrayList(this, name, clazz)
}
