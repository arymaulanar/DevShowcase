package com.paopeye.devshowcase.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.paopeye.kit.extension.emptyInt

object FragmentUtils {

    fun replaceFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        frameId: Int,
        addToBackStack: Boolean = true,
        sharedElement: View? = null,
        sharedElementName: String? = null,
        tag: String? = null
    ) {
        val transaction = fragmentManager.beginTransaction()
            .replace(frameId, fragment, tag)

        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        if (sharedElement != null) {
            transaction.addSharedElement(
                sharedElement,
                sharedElementName.orEmpty()
            )
        }
        transaction.commit()
    }

    fun addFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        frameId: Int,
        addToBackStack: Boolean = false,
        tag: String? = null
    ) {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment, tag)
        if (fragmentManager.backStackEntryCount > emptyInt()) {
            transaction.hide(fragmentManager.fragments[fragmentManager.backStackEntryCount - 1])
        }

        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }

        transaction.commit()
    }
}
