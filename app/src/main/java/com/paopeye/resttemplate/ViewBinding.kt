package com.paopeye.resttemplate

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
    val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
    private var binding: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val bindingValue = viewBindingFactory(thisRef.requireView())
        setValue(thisRef, bindingValue)
        return binding ?: bindingValue
    }

    private fun setValue(fragment: Fragment, value: T) {
        fragment.lifecycle.removeObserver(this)
        this.binding = value
        fragment.lifecycle.addObserver(this)
    }
}

fun <T : ViewBinding> viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(viewBindingFactory)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) { bindingInflater.invoke(layoutInflater) }
