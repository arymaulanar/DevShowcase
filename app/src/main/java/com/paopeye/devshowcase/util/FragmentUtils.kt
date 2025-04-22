package com.paopeye.devshowcase.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object FragmentUtils {

    fun replaceFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        frameId: Int,
        addToBackStack: Boolean = false,
        tag: String? = null
    ) {
        val transaction = fragmentManager.beginTransaction()
            .replace(frameId, fragment, tag)

        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }

        transaction.commit()
    }
}
