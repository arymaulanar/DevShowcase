package com.paopeye.devshowcase.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paopeye.devshowcase.MainActivity
import com.paopeye.kit.extension.emptyString

abstract class BaseFragment : Fragment() {

    abstract fun setupView(view: View)
    abstract fun isUseToolbar(): Boolean
    open fun toolbarTitle(): String = emptyString()
    open fun toolbarLeftClickListener(): (() -> Unit)? = null
    abstract fun getLayoutRes(): Int
    abstract fun subscribeState()
    private lateinit var container: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.let { this.container = it }
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        subscribeState()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        if (!isUseToolbar()) return
        (activity as MainActivity).setupToolbar(
            toolbarTitle(),
            toolbarLeftClickListener()
        )
    }

    protected fun updateToolbar(
        title: String,
        isLeftImageVisible: Boolean,
        backgroundColor: Int?
    ) {
        (activity as MainActivity).updateToolbar(title, isLeftImageVisible, backgroundColor)
    }

    protected fun showLoading() {
        (activity as MainActivity).loadingVisibility(true)
    }

    protected fun hideLoading() {
        (activity as MainActivity).loadingVisibility(false)
    }
}
