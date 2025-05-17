package com.paopeye.devshowcase.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.paopeye.devshowcase.MainActivity
import com.paopeye.devshowcase.ToolbarListener
import com.paopeye.kit.extension.emptyLong
import com.paopeye.kit.extension.emptyString
import java.util.concurrent.TimeUnit

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    abstract fun isUseToolbar(): Boolean
    abstract fun isUseLeftImageToolbar(): Boolean
    abstract fun setupView()
    abstract fun subscribeState()
    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    open fun toolbarTitle(): String = emptyString()
    open fun toolbarLeftClickListener(): (() -> Unit) = {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private lateinit var container: ViewGroup
    private lateinit var toolbarListener: ToolbarListener
    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding ?: throw IllegalStateException(
            "Fragment ${this.javaClass.name} Binding Error"
        )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is MainActivity) {
            throw IllegalArgumentException(
                "Fragment ${this.javaClass.name} Illegal Error"
            )
        }
        toolbarListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.let { this.container = it }
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        subscribeState()
    }

    override fun onResume() {
        super.onResume()
        toolbarListener.setTitleToolbar(toolbarTitle())
        toolbarListener.setVisibilityToolbar(isUseToolbar())
        toolbarListener.setVisibilityLeftImageButton(isUseLeftImageToolbar())
        toolbarListener.setListenerLeftImageButton(toolbarLeftClickListener())
        toolbarListener.setBackgroundStatusBar(Color.TRANSPARENT)
        toolbarListener.setBackgroundToolbar(Color.TRANSPARENT)
    }

    protected fun replaceFragment(fragment: Fragment) {
        (activity as MainActivity).replaceFragment(fragment)
    }

    protected fun updateToolbar(
        title: String,
        isLeftImageVisible: Boolean,
        toolbarBackgroundColor: Int?,
        statusBarBackgroundColor: Int?
    ) {
        toolbarListener.setTitleToolbar(title)
        toolbarListener.setVisibilityLeftImageButton(isLeftImageVisible)
        toolbarListener.setBackgroundStatusBar(statusBarBackgroundColor)
        toolbarListener.setBackgroundToolbar(toolbarBackgroundColor)
    }

    protected fun showLoading() {
        (activity as MainActivity).loadingVisibility(true)
    }

    protected fun hideLoading() {
        (activity as MainActivity).loadingVisibility(false)
    }
}
